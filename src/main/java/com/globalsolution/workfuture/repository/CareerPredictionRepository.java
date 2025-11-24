package com.globalsolution.workfuture.repository;

import com.globalsolution.workfuture.model.CareerPrediction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CareerPredictionRepository extends JpaRepository<CareerPrediction, Long> {
    Page<CareerPrediction> findByUserId(Long userId, Pageable pageable);
    List<CareerPrediction> findByTargetDateBetween(LocalDateTime start, LocalDateTime end);
    List<CareerPrediction> findByUserIdAndType(Long userId, CareerPrediction.PredictionType type);
}

