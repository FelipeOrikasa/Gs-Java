package com.globalsolution.workfuture.repository;

import com.globalsolution.workfuture.model.Wellbeing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WellbeingRepository extends JpaRepository<Wellbeing, Long> {
    Page<Wellbeing> findByUserId(Long userId, Pageable pageable);
    
    @Query("SELECT w FROM Wellbeing w WHERE w.user.id = :userId " +
           "AND w.timestamp BETWEEN :startDate AND :endDate ORDER BY w.timestamp DESC")
    List<Wellbeing> findByUserIdAndDateRange(
        @Param("userId") Long userId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    @Query("SELECT AVG(w.mentalHealthScore) FROM Wellbeing w WHERE w.user.id = :userId " +
           "AND w.timestamp BETWEEN :startDate AND :endDate")
    Double getAverageMentalHealthScore(
        @Param("userId") Long userId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
}

