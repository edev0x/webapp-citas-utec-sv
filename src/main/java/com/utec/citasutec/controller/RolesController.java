package com.utec.citasutec.controller;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

import com.google.gson.Gson;
import com.utec.citasutec.model.dto.request.RoleRequestDto;
import com.utec.citasutec.model.dto.response.ApiResponse;
import com.utec.citasutec.service.RoleService;
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
@WebServlet(name = "RolesController", urlPatterns = { "/api/roles" })
@ServletSecurity(@HttpConstraint(rolesAllowed = { "ADMIN" }))
public class RolesController extends HttpServlet {
    private static final Gson GSON = new Gson();

    @Inject
    private RoleService roleService;

    @Inject
    private Validator validator;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RoleRequestDto roleRequestDto = GSON.fromJson(req.getReader(),
                    RoleRequestDto.class);

            Set<ConstraintViolation<RoleRequestDto>> violations = validator.validate(roleRequestDto);

            if (!violations.isEmpty()) {
                ResponseUtils.sendResponse(resp,
                        ApiResponse.validationError(ConstraintFormatter.getValidationErrors(violations)),
                        HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            roleService.save(roleRequestDto);
            ResponseUtils.sendCreatedResponse(resp, "/api/roles");
        } catch (Exception ex) {
            log.atError().log("unexpected Error while saving role info: {}", ex.getMessage());
            ResponseUtils.sendUnexpectedError(resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String roleIdParam = req.getParameter("roleId");

            if (Objects.isNull(roleIdParam) || roleIdParam.isEmpty()) {
                log.atWarn().log("Missing roleId parameter in delete request");
                ResponseUtils.sendResponse(resp, ApiResponse.error(ValidationMessages.ROLE_ID_REQUIRED),
                        HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Integer roleId = Integer.parseInt(roleIdParam);
            roleService.deleteById(roleId);
            ResponseUtils.sendNoContentResponse(resp);
        } catch (Exception ex) {
            log.atError().log("unexpected Error while deleting role info: {}", ex.getMessage());
            ResponseUtils.sendUnexpectedError(resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RoleRequestDto roleRequestDto = GSON.fromJson(req.getReader(),
                    RoleRequestDto.class);

            Set<ConstraintViolation<RoleRequestDto>> violations = validator.validate(roleRequestDto);

            if (!violations.isEmpty()) {
                ResponseUtils.sendResponse(resp,
                        ApiResponse.validationError(ConstraintFormatter.getValidationErrors(violations)),
                        HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            roleService.update(roleRequestDto);
            ResponseUtils.sendCommonSuccess(resp);
        } catch (Exception ex) {
            log.atError().log("unexpected Error while updating role info: {}", ex.getMessage());
            ResponseUtils.sendUnexpectedError(resp);
        }
    }
}
