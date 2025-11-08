package com.utec.citasutec.model.dto;

import com.utec.citasutec.model.entity.User;

public record UserDto(
    Integer id,
    String firstName,
    String lastName,
    String email,
    Boolean isActive,
    RoleDto role
) {
    public static UserDto fromUser(User user) {
        return new UserDto(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getIsActive(),
            RoleDto.fromRole(user.getRol())
        );
    }

}
