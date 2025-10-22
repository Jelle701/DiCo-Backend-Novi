package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.diabetes.DiabetesSummaryDto;

public interface DiabetesService {
    DiabetesSummaryDto getDiabetesSummary(String username);
}
