package com.spring.jwt.ExpressInterest;

import com.spring.jwt.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interests")
@RequiredArgsConstructor
public class ExpressInterestController {

    private final ExpressInterestService service;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto<?>> create(@RequestBody ExpressInterestDTO dto) {
        try {
            return ResponseEntity.ok(ResponseDto.success("Interest sent", service.create(dto)));
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .body(ResponseDto.error("Failed to send interest", ex.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseDto.success("Found", service.getById(id)));
    }

    @GetMapping("/sent/{userId}")
    public ResponseEntity<ResponseDto<?>> getSent(@PathVariable Integer userId) {
        return ResponseEntity.ok(ResponseDto.success("Sent interests", service.getSent(userId)));
    }

    @GetMapping("/received/{userId}")
    public ResponseEntity<ResponseDto<?>> getReceived(@PathVariable Integer userId) {
        return ResponseEntity.ok(ResponseDto.success("Received interests", service.getReceived1(userId)));
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<ResponseDto<?>> updateStatus(@PathVariable Long id, @PathVariable String status) {
        return ResponseEntity.ok(ResponseDto.success("Status updated", service.updateStatus(id, status)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ResponseDto.success("Deleted", null));
    }

    @GetMapping("/summary/{userId}")
    public ResponseEntity<ResponseDto<InterestCountDTO>> getSummary(@PathVariable Integer userId) {
        try {
            InterestCountDTO dto = service.getInterestSummary(userId);
            return ResponseEntity.ok(ResponseDto.success("Summary fetched", dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.error("Failed to fetch summary", e.getMessage()));
        }
    }
    @GetMapping("/receivedProfiles/{userId}")
    public ResponseEntity<ResponseDto<List<ExpressInterestWithProfileDTO>>> getReceivedInterests(
            @PathVariable Integer userId,
            @RequestParam(required = false, defaultValue = "PENDING") String status) {

        List<ExpressInterestWithProfileDTO> data = service.getReceived(userId, status);

        return ResponseEntity.ok(
                ResponseDto.success("Received interest fetched successfully", data)
        );
    }
}
