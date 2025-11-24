package com.globalsolution.workfuture.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "trainings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Training {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 200)
    @Column(nullable = false)
    private String title;
    
    @Size(max = 2000)
    private String description;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainingType type;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainingFormat format;
    
    private Integer durationHours; // Duração em horas
    private String provider; // Provedor do treinamento
    private String url; // URL do curso
    private Double price;
    private Boolean isFree = false;
    private Boolean isCertified = false; // Oferece certificado
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill; // Habilidade relacionada
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_path_id")
    private CareerPath careerPath; // Caminho de carreira relacionado
    
    private Integer enrollmentCount = 0; // Número de inscritos
    private Double averageRating = 0.0; // Avaliação média
    
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
    
    public enum TrainingType {
        UPSKILLING, RESKILLING, SOFT_SKILLS, TECHNICAL, LEADERSHIP, OTHER
    }
    
    public enum TrainingFormat {
        ONLINE, PRESENTIAL, HYBRID, SELF_PACED, BOOTCAMPS
    }
}

