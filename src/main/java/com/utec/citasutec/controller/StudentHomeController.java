package com.utec.citasutec.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "StudentHomeController", urlPatterns = { "/app/s/home", "/app/s", "/app/s/" })
@ServletSecurity(@HttpConstraint(rolesAllowed = { "ESTUDIANTE", "ADMIN" }))
public class StudentHomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/protected/student/home.jsp").forward(req, resp);
    }
}
