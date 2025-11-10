package com.utec.citasutec.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.utec.citasutec.model.dto.response.UserDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@WebServlet(name = "userController", value = "/api/users")
@ServletSecurity(@HttpConstraint(rolesAllowed = { "ADMIN" }))
public class UserController extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Inject
    private UserService userService;

    @Inject
    private Validator validator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDto> users = userService.findAllWithRoles();
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

        log.info("Creating new user...");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        HashMap<String, Object> responseMap = new HashMap<>();

        UserDto userDto = GSON.fromJson(req.getReader(), UserDto.class);

        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        if (!constraintViolations.isEmpty()) {
            Map<String, String> validationErrors = ConstraintFormatter.getValidationErrors(constraintViolations);
            responseMap.put(AttributeIdentifiers.VALIDATION_ERRORS, validationErrors);
            responseMap.put("error", true);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(GSON.toJson(responseMap));
            resp.getWriter().flush();
            return;
        }

        boolean emailExists = userService.findByEmail(userDto.email()) != null;

        if (emailExists) {
            responseMap.put(AttributeIdentifiers.RESOURCE_EXISTS, ValidationMessages.USER_ALREADY_EXISTS);
            responseMap.put("error", true);
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getWriter().write(GSON.toJson(responseMap));
            resp.getWriter().flush();
            return;
        }
        
        userService.save(userDto);
        responseMap.put("message", "User created successfully");;
        responseMap.put("error", false);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(GSON.toJson(responseMap));
        resp.getWriter().flush();
    }
}
