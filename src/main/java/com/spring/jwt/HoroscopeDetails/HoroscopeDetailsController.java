package com.spring.jwt.HoroscopeDetails;

import com.spring.jwt.dto.ResponseDto;
<<<<<<< HEAD
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
=======
>>>>>>> 958f3652905e138112b0a552cd9eb6e947182df2
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/horoscope")
@RequiredArgsConstructor
public class HoroscopeDetailsController {

    private final HoroscopeDetailsService horoscopeService;

<<<<<<< HEAD
    @Operation(summary = "Create horoscope", description = "saving horoscope details of an user")
    @PostMapping("/add")
    public ResponseEntity<ResponseDto<?>> create(@Valid @RequestBody HoroscopeDetailsDTO dto) {
=======
    @PostMapping("/add")
    public ResponseEntity<ResponseDto<?>> create(@RequestBody HoroscopeDetailsDTO dto) {
>>>>>>> 958f3652905e138112b0a552cd9eb6e947182df2
        try {
            return ResponseEntity.ok(ResponseDto.success("Created successfully",
                    horoscopeService.create(dto)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseDto.error("Failed to create", e.getMessage()));
        }
    }

<<<<<<< HEAD
    @Operation(summary = "Get horoscope by ID", description = "Retrieve a horoscope by its ID")
=======
>>>>>>> 958f3652905e138112b0a552cd9eb6e947182df2
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

<<<<<<< HEAD
    @Operation(summary = "Update horoscope by ID", description = "Update horoscope by its ID")
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> update(@Valid @PathVariable Integer id,
=======
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> update(@PathVariable Integer id,
>>>>>>> 958f3652905e138112b0a552cd9eb6e947182df2
                                                 @RequestBody HoroscopeDetailsDTO dto) {
        try {
            return ResponseEntity.ok(ResponseDto.success("Updated successfully",
                    horoscopeService.update(id, dto)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseDto.error("Failed to update", e.getMessage()));
        }
    }

<<<<<<< HEAD
    @Operation(summary = "Delete a horoscope by ID", description = "Delete a horoscope by its ID for the current user")
=======
>>>>>>> 958f3652905e138112b0a552cd9eb6e947182df2
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
