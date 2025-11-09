package com.utec.citasutec.model.dto.response;

import com.utec.citasutec.model.entity.Professional;
import lombok.Builder;

public record ProfessionalResponseDto(
    Integer id,
    String fullName,
    String email,
    String specialty,
    boolean isActive
) {
    public static ProfessionalResponseDto fromEntity(Professional professional) {
        return new ProfessionalResponseDto(
            professional.getId(),
            professional.getName(),
            professional.getEmail(),
            professional.getSpecialty(),
            professional.getIsActive()
        );
    }
}
