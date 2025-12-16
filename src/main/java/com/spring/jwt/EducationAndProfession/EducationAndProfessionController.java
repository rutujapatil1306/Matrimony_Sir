package com.spring.jwt.EducationAndProfession;

import com.spring.jwt.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/education")
@RequiredArgsConstructor
public class EducationAndProfessionController {

    private final EducationAndProfessionService educationService;

    @PostMapping("add")
    public ResponseEntity<ResponseDto<?>> create(@RequestBody EducationAndProfessionDTO dto) {
        try {
            EducationAndProfessionDTO saved = educationService.create(dto);
            return ResponseEntity.ok(ResponseDto.success("Created successfully", saved));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.error("Failed to create", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> getById(@PathVariable Integer id) {
        try {
            EducationAndProfessionDTO dto = educationService.getById(id);
            return ResponseEntity.ok(ResponseDto.success("Fetched successfully", dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.error("Failed to fetch", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseDto<?>> getAll() {
        try {
            List<EducationAndProfessionDTO> list = educationService.getAll();
            return ResponseEntity.ok(ResponseDto.success("Fetched successfully", list));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.error("Failed to fetch", e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> update(@PathVariable Integer id, @RequestBody EducationAndProfessionDTO dto) {
        try {
            EducationAndProfessionDTO updated = educationService.update(id, dto);
            return ResponseEntity.ok(ResponseDto.success("Updated successfully", updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.error("Failed to update", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> delete(@PathVariable Integer id) {
        try {
            educationService.delete(id);
            return ResponseEntity.ok(ResponseDto.success("Deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.error("Failed to delete", e.getMessage()));
        }
    }
}
