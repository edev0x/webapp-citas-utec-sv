package com.utec.citasutec.controller;

import com.utec.citasutec.model.dto.LoginDto;
import com.utec.citasutec.util.AttributeIdentifiers;
import com.utec.citasutec.util.formatters.ConstraintFormatter;
import com.utec.citasutec.util.formatters.Obfuscator;
import com.utec.citasutec.util.validators.ValidationMessages;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Slf4j
@WebServlet(name = "LoginController", urlPatterns = { "/login", "/authenticate" })
public class LoginController extends HttpServlet {
    @Inject
    private SecurityContext securityContext;

    @Inject
    private Validator validator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter(AttributeIdentifiers.EMAIL_INPUT);
        String password = req.getParameter(AttributeIdentifiers.PASSWORD_INPUT);

        Set<ConstraintViolation<LoginDto>> constraintViolations = validator.validate(new LoginDto(email, password, false));

        if (!constraintViolations.isEmpty()) {
            Map<String, String> validationErrors = ConstraintFormatter.getValidationErrors(constraintViolations);
            req.setAttribute(AttributeIdentifiers.VALIDATION_ERRORS, validationErrors);
            req.setAttribute(AttributeIdentifiers.EMAIL_INPUT, email);
            req.setAttribute(AttributeIdentifiers.PASSWORD_INPUT, password);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
            dispatcher.forward(req,resp);
            return;
        }

        try {
            AuthenticationParameters parameters = AuthenticationParameters.withParams()
                .credential(new UsernamePasswordCredential(email, password));

            AuthenticationStatus authStatus = securityContext.authenticate(req, resp, parameters);

            String obfuscatedEmail = Obfuscator.obfuscateEmail(email);

            switch (authStatus) {
                case SEND_FAILURE -> {
                    log.atError().log("Authentication failed for user {}", obfuscatedEmail);
                    req.setAttribute(AttributeIdentifiers.INVALID_CREDENTIALS, ValidationMessages.INVALID_CREDENTIALS);
                    req.setAttribute(AttributeIdentifiers.EMAIL_INPUT, email);
                    req.setAttribute(AttributeIdentifiers.PASSWORD_INPUT, password);
                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
                }
                case SEND_CONTINUE -> {
                    // Do nothing - the authentication mechanism is handling the response.
                    log.atDebug().log("Authentication continuing for user {}", obfuscatedEmail);
                }
                case SUCCESS -> {
                    log.atInfo().log("User {} successfully authenticated", obfuscatedEmail);
                    resp.sendRedirect(req.getContextPath() + "/app/s/home");
                }
                default -> {
                    log.atWarn().log("Unexpected authentication status: {}", obfuscatedEmail);
                    resp.sendRedirect(req.getContextPath() + "/login");
                }
            }
        } catch (Exception e) {
            log.atError().log("Authentication failed: {}", e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
