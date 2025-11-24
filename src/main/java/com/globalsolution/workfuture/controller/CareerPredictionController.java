package com.globalsolution.workfuture.controller;

import com.globalsolution.workfuture.dto.ApiResponse;
import com.globalsolution.workfuture.dto.CareerPredictionDTO;
import com.globalsolution.workfuture.model.CareerPrediction;
import com.globalsolution.workfuture.service.CareerPredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/predictions")
@RequiredArgsConstructor
public class CareerPredictionController {
    
    private final CareerPredictionService predictionService;
    
    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<CareerPredictionDTO>> generatePrediction(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long careerPathId,
            @RequestParam CareerPrediction.PredictionType type) {
        CompletableFuture<CareerPredictionDTO> future = 
            CompletableFuture.supplyAsync(() -> predictionService.generatePrediction(userId, careerPathId, type));
        
        try {
            CareerPredictionDTO prediction = future.get();
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Prediction generated successfully", prediction));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error generating prediction: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CareerPredictionDTO>> getPredictionById(@PathVariable Long id) {
        CareerPredictionDTO prediction = predictionService.getPredictionById(id);
        return ResponseEntity.ok(ApiResponse.success(prediction));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Page<CareerPredictionDTO>>> getPredictionsByUser(
            @PathVariable Long userId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<CareerPredictionDTO> predictions = predictionService.getPredictionsByUser(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success(predictions));
    }
    
    @GetMapping("/range")
    public ResponseEntity<ApiResponse<List<CareerPredictionDTO>>> getPredictionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<CareerPredictionDTO> predictions = predictionService.getPredictionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(predictions));
    }
}

