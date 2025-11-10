package com.utec.citasutec.service;

import com.utec.citasutec.model.dto.response.UserDto;
import com.utec.citasutec.model.entity.Rol;
import com.utec.citasutec.model.entity.User;
import com.utec.citasutec.repository.UserRepository;
import com.utec.citasutec.repository.factory.Paginated;
import com.utec.citasutec.util.exceptions.AppServiceTxException;
import com.utec.citasutec.util.security.CredentialsUtils;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Stateless
public class UserService {
    @Inject
    private UserRepository userRepository;

    @Inject
    private RoleService roleService;

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserDto::fromEntity).toList();
    }

    public List<UserDto> findAllWithRoles() {
        return userRepository.findAllWithRoles().stream().map(UserDto::fromEntity).toList();
    }

    public UserDto findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserDto::fromEntity).orElse(null);
    }

    public long countActiveUsers() {
        return userRepository.countActiveUsers();
    }

    public long countAllUsers() {
        return userRepository.count();
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public Paginated<UserDto> findAllUsersPaginated(int page, int size, String searchField, String searchTerm) {
        try {
            Paginated<User> paginatedUsers = userRepository.getPageable(page, size, searchField, searchTerm);

            List<UserDto> userDtos = paginatedUsers.getItems().stream()
                    .map(UserDto::fromEntity)
                    .toList();
            return new Paginated<>(userDtos, paginatedUsers.getTotalItems(), paginatedUsers.getCurrentPage(),
                    paginatedUsers.getPageSize(), paginatedUsers.getTotalPages());
        } catch (Exception e) {
            throw new AppServiceTxException("Error retrieving paginated users", e);
        }
    }

    public void save(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.email());
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setPasswordHash(CredentialsUtils.hashPassword(userDto.password()));
        user.setIsActive(userDto.isActive());

        Rol role = roleService.findById(userDto.role().id());

        if (Objects.isNull(role)) {
            throw new AppServiceTxException("Role with ID " + userDto.role().id() + " not found");
        }

        user.setRol(role);
        userRepository.save(user);

        log.atInfo().log("User with email {} successfully created", userDto.email());
    }
}
