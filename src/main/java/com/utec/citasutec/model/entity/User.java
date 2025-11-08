package com.utec.citasutec.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "usuario", indexes = {
    @Index(name = "idx_nombre_apellido", columnList = "nombre, apellido"),
    @Index(name = "idx_email", columnList = "correo"),
    @Index(name = "idx_rol", columnList = "id_rol")
}, uniqueConstraints = {
    @UniqueConstraint(name = "correo", columnNames = {"correo"})
})
@NamedQueries(
    value = {
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
        @NamedQuery(name = "User.findByRol", query = "SELECT u FROM User u WHERE u.rol = :rolId"),
        @NamedQuery(name = "User.findByNombre", query = "SELECT u FROM User u WHERE u.firstName = :nombre"),
        @NamedQuery(name = "User.updateDetails", query = "UPDATE User u SET u.firstName = :nombre, u.lastName = :apellido, u.email = :correo WHERE u.id = :userId"),
        @NamedQuery(name = "User.updatePassword", query = "UPDATE User u SET u.passwordHash = :newPassword WHERE u.id = :userId"),
        @NamedQuery(name = "User.updateRole", query = "UPDATE User u SET u.rol = :newRole WHERE u.id = :userId"),
        @NamedQuery(name = "User.updateStatus", query = "UPDATE User u SET u.isActive = :status WHERE u.id = :userId"),
    }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 100)
    private String firstName;

    @Size(max = 100)
    @NotNull
    @Column(name = "apellido", nullable = false, length = 100)
    private String lastName;

    @Size(max = 255)
    @NotNull
    @Column(name = "correo", nullable = false)
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @Column(name = "activo")
    private Boolean isActive;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "user")
    private Set<Log> logs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Appointment> appointments = new LinkedHashSet<>();

}