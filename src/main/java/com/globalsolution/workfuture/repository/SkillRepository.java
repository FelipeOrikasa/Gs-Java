package com.globalsolution.workfuture.repository;

import com.globalsolution.workfuture.model.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Page<Skill> findByInDemandTrue(Pageable pageable);
    Page<Skill> findByFutureProofTrue(Pageable pageable);
    List<Skill> findByCategory(Skill.SkillCategory category);
    boolean existsByName(String name);
}

