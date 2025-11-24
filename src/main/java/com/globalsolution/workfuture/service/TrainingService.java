package com.globalsolution.workfuture.service;

import com.globalsolution.workfuture.dto.TrainingDTO;
import com.globalsolution.workfuture.model.CareerPath;
import com.globalsolution.workfuture.model.Skill;
import com.globalsolution.workfuture.model.Training;
import com.globalsolution.workfuture.repository.CareerPathRepository;
import com.globalsolution.workfuture.repository.SkillRepository;
import com.globalsolution.workfuture.repository.TrainingRepository;
import com.globalsolution.workfuture.service.messaging.WorkFutureMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrainingService {
    
    private final TrainingRepository trainingRepository;
    private final SkillRepository skillRepository;
    private final CareerPathRepository careerPathRepository;
    private final WorkFutureMessageProducer messageProducer;
    
    @Transactional
    @CacheEvict(value = "trainings", allEntries = true)
    public TrainingDTO createTraining(TrainingDTO trainingDTO) {
        Training training = new Training();
        training.setTitle(trainingDTO.getTitle());
        training.setDescription(trainingDTO.getDescription());
        training.setType(trainingDTO.getType());
        training.setFormat(trainingDTO.getFormat());
        training.setDurationHours(trainingDTO.getDurationHours());
        training.setProvider(trainingDTO.getProvider());
        training.setUrl(trainingDTO.getUrl());
        training.setPrice(trainingDTO.getPrice());
        training.setIsFree(trainingDTO.getIsFree() != null ? trainingDTO.getIsFree() : false);
        training.setIsCertified(trainingDTO.getIsCertified() != null ? trainingDTO.getIsCertified() : false);
        training.setEnrollmentCount(0);
        training.setAverageRating(0.0);
        
        if (trainingDTO.getSkillId() != null) {
            Skill skill = skillRepository.findById(trainingDTO.getSkillId())
                .orElseThrow(() -> new RuntimeException("Skill not found"));
            training.setSkill(skill);
        }
        
        if (trainingDTO.getCareerPathId() != null) {
            CareerPath careerPath = careerPathRepository.findById(trainingDTO.getCareerPathId())
                .orElseThrow(() -> new RuntimeException("Career path not found"));
            training.setCareerPath(careerPath);
        }
        
        training = trainingRepository.save(training);
        
        // Enviar mensagem assíncrona
        messageProducer.sendTrainingMessage(trainingDTO);
        
        return new TrainingDTO(training);
    }
    
    public Page<TrainingDTO> getAllTrainings(Pageable pageable) {
        return trainingRepository.findAll(pageable)
            .map(TrainingDTO::new);
    }
    
    public Page<TrainingDTO> getFreeTrainings(Pageable pageable) {
        return trainingRepository.findByIsFreeTrue(pageable)
            .map(TrainingDTO::new);
    }
    
    public Page<TrainingDTO> getTrainingsBySkill(Long skillId, Pageable pageable) {
        return trainingRepository.findBySkillId(skillId, pageable)
            .map(TrainingDTO::new);
    }
    
    public Page<TrainingDTO> getTrainingsByCareerPath(Long careerPathId, Pageable pageable) {
        return trainingRepository.findByCareerPathId(careerPathId, pageable)
            .map(TrainingDTO::new);
    }
}

