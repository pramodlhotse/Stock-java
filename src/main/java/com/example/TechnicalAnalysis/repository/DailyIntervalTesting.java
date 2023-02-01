package com.example.TechnicalAnalysis.repository;

import com.example.TechnicalAnalysis.entity.FilteredResponse;

import com.example.TechnicalAnalysis.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyIntervalTesting extends JpaRepository<Result, Long> {
}
