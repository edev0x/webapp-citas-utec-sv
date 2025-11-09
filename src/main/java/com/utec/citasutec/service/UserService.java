package com.utec.citasutec.service;

import com.utec.citasutec.model.dto.response.UserDto;
import com.utec.citasutec.model.entity.User;
import com.utec.citasutec.repository.UserRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@Stateless
public class UserService {
    @Inject
    private UserRepository userRepository;

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserDto::fromUser).toList();
    }

    public List<UserDto> findAllWithRoles() {
        return userRepository.findAllWithRoles().stream().map(UserDto::fromUser).toList();
    }

    public UserDto findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserDto::fromUser).orElse(null);
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
}
