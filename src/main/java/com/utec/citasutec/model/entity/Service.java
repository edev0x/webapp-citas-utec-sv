package com.utec.citasutec.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "servicio", indexes = {
    @Index(name = "idx_profesional", columnList = "id_profesional")
})
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio", nullable = false)
    private Integer id;

    @Size(max = 250)
    @NotNull
    @Column(name = "nombre_servicio", nullable = false, length = 250)
    private String serviceName;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate serviceDate;

    @NotNull
    @Column(name = "duracion", nullable = false)
    private Integer totalDuration;

    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime startTime;

    @NotNull
    @Column(name = "hora_fin", nullable = false)
    private LocalTime endTime;

    @NotNull
    @Lob
    @Column(name = "descripcion", nullable = false)
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_profesional", nullable = false)
    private Professional professional;

}