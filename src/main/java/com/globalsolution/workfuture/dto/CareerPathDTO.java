package com.globalsolution.workfuture.dto;

import com.globalsolution.workfuture.model.CareerPath;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.globalsolution.workfuture.model.Skill;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerPathDTO {
    
    private Long id;
    
    @NotBlank(message = "{validation.career.title.required}")
    @Size(max = 100, message = "{validation.career.title.size}")
    private String title;
    
    @Size(max = 1000, message = "{validation.career.description.size}")
    private String description;
    
    @NotNull(message = "{validation.career.type.required}")
    private CareerPath.CareerType type;
    
    private Integer estimatedYears;
    private Double averageSalary;
    private Integer jobGrowth;
    private Boolean futureCareer;
    
    private Set<Long> requiredSkillIds;
    
    public CareerPathDTO(CareerPath careerPath) {
        this.id = careerPath.getId();
        this.title = careerPath.getTitle();
        this.description = careerPath.getDescription();
        this.type = careerPath.getType();
        this.estimatedYears = careerPath.getEstimatedYears();
        this.averageSalary = careerPath.getAverageSalary();
        this.jobGrowth = careerPath.getJobGrowth();
        this.futureCareer = careerPath.getFutureCareer();
        this.requiredSkillIds = careerPath.getRequiredSkills() != null ?
            careerPath.getRequiredSkills().stream()
                .map(Skill::getId)
                .collect(Collectors.toSet()) : null;
    }
}

