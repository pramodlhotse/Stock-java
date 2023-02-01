package com.example.TechnicalAnalysis.repository;

import com.example.TechnicalAnalysis.entity.PE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PERepository extends JpaRepository<PE, Long> {
}
