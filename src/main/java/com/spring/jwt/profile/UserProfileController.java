package com.spring.jwt.profile;

import com.spring.jwt.dto.ResponseDto;
<<<<<<< HEAD
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
=======
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
>>>>>>> 958f3652905e138112b0a552cd9eb6e947182df2
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-profile")
@RequiredArgsConstructor
<<<<<<< HEAD
@Validated
@Slf4j
@Tag(name = "User profile management Api", description = "Apis for user profile management")
=======
>>>>>>> 958f3652905e138112b0a552cd9eb6e947182df2
public class UserProfileController {

    private final UserProfileService userProfileService;

    // CREATE
<<<<<<< HEAD
    @Operation(summary = "Create user personal information", description = "saving personal details of an user")
    @PostMapping
    public ResponseEntity<ResponseDto<UserProfileDTO2>> create(@RequestBody UserProfileDTO2 dto) {
=======
    @PostMapping
    public ResponseEntity<ResponseDto<?>> create(@RequestBody UserProfileDTO2 dto) {
>>>>>>> 958f3652905e138112b0a552cd9eb6e947182df2
        try {
            UserProfileDTO2 saved = userProfileService.createUserProfile(dto);
            return ResponseEntity.ok(ResponseDto.success("Profile created successfully", saved));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.error("Failed to create profile", e.getMessage()));
        }
    }

    // GET BY ID
<<<<<<< HEAD
    @Operation(summary = "Get user personal information by ID", description = "Get personal details of an user by its ID")
=======
>>>>>>> 958f3652905e138112b0a552cd9eb6e947182df2
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
<<<<<<< HEAD
    @Operation(summary = "Update user personal information by ID", description = "Update personal details of an user by its ID")
=======
>>>>>>> 958f3652905e138112b0a552cd9eb6e947182df2
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
<<<<<<< HEAD
    @Operation(summary = "Delete user personal information by ID", description = "Delete personal details of an user by its ID")
=======
>>>>>>> 958f3652905e138112b0a552cd9eb6e947182df2
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
<<<<<<< HEAD

    @Operation(summary = "Get user profile information by Gender", description = "Get user profile of an user by its Gender")
=======
>>>>>>> 958f3652905e138112b0a552cd9eb6e947182df2
    @GetMapping("/profiles/gender/{gender}")
    public ResponseEntity<ResponseDto<List<Object>>> getProfilesByGender(@PathVariable String gender) {

        List<Object> data = userProfileService.getProfilesByGender(gender);

        return ResponseEntity.ok(ResponseDto.success("Profiles fetched", data));
    }

}
