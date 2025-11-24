package com.globalsolution.workfuture.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "career_predictions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerPrediction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_path_id")
    private CareerPath recommendedCareer;
    
    @NotNull
    @Column(nullable = false)
    private Double compatibilityScore; // 0-100 (quanto o usuário se encaixa)
    
    @NotNull
    @Column(nullable = false)
    private Double futureDemandScore; // 0-100 (demanda futura)
    
    @NotNull
    @Column(nullable = false)
    private Double salaryPotential; // Potencial salarial
    
    @Column(length = 3000)
    private String aiAnalysis; // Análise gerada pela IA
    
    @Column(length = 2000)
    private String recommendedSkills; // Habilidades recomendadas
    
    @Column(length = 2000)
    private String actionPlan; // Plano de ação sugerido
    
    @NotNull
    @Column(nullable = false)
    private LocalDateTime predictionDate;
    
    @NotNull
    @Column(nullable = false)
    private LocalDateTime targetDate; // Data alvo para alcançar
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PredictionType type;
    
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (predictionDate == null) {
            predictionDate = LocalDateTime.now();
        }
    }
    
    public enum PredictionType {
        SHORT_TERM, // 1-2 anos
        MEDIUM_TERM, // 3-5 anos
        LONG_TERM // 5+ anos
    }
}

