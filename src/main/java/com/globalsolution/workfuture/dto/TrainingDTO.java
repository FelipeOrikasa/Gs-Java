package com.globalsolution.workfuture.dto;

import com.globalsolution.workfuture.model.Training;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDTO {
    
    private Long id;
    
    @NotBlank(message = "{validation.training.title.required}")
    @Size(max = 200, message = "{validation.training.title.size}")
    private String title;
    
    @Size(max = 2000, message = "{validation.training.description.size}")
    private String description;
    
    @NotNull(message = "{validation.training.type.required}")
    private Training.TrainingType type;
    
    @NotNull(message = "{validation.training.format.required}")
    private Training.TrainingFormat format;
    
    @Positive(message = "{validation.training.duration.positive}")
    private Integer durationHours;
    
    @Size(max = 200, message = "{validation.training.provider.size}")
    private String provider;
    
    private String url;
    
    @Positive(message = "{validation.training.price.positive}")
    private Double price;
    
    private Boolean isFree;
    private Boolean isCertified;
    
    private Long skillId;
    private Long careerPathId;
    
    private Integer enrollmentCount;
    private Double averageRating;
    
    public TrainingDTO(Training training) {
        this.id = training.getId();
        this.title = training.getTitle();
        this.description = training.getDescription();
        this.type = training.getType();
        this.format = training.getFormat();
        this.durationHours = training.getDurationHours();
        this.provider = training.getProvider();
        this.url = training.getUrl();
        this.price = training.getPrice();
        this.isFree = training.getIsFree();
        this.isCertified = training.getIsCertified();
        this.skillId = training.getSkill() != null ? training.getSkill().getId() : null;
        this.careerPathId = training.getCareerPath() != null ? training.getCareerPath().getId() : null;
        this.enrollmentCount = training.getEnrollmentCount();
        this.averageRating = training.getAverageRating();
    }
}

