package com.utec.citasutec.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.utec.citasutec.model.dto.request.AppointmentRequestDto;
import com.utec.citasutec.model.dto.response.AppointmentResponse;
import com.utec.citasutec.model.dto.response.AppointmentResponseDto;
import com.utec.citasutec.service.AppointmentService;
import com.utec.citasutec.util.AttributeIdentifiers;
import com.utec.citasutec.util.ServletUtils;
import com.utec.citasutec.util.formatters.ConstraintFormatter;
import com.utec.citasutec.util.formatters.LocalDateGsonAdapter;
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

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Slf4j
@WebServlet(name = "AppointmentsController", urlPatterns = { "/appointments" })
@ServletSecurity(@HttpConstraint(rolesAllowed = { "ADMIN", "AUDITOR", "ESTUDIANTE", "PROFESIONAL" }))
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
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(new Gson().toJson(allAppointments));
                resp.getWriter().flush();
            } else {
                List<AppointmentResponseDto> appointmentsByUser = appointmentService.findAllAppointmentsCreatedByUser(securityContext.getCallerPrincipal().getName());
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(new Gson().toJson(appointmentsByUser));
                resp.getWriter().flush();
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(new Gson().toJson(new HashMap<String, Object>() {{
                put(AttributeIdentifiers.ERROR, true);
                put(AttributeIdentifiers.MESSAGE, ValidationMessages.UNEXPECTED_ERROR);
            }}));
            resp.getWriter().flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setJsonAsDefaultContentType(resp);
        try {
            HashMap<String, Object> responseMap = new HashMap<>();
            AppointmentRequestDto appointmentRequestDto = GSON.fromJson(req.getReader(), AppointmentRequestDto.class);
            Set<ConstraintViolation<AppointmentRequestDto>> constraintViolations = validator.validate(appointmentRequestDto);

            if (!constraintViolations.isEmpty()) {
                log.error("Validation errors: {}", constraintViolations);
                responseMap.put(AttributeIdentifiers.ERROR, true);
                responseMap.put(AttributeIdentifiers.VALIDATION_ERRORS, ConstraintFormatter.getValidationErrors(constraintViolations));
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(new Gson().toJson(responseMap));
                resp.getWriter().flush();
                return;
            }


            log.atInfo().log("Appointment request received: {}", appointmentRequestDto);

            appointmentService.createAppointment(appointmentRequestDto);
            responseMap.put(AttributeIdentifiers.MESSAGE, ValidationMessages.SUCCESSFUL_OPERATION);
            responseMap.put(AttributeIdentifiers.ERROR, false);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(new Gson().toJson(responseMap));
            resp.getWriter().flush();

        } catch (Exception e) {
            log.error("Error parsing request body: {}", e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(new Gson().toJson(new HashMap<String, Object>() {{
                put(AttributeIdentifiers.ERROR, true);
                put(AttributeIdentifiers.MESSAGE, ValidationMessages.UNEXPECTED_ERROR);
            }}));
            resp.getWriter().flush();
        }
    }
}
