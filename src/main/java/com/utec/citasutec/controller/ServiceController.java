package com.utec.citasutec.controller;

import com.utec.citasutec.model.dto.response.ProfessionalServiceResponseDto;
import com.utec.citasutec.model.ejb.SecurityBean;
import com.utec.citasutec.service.ProfessionalService;
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
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@WebServlet(name = "ServiceController", urlPatterns = { "/api/services" })
@ServletSecurity(@HttpConstraint(rolesAllowed = { "ADMIN", "PROFESIONAL" }))
public class ServiceController extends HttpServlet {

    @Inject
    private ProfessionalService professionalService;

    @Inject
    private SecurityBean securityBean;

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
        super.doPost(req, resp);
    }
}
