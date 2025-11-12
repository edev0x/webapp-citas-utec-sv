package com.utec.citasutec.service;

import com.utec.citasutec.model.dto.response.ProfessionalResponseDto;
import com.utec.citasutec.model.entity.Professional;
import com.utec.citasutec.repository.ProfessionalRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@Stateless
public class ProfessionalService {

    @Inject
    private ProfessionalRepository professionalRepository;

    public long countAll() {
        return professionalRepository.count();
    }

    public Optional<Professional> findById(Integer id) {
        return professionalRepository.findById(id);
    }

    public Optional<ProfessionalResponseDto> findByIdAsDto(Integer id) {
        return this.findById(id).map(ProfessionalResponseDto::fromEntity);
    }

    public List<ProfessionalResponseDto> findAll() {
        return professionalRepository.findAll().stream().map(ProfessionalResponseDto::fromEntity).toList();
    }
}
