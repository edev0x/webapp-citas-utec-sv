package com.utec.citasutec.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "rol", indexes = {
    @Index(name = "idx_nombre", columnList = "nombre")
}, uniqueConstraints = {
    @UniqueConstraint(name = "nombre", columnNames = {"nombre"})
})
public class Rol {
    @Id
    @Column(name = "id_rol", nullable = false)
    private Byte id;

    @Size(max = 20)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 20)
    private String name;

    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> users = new LinkedHashSet<>();

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}