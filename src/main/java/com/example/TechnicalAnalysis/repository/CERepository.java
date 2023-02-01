package com.example.TechnicalAnalysis.repository;

import com.example.TechnicalAnalysis.entity.CE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CERepository extends JpaRepository<CE, Long> {

    List<CE> findByDate(String date, String symbol);

}
