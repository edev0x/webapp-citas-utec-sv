package com.utec.citasutec.controller;

import java.io.IOException;
import java.util.Objects;

import com.utec.citasutec.model.dto.response.UserDto;
import com.utec.citasutec.repository.factory.Paginated;
import com.utec.citasutec.service.UserService;
import com.utec.citasutec.util.ResourceConstants;
import com.utec.citasutec.util.exceptions.UtecAppException;
import com.utec.citasutec.util.security.RedirectUtils;

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
@ServletSecurity(@HttpConstraint(rolesAllowed = { "ADMIN", "AUDITOR" }))
public class AdminResourcesController extends HttpServlet {

    protected int page = 1;
    protected int recordsPerPage = 10;

    @Inject
    private UserService userService;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String resource = req.getParameter("resource");

        if (Objects.isNull(resource) || resource.isBlank() || resource.isEmpty()) {
            log.warn("Resource parameter is missing or empty. Redirecting to admin dashboard.");
            resp.sendRedirect(req.getContextPath() + "/app/dashboard");
            return;
        }

        if (!resource.matches("^[a-zA-Z0-9_-]+$")) {
            log.warn("Invalid resource parameter: {}. Redirecting to admin dashboard.", resource);
            resp.sendRedirect(req.getContextPath() + "/app/dashboard?error=invalid_resource");
            return;
        }

        switch (resource) {
            case ResourceConstants.USERS:
                handleUserResource(req, resp);
                break;
            case ResourceConstants.SERVICES:
                handleServicesResource(req, resp);
                break;
            default:
                log.warn("Unknown resource requested: {}. Redirecting to admin dashboard.", resource);
                resp.sendRedirect(req.getContextPath() + "/app/dashboard?error=unknown_resource");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new ServletException("POST method is not supported for this endpoint.");
    }

    /**
     * Handles requests related to user resources.
     * @param request
     * @param response
     */
    protected void handleUserResource(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (Objects.nonNull(request.getParameter("page"))) {
                page = Integer.parseInt(request.getParameter("page"));
            }

            if (Objects.nonNull(request.getParameter("limit"))) {
                recordsPerPage = Integer.parseInt(request.getParameter("limit"));
            }

            String searchField = request.getParameter("searchField");
            String searchTerm = request.getParameter("searchTerm");

            Paginated<UserDto> paginatedUsers = userService.findAllUsersPaginated(page, recordsPerPage, searchField, searchTerm);
            request.setAttribute("paginatedUsers", paginatedUsers);

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
    protected void handleServicesResource(HttpServletRequest request, HttpServletResponse response) {
        try {
           

            request.getRequestDispatcher(RedirectUtils.getResourcePath(ResourceConstants.SERVICES)).forward(request, response);
        } catch (ServletException | IOException | NumberFormatException e) {
            log.atError().log("Error forwarding to services management page: {}", e.getMessage());
            throw new UtecAppException("Error while trying to perform services request");
        }
    }
}
