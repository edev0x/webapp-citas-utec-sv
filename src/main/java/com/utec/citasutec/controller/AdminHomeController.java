package com.utec.citasutec.controller;

import com.utec.citasutec.service.AppointmentService;
import com.utec.citasutec.service.UserService;
import com.utec.citasutec.util.AppointmentState;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AdminHomeController", urlPatterns = { "/app/dashboard" })
@ServletSecurity(@HttpConstraint(rolesAllowed = {"ADMIN"}))
public class AdminHomeController extends HttpServlet {

    @Inject
    private UserService userService;

    @Inject
    private AppointmentService appointmentService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("totalActiveUsers", userService.countActiveUsers());
        req.setAttribute("totalAppointments", appointmentService.countAll());
        req.setAttribute("totalConfirmedAppointments", appointmentService.countByState(AppointmentState.CONFIRMED.getTranslation()));

        req.getRequestDispatcher("/WEB-INF/views/protected/admin/home.jsp").forward(req, resp);
    }
}
