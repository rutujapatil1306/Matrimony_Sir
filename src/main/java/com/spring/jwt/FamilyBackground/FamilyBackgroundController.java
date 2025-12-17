package com.spring.jwt.FamilyBackground;

import com.spring.jwt.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/family")
@RequiredArgsConstructor
public class FamilyBackgroundController {

    private final FamilyBackgroundService service;

    @PostMapping("add")
    public ResponseEntity<ResponseDto<?>> create(@RequestBody FamilyBackgroundDTO dto) {
        try {
            return ResponseEntity.ok(ResponseDto.success("Created", service.create(dto)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.error("Failed to create", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> getById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(ResponseDto.success("Fetched", service.getById(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.error("Failed to fetch", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseDto<?>> getAll() {
        try {
            return ResponseEntity.ok(ResponseDto.success("Fetched", service.getAll()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.error("Failed to fetch", e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> update(@PathVariable Integer id, @RequestBody FamilyBackgroundDTO dto) {
        try {
            return ResponseEntity.ok(ResponseDto.success("Updated", service.update(id, dto)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.error("Failed to update", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> delete(@PathVariable Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(ResponseDto.success("Deleted", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.error("Failed to delete", e.getMessage()));
        }
    }
}
