package com.example.TechnicalAnalysis.repository;

import com.example.TechnicalAnalysis.entity.CE;

import java.util.List;

public interface CERepositoryCustom {
    List<CE> getByDate(String input);
}
