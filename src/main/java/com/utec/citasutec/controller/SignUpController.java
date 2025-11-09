package com.utec.citasutec.controller;

import com.utec.citasutec.model.dto.request.RegisterDto;
import com.utec.citasutec.service.AuthService;
import com.utec.citasutec.util.formatters.ConstraintFormatter;
import com.utec.citasutec.util.validators.ValidationMessages;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

@WebServlet(name = "SignUpController", value = "/signup")
public class SignUpController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(SignUpController.class.getName());

    @Inject
    private AuthService authService;

    @Inject
    private Validator validator;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        RegisterDto registerDto = new RegisterDto(firstName, lastName, email, password);

        Set<ConstraintViolation<RegisterDto>> constraintViolations = validator.validate(registerDto);

        if (!constraintViolations.isEmpty()) {
            Map<String, String> validationErrors = ConstraintFormatter.getValidationErrors(constraintViolations);
            logger.info("Validation errors: " + validationErrors);
            req.setAttribute("validationErrors", validationErrors);
            req.setAttribute("registerDto", registerDto);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        if (authService.isEmailTaken(email)) {
            req.setAttribute("emailTaken", true);
            req.setAttribute("emailTakenError", ValidationMessages.ALREADY_EXISTS);
            req.setAttribute("registerDto", registerDto);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        authService.signUp(registerDto);
        req.setAttribute("registrationSuccess", true);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
