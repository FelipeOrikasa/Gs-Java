package com.globalsolution.workfuture.dto;

import com.globalsolution.workfuture.model.Skill;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillDTO {
    
    private Long id;
    
    @NotBlank(message = "{validation.skill.name.required}")
    @Size(max = 100, message = "{validation.skill.name.size}")
    private String name;
    
    @Size(max = 500, message = "{validation.skill.description.size}")
    private String description;
    
    @NotNull(message = "{validation.skill.category.required}")
    private Skill.SkillCategory category;
    
    @NotNull(message = "{validation.skill.level.required}")
    private Skill.SkillLevel level;
    
    private Boolean inDemand;
    private Boolean futureProof;
    
    public SkillDTO(Skill skill) {
        this.id = skill.getId();
        this.name = skill.getName();
        this.description = skill.getDescription();
        this.category = skill.getCategory();
        this.level = skill.getLevel();
        this.inDemand = skill.getInDemand();
        this.futureProof = skill.getFutureProof();
    }
}

