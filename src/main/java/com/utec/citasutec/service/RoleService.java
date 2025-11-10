package com.utec.citasutec.service;

import java.util.List;

import com.utec.citasutec.model.dto.response.RoleDto;
import com.utec.citasutec.model.entity.Rol;
import com.utec.citasutec.repository.RoleRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class RoleService {
    @Inject
    private RoleRepository roleRepository;

    public Rol findById(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Rol findByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

    public List<RoleDto> findAllRoles() {
        return roleRepository.findAll().stream().map(RoleDto::fromEntity).toList();
    }
}
