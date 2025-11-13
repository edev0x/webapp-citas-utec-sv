package com.utec.citasutec.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.utec.citasutec.model.dto.request.AppointmentRequestDto;
import com.utec.citasutec.model.dto.response.ApiResponse;
import com.utec.citasutec.model.dto.response.AppointmentResponseDto;
import com.utec.citasutec.service.AppointmentService;
import com.utec.citasutec.util.ServletUtils;
import com.utec.citasutec.util.exceptions.AppServiceTxException;
import com.utec.citasutec.util.exceptions.RepositoryTransactionException;
import com.utec.citasutec.util.formatters.ConstraintFormatter;
import com.utec.citasutec.util.formatters.LocalDateGsonAdapter;
import com.utec.citasutec.util.formatters.ResponseUtils;
import com.utec.citasutec.util.validators.ValidationMessages;

import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "AppointmentsController", urlPatterns = {"/appointments"})
@ServletSecurity(@HttpConstraint(rolesAllowed = {"ADMIN", "AUDITOR", "ESTUDIANTE", "PROFESIONAL"}))
public class AppointmentsController extends HttpServlet {
    private static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateGsonAdapter())
        .setPrettyPrinting().create();

    @Inject
    private AppointmentService appointmentService;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private Validator validator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setJsonAsDefaultContentType(resp);
        try {
            boolean isAdmin = securityContext.isCallerInRole("ADMIN");
            boolean isAuditor = securityContext.isCallerInRole("AUDITOR");

            if (isAdmin || isAuditor) {
                List<AppointmentResponseDto> allAppointments = appointmentService.findAll();
                ResponseUtils.sendSuccessResponseWithContent(resp, allAppointments);
            } else {
                List<AppointmentResponseDto> appointmentsByUser = appointmentService.findAllAppointmentsCreatedByUser(securityContext.getCallerPrincipal().getName());
                ResponseUtils.sendSuccessResponseWithContent(resp, appointmentsByUser);
            }
        } catch (Exception e) {
            log.atError().log("Unexpected error while getting data: {}", e.getMessage());
            ResponseUtils.sendUnexpectedError(resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setJsonAsDefaultContentType(resp);
        try {
            AppointmentRequestDto appointmentRequestDto = GSON.fromJson(req.getReader(), AppointmentRequestDto.class);
            Set<ConstraintViolation<AppointmentRequestDto>> constraintViolations = validator.validate(appointmentRequestDto);

            if (!constraintViolations.isEmpty()) {
                log.error("Validation errors: {}", constraintViolations);
                ResponseUtils.sendResponse(resp, ApiResponse.validationError(ConstraintFormatter
                    .getValidationErrors(constraintViolations)), HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            log.atInfo().log("Appointment request received: {}", appointmentRequestDto);

            appointmentService.createAppointment(appointmentRequestDto);
            ResponseUtils.sendCreatedResponse(resp, "/appointments");
        } catch (Exception e) {
            log.error("Error parsing request body: {}", e.getMessage());
            ResponseUtils.sendUnexpectedError(resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setJsonAsDefaultContentType(resp);
        try {
            boolean isAdmin = securityContext.isCallerInRole("ADMIN");
            Integer appointmentId = Integer.parseInt(req.getParameter("id"));
            if (isAdmin) {
                appointmentService.deleteById(appointmentId);
            } else {
                appointmentService.deleteIfAssignedToUser(appointmentId, securityContext.getCallerPrincipal().getName());
            }

            ResponseUtils.sendNoContentResponse(resp);
        } catch (RepositoryTransactionException ex) {
            log.atError().log("Repository transaction failed: {}", ex.getMessage());
            ResponseUtils.sendUnexpectedError(resp);
        } catch (Exception e) {
            log.atError().log("Error parsing request body: {}", e.getMessage());
            ResponseUtils.sendUnexpectedError(resp);
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setJsonAsDefaultContentType(resp);
        try {
            AppointmentRequestDto appointmentRequestDto = GSON.fromJson(req.getReader(), AppointmentRequestDto.class);
            Set<ConstraintViolation<AppointmentRequestDto>> constraintViolations = validator.validate(appointmentRequestDto);

            if (!constraintViolations.isEmpty()) {
                log.error("Validation errors on update: {}", constraintViolations);
                ResponseUtils.sendResponse(resp, ApiResponse.validationError(ConstraintFormatter
                        .getValidationErrors(constraintViolations)), HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            boolean isAdmin = securityContext.isCallerInRole("ADMIN");

            if (isAdmin) {
                this.appointmentService.updateAppointment(appointmentRequestDto);
            } else {
                this.appointmentService.updateAppointmentIfAssignedToUser(
                    appointmentRequestDto,
                    securityContext.getCallerPrincipal().getName()
                );
            }

            ResponseUtils.sendResponse(resp, ApiResponse.success(ValidationMessages.SUCCESSFUL_OPERATION), HttpServletResponse.SC_OK);
        } catch (AppServiceTxException e) {
            log.atError().log("Service transaction failed: {}", e.getMessage());
            ResponseUtils.sendResponse(resp, ApiResponse.error(ValidationMessages.UNEXPECTED_ERROR), HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            log.atError().log("Unexpected error: {}", e.getMessage());
            ResponseUtils.sendUnexpectedError(resp);
        }
    }
}
