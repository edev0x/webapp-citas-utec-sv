package com.utec.citasutec.repository;

import com.utec.citasutec.model.dto.response.AppointmentByMonthResponse;
import com.utec.citasutec.model.dto.response.AppointmentByStateResponse;
import com.utec.citasutec.model.dto.response.AppointmentResponse;
import com.utec.citasutec.model.entity.Appointment;
import com.utec.citasutec.repository.factory.CrudRepository;
import jakarta.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@Stateless
public class AppointmentsRepository extends CrudRepository<Appointment> {

    public AppointmentsRepository() {
        super(Appointment.class);
    }

    public long countByProfessionalId(Integer id) {
        return em.createQuery("SELECT COUNT(a) FROM Appointment a WHERE a.professional.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public long countByState(String state) {
        return em.createQuery("SELECT COUNT(a) FROM Appointment a WHERE a.state = :state", Long.class)
                .setParameter("state", state)
                .getSingleResult();
    }

    public List<AppointmentByMonthResponse> countAppointmentsByMonth() {
        try {
            List<Object[]> results = em.createQuery(
                    "SELECT YEAR(a.appointmentDate) as year, MONTH(a.appointmentDate) as month, COUNT(a) as count " +
                            "FROM Appointment a " +
                            "GROUP BY YEAR(a.appointmentDate), MONTH(a.appointmentDate) " +
                            "ORDER BY YEAR(a.appointmentDate) ASC, MONTH(a.appointmentDate) ASC",
                    Object[].class)
                    .getResultList();

            return results.stream()
                    .map(row -> new AppointmentByMonthResponse(
                            ((Number) row[0]).intValue(),
                            ((Number) row[1]).intValue(),
                            ((Number) row[2]).intValue()))
                    .toList();
        } catch (Exception e) {
            log.atError().log("Error counting appointments by month: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<AppointmentByStateResponse> countAppointmentsByState() {
        try {
            List<Object[]> results = em.createQuery(
                    "SELECT COUNT(a) as count, a.state as state  " +
                            "FROM Appointment a " +
                            "GROUP BY a.state " +
                            "ORDER BY a.state ASC",
                    Object[].class)
                    .getResultList();

            return results.stream()
                    .map(row -> new AppointmentByStateResponse(
                            ((Number) row[0]).intValue(),
                            (String) row[1]))
                    .toList();
        } catch (Exception ex) {
            log.atError().log("Error counting appointments by state: {}", ex.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Appointment> findAllAppointments() {
        return em.createQuery("SELECT a FROM Appointment a JOIN FETCH a.user JOIN FETCH a.professional ", Appointment.class)
            .getResultList();
    }

    public List<AppointmentResponse> findAllUpcomingAppointments() {
        try {
            List<Appointment> appointments = em
                    .createQuery(
                            "SELECT a FROM Appointment a JOIN FETCH a.user JOIN FETCH a.professional WHERE a.appointmentDate > CURRENT_TIMESTAMP ORDER BY a.appointmentDate ASC, a.startTime ASC",
                            Appointment.class)
                    .setMaxResults(5)
                    .getResultList();

            return appointments.stream()
                    .map(AppointmentResponse::fromEntity)
                    .toList();
        } catch (Exception e) {
            log.atError().log("Error retrieving upcoming appointments: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Appointment> findAllAppointmentsByUser(String email) {
        return em.createQuery("SELECT a FROM Appointment a JOIN FETCH a.user u JOIN FETCH a.professional WHERE u.email = :email", Appointment.class)
            .getResultList();
    }
}
