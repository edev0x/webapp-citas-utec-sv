package com.utec.citasutec.service;

import java.util.List;
import java.util.Optional;

import com.utec.citasutec.model.dto.request.AppointmentRequestDto;
import com.utec.citasutec.model.dto.response.AppointmentByMonthResponse;
import com.utec.citasutec.model.dto.response.AppointmentByStateResponse;
import com.utec.citasutec.model.entity.Appointment;
import com.utec.citasutec.model.entity.Professional;
import com.utec.citasutec.model.entity.User;
import com.utec.citasutec.repository.AppointmentsRepository;

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
    private void createAppointment(AppointmentRequestDto appointmentRequestDto) {
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
}
