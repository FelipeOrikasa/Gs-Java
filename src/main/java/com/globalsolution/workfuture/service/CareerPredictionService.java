package com.globalsolution.workfuture.service;

import com.globalsolution.workfuture.dto.CareerPredictionDTO;
import com.globalsolution.workfuture.model.CareerPath;
import com.globalsolution.workfuture.model.CareerPrediction;
import com.globalsolution.workfuture.model.User;
import com.globalsolution.workfuture.repository.CareerPathRepository;
import com.globalsolution.workfuture.repository.CareerPredictionRepository;
import com.globalsolution.workfuture.repository.UserRepository;
import com.globalsolution.workfuture.service.ai.CareerAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CareerPredictionService {
    
    private final CareerPredictionRepository predictionRepository;
    private final UserRepository userRepository;
    private final CareerPathRepository careerPathRepository;
    private final CareerAIService aiService;
    
    @Async
    @Transactional
    @CacheEvict(value = "predictions", allEntries = true)
    public CareerPredictionDTO generatePrediction(Long userId, Long careerPathId, 
                                                  CareerPrediction.PredictionType type) {
        User user = userId != null ? 
            userRepository.findById(userId).orElse(null) : null;
        
        CareerPath careerPath = careerPathId != null ?
            careerPathRepository.findById(careerPathId).orElse(null) : null;
        
        // Calcular scores (simplificado - em produção usar ML)
        Double compatibilityScore = calculateCompatibility(user, careerPath);
        Double futureDemandScore = careerPath != null && careerPath.getFutureCareer() ? 
            85.0 : (careerPath != null && careerPath.getJobGrowth() != null ? 
                Math.min(100.0, careerPath.getJobGrowth() * 1.2) : 70.0);
        Double salaryPotential = careerPath != null && careerPath.getAverageSalary() != null ?
            careerPath.getAverageSalary() : 5000.0;
        
        // Usar IA para gerar análise
        String aiAnalysis = aiService.generateCareerAnalysis(user, careerPath, 
            compatibilityScore, futureDemandScore);
        
        // Gerar recomendações
        String recommendedSkills = generateRecommendedSkills(user, careerPath);
        String actionPlan = generateActionPlan(user, careerPath, type);
        
        LocalDateTime targetDate = LocalDateTime.now().plusYears(
            type == CareerPrediction.PredictionType.SHORT_TERM ? 2 :
            type == CareerPrediction.PredictionType.MEDIUM_TERM ? 5 : 10
        );
        
        CareerPrediction prediction = new CareerPrediction();
        prediction.setUser(user);
        prediction.setRecommendedCareer(careerPath);
        prediction.setCompatibilityScore(compatibilityScore);
        prediction.setFutureDemandScore(futureDemandScore);
        prediction.setSalaryPotential(salaryPotential);
        prediction.setAiAnalysis(aiAnalysis);
        prediction.setRecommendedSkills(recommendedSkills);
        prediction.setActionPlan(actionPlan);
        prediction.setPredictionDate(LocalDateTime.now());
        prediction.setTargetDate(targetDate);
        prediction.setType(type);
        
        prediction = predictionRepository.save(prediction);
        return new CareerPredictionDTO(prediction);
    }
    
    private Double calculateCompatibility(User user, CareerPath careerPath) {
        if (user == null || careerPath == null) return 70.0;
        
        // Lógica simplificada de compatibilidade
        double score = 50.0;
        
        // Ajustar baseado em experiência
        if (user.getYearsOfExperience() != null) {
            score += Math.min(20.0, user.getYearsOfExperience() * 2);
        }
        
        // Ajustar baseado em habilidades (se tiver skills cadastradas)
        if (user.getSkills() != null && !user.getSkills().isEmpty() && 
            careerPath.getRequiredSkills() != null && !careerPath.getRequiredSkills().isEmpty()) {
            long matchingSkills = user.getSkills().stream()
                .filter(skill -> careerPath.getRequiredSkills().contains(skill))
                .count();
            score += Math.min(30.0, matchingSkills * 10);
        }
        
        return Math.min(100.0, score);
    }
    
    private String generateRecommendedSkills(User user, CareerPath careerPath) {
        if (careerPath == null || careerPath.getRequiredSkills() == null) {
            return "Habilidades técnicas em tecnologia, soft skills e comunicação";
        }
        
        return careerPath.getRequiredSkills().stream()
            .map(skill -> skill.getName())
            .limit(5)
            .reduce((a, b) -> a + ", " + b)
            .orElse("Desenvolver habilidades técnicas e interpessoais");
    }
    
    private String generateActionPlan(User user, CareerPath careerPath, CareerPrediction.PredictionType type) {
        StringBuilder plan = new StringBuilder();
        plan.append("1. Identificar gaps de habilidades\n");
        plan.append("2. Buscar treinamentos relevantes\n");
        plan.append("3. Praticar habilidades em projetos reais\n");
        plan.append("4. Construir portfólio na área\n");
        plan.append("5. Networking com profissionais da área\n");
        if (type == CareerPrediction.PredictionType.SHORT_TERM) {
            plan.append("6. Foco em certificações rápidas\n");
        }
        return plan.toString();
    }
    
    @Cacheable(value = "predictions", key = "#id")
    public CareerPredictionDTO getPredictionById(Long id) {
        CareerPrediction prediction = predictionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Prediction not found"));
        return new CareerPredictionDTO(prediction);
    }
    
    @Cacheable(value = "predictions")
    public Page<CareerPredictionDTO> getPredictionsByUser(Long userId, Pageable pageable) {
        return predictionRepository.findByUserId(userId, pageable)
            .map(CareerPredictionDTO::new);
    }
    
    public List<CareerPredictionDTO> getPredictionsByDateRange(LocalDateTime start, LocalDateTime end) {
        return predictionRepository.findByTargetDateBetween(start, end).stream()
            .map(CareerPredictionDTO::new)
            .toList();
    }
}

