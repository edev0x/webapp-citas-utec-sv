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
        if (credential instanceof UsernamePasswordCredential upc) {
            log.atInfo().log("Validating credentials");

            LoginDto loginDto = new LoginDto(upc.getCaller(), upc.getPasswordAsString(), false);
            UserDto userDto = authService.login(loginDto);

            if (Objects.isNull(userDto)) {
                log.atInfo().log("User {} not found", upc.getCaller());
                return CredentialValidationResult.INVALID_RESULT;
            }

            log.atInfo().log("User successfully logged in");
            Set<String> roles = Set.of(userDto.role().name());

            return new CredentialValidationResult(upc.getCaller(), roles);
        }
        return CredentialValidationResult.INVALID_RESULT;
    }
}
