package com.globalsolution.workfuture.controller;

import com.globalsolution.workfuture.dto.ApiResponse;
import com.globalsolution.workfuture.dto.WellbeingDTO;
import com.globalsolution.workfuture.service.WellbeingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/wellbeing")
@RequiredArgsConstructor
public class WellbeingController {
    
    private final WellbeingService wellbeingService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<WellbeingDTO>> createWellbeing(
            @Valid @RequestBody WellbeingDTO wellbeingDTO) {
        WellbeingDTO created = wellbeingService.createWellbeing(wellbeingDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Wellbeing record created successfully", created));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Page<WellbeingDTO>>> getWellbeingByUser(
            @PathVariable Long userId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<WellbeingDTO> wellbeing = wellbeingService.getWellbeingByUser(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success(wellbeing));
    }
    
    @GetMapping("/user/{userId}/average-mental-health")
    public ResponseEntity<ApiResponse<Double>> getAverageMentalHealthScore(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        Double average = wellbeingService.getAverageMentalHealthScore(userId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(average != null ? average : 0.0));
    }
}

