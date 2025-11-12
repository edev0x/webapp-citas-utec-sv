package com.utec.citasutec.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet(name = "ServiceController", urlPatterns = { "/api/services" })
public class ServiceController extends HttpServlet {
}