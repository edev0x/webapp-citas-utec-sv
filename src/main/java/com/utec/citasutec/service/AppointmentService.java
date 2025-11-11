package com.utec.citasutec.service;

import java.util.List;
import java.util.Optional;

import com.utec.citasutec.model.dto.request.AppointmentRequestDto;
import com.utec.citasutec.model.dto.response.AppointmentByMonthResponse;
import com.utec.citasutec.model.dto.response.AppointmentByStateResponse;
import com.utec.citasutec.model.dto.response.AppointmentResponse;
import com.utec.citasutec.model.entity.Appointment;
import com.utec.citasutec.model.entity.Professional;
import com.utec.citasutec.model.entity.User;
import com.utec.citasutec.repository.AppointmentsRepository;
import com.utec.citasutec.repository.factory.Paginated;
import com.utec.citasutec.util.exceptions.AppServiceTxException;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Stateless
public class AppointmentService {

    @Inject
    private AppointmentsRepository appointmentsRepository;

    @Inject
    private UserService userService;

    @Inject
    private ProfessionalService professionalService;

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
        Optional<User> user = userService.findById(appointmentRequestDto.userId());
        Optional<Professional> professional = professionalService.findById(appointmentRequestDto.professionalId());

        if (user.isPresent() && professional.isPresent()) {
            Appointment appointment = new Appointment();
            appointment.setUser(user.get());
            appointment.setProfessional(professional.get());
            appointment.setAppointmentDate(appointmentRequestDto.appointmentDate());
            appointment.setState(appointmentRequestDto.state());

            appointmentsRepository.save(appointment);
            log.atInfo().log("Appointment created successfully for user ID: {} with professional ID: {}",
                    appointmentRequestDto.userId(), appointmentRequestDto.professionalId());
        }
    }

    public List<AppointmentResponse> findAllAppointments() {
        try {
            return appointmentsRepository.findAllAppointments();
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
}
