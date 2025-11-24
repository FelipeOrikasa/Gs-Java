package com.globalsolution.workfuture.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "career_paths")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerPath {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String title;
    
    @Size(max = 1000)
    private String description;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CareerType type;
    
    private Integer estimatedYears; // Anos estimados para alcançar
    private Double averageSalary; // Salário médio
    private Integer jobGrowth; // Crescimento de empregos (%)
    private Boolean futureCareer = false; // Carreira do futuro
    
    @ManyToMany
    @JoinTable(
        name = "career_path_skills",
        joinColumns = @JoinColumn(name = "career_path_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> requiredSkills = new HashSet<>();
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum CareerType {
        TECHNOLOGY, BUSINESS, CREATIVE, HEALTHCARE, EDUCATION,
        SUSTAINABILITY, AI_SPECIALIST, DATA_SCIENTIST, OTHER
    }
}

