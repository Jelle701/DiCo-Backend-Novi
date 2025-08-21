package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import java.util.List;

public interface ProviderService {
    void linkPatient(String providerUsername, String accessCode);
    List<FullUserProfileDto> getLinkedPatients(String providerUsername);
}
