package com.utec.citasutec.service;

import java.util.List;

import com.utec.citasutec.model.dto.request.RoleRequestDto;
import com.utec.citasutec.model.dto.response.RoleDto;
import com.utec.citasutec.model.entity.Rol;
import com.utec.citasutec.repository.RoleRepository;
import com.utec.citasutec.util.exceptions.AppServiceTxException;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    public void save(RoleRequestDto roleRequestDto) {
        try {
            Rol role = new Rol();
            role.setName(roleRequestDto.name());
            roleRepository.save(role);
        } catch (Exception ex) {
            log.atError().log("Error saving role: {}", ex.getMessage());
            throw new AppServiceTxException("Error while trying to save the role" + roleRequestDto.name());
        }
    }

    public void deleteById(Integer id) {
        try {
            roleRepository.deleteById(id);
        } catch (Exception ex) {
            log.atError().log("Error deleting role with id {}: {}", id, ex.getMessage());
            throw new AppServiceTxException("Error while trying to delete the role with id " + id);
        }
    }

    public void update(RoleRequestDto roleRequestDto) {
        try {
            Rol existingRole = roleRepository.findById(roleRequestDto.id())
                    .orElseThrow(() -> new AppServiceTxException("Role with ID " + roleRequestDto.id() + " not found"));

            existingRole.setName(roleRequestDto.name());
            roleRepository.update(existingRole);
        } catch (Exception ex) {
            log.atError().log("Error updating role: {}", ex.getMessage());
            throw new AppServiceTxException("Error while trying to update the role with id " + roleRequestDto.id());
        }
    }
}
