package com.globalsolution.workfuture.controller;

import com.globalsolution.workfuture.dto.ApiResponse;
import com.globalsolution.workfuture.dto.TrainingDTO;
import com.globalsolution.workfuture.service.TrainingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainings")
@RequiredArgsConstructor
public class TrainingController {
    
    private final TrainingService trainingService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<TrainingDTO>> createTraining(
            @Valid @RequestBody TrainingDTO trainingDTO) {
        TrainingDTO created = trainingService.createTraining(trainingDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Training created successfully", created));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<Page<TrainingDTO>>> getAllTrainings(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<TrainingDTO> trainings = trainingService.getAllTrainings(pageable);
        return ResponseEntity.ok(ApiResponse.success(trainings));
    }
    
    @GetMapping("/free")
    public ResponseEntity<ApiResponse<Page<TrainingDTO>>> getFreeTrainings(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<TrainingDTO> trainings = trainingService.getFreeTrainings(pageable);
        return ResponseEntity.ok(ApiResponse.success(trainings));
    }
    
    @GetMapping("/skill/{skillId}")
    public ResponseEntity<ApiResponse<Page<TrainingDTO>>> getTrainingsBySkill(
            @PathVariable Long skillId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<TrainingDTO> trainings = trainingService.getTrainingsBySkill(skillId, pageable);
        return ResponseEntity.ok(ApiResponse.success(trainings));
    }
    
    @GetMapping("/career-path/{careerPathId}")
    public ResponseEntity<ApiResponse<Page<TrainingDTO>>> getTrainingsByCareerPath(
            @PathVariable Long careerPathId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<TrainingDTO> trainings = trainingService.getTrainingsByCareerPath(careerPathId, pageable);
        return ResponseEntity.ok(ApiResponse.success(trainings));
    }
}

