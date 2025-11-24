package com.globalsolution.workfuture.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "wellbeing", indexes = {
    @Index(name = "idx_user_timestamp", columnList = "user_id,timestamp")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wellbeing {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @NotNull
    @Column(nullable = false)
    private Integer stressLevel; // 1-10
    
    @NotNull
    @Column(nullable = false)
    private Integer workLifeBalance; // 1-10
    
    @NotNull
    @Column(nullable = false)
    private Integer jobSatisfaction; // 1-10
    
    @NotNull
    @Column(nullable = false)
    private Integer mentalHealthScore; // 1-10
    
    private Integer workHours; // Horas trabalhadas na semana
    private Boolean isRemote = false;
    private Boolean isHybrid = false;
    
    @Size(max = 1000)
    private String notes; // Observações do usuário
    
    @NotNull
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
}

