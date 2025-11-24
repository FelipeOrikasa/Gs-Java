package com.globalsolution.workfuture.controller;

import com.globalsolution.workfuture.dto.ApiResponse;
import com.globalsolution.workfuture.dto.CareerPathDTO;
import com.globalsolution.workfuture.service.CareerPathService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/career-paths")
@RequiredArgsConstructor
public class CareerPathController {
    
    private final CareerPathService careerPathService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<CareerPathDTO>> createCareerPath(
            @Valid @RequestBody CareerPathDTO careerPathDTO) {
        CareerPathDTO created = careerPathService.createCareerPath(careerPathDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Career path created successfully", created));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CareerPathDTO>>> getAllCareerPaths(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<CareerPathDTO> careerPaths = careerPathService.getAllCareerPaths(pageable);
        return ResponseEntity.ok(ApiResponse.success(careerPaths));
    }
    
    @GetMapping("/future")
    public ResponseEntity<ApiResponse<Page<CareerPathDTO>>> getFutureCareers(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<CareerPathDTO> careerPaths = careerPathService.getFutureCareers(pageable);
        return ResponseEntity.ok(ApiResponse.success(careerPaths));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CareerPathDTO>> getCareerPathById(@PathVariable Long id) {
        CareerPathDTO careerPath = careerPathService.getCareerPathById(id);
        return ResponseEntity.ok(ApiResponse.success(careerPath));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CareerPathDTO>> updateCareerPath(
            @PathVariable Long id,
            @Valid @RequestBody CareerPathDTO careerPathDTO) {
        CareerPathDTO updated = careerPathService.updateCareerPath(id, careerPathDTO);
        return ResponseEntity.ok(ApiResponse.success("Career path updated successfully", updated));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCareerPath(@PathVariable Long id) {
        careerPathService.deleteCareerPath(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(ApiResponse.success("Career path deleted successfully", null));
    }
}

