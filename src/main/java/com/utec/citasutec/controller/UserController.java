package com.utec.citasutec.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.utec.citasutec.model.dto.request.UserRequestDto;
import com.utec.citasutec.model.dto.response.UserResponseDto;
import com.utec.citasutec.model.ejb.SecurityBean;
import com.utec.citasutec.service.UserService;
import com.utec.citasutec.util.AttributeIdentifiers;
import com.utec.citasutec.util.formatters.ConstraintFormatter;
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

    private static final Gson GSON = new GsonBuilder().create();

    @Inject
    private UserService userService;

    @Inject
    private SecurityBean securityBean;

    @Inject
    private Validator validator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserResponseDto> users = userService.findAllWithRoles();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String jsonResponse = new Gson().toJson(users);
        resp.getWriter().write(jsonResponse);
        resp.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getMethod().equalsIgnoreCase("POST")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Method Not Allowed");
            return;
        }

        setupResponseDefaults(resp);

        HashMap<String, Object> responseMap = new HashMap<>();

        UserRequestDto userRequestDto = this.parseAndValidateRequest(req, resp);
        if (Objects.isNull(userRequestDto)) {
            return;
        }

        boolean emailExists = userService.findByEmail(userRequestDto.email()) != null;

        if (emailExists) {
            responseMap.put(AttributeIdentifiers.RESOURCE_EXISTS, ValidationMessages.USER_ALREADY_EXISTS);
            responseMap.put(AttributeIdentifiers.ERROR, true);
            sendResponse(resp, HttpServletResponse.SC_CONFLICT, responseMap);
            return;
        }

        userService.save(userRequestDto);
        responseMap.put(AttributeIdentifiers.MESSAGE, ValidationMessages.SUCCESSFUL_OPERATION);
        responseMap.put(AttributeIdentifiers.ERROR, false);

        sendResponse(resp, HttpServletResponse.SC_CREATED, responseMap);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getMethod().equalsIgnoreCase("DELETE")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Method Not Allowed");
            return;
        }
        log.info("Deleting user...");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            List<UserResponseDto> users = userService.findAllWithRoles();

            String currentUserEmail = securityBean.getUsername();

            UserResponseDto currentUser = users.stream().filter(u -> u.email().equals(currentUserEmail)).findFirst().orElse(null);

            if (Objects.isNull(currentUser)) {
                responseMap.put(AttributeIdentifiers.ERROR, true);
                responseMap.put(AttributeIdentifiers.MESSAGE, ValidationMessages.CANNOT_DELETE_SAME_USER);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(GSON.toJson(responseMap));
                resp.getWriter().flush();
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
                responseMap.put(AttributeIdentifiers.ERROR, true);
                responseMap.put(AttributeIdentifiers.MESSAGE, ValidationMessages.CANNOT_DELETE_DEFAULT_ADMIN);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(GSON.toJson(responseMap));
                resp.getWriter().flush();
                return;
            }

            userService.deleteById(userId);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            resp.getWriter().write(GSON.toJson(responseMap));
            resp.getWriter().flush();
        } catch (Exception e) {
            log.error("Error deleting user: {}", e.getMessage());
            responseMap.put(AttributeIdentifiers.ERROR, true);
            responseMap.put(AttributeIdentifiers.MESSAGE, ValidationMessages.UNEXPECTED_ERROR);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(GSON.toJson(e.getMessage()));
            resp.getWriter().flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getMethod().equalsIgnoreCase("PUT")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Method Not Allowed");
            return;
        }

        setupResponseDefaults(resp);

        HashMap<String, Object> responseMap = new HashMap<>();

        UserRequestDto userRequestDto = this.parseAndValidateRequest(req, resp);

        if (Objects.isNull(userRequestDto)) {
            return;
        }

        userService.updateUser(userRequestDto);
        responseMap.put(AttributeIdentifiers.MESSAGE, ValidationMessages.SUCCESSFUL_OPERATION);
        responseMap.put(AttributeIdentifiers.ERROR, false);
        sendResponse(resp, HttpServletResponse.SC_OK, responseMap);
    }

    private UserRequestDto parseAndValidateRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserRequestDto userRequestDto = GSON.fromJson(req.getReader(), UserRequestDto.class);
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        if (!violations.isEmpty()) {
            sendErrorResponse(resp, ConstraintFormatter.getValidationErrors(violations));
            return null;
        }

        return userRequestDto;
    }

    private void sendErrorResponse(HttpServletResponse resp, Object errorValue)
        throws IOException {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put(AttributeIdentifiers.VALIDATION_ERRORS, errorValue);
        responseMap.put(AttributeIdentifiers.ERROR, true);
        sendResponse(resp, HttpServletResponse.SC_BAD_REQUEST, responseMap);
    }

    private void setupResponseDefaults(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    private void sendResponse(HttpServletResponse resp, int status, HashMap<String, Object> responseMap)
        throws IOException {
        resp.setStatus(status);
        resp.getWriter().write(GSON.toJson(responseMap));
        resp.getWriter().flush();
    }
}
