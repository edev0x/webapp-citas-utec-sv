package com.utec.citasutec.controller;

import com.utec.citasutec.model.dto.response.UserResponseDto;
import com.utec.citasutec.service.ProfessionalService;
import com.utec.citasutec.service.UserService;
import com.utec.citasutec.util.AppointmentState;
import com.utec.citasutec.util.ResourceConstants;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CalendarController", urlPatterns = { "/app/calendar" })
public class CalendarController extends HttpServlet {

    @Inject
    private ProfessionalService professionalService;

    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("appointmentStates", AppointmentState.getStates());
        req.setAttribute("users", userService.findActiveUsersByRole(ResourceConstants.DefaultRoles.ESTUDIANTE.toString()));
        req.setAttribute("professionals", professionalService.findAll());
        req.getRequestDispatcher("/WEB-INF/views/protected/calendar/page.jsp").forward(req, resp);
    }
}
