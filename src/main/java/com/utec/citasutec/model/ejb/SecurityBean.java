package com.utec.citasutec.model.ejb;

import com.utec.citasutec.service.RoleService;
import com.utec.citasutec.service.UserService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;

import java.util.Collections;
import java.util.Set;

@Named("security")
@RequestScoped
public class SecurityBean {
    @Inject
    private SecurityContext securityContext;

    @Inject
    private UserService userService;

    public boolean isLoggedIn() {
        return securityContext.getCallerPrincipal() != null;
    }

    public String getUsername() {
        return isLoggedIn() ? securityContext.getCallerPrincipal().getName() : "";
    }

    public boolean hasRole(String role) {
        return securityContext.isCallerInRole(role);
    }

    public Set<String> getRoles() {
        return Collections.singleton(userService.findByEmail(this.getUsername()).role().name());
    }
}
