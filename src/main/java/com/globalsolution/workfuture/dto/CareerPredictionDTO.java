package com.globalsolution.workfuture.dto;

import com.globalsolution.workfuture.model.CareerPrediction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerPredictionDTO {
    
    private Long id;
    
    private Long userId;
    
    private Long careerPathId;
    
    @NotNull(message = "{validation.prediction.compatibility.required}")
    @Positive(message = "{validation.prediction.compatibility.positive}")
    private Double compatibilityScore;
    
    @NotNull(message = "{validation.prediction.demand.required}")
    @Positive(message = "{validation.prediction.demand.positive}")
    private Double futureDemandScore;
    
    @NotNull(message = "{validation.prediction.salary.required}")
    @Positive(message = "{validation.prediction.salary.positive}")
    private Double salaryPotential;
    
    private String aiAnalysis;
    private String recommendedSkills;
    private String actionPlan;
    
    @NotNull(message = "{validation.prediction.date.required}")
    private LocalDateTime predictionDate;
    
    @NotNull(message = "{validation.prediction.target.required}")
    private LocalDateTime targetDate;
    
    @NotNull(message = "{validation.prediction.type.required}")
    private CareerPrediction.PredictionType type;
    
    public CareerPredictionDTO(CareerPrediction prediction) {
        this.id = prediction.getId();
        this.userId = prediction.getUser() != null ? prediction.getUser().getId() : null;
        this.careerPathId = prediction.getRecommendedCareer() != null ? 
            prediction.getRecommendedCareer().getId() : null;
        this.compatibilityScore = prediction.getCompatibilityScore();
        this.futureDemandScore = prediction.getFutureDemandScore();
        this.salaryPotential = prediction.getSalaryPotential();
        this.aiAnalysis = prediction.getAiAnalysis();
        this.recommendedSkills = prediction.getRecommendedSkills();
        this.actionPlan = prediction.getActionPlan();
        this.predictionDate = prediction.getPredictionDate();
        this.targetDate = prediction.getTargetDate();
        this.type = prediction.getType();
    }
}

