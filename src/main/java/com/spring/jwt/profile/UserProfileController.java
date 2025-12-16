package com.spring.jwt.profile;

import com.spring.jwt.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    // CREATE
    @PostMapping
    public ResponseEntity<ResponseDto<?>> create(@RequestBody UserProfileDTO2 dto) {
        try {
            UserProfileDTO2 saved = userProfileService.createUserProfile(dto);
            return ResponseEntity.ok(ResponseDto.success("Profile created successfully", saved));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.error("Failed to create profile", e.getMessage()));
        }
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> getById(@PathVariable Integer id) {
        try {
            UserProfileDTO2 profile = userProfileService.getUserProfileById(id);
            return ResponseEntity.ok(ResponseDto.success("Profile fetched successfully", profile));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.error("Failed to fetch profile", e.getMessage()));
        }
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<ResponseDto<?>> getAll() {
        try {
            return ResponseEntity.ok(
                    ResponseDto.success("Profiles fetched successfully", userProfileService.getAllUserProfiles())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.error("Failed to fetch profiles", e.getMessage()));
        }
    }

    // PATCH UPDATE (Partial Update)
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> update(@PathVariable Integer id,
                                                 @RequestBody UserProfileDTO2 dto) {
        try {
            UserProfileDTO2 updated = userProfileService.updateUserProfile(id, dto);
            return ResponseEntity.ok(ResponseDto.success("Profile updated successfully", updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.error("Failed to update profile", e.getMessage()));
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> delete(@PathVariable Integer id) {
        try {
            userProfileService.deleteUserProfile(id);
            return ResponseEntity.ok(ResponseDto.success("Profile deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.error("Failed to delete profile", e.getMessage()));
        }
    }
    @GetMapping("/profiles/gender/{gender}")
    public ResponseEntity<ResponseDto<List<Object>>> getProfilesByGender(@PathVariable String gender) {

        List<Object> data = userProfileService.getProfilesByGender(gender);

        return ResponseEntity.ok(ResponseDto.success("Profiles fetched", data));
    }

}
