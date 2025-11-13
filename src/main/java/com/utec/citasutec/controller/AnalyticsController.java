package com.utec.citasutec.controller;

import com.utec.citasutec.service.AppointmentService;
import com.utec.citasutec.util.formatters.ResponseUtils;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@WebServlet(name = "AnalyticsController", urlPatterns = { "/analytics" })
@ServletSecurity(@HttpConstraint(rolesAllowed = {"ADMIN", "AUDITOR" }))
public class AnalyticsController extends HttpServlet {

    @Inject
    private AppointmentService appointmentService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");
        String resourceParam = request.getParameter("resource");
        String byResourceParam = request.getParameter("by");


        if (Objects.nonNull(resourceParam) && resourceParam.equals("appointments") && Objects.nonNull(byResourceParam) && byResourceParam.equals("month")) {
            ResponseUtils.sendSuccessResponseWithContent(response, this.appointmentService.countAppointmentsByMonth());
        } else if (Objects.nonNull(resourceParam) && resourceParam.equals("appointments") && Objects.nonNull(byResourceParam) && byResourceParam.equals("state")) {
            ResponseUtils.sendSuccessResponseWithContent(response, this.appointmentService.countAppointmentsByState());
        }  else {
            ResponseUtils.sendSuccessResponseWithContent(response, new HashMap<>() {{
                put("health", "ok");
            }});
        }
    }
}
