package com.utec.citasutec.service;

import com.utec.citasutec.model.dto.request.AppointmentRequestDto;
import com.utec.citasutec.model.dto.response.AppointmentByMonthResponse;
import com.utec.citasutec.model.dto.response.AppointmentByStateResponse;
import com.utec.citasutec.model.dto.response.AppointmentResponse;
import com.utec.citasutec.model.dto.response.AppointmentResponseDto;
import com.utec.citasutec.model.entity.Appointment;
import com.utec.citasutec.model.entity.Log;
import com.utec.citasutec.model.entity.Professional;
import com.utec.citasutec.model.entity.User;
import com.utec.citasutec.repository.AppointmentsRepository;
import com.utec.citasutec.repository.factory.Paginated;
import com.utec.citasutec.util.AppointmentLogsActions;
import com.utec.citasutec.util.exceptions.AppServiceTxException;
import com.utec.citasutec.util.exceptions.RepositoryTransactionException;
import com.utec.citasutec.util.validators.ValidationMessages;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Stateless
public class AppointmentService {

    @Inject
    private AppointmentsRepository appointmentsRepository;

    @Inject
    private UserService userService;

    @Inject
    private ProfessionalService professionalService;

    @Inject
    private LogService logService;

    public long countAll() {
        return appointmentsRepository.count();
    }

    public long countByProfessionalId(Integer id) {
        return appointmentsRepository.countByProfessionalId(id);
    }

    public long countByState(String state) {
        return appointmentsRepository.countByState(state);
    }

    public List<AppointmentByMonthResponse> countAppointmentsByMonth() {
        return appointmentsRepository.countAppointmentsByMonth();
    }

    public List<AppointmentByStateResponse> countAppointmentsByState() {
        return appointmentsRepository.countAppointmentsByState();
    }

    @Transactional
    public void createAppointment(AppointmentRequestDto appointmentRequestDto) {
        try {
            User user = validateAndGetUser(appointmentRequestDto.userId());
            Professional professional = validateAndGetProfessional(appointmentRequestDto.professionalId());

            Appointment appointment = fromDto(appointmentRequestDto, user, professional);
            appointmentsRepository.save(appointment);
            logService.saveLog(user, appointment, AppointmentLogsActions.APPOINTMENT_CREATED.getMessage());

            log.atInfo().log("Appointment created successfully for user ID: {} with professional ID: {}",  appointmentRequestDto.userId(), appointmentRequestDto.professionalId());
        } catch (RepositoryTransactionException ex) {
            log.atError().log("Database error while creating appointment: {}", ex.getMessage());
            throw new AppServiceTxException("Database error while creating appointment");
        } catch (Exception e) {
            log.atError().log("Unexpected error while creating appointment: {}", e.getMessage());
            throw new AppServiceTxException("Unexpected error while creating appointment");
        }
    }

    public List<AppointmentResponse> findAllAppointments() {
        try {
            return appointmentsRepository.findAllAppointments().stream().map(AppointmentResponse::fromEntity).toList();
        } catch (Exception e) {
            log.atError().log("Error retrieving all appointments: {}", e.getMessage());
            throw new AppServiceTxException("Error retrieving all appointments");
        }
    }

    public List<AppointmentResponse> findAllUpcomingAppointments() {
        try {
            return appointmentsRepository.findAllUpcomingAppointments();
        } catch (Exception e) {
            log.atError().log("Error retrieving upcoming appointments: {}", e.getMessage());
            throw new AppServiceTxException("Error retrieving upcoming appointments");
        }
    }

    public Paginated<AppointmentResponse> findAllAppointmentsPaginated(int page, int size, String searchField,
            String searchTerm) {
        try {
            Paginated<Appointment> paginatedAppointments = appointmentsRepository.getPageable(page, size, searchField,
                    searchTerm);

            List<AppointmentResponse> appointmentResponses = paginatedAppointments.getItems().stream()
                    .map(AppointmentResponse::fromEntity)
                    .toList();
            return new Paginated<>(appointmentResponses, paginatedAppointments.getTotalItems(),
                    paginatedAppointments.getCurrentPage(),
                    paginatedAppointments.getPageSize(), paginatedAppointments.getTotalPages());
        } catch (Exception e) {
            log.atError().log("Error retrieving paginated appointments: {}", e.getMessage());
            throw new AppServiceTxException("Error retrieving paginated appointments");
        }
    }

    public List<AppointmentResponseDto> findAllAppointmentsCreatedByUser(String email) {
        try {
            return this.appointmentsRepository.findAllAppointmentsByUser(email)
                .stream().map(AppointmentResponseDto::fromEntity).toList();
        } catch (Exception e) {
            log.atError().log("Error retrieving appointments created by user: {}", e.getMessage());
            throw new AppServiceTxException("Error retrieving appointments created by user");
        }
    }

    public List<AppointmentResponseDto> findAll() {
        try {
            return this.appointmentsRepository.findAllAppointments().stream().map(AppointmentResponseDto::fromEntity).toList();
        } catch (Exception e) {
            log.atError().log("Error retrieving appointments: {}", e.getMessage());
            throw new AppServiceTxException("Error retrieving appointments");
        }
    }

    @Transactional
    public void deleteById(Integer id) {
        try {
            boolean exists = appointmentsRepository.exists(id);
            if (exists) {
                appointmentsRepository.deleteById(id);
                log.atInfo().log("Appointment deleted successfully for ID: {}", id);
            } else {
                throw new AppServiceTxException("Appointment with ID " + id + " not found");
            }
        } catch (RepositoryTransactionException ex) {
            log.atError().log("Database error while deleting appointment: {}", ex.getMessage());
            throw new AppServiceTxException("Database error while deleting appointment");
        } catch (Exception e) {
            log.atError().log("Unexpected error while deleting appointment: {}", e.getMessage());
            throw new AppServiceTxException("Unexpected error while deleting appointment");
        }
    }

    @Transactional
    public void deleteIfAssignedToUser(Integer id, String userEmail) {
        try {
            boolean exists = appointmentsRepository.exists(id);
            if (exists) {
                this.appointmentsRepository.deleteIfAssignedToUser(id, userEmail);
            }

        } catch (RepositoryTransactionException ex) {
            log.atError().log("Database error while deleting appointment assigned to user: {}", ex.getMessage());
            throw new AppServiceTxException("Database error while deleting appointment");
        } catch (Exception e) {
            log.atError().log("Unexpected error while deleting appointment assigned to user: {}", e.getMessage());
            throw new AppServiceTxException("Unexpected error while deleting appointment");
        }
    }

    @Transactional
    public void updateAppointment(AppointmentRequestDto appointmentRequestDto) {
        try {
            User user = validateAndGetUser(appointmentRequestDto.userId());
            Professional professional = validateAndGetProfessional(appointmentRequestDto.professionalId());

            Appointment existingAppointment = appointmentsRepository.findById(appointmentRequestDto.id())
                .orElseThrow(() -> new AppServiceTxException("Appointment with ID " + appointmentRequestDto.id() + " not found"));

            existingAppointment.setUser(user);
            existingAppointment.setProfessional(professional);
            existingAppointment.setAppointmentDate(LocalDate.parse(appointmentRequestDto.appointmentDate()));
            existingAppointment.setStartTime(LocalTime.parse(appointmentRequestDto.appointmentStartTime()));
            existingAppointment.setEndTime(LocalTime.parse(appointmentRequestDto.appointmentEndTime()));
            existingAppointment.setState(appointmentRequestDto.state());
            existingAppointment.setReason(appointmentRequestDto.reason());


            logService.saveLogAndFlush(user, existingAppointment, AppointmentLogsActions.STATE_UPDATE.getMessage());
            log.atInfo().log("Appointment updated successfully for user ID: {}",  appointmentRequestDto.userId());
        } catch (RepositoryTransactionException ex) {
            log.atError().log("Database error while updating appointment state: {}", ex.getMessage());
            throw new AppServiceTxException("Database error while updating appointment");
        } catch (Exception e) {
            log.atError().log("Unexpected error while updating appointment state: {}", e.getMessage());
            throw new AppServiceTxException("Unexpected error while updating appointment");
        }
    }

    @Transactional
    public void updateAppointmentIfAssignedToUser(AppointmentRequestDto appointmentRequestDto, String userEmail) {
        try {
            Appointment existingAppointment = appointmentsRepository.findById(appointmentRequestDto.id())
                .orElseThrow(() -> new AppServiceTxException("Appointment cannot be updated because it does not exist. ID: " + appointmentRequestDto.id() + ""));

            validateAppointmentOwnership(existingAppointment, userEmail);

            User user = validateAndGetUser(appointmentRequestDto.userId());
            Professional professional = validateAndGetProfessional(appointmentRequestDto.professionalId());

            existingAppointment.setUser(user);
            existingAppointment.setProfessional(professional);
            existingAppointment.setAppointmentDate(LocalDate.parse(appointmentRequestDto.appointmentDate()));
            existingAppointment.setStartTime(LocalTime.parse(appointmentRequestDto.appointmentStartTime()));
            existingAppointment.setEndTime(LocalTime.parse(appointmentRequestDto.appointmentEndTime()));
            existingAppointment.setState(appointmentRequestDto.state());
            existingAppointment.setReason(appointmentRequestDto.reason());

            logService.saveLogAndFlush(user, existingAppointment, AppointmentLogsActions.STATE_UPDATE.getMessage());
            log.atInfo().log("Appointment updated successfully for user ID: {} with professional ID: {}", appointmentRequestDto.userId(), appointmentRequestDto.professionalId());
        } catch (RepositoryTransactionException ex) {
            log.atError().log("Database error while updating appointment: {}", ex.getMessage());
            throw new AppServiceTxException("Database error while updating appointment");
        } catch (Exception e) {
            log.atError().log("Unexpected error while updating appointment: {}", e.getMessage());
            throw new AppServiceTxException("Unexpected error while updating appointment");
        }
    }

    private static Appointment fromDto(AppointmentRequestDto appointmentRequestDto, User user, Professional professional) {
        return Appointment.builder()
            .user(user)
            .professional(professional)
            .appointmentDate(LocalDate.parse(appointmentRequestDto.appointmentDate()))
            .startTime(LocalTime.parse(appointmentRequestDto.appointmentStartTime()))
            .endTime(LocalTime.parse(appointmentRequestDto.appointmentEndTime()))
            .state(appointmentRequestDto.state())
            .reason(appointmentRequestDto.reason())
            .build();
    }

    private User validateAndGetUser(Integer id) {
        return userService.findById(id).orElseThrow(() -> new AppServiceTxException(String.format(ValidationMessages.USER_NOT_FOUND_EXCEPTION, id)));
    }

    private Professional validateAndGetProfessional(Integer id) {
        return professionalService.findById(id).orElseThrow(() -> new AppServiceTxException(String.format(ValidationMessages.PROFESSIONAL_NOT_FOUND_EXCEPTION, id)));
    }

    private void validateAppointmentOwnership(Appointment appointment, String userEmail) {
        if (!appointment.getUser().getEmail().equals(userEmail)) {
            throw new AppServiceTxException("Appointment cannot be updated because the current user is not the owner of the appointment. User email: " + userEmail + ", Appointment email: " + appointment.getUser().getEmail() + ". Please, contact the administrator if you believe this is an error.");
        }
    }

}
