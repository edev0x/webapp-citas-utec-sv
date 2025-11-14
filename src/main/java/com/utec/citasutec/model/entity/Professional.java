package com.utec.citasutec.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "profesional")
public class Professional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesional", nullable = false)
    private Integer id;

    @Size(max = 150)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 150)
    private String name;

    @Size(max = 255)
    @NotNull
    @Column(name = "correo", nullable = false)
    private String email;

    @Size(max = 200)
    @NotNull
    @Column(name = "especialidad", nullable = false, length = 200)
    private String specialty;

    @Column(name = "estado")
    private Boolean isActive;

    @OneToMany(mappedBy = "professional")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Appointment> appointments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "professional")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Service> services = new LinkedHashSet<>();

}