package com.utec.citasutec.controller;

import java.io.IOException;
import java.util.Objects;

import com.utec.citasutec.service.ProfessionalService;
import com.utec.citasutec.service.RoleService;
import com.utec.citasutec.service.UserService;
import com.utec.citasutec.util.AppointmentState;
import com.utec.citasutec.util.ResourceConstants;
import com.utec.citasutec.util.exceptions.UtecAppException;
import com.utec.citasutec.util.security.RedirectUtils;

import com.utec.citasutec.util.validators.ValidationConstants;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "AdminResourcesController", urlPatterns = { "/app/manage" })
@ServletSecurity(@HttpConstraint(rolesAllowed = { "ADMIN" }))
public class AdminResourcesController extends HttpServlet {

    protected int page = 1;
    protected int recordsPerPage = 10;

    @Inject
    private UserService userService;

    @Inject
    private RoleService roleService;

    @Inject
    private ProfessionalService professionalService;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Admin resource management request received.");
        String resource = req.getParameter("resource");

        if (Objects.isNull(resource) || resource.isBlank()) {
            log.warn("Resource parameter is missing or empty. Redirecting to admin dashboard.");
            resp.sendRedirect(req.getContextPath() + "/app/dashboard");
            return;
        }

        if (!resource.matches(ValidationConstants.RESOURCE_PATTERN)) {
            log.warn("Invalid resource parameter: {}. Redirecting to admin dashboard.", resource);
            resp.sendRedirect(req.getContextPath() + "/app/dashboard?error=invalid_resource");
            return;
        }

        switch (resource) {
            case ResourceConstants.USERS:
                handleGetUserResource(req, resp);
                break;
            case ResourceConstants.SERVICES:
                handleGetServicesResource(req, resp);
                break;
            case ResourceConstants.PROFESSIONALS:
                handleGetProfessionalsResource(req, resp);
                break;
            case ResourceConstants.APPOINTMENTS:
                handleGetAppointmentsResource(req, resp);
            default:
                log.warn("Unknown resource requested: {}. Redirecting to admin dashboard.", resource);
                resp.sendRedirect(req.getContextPath() + "/app/dashboard?error=unknown_resource");
                break;
        }
    }

    /**
     * Handles requests related to user resources.
     * @param request
     * @param response
     */
    private void handleGetUserResource(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (Objects.nonNull(request.getParameter("page"))) {
                page = Integer.parseInt(request.getParameter("page"));
            }

            if (Objects.nonNull(request.getParameter("limit"))) {
                recordsPerPage = Integer.parseInt(request.getParameter("limit"));
            }

            String searchField = request.getParameter("searchField");
            String searchTerm = request.getParameter("searchTerm");

            request.setAttribute("paginatedUsers", userService.findAllUsersPaginated(page, recordsPerPage, searchField, searchTerm));
            request.setAttribute("roles", roleService.findAllRoles());

            request.getRequestDispatcher(RedirectUtils.getResourcePath(ResourceConstants.USERS)).forward(request, response);
        } catch (ServletException | IOException e) {
            log.atError().log("Error forwarding to user management page: {}", e.getMessage());
            throw new UtecAppException("Error while trying to perform user request");
        }
    }

    /**
     * Handles requests related to services resources.
     * @param request
     * @param response
     */
    private void handleGetServicesResource(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(RedirectUtils.getResourcePath(ResourceConstants.SERVICES)).forward(request, response);
        } catch (ServletException | IOException | NumberFormatException e) {
            log.atError().log("Error forwarding to services management page: {}", e.getMessage());
            throw new UtecAppException("Error while trying to perform services request");
        }
    }

    private void handleGetProfessionalsResource(HttpServletRequest request, HttpServletResponse response) {
        try {

            request.setAttribute("appointmentStates", AppointmentState.getStates());
            request.setAttribute("professionals", professionalService.findAll());
            request.getRequestDispatcher(RedirectUtils.getResourcePath(ResourceConstants.PROFESSIONALS)).forward(request, response);
        } catch (ServletException | IOException | NumberFormatException e) {
            log.atError().log("Error forwarding to professionals management page: {}", e.getMessage());
            throw new UtecAppException("Error while trying to perform professionals request");
        }
    }

    private void handleGetAppointmentsResource(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(RedirectUtils.getResourcePath(ResourceConstants.APPOINTMENTS)).forward(request, response);
        } catch (ServletException | IOException ex) {
            log.atError().log("Error forwarding to appointments page: {}", ex.getMessage());
            throw new UtecAppException("Error while trying to perform appointments request");
        }
    }
}
