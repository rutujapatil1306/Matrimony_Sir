package com.spring.jwt.config.filter;

import com.spring.jwt.jwt.JwtConfig;
import com.spring.jwt.jwt.JwtService;
import com.spring.jwt.jwt.ActiveSessionService;
import com.spring.jwt.service.security.UserDetailsServiceCustom;
import com.spring.jwt.utils.BaseResponseDTO;
import com.spring.jwt.utils.HelperUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private final JwtService jwtService;
    private final UserDetailsServiceCustom userDetailsService;
    private final ActiveSessionService activeSessionService;
    private final int sessionIdleTimeout;

    private static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";

    private boolean setauthreq = true;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        log.debug("JWT Filter processing request for path: {}", path);

        if (path.contains("/api/jwtUnAuthorize/block") || path.contains("/api/jwtUnAuthorize/Exclude")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!setauthreq) {
            handleAccessBlocked(response);
            return;
        }

        // AUTHORIZATION REMOVED: Spring Security's authorizeHttpRequests handles public
        // vs protected URLs
        // This filter now focuses solely on JWT token validation

        String token = getJwtFromRequest(request);

        if (token == null) {
            log.warn("No JWT token found for protected path: {}", path);
            handleAccessDenied(response);
            return;
        }

        try {
            if (!processToken(request, token)) {
                log.warn("Invalid token for path: {}", path);
                String reason = getSpecificInvalidReason(token, request);
                handleInvalidToken(response, reason);
                return;
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT token: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            handleExpiredToken(response);
        } catch (JwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            handleInvalidToken(response, "Invalid token: " + e.getMessage());
        } catch (Exception e) {
            log.error("Authentication error: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            handleAuthenticationException(response, e);
        }
    }

    // REMOVED: isPublic(HttpServletRequest request)
    // Spring Security's authorizeHttpRequests() configuration handles public URL
    // authorization.
    // This filter focuses on JWT token validation only - no need for duplicate
    // authorization logic.

    private String getSpecificInvalidReason(String token, HttpServletRequest request) {
        try {
            if (jwtService.isBlacklisted(token)) {
                return "Token is revoked/blacklisted";
            }
            Claims claims = jwtService.extractClaims(token);
            String tokenDfp = claims.get("dfp", String.class);
            String reqDfp = jwtService.generateDeviceFingerprint(request);
            if (StringUtils.hasText(tokenDfp) && StringUtils.hasText(reqDfp) && !tokenDfp.equals(reqDfp)) {
                return "Device mismatch: please login again on this device";
            }
            String username = claims.getSubject();
            String tokenId = claims.getId();
            if (StringUtils.hasText(username) && StringUtils.hasText(tokenId)
                    && !activeSessionService.isCurrentAccessToken(username, tokenId)) {
                return "Session expired or active on another device. Please login again.";
            }
            return "Invalid or expired token";
        } catch (ExpiredJwtException e) {
            return "Expired token";
        } catch (com.spring.jwt.exception.BaseException e) {
            if (e.getMessage().contains("expiration")) {
                return "Expired token";
            }
            return e.getMessage();
        } catch (JwtException e) {
            return "Malformed or invalid token";
        } catch (Exception e) {
            return "Unauthorized";
        }
    }

    /**
     * Process the JWT token and set authentication if valid
     * 
     * @return true if token is valid and authentication was set, false otherwise
     */
    private boolean processToken(HttpServletRequest request, String token) {
        String deviceFingerprint = jwtService.generateDeviceFingerprint(request);
        if (jwtService.isValidToken(token, deviceFingerprint)) {
            Claims claims = jwtService.extractClaims(token);
            String username = claims.getSubject();

            if (jwtService.isRefreshToken(token)) {
                log.warn("Refresh token used for API access - not allowed");
                return false;
            }

            if (!ObjectUtils.isEmpty(username)) {
                log.debug("Valid token found for user: {}", username);

                List<String> authorities = claims.get("authorities", List.class);
                if (authorities == null) {
                    authorities = claims.get("roles", List.class);
                }

                if (authorities != null) {
                    try {
                        // Load full user details instead of just using username string
                        var userDetails = userDetailsService.loadUserByUsername(username);

                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                userDetails, // Use UserDetailsCustom object instead of String
                                null,
                                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

                        SecurityContextHolder.getContext().setAuthentication(auth);

                        // If token is not the current session, fail fast with specific message
                        try {
                            String tokenId = claims.getId();
                            if (!activeSessionService.isCurrentAccessToken(username, tokenId)) {
                                log.warn("Token not current for user: {}", username);
                                return false;
                            }
                        } catch (Exception ignored) {
                        }

                        log.debug("Authentication set in security context for user: {}", username);
                        return true;

                    } catch (Exception e) {
                        log.warn("Failed to load user details for username: {} - {}", username, e.getMessage());
                        return false;
                    }
                } else {
                    log.warn("No authorities found in token for user: {}", username);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Extract JWT token from request (header or cookie)
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtConfig.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtConfig.getPrefix() + " ")) {
            log.debug("Found token in Authorization header");
            return bearerToken.substring((jwtConfig.getPrefix() + " ").length());
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Optional<Cookie> accessTokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> ACCESS_TOKEN_COOKIE_NAME.equals(cookie.getName()))
                    .findFirst();

            if (accessTokenCookie.isPresent()) {
                log.debug("Found access token in cookie");
                return accessTokenCookie.get().getValue();
            }
        }
        return null;
    }

    private void handleAccessBlocked(HttpServletResponse response) throws IOException {
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        responseDTO.setCode(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()));
        responseDTO.setMessage("d7324asdx8hg");

        String json = HelperUtils.JSON_WRITER.writeValueAsString(responseDTO);

        response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(json);
    }

    private void handleAccessDenied(HttpServletResponse response) throws IOException {
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        responseDTO.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        responseDTO.setMessage("Access denied: Authentication required");

        String json = HelperUtils.JSON_WRITER.writeValueAsString(responseDTO);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(json);
    }

    private void handleInvalidToken(HttpServletResponse response, String message) throws IOException {
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        responseDTO.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        responseDTO.setMessage(message);

        String json = HelperUtils.JSON_WRITER.writeValueAsString(responseDTO);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(json);
    }

    private void handleExpiredToken(HttpServletResponse response) throws IOException {
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        responseDTO.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        responseDTO.setMessage("Expired token");

        String json = HelperUtils.JSON_WRITER.writeValueAsString(responseDTO);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(json);
    }

    private void handleAuthenticationException(HttpServletResponse response, Exception e) throws IOException {
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        responseDTO.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        responseDTO.setMessage("Authentication failed: " + e.getMessage());

        String json = HelperUtils.JSON_WRITER.writeValueAsString(responseDTO);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(json);
    }

    public void setauthreq(boolean setauthreq) {
        this.setauthreq = setauthreq;
    }
}
