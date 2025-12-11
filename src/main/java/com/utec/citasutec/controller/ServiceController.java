package com.utec.citasutec.controller;

import com.google.gson.Gson;
import com.utec.citasutec.model.dto.request.ProfessionalRequestDto;
import com.utec.citasutec.model.dto.request.ProfessionalServiceRequestDto;
import com.utec.citasutec.model.dto.response.ApiResponse;
import com.utec.citasutec.model.dto.response.ProfessionalServiceResponseDto;
import com.utec.citasutec.model.ejb.SecurityBean;
import com.utec.citasutec.service.ProfessionalService;
import com.utec.citasutec.util.formatters.ConstraintFormatter;
import com.utec.citasutec.util.formatters.ResponseUtils;
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
import java.util.List;
import java.util.Set;

@Slf4j
@WebServlet(name = "ServiceController", urlPatterns = { "/api/services" })
@ServletSecurity(@HttpConstraint(rolesAllowed = { "ADMIN", "PROFESIONAL" }))
public class ServiceController extends HttpServlet {
    private static final Gson Gson = new Gson();

    @Inject
    private ProfessionalService professionalService;

    @Inject
    private SecurityBean securityBean;

    @Inject
    private Validator validator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (securityBean.isAdmin()) {
                ResponseUtils.sendSuccessResponseWithContent(resp, professionalService.findAll());
            } else {
                ResponseUtils.sendSuccessResponseWithContent(resp, professionalService.findAllServicesByProfessionalEmail(securityBean.getUsername()));
            }
        } catch (Exception ex) {
            log.atError().log("unexpected Error while getting services: {}", ex.getMessage());
            ResponseUtils.sendUnexpectedError(resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ProfessionalServiceRequestDto requestDto = Gson.fromJson(req.getReader(), ProfessionalServiceRequestDto.class);

            Set<ConstraintViolation<ProfessionalServiceRequestDto>> constraintViolations = validator.validate(requestDto);

            if (!constraintViolations.isEmpty()) {
                ResponseUtils.sendResponse(resp,
                    ApiResponse.validationError(ConstraintFormatter.getValidationErrors(constraintViolations)),
                    HttpServletResponse.SC_BAD_REQUEST
                    );
            }

            professionalService.saveService(requestDto);
            ResponseUtils.sendCreatedResponse(resp, "/api/services");
        } catch (Exception ex) {
            log.atError().log("Unexpected error when trying to create new service: {}", ex.getMessage());
            ResponseUtils.sendUnexpectedError(resp);
        }
    }
}
