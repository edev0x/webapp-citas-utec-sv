package com.utec.citasutec.model.dto.response;

import com.utec.citasutec.model.entity.Rol;

import java.util.Objects;

public record RoleDto(
    Integer id,
    String name
) {
    public static RoleDto fromRole(Rol rol) {
        if (Objects.isNull(rol)) return null;

        return new RoleDto(rol.getId().intValue(), rol.getName());
    }
}
