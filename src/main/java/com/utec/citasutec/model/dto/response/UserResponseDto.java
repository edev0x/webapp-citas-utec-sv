package com.utec.citasutec.model.dto.response;

import com.utec.citasutec.model.entity.User;

public record UserResponseDto(
    Integer id,
    String firstName,
    String lastName,
    String email,
    Boolean isActive,
    RoleDto role
) {
    public static UserResponseDto fromEntity(User user) {
        return new UserResponseDto(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getIsActive(),
            RoleDto.fromEntity(user.getRol())
        );
    }

    public String fullName() {
        return firstName + " " + lastName;
    }
}
