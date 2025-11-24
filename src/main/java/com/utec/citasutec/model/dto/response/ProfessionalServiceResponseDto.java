package com.utec.citasutec.model.dto.response;

import com.utec.citasutec.model.entity.Service;

import java.time.LocalTime;

public record ProfessionalServiceResponseDto (
    Integer id,
    String serviceName,
    String description,
    int duration,
    LocalTime startTime,
    LocalTime endTime,
    Integer professionalId
) {
    public static ProfessionalServiceResponseDto fromEntity(Service service) {
        return new ProfessionalServiceResponseDto(
            service.getId(),
            service.getServiceName(),
            service.getDescription(),
            service.getTotalDuration(),
            service.getStartTime(),
            service.getEndTime(),
            service.getProfessional().getId()
        );
    }
}
