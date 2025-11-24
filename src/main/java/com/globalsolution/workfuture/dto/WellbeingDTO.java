package com.globalsolution.workfuture.dto;

import com.globalsolution.workfuture.model.Wellbeing;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WellbeingDTO {
    
    private Long id;
    
    @NotNull(message = "{validation.wellbeing.user.required}")
    private Long userId;
    
    @NotNull(message = "{validation.wellbeing.stress.required}")
    @Min(value = 1, message = "{validation.wellbeing.stress.min}")
    @Max(value = 10, message = "{validation.wellbeing.stress.max}")
    private Integer stressLevel;
    
    @NotNull(message = "{validation.wellbeing.balance.required}")
    @Min(value = 1, message = "{validation.wellbeing.balance.min}")
    @Max(value = 10, message = "{validation.wellbeing.balance.max}")
    private Integer workLifeBalance;
    
    @NotNull(message = "{validation.wellbeing.satisfaction.required}")
    @Min(value = 1, message = "{validation.wellbeing.satisfaction.min}")
    @Max(value = 10, message = "{validation.wellbeing.satisfaction.max}")
    private Integer jobSatisfaction;
    
    @NotNull(message = "{validation.wellbeing.mental.required}")
    @Min(value = 1, message = "{validation.wellbeing.mental.min}")
    @Max(value = 10, message = "{validation.wellbeing.mental.max}")
    private Integer mentalHealthScore;
    
    @Min(value = 0, message = "{validation.wellbeing.hours.min}")
    @Max(value = 168, message = "{validation.wellbeing.hours.max}")
    private Integer workHours;
    
    private Boolean isRemote;
    private Boolean isHybrid;
    
    @Size(max = 1000, message = "{validation.wellbeing.notes.size}")
    private String notes;
    
    @NotNull(message = "{validation.wellbeing.timestamp.required}")
    private LocalDateTime timestamp;
    
    public WellbeingDTO(Wellbeing wellbeing) {
        this.id = wellbeing.getId();
        this.userId = wellbeing.getUser().getId();
        this.stressLevel = wellbeing.getStressLevel();
        this.workLifeBalance = wellbeing.getWorkLifeBalance();
        this.jobSatisfaction = wellbeing.getJobSatisfaction();
        this.mentalHealthScore = wellbeing.getMentalHealthScore();
        this.workHours = wellbeing.getWorkHours();
        this.isRemote = wellbeing.getIsRemote();
        this.isHybrid = wellbeing.getIsHybrid();
        this.notes = wellbeing.getNotes();
        this.timestamp = wellbeing.getTimestamp();
    }
}

