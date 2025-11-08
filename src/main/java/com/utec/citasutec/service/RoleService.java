package com.utec.citasutec.service;

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
}
