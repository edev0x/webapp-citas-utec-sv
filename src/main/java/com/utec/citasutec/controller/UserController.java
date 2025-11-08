package com.utec.citasutec.controller;

import com.google.gson.Gson;
import com.utec.citasutec.model.dto.UserDto;
import com.utec.citasutec.service.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "userController", value = "/users")
@ServletSecurity(@HttpConstraint(rolesAllowed = { "ADMIN" }))
public class UserController extends HttpServlet {
    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDto> users = userService.findAllWithRoles();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String jsonResponse = new Gson().toJson(users);
        resp.getWriter().write(jsonResponse);
        resp.getWriter().flush();
    }
}
