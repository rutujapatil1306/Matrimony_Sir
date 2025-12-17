package com.spring.jwt.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.jwt.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler
{

    private final ObjectMapper objectMapper;

    /**
     * Handle authentication failures (missing or invalid JWT token)
     * This is called when a user tries to access a secured endpoint without proper authentication
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException
    {

        log.warn("Authentication failed for request: {} {} - {}",
                request.getMethod(), request.getRequestURI(), authException.getMessage());

        ErrorResponseDTO error = createAuthenticationError(request, authException);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }

    /**
     * Handle authorization failures (valid token but insufficient permissions)
     * This is called when an authenticated user tries to access a resource they don't have permission for
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException
    {

        log.warn("Access denied for request: {} {} - {}",
                request.getMethod(), request.getRequestURI(), accessDeniedException.getMessage());

        ErrorResponseDTO error = ErrorResponseDTO.accessDenied(
                request.getRequestURI(),
                request.getMethod(),
                "this resource"
        );

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }

    /**
     * Create detailed authentication error response based on the type of authentication failure
     */
    private ErrorResponseDTO createAuthenticationError(HttpServletRequest request, AuthenticationException ex)
    {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || authHeader.trim().isEmpty())
        {
            return ErrorResponseDTO.builder()
                    .status(401)
                    .errorCode("MISSING_AUTHORIZATION_HEADER")
                    .message("Authorization header is required")
                    .details("Please include the Authorization header with a valid JWT token")
                    .path(request.getRequestURI())
                    .method(request.getMethod())
                    .timestamp(java.time.LocalDateTime.now())
                    .suggestedActions(java.util.List.of(
                            "Add 'Authorization: Bearer <your-jwt-token>' header to your request",
                            "Ensure you have logged in and obtained a valid JWT token",
                            "Check that the header name is 'Authorization' (case-sensitive)"
                    ))
                    .additionalInfo(java.util.Map.of(
                            "expectedHeaderFormat", "Authorization: Bearer <jwt-token>",
                            "loginEndpoint", "/api/v1/auth/login"
                    ))
                    .build();
        } else if (!authHeader.startsWith("Bearer "))
        {
            return ErrorResponseDTO.builder()
                    .status(401)
                    .errorCode("INVALID_AUTHORIZATION_FORMAT")
                    .message("Invalid Authorization header format")
                    .details("Authorization header must use Bearer token format")
                    .path(request.getRequestURI())
                    .method(request.getMethod())
                    .timestamp(java.time.LocalDateTime.now())
                    .suggestedActions(java.util.List.of(
                            "Use format: 'Authorization: Bearer <your-jwt-token>'",
                            "Ensure there's a space after 'Bearer'",
                            "Remove any extra characters or quotes around the token"
                    ))
                    .additionalInfo(java.util.Map.of(
                            "currentFormat", authHeader.length() > 50 ? authHeader.substring(0, 50) + "..." : authHeader,
                            "expectedFormat", "Bearer <jwt-token>"
                    ))
                    .build();
        } else
        {
            String errorDetails = determineJwtErrorDetails(ex.getMessage());

            return ErrorResponseDTO.builder()
                    .status(401)
                    .errorCode("INVALID_JWT_TOKEN")
                    .message("Invalid or expired JWT token")
                    .details(errorDetails)
                    .path(request.getRequestURI())
                    .method(request.getMethod())
                    .timestamp(java.time.LocalDateTime.now())
                    .suggestedActions(java.util.List.of(
                            "Login again to get a fresh JWT token",
                            "Check if your token has expired",
                            "Verify the token is correctly copied without extra spaces",
                            "Ensure you're using the token from the latest login"
                    ))
                    .additionalInfo(java.util.Map.of(
                            "tokenLength", authHeader.length() - 7,
                            "loginEndpoint", "/jwt/login"
                    ))
                    .build();
        }
    }

    /**
     * Determine specific JWT error details based on the exception message
     */
    private String determineJwtErrorDetails(String exceptionMessage)
    {
        if (exceptionMessage == null)
        {
            return "The provided JWT token is invalid";
        }

        String lowerMessage = exceptionMessage.toLowerCase();

        if (lowerMessage.contains("expired"))
        {
            return "Your JWT token has expired. Please login again to get a new token.";
        } else if (lowerMessage.contains("malformed") || lowerMessage.contains("invalid")) {
            return "The JWT token format is invalid. Please ensure you're using a valid token.";
        } else if (lowerMessage.contains("signature"))
        {
            return "JWT token signature verification failed. The token may be tampered with.";
        } else if (lowerMessage.contains("unsupported"))
        {
            return "The JWT token format is not supported by this system.";
        } else
        {
            return "The provided JWT token is invalid or cannot be processed.";
        }
    }
}