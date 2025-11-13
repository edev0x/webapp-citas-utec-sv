package com.utec.citasutec.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.utec.citasutec.model.dto.request.ProfessionalRequestDto;
import com.utec.citasutec.model.dto.request.ProfessionalServiceRequestDto;
import com.utec.citasutec.model.dto.response.ProfessionalResponseDto;
import com.utec.citasutec.model.entity.Professional;
import com.utec.citasutec.model.entity.Service;
import com.utec.citasutec.repository.ProfessionalRepository;
import com.utec.citasutec.repository.ProfessionalServiceRepository;
import com.utec.citasutec.util.exceptions.AppServiceTxException;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Stateless
public class ProfessionalService {

    @Inject
    private ProfessionalRepository professionalRepository;

    @Inject
    private ProfessionalServiceRepository  serviceRepository;

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

    public void save(Professional professional) {
        professionalRepository.save(professional);
    }

    @Transactional
    public void saveService(ProfessionalServiceRequestDto professionalServiceRequestDto) {
        try {
            Professional professional = professionalRepository.findById(professionalServiceRequestDto.professionalId())
                .orElseThrow(() -> new AppServiceTxException("Professional with ID " + professionalServiceRequestDto.professionalId() + " not found"));

            LocalTime startTime = LocalTime.parse(professionalServiceRequestDto.startTime());
            LocalTime endTime = LocalTime.parse(professionalServiceRequestDto.endTime());

            Service service = new Service();
            service.setProfessional(professional);
            service.setServiceName(professionalServiceRequestDto.serviceName());
            service.setServiceDate(LocalDate.parse(professionalServiceRequestDto.date()));
            service.setStartTime(startTime);
            service.setEndTime(endTime);
            service.setDescription(professionalServiceRequestDto.serviceDescription());
            service.setTotalDuration((int) Duration.between(startTime, endTime).toMinutes());

            this.serviceRepository.save(service);
        } catch (Exception e) {
            log.atError().log("Error saving service: {}", e.getMessage());
            throw new AppServiceTxException("Error while saving service");
        }
    }

    @Transactional
    public void saveProfessional(ProfessionalRequestDto professionalRequestDto) {
        try {
            Professional professional = new Professional();
            professional.setName(professionalRequestDto.fullName());
            professional.setEmail(professionalRequestDto.email());
            professional.setSpecialty(professionalRequestDto.specialty());
            professional.setIsActive(professionalRequestDto.isActive());

            this.professionalRepository.save(professional);
        } catch (Exception e) {
            log.atError().log("Error saving professional: {}", e.getMessage());
            throw new AppServiceTxException("Error while saving professional");
        }
    }

    @Transactional
    public void deleteProfessionalById(Integer id) {
        try {
            professionalRepository.deleteById(id);
        } catch (Exception e) {
            log.atError().log("Error deleting professional with ID {}: {}", id, e.getMessage());
            throw new AppServiceTxException("Error deleting professional", e);
        }
    }

    @Transactional
    public void deleteServiceById(Integer id) {
        try {
            serviceRepository.deleteById(id);
        } catch (Exception e) {
            log.atError().log("Error deleting service with ID {}: {}", id, e.getMessage());
            throw new AppServiceTxException("Error deleting service", e);
        }
    }

    @Transactional
    public void updateProfessional(ProfessionalRequestDto professionalRequestDto) {
        try {
            Professional professional  = this.professionalRepository.findById(professionalRequestDto.id())
                .orElseThrow(() -> new AppServiceTxException("Professional with ID " + professionalRequestDto.id() + " not found"));

            professional.setName(professionalRequestDto.fullName());
            professional.setEmail(professionalRequestDto.email());
            professional.setSpecialty(professionalRequestDto.specialty());
            professional.setIsActive(professionalRequestDto.isActive());

            this.professionalRepository.update(professional);
            log.atInfo().log("Professional with ID {} successfully updated", professionalRequestDto.id());
        } catch (Exception e) {
            log.atError().log("Error updating professional with ID {}: {}", professionalRequestDto.id(), e.getMessage());
            throw new AppServiceTxException("Error updating professional", e);
        }
    }

    @Transactional
    public void updateService(ProfessionalServiceRequestDto professionalServiceRequestDto) {
        try {
            Service service  = this.serviceRepository.findById(professionalServiceRequestDto.id())
                .orElseThrow(() -> new AppServiceTxException("Service with ID " + professionalServiceRequestDto.id() + " not found"));

            Professional professional = this.professionalRepository.findById(professionalServiceRequestDto.professionalId())
                .orElseThrow(() -> new AppServiceTxException("Professional with ID " + professionalServiceRequestDto.professionalId() + " not found"));

            LocalTime startTime = LocalTime.parse(professionalServiceRequestDto.startTime());
            LocalTime endTime = LocalTime.parse(professionalServiceRequestDto.endTime());

            service.setStartTime(startTime);
            service.setEndTime(endTime);
            service.setServiceDate(LocalDate.parse(professionalServiceRequestDto.date()));
            service.setServiceName(professionalServiceRequestDto.serviceName());
            service.setDescription(professionalServiceRequestDto.serviceDescription());
            service.setTotalDuration((int) Duration.between(startTime, endTime).toMinutes());
            service.setProfessional(professional);

            this.serviceRepository.update(service);
            log.atInfo().log("Service with ID {} successfully updated", professionalServiceRequestDto.id());
        } catch (Exception e) {
            log.atError().log("Error updating service with ID {}: {}", professionalServiceRequestDto.id(), e.getMessage());
            throw new AppServiceTxException("Error updating service", e);
        }
    }
}
