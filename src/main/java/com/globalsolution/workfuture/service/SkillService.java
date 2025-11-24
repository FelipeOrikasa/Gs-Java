package com.globalsolution.workfuture.service;

import com.globalsolution.workfuture.dto.SkillDTO;
import com.globalsolution.workfuture.model.Skill;
import com.globalsolution.workfuture.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SkillService {
    
    private final SkillRepository skillRepository;
    
    @Transactional
    @CacheEvict(value = "skills", allEntries = true)
    public SkillDTO createSkill(SkillDTO skillDTO) {
        if (skillRepository.existsByName(skillDTO.getName())) {
            throw new RuntimeException("Skill name already exists");
        }
        
        Skill skill = new Skill();
        skill.setName(skillDTO.getName());
        skill.setDescription(skillDTO.getDescription());
        skill.setCategory(skillDTO.getCategory());
        skill.setLevel(skillDTO.getLevel());
        skill.setInDemand(skillDTO.getInDemand() != null ? skillDTO.getInDemand() : false);
        skill.setFutureProof(skillDTO.getFutureProof() != null ? skillDTO.getFutureProof() : false);
        
        skill = skillRepository.save(skill);
        return new SkillDTO(skill);
    }
    
    @Cacheable(value = "skills", key = "#id")
    public SkillDTO getSkillById(Long id) {
        Skill skill = skillRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Skill not found"));
        return new SkillDTO(skill);
    }
    
    @Cacheable(value = "skills")
    public Page<SkillDTO> getAllSkills(Pageable pageable) {
        return skillRepository.findAll(pageable)
            .map(SkillDTO::new);
    }
    
    @Cacheable(value = "skills")
    public Page<SkillDTO> getInDemandSkills(Pageable pageable) {
        return skillRepository.findByInDemandTrue(pageable)
            .map(SkillDTO::new);
    }
    
    @Cacheable(value = "skills")
    public Page<SkillDTO> getFutureProofSkills(Pageable pageable) {
        return skillRepository.findByFutureProofTrue(pageable)
            .map(SkillDTO::new);
    }
    
    @Transactional
    @CacheEvict(value = "skills", allEntries = true)
    public SkillDTO updateSkill(Long id, SkillDTO skillDTO) {
        Skill skill = skillRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Skill not found"));
        
        if (skillDTO.getName() != null) skill.setName(skillDTO.getName());
        if (skillDTO.getDescription() != null) skill.setDescription(skillDTO.getDescription());
        if (skillDTO.getCategory() != null) skill.setCategory(skillDTO.getCategory());
        if (skillDTO.getLevel() != null) skill.setLevel(skillDTO.getLevel());
        if (skillDTO.getInDemand() != null) skill.setInDemand(skillDTO.getInDemand());
        if (skillDTO.getFutureProof() != null) skill.setFutureProof(skillDTO.getFutureProof());
        
        skill = skillRepository.save(skill);
        return new SkillDTO(skill);
    }
    
    @Transactional
    @CacheEvict(value = "skills", allEntries = true)
    public void deleteSkill(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new RuntimeException("Skill not found");
        }
        skillRepository.deleteById(id);
    }
}

