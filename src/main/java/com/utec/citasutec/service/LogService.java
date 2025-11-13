package com.utec.citasutec.service;

import com.utec.citasutec.model.entity.Appointment;
import com.utec.citasutec.model.entity.Log;
import com.utec.citasutec.model.entity.User;
import com.utec.citasutec.repository.LogRepository;
import com.utec.citasutec.util.exceptions.AppServiceTxException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Stateless
public class LogService {
    private static final String DEFAULT_COMMENT = "No definido";

    @Inject
    private LogRepository logRepository;

    public void saveLog(User user, Appointment appointment, String actionPerformed) {
        try {
            Log log = new Log();
            log.setUser(user);
            log.setAppointment(appointment);
            log.setAction(actionPerformed);
            log.setComment(DEFAULT_COMMENT);

            this.logRepository.saveLog(log);
        } catch (Exception e) {
            log.atError().log("Error saving log: {}", e.getMessage());
            throw new AppServiceTxException("Error while saving log");
        }
    }

    public Log saveLogAndFlush(User user, Appointment appointment, String actionPerformed) {
        try {
            Log log = new Log();
            log.setUser(user);
            log.setAppointment(appointment);
            log.setAction(actionPerformed);
            log.setComment(DEFAULT_COMMENT);

            return this.logRepository.saveLogAndFlush(log);
        } catch (Exception e) {
            log.atError().log("Error saving new log: {}", e.getMessage());
            throw new AppServiceTxException("Error while saving log");
        }
    }
}
