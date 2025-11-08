package com.utec.citasutec.service;

import com.utec.citasutec.model.dto.LoginDto;
import com.utec.citasutec.model.dto.RegisterDto;
import com.utec.citasutec.model.dto.UserDto;
import com.utec.citasutec.model.entity.Rol;
import com.utec.citasutec.model.entity.User;
import com.utec.citasutec.repository.UserRepository;
import com.utec.citasutec.util.security.CredentialsUtils;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Stateless
public class AuthService {;
    private static final String DEFAULT_ROLE = "ESTUDIANTE";

    @Inject
    private UserRepository userRepository;

    @Inject
    private RoleService roleService;

    public UserDto login(LoginDto credentials) {
        User user = userRepository.findByEmail(credentials.email()).orElse(null);
        if (Objects.isNull(user) || !isValidCredentials(credentials.password(), user.getPasswordHash())) {
            return null;
        }

        return UserDto.fromUser(user);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void signUp(RegisterDto registerDto) {
        log.atInfo().log("Signing up user with email: {}", registerDto.email());

        Rol defaultRole = roleService.findByName(DEFAULT_ROLE);

        log.atInfo().log("Default role: {}", defaultRole);

        if (Objects.isNull(defaultRole)) {
            throw new NoResultException("No role found with name " + DEFAULT_ROLE);
        }

        User newUser = new User();
        newUser.setFirstName(registerDto.firstName());
        newUser.setLastName(registerDto.lastName());
        newUser.setEmail(registerDto.email());
        newUser.setPasswordHash(CredentialsUtils.hashPassword(registerDto.password()));
        newUser.setRol(defaultRole);
        newUser.setIsActive(true);
        userRepository.save(newUser);

        log.atInfo().log("User with email {} successfully signed up", registerDto.email());
    }

    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean isValidCredentials(String plainPassword, String passwordHash) {
        return CredentialsUtils.checkPassword(plainPassword, passwordHash);
    }
}
