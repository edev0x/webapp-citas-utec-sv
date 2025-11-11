package com.utec.citasutec.service;

import com.utec.citasutec.model.dto.request.UserRequestDto;
import com.utec.citasutec.model.dto.response.UserResponseDto;
import com.utec.citasutec.model.entity.Rol;
import com.utec.citasutec.model.entity.User;
import com.utec.citasutec.repository.UserRepository;
import com.utec.citasutec.repository.factory.Paginated;
import com.utec.citasutec.util.exceptions.AppServiceTxException;
import com.utec.citasutec.util.security.CredentialsUtils;

import jakarta.ejb.EJBException;
import jakarta.ejb.EJBTransactionRolledbackException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(UserResponseDto::fromEntity).toList();
    }

    public List<UserResponseDto> findAllWithRoles() {
        return userRepository.findAllWithRoles().stream().map(UserResponseDto::fromEntity).toList();
    }

    public UserResponseDto findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserResponseDto::fromEntity).orElse(null);
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

    public Paginated<UserResponseDto> findAllUsersPaginated(int page, int size, String searchField, String searchTerm) {
        try {
            Paginated<User> paginatedUsers = userRepository.getPageable(page, size, searchField, searchTerm);

            List<UserResponseDto> userResponseDtos = paginatedUsers.getItems().stream()
                    .map(UserResponseDto::fromEntity)
                    .toList();
            return new Paginated<>(userResponseDtos, paginatedUsers.getTotalItems(), paginatedUsers.getCurrentPage(),
                    paginatedUsers.getPageSize(), paginatedUsers.getTotalPages());
        } catch (Exception e) {
            throw new AppServiceTxException("Error retrieving paginated users", e);
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void save(UserRequestDto userResponseDto) {
        User user = new User();
        user.setEmail(userResponseDto.email());
        user.setFirstName(userResponseDto.firstName());
        user.setLastName(userResponseDto.lastName());
        user.setPasswordHash(CredentialsUtils.hashPassword(userResponseDto.password()));
        user.setIsActive(userResponseDto.isActive());

        Rol role = roleService.findById(userResponseDto.role().id());

        if (Objects.isNull(role)) {
            throw new AppServiceTxException("Role with ID " + userResponseDto.role().id() + " not found");
        }

        user.setRol(role);
        userRepository.save(user);

        log.atInfo().log("User with email {} successfully created", userResponseDto.email());
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteById(Integer id) {
        try {
            userRepository.deleteById(id);
            log.atInfo().log("User with ID {} successfully deleted", id);
        } catch (EJBTransactionRolledbackException ex) {
            log.atError().log("Error deleting user with ID {}: {}", id, ex.getMessage());
            throw new AppServiceTxException("Error deleting user", ex);
        }
    }

    public void updateUser(UserRequestDto userRequestDto) {
        try {
            User user = userRepository.findById(userRequestDto.id()).orElse(null);
            if (Objects.isNull(user)) {
                throw new AppServiceTxException("User with ID " + userRequestDto.id() + " not found");
            }

            user.setEmail(userRequestDto.email());
            user.setFirstName(userRequestDto.firstName());
            user.setLastName(userRequestDto.lastName());

            String oldPasswordHash = user.getPasswordHash();
            String newPasswordHash = CredentialsUtils.hashPassword(userRequestDto.password());

            if (!CredentialsUtils.checkPassword(userRequestDto.password(), oldPasswordHash)
                && Objects.nonNull(userRequestDto.password()) && !userRequestDto.password().isEmpty()) {
                user.setPasswordHash(CredentialsUtils.hashPassword(newPasswordHash));
            }

            user.setRol(roleService.findById(userRequestDto.role().id()));
            user.setIsActive(userRequestDto.isActive());

            userRepository.update(user);
            log.atInfo().log("User with ID {} successfully updated", userRequestDto.id());
        } catch (EJBException ex) {
            log.atError().log("Error updating user with ID {}: {}", userRequestDto.id(), ex.getMessage());
            throw new AppServiceTxException("Error updating user", ex);
        }
    }
}
