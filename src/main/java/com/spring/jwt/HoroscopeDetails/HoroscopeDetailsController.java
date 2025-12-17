package com.spring.jwt.HoroscopeDetails;

import com.spring.jwt.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/horoscope")
@RequiredArgsConstructor
public class HoroscopeDetailsController {

    private final HoroscopeDetailsService horoscopeService;

    @Operation(summary = "Create horoscope", description = "saving horoscope details of an user")
    @PostMapping("/add")
    public ResponseEntity<ResponseDto<?>> create(@Valid @RequestBody HoroscopeDetailsDTO dto) {
        try {
            return ResponseEntity.ok(ResponseDto.success("Created successfully",
                    horoscopeService.create(dto)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseDto.error("Failed to create", e.getMessage()));
        }
    }

    @Operation(summary = "Get horoscope by ID", description = "Retrieve a horoscope by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> getById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(ResponseDto.success("Fetched successfully",
                    horoscopeService.getById(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseDto.error("Failed to fetch", e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<?>> getAll() {
        try {
            return ResponseEntity.ok(ResponseDto.success("Fetched successfully",
                    horoscopeService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseDto.error("Failed to fetch", e.getMessage()));
        }
    }

    @Operation(summary = "Update horoscope by ID", description = "Update horoscope by its ID")
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> update(@Valid @PathVariable Integer id,
                                                 @RequestBody HoroscopeDetailsDTO dto) {
        try {
            return ResponseEntity.ok(ResponseDto.success("Updated successfully",
                    horoscopeService.update(id, dto)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseDto.error("Failed to update", e.getMessage()));
        }
    }

    @Operation(summary = "Delete a horoscope by ID", description = "Delete a horoscope by its ID for the current user")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> delete(@PathVariable Integer id) {
        try {
            horoscopeService.delete(id);
            return ResponseEntity.ok(ResponseDto.success("Deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseDto.error("Failed to delete", e.getMessage()));
        }
    }
}
