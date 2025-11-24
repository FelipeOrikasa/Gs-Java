package com.globalsolution.workfuture.service;

import com.globalsolution.workfuture.dto.CareerPathDTO;
import com.globalsolution.workfuture.model.CareerPath;
import com.globalsolution.workfuture.model.Skill;
import com.globalsolution.workfuture.repository.CareerPathRepository;
import com.globalsolution.workfuture.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CareerPathService {
    
    private final CareerPathRepository careerPathRepository;
    private final SkillRepository skillRepository;
    
    @Transactional
    @CacheEvict(value = "careerPaths", allEntries = true)
    public CareerPathDTO createCareerPath(CareerPathDTO careerPathDTO) {
        CareerPath careerPath = new CareerPath();
        careerPath.setTitle(careerPathDTO.getTitle());
        careerPath.setDescription(careerPathDTO.getDescription());
        careerPath.setType(careerPathDTO.getType());
        careerPath.setEstimatedYears(careerPathDTO.getEstimatedYears());
        careerPath.setAverageSalary(careerPathDTO.getAverageSalary());
        careerPath.setJobGrowth(careerPathDTO.getJobGrowth());
        careerPath.setFutureCareer(careerPathDTO.getFutureCareer() != null ? 
            careerPathDTO.getFutureCareer() : false);
        
        // Adicionar skills relacionadas
        if (careerPathDTO.getRequiredSkillIds() != null && !careerPathDTO.getRequiredSkillIds().isEmpty()) {
            Set<Skill> skills = new HashSet<>();
            for (Long skillId : careerPathDTO.getRequiredSkillIds()) {
                skillRepository.findById(skillId).ifPresent(skills::add);
            }
            careerPath.setRequiredSkills(skills);
        }
        
        careerPath = careerPathRepository.save(careerPath);
        return new CareerPathDTO(careerPath);
    }
    
    @Cacheable(value = "careerPaths", key = "#id")
    public CareerPathDTO getCareerPathById(Long id) {
        CareerPath careerPath = careerPathRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Career path not found"));
        return new CareerPathDTO(careerPath);
    }
    
    @Cacheable(value = "careerPaths")
    public Page<CareerPathDTO> getAllCareerPaths(Pageable pageable) {
        return careerPathRepository.findAll(pageable)
            .map(CareerPathDTO::new);
    }
    
    @Cacheable(value = "careerPaths")
    public Page<CareerPathDTO> getFutureCareers(Pageable pageable) {
        return careerPathRepository.findByFutureCareerTrue(pageable)
            .map(CareerPathDTO::new);
    }
    
    @Transactional
    @CacheEvict(value = "careerPaths", allEntries = true)
    public CareerPathDTO updateCareerPath(Long id, CareerPathDTO careerPathDTO) {
        CareerPath careerPath = careerPathRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Career path not found"));
        
        if (careerPathDTO.getTitle() != null) careerPath.setTitle(careerPathDTO.getTitle());
        if (careerPathDTO.getDescription() != null) careerPath.setDescription(careerPathDTO.getDescription());
        if (careerPathDTO.getAverageSalary() != null) careerPath.setAverageSalary(careerPathDTO.getAverageSalary());
        if (careerPathDTO.getJobGrowth() != null) careerPath.setJobGrowth(careerPathDTO.getJobGrowth());
        if (careerPathDTO.getFutureCareer() != null) careerPath.setFutureCareer(careerPathDTO.getFutureCareer());
        
        careerPath = careerPathRepository.save(careerPath);
        return new CareerPathDTO(careerPath);
    }
    
    @Transactional
    @CacheEvict(value = "careerPaths", allEntries = true)
    public void deleteCareerPath(Long id) {
        if (!careerPathRepository.existsById(id)) {
            throw new RuntimeException("Career path not found");
        }
        careerPathRepository.deleteById(id);
    }
}

