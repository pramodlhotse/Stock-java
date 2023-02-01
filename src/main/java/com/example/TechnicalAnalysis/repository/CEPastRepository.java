package com.example.TechnicalAnalysis.repository;

import com.example.TechnicalAnalysis.entity.CEPast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CEPastRepository extends JpaRepository<CEPast, Long> {
}
