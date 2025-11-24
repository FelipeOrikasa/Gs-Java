package com.globalsolution.workfuture.service;

import com.globalsolution.workfuture.dto.WellbeingDTO;
import com.globalsolution.workfuture.model.User;
import com.globalsolution.workfuture.model.Wellbeing;
import com.globalsolution.workfuture.repository.UserRepository;
import com.globalsolution.workfuture.repository.WellbeingRepository;
import com.globalsolution.workfuture.service.messaging.WorkFutureMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WellbeingService {
    
    private final WellbeingRepository wellbeingRepository;
    private final UserRepository userRepository;
    private final WorkFutureMessageProducer messageProducer;
    
    @Transactional
    @CacheEvict(value = "wellbeing", allEntries = true)
    public WellbeingDTO createWellbeing(WellbeingDTO wellbeingDTO) {
        User user = userRepository.findById(wellbeingDTO.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Wellbeing wellbeing = new Wellbeing();
        wellbeing.setUser(user);
        wellbeing.setStressLevel(wellbeingDTO.getStressLevel());
        wellbeing.setWorkLifeBalance(wellbeingDTO.getWorkLifeBalance());
        wellbeing.setJobSatisfaction(wellbeingDTO.getJobSatisfaction());
        wellbeing.setMentalHealthScore(wellbeingDTO.getMentalHealthScore());
        wellbeing.setWorkHours(wellbeingDTO.getWorkHours());
        wellbeing.setIsRemote(wellbeingDTO.getIsRemote() != null ? wellbeingDTO.getIsRemote() : false);
        wellbeing.setIsHybrid(wellbeingDTO.getIsHybrid() != null ? wellbeingDTO.getIsHybrid() : false);
        wellbeing.setNotes(wellbeingDTO.getNotes());
        wellbeing.setTimestamp(wellbeingDTO.getTimestamp() != null ? 
            wellbeingDTO.getTimestamp() : LocalDateTime.now());
        
        wellbeing = wellbeingRepository.save(wellbeing);
        
        // Enviar mensagem assíncrona
        messageProducer.sendWellbeingMessage(wellbeingDTO);
        
        return new WellbeingDTO(wellbeing);
    }
    
    public Page<WellbeingDTO> getWellbeingByUser(Long userId, Pageable pageable) {
        return wellbeingRepository.findByUserId(userId, pageable)
            .map(WellbeingDTO::new);
    }
    
    public Double getAverageMentalHealthScore(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return wellbeingRepository.getAverageMentalHealthScore(userId, startDate, endDate);
    }
}

