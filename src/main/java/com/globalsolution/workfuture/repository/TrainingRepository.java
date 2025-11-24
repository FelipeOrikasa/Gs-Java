package com.globalsolution.workfuture.repository;

import com.globalsolution.workfuture.model.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    Page<Training> findByIsFreeTrue(Pageable pageable);
    Page<Training> findBySkillId(Long skillId, Pageable pageable);
    Page<Training> findByCareerPathId(Long careerPathId, Pageable pageable);
    List<Training> findByType(Training.TrainingType type);
}

