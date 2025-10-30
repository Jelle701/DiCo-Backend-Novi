// Interface for services related to diabetes data.
package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.diabetes.DiabetesSummaryDto;

public interface DiabetesService {
    // Retrieves a summary of diabetes data for a user.
    DiabetesSummaryDto getDiabetesSummary(String username);
}
