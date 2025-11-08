package com.utec.citasutec.config;

import com.utec.citasutec.model.dto.LoginDto;
import com.utec.citasutec.model.dto.UserDto;
import com.utec.citasutec.service.AuthService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Set;

@Slf4j
@ApplicationScoped
public class AppIdentityStore implements IdentityStore {
    @Inject
    private AuthService authService;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        log.atInfo().log("Validating credentials");

        if (!(credential instanceof UsernamePasswordCredential upc)) {
            return CredentialValidationResult.INVALID_RESULT;
        }
        LoginDto loginDto = new LoginDto(upc.getCaller(), upc.getPasswordAsString(), false);
        UserDto userDto = authService.login(loginDto);

        if (Objects.nonNull(authService.login(loginDto))) {
            log.atInfo().log("User {} successfully logged in", userDto.email());
            Set<String> roles = Set.of(userDto.role().name());

            return new CredentialValidationResult(upc.getCaller(), roles);
        }

        return CredentialValidationResult.INVALID_RESULT;
    }
}
