package com.utec.citasutec.controller;

import com.google.gson.Gson;
import com.utec.citasutec.model.dto.request.UserRequestDto;
import com.utec.citasutec.model.dto.response.ApiResponse;
import com.utec.citasutec.model.dto.response.UserResponseDto;
import com.utec.citasutec.model.ejb.SecurityBean;
import com.utec.citasutec.service.UserService;
import com.utec.citasutec.util.AttributeIdentifiers;
import com.utec.citasutec.util.ServletUtils;
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

import java.io.IOException;
import java.util.*;

@Slf4j
@WebServlet(name = "userController", value = "/api/users")
@ServletSecurity(@HttpConstraint(rolesAllowed = {"ADMIN"}))
public class UserController extends HttpServlet {

    private static final Gson GSON = new Gson();

    @Inject
    private UserService userService;

    @Inject
    private SecurityBean securityBean;

    @Inject
    private Validator validator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserResponseDto> users = userService.findAllWithRoles();
        ResponseUtils.sendSuccessResponseWithContent(resp, users);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       try {
           ServletUtils.setJsonAsDefaultContentType(resp);

           UserRequestDto userRequestDto = this.parseAndValidateRequest(req, resp);
           if (Objects.isNull(userRequestDto)) {
               return;
           }

           boolean emailExists = userService.findByEmail(userRequestDto.email()) != null;

           if (emailExists) {
               ResponseUtils.sendResponse(resp, ApiResponse.validationError(new HashMap<>(){
                   {
                       put(AttributeIdentifiers.RESOURCE_EXISTS, ValidationMessages.USER_ALREADY_EXISTS);
                   }
               }), HttpServletResponse.SC_CONFLICT);
               return;
           }

           userService.save(userRequestDto);
           ResponseUtils.sendCreatedResponse(resp, "/api/users");
       } catch (Exception e) {
           log.error("Error creating user: {}", e.getMessage());
           ResponseUtils.sendUnexpectedError(resp);
       }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Deleting user...");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<UserResponseDto> users = userService.findAllWithRoles();

            String currentUserEmail = securityBean.getUsername();

            UserResponseDto currentUser = users.stream().filter(u -> u.email().equals(currentUserEmail)).findFirst().orElse(null);

            if (Objects.isNull(currentUser)) {
                ResponseUtils.sendResponse(resp, ApiResponse.error(ValidationMessages.CANNOT_DELETE_SAME_USER), HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            Integer userId = Integer.parseInt(req.getParameter("id"));

            long totalAdmins = users.stream()
                .filter(u -> Objects.nonNull(u.role()) && u.role().name().equals("ADMIN"))
                .count();

            boolean isUserToBeDeletedAnAdmin = users.stream()
                .filter(u -> u.id().equals(userId))
                .anyMatch(u -> Objects.nonNull(u.role()) && u.role().name().equals("ADMIN"));

            if (totalAdmins == 1 && isUserToBeDeletedAnAdmin) {
                ResponseUtils.sendResponse(resp, ApiResponse.error(ValidationMessages.CANNOT_DELETE_DEFAULT_ADMIN), HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            userService.deleteById(userId);
            ResponseUtils.sendNoContentResponse(resp);
        } catch (Exception e) {
            log.error("Error deleting user: {}", e.getMessage());
            ResponseUtils.sendUnexpectedError(resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ServletUtils.setJsonAsDefaultContentType(resp);

            UserRequestDto userRequestDto = this.parseAndValidateRequest(req, resp);

            if (Objects.isNull(userRequestDto)) {
                return;
            }
            userService.updateUser(userRequestDto);
            ResponseUtils.sendCommonSuccess(resp);
        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage());
            ResponseUtils.sendUnexpectedError(resp);
        }
    }

    private UserRequestDto parseAndValidateRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserRequestDto userRequestDto = GSON.fromJson(req.getReader(), UserRequestDto.class);
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        if (!violations.isEmpty()) {
            ResponseUtils.sendResponse(resp, ApiResponse.validationError(ConstraintFormatter.getValidationErrors(violations)), HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        return userRequestDto;
    }
}
