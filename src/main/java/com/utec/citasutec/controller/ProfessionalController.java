package com.utec.citasutec.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

import com.google.gson.Gson;
import com.utec.citasutec.model.dto.request.ProfessionalRequestDto;
import com.utec.citasutec.model.dto.response.ApiResponse;
import com.utec.citasutec.service.ProfessionalService;
import com.utec.citasutec.util.formatters.ConstraintFormatter;
import com.utec.citasutec.util.formatters.ResponseUtils;
import com.utec.citasutec.util.validators.ValidationMessages;

import jakarta.inject.Inject;
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
@WebServlet(name = "ProfessionalController", urlPatterns = { "/api/professionals" })
@ServletSecurity(@HttpConstraint(rolesAllowed = { "ADMIN" }))
public class ProfessionalController extends HttpServlet {
    public static final Gson GSON = new Gson();

    @Inject
    private ProfessionalService professionalService;

    @Inject
    private Validator validator;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ProfessionalRequestDto professionalRequestDto = GSON.fromJson(req.getReader(),
                    ProfessionalRequestDto.class);

            Set<ConstraintViolation<ProfessionalRequestDto>> violations = validator.validate(professionalRequestDto);

            if (!violations.isEmpty()) {
                log.atWarn().log("Validation errors while saving professional info: {}", violations);
                ResponseUtils.sendResponse(resp,
                        ApiResponse.validationError(ConstraintFormatter.getValidationErrors(violations)),
                        HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            professionalService.saveProfessional(professionalRequestDto);
            ResponseUtils.sendCreatedResponse(resp, "/api/professionals");
        } catch (Exception ex) {
            log.atError().log("unexpected Error while saving professional info: {}", ex.getMessage());
            ResponseUtils.sendUnexpectedError(resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int professionalIdParam = Integer.parseInt(req.getParameter("id"));

            if (professionalIdParam <= 0) {
                log.atWarn().log("Missing professionalId parameter in delete request");
                ResponseUtils.sendResponse(resp,
                        ApiResponse.validationError(new HashMap<>() {
                            {
                                put("professionalId", String.format(ValidationMessages.REQUIRED_FIELD_ERROR, "id"));
                            }
                        }),
                        HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            professionalService.deleteProfessionalById(professionalIdParam);
            ResponseUtils.sendNoContentResponse(resp);
        } catch (Exception ex) {
            log.atError().log("unexpected Error while deleting professional info: {}", ex.getMessage());
            ResponseUtils.sendUnexpectedError(resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ProfessionalRequestDto professionalRequestDto = GSON.fromJson(req.getReader(),
                    ProfessionalRequestDto.class);

            Set<ConstraintViolation<ProfessionalRequestDto>> violations = validator.validate(professionalRequestDto);

            if (!violations.isEmpty()) {
                log.atWarn().log("Validation errors while updating professional info: {}", violations);
                ResponseUtils.sendResponse(resp,
                        ApiResponse.validationError(ConstraintFormatter.getValidationErrors(violations)),
                        HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            professionalService.updateProfessional(professionalRequestDto);
            ResponseUtils.sendCommonSuccess(resp);
        } catch (Exception ex) {
            log.atError().log("unexpected Error while updating professional info: {}", ex.getMessage());
            ResponseUtils.sendUnexpectedError(resp);
        }
    }
}
