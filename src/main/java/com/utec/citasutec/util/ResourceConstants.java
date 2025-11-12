package com.utec.citasutec.util;

public interface ResourceConstants {
    String USERS = "users";
    String PROFESSIONALS = "professionals";
    String STUDENTS = "students";
    String APPOINTMENTS = "appointments";
    String AUDITS = "audits";
    String ROLES = "roles";
    String SERVICES = "services";

    enum DefaultRoles {
        USUARIO, ESTUDIANTE, PROFESIONAL, ADMIN, AUDITOR
    }
}
