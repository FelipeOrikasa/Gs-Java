package com.globalsolution.workfuture.controller;

import com.globalsolution.workfuture.dto.ApiResponse;
import com.globalsolution.workfuture.dto.SkillDTO;
import com.globalsolution.workfuture.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {
    
    private final SkillService skillService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<SkillDTO>> createSkill(@Valid @RequestBody SkillDTO skillDTO) {
        SkillDTO createdSkill = skillService.createSkill(skillDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Skill created successfully", createdSkill));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<Page<SkillDTO>>> getAllSkills(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<SkillDTO> skills = skillService.getAllSkills(pageable);
        return ResponseEntity.ok(ApiResponse.success(skills));
    }
    
    @GetMapping("/in-demand")
    public ResponseEntity<ApiResponse<Page<SkillDTO>>> getInDemandSkills(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<SkillDTO> skills = skillService.getInDemandSkills(pageable);
        return ResponseEntity.ok(ApiResponse.success(skills));
    }
    
    @GetMapping("/future-proof")
    public ResponseEntity<ApiResponse<Page<SkillDTO>>> getFutureProofSkills(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<SkillDTO> skills = skillService.getFutureProofSkills(pageable);
        return ResponseEntity.ok(ApiResponse.success(skills));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillDTO>> getSkillById(@PathVariable Long id) {
        SkillDTO skill = skillService.getSkillById(id);
        return ResponseEntity.ok(ApiResponse.success(skill));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillDTO>> updateSkill(
            @PathVariable Long id,
            @Valid @RequestBody SkillDTO skillDTO) {
        SkillDTO updatedSkill = skillService.updateSkill(id, skillDTO);
        return ResponseEntity.ok(ApiResponse.success("Skill updated successfully", updatedSkill));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(ApiResponse.success("Skill deleted successfully", null));
    }
}

