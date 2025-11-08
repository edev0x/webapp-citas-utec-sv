package com.utec.citasutec.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;

@CustomFormAuthenticationMechanismDefinition(
    loginToContinue = @LoginToContinue(
        loginPage = "/login.jsp",
        useForwardToLogin = false,
        useForwardToLoginExpression = ""
    )
)
@ApplicationScoped
public class SecurityConfiguration {
}
