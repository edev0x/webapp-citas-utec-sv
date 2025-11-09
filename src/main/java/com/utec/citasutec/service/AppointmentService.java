package com.utec.citasutec.service;

import com.utec.citasutec.model.dto.request.AppointmentRequestDto;
import com.utec.citasutec.model.dto.response.AppointmentByMonthResponse;
import com.utec.citasutec.model.dto.response.AppointmentByStateResponse;
import com.utec.citasutec.model.entity.Appointment;
import com.utec.citasutec.repository.AppointmentsRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class AppointmentService {

    @Inject
    private AppointmentsRepository appointmentsRepository;

    @Inject
    private UserService userService;

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
}
