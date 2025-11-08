package com.utec.citasutec.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cita", indexes = {
    @Index(name = "id_estudiante", columnList = "id_estudiante"),
    @Index(name = "id_profesional", columnList = "id_profesional")
})
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_estudiante", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_profesional", nullable = false)
    private Professional professional;

    @NotNull
    @Column(name = "fecha_cita", nullable = false)
    private LocalDate appointmentDate;

    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime startTime;

    @NotNull
    @Column(name = "hora_fin", nullable = false)
    private LocalTime endTime;

    @NotNull
    @ColumnDefault("'PENDIENTE'")
    @Lob
    @Column(name = "estado", nullable = false)
    private String state;

    @NotNull
    @Lob
    @Column(name = "motivo", nullable = false)
    private String reason;

    @Column(name = "fecha_creacion")
    private Instant creationDate;

    @OneToMany(mappedBy = "appointment")
    private Set<Log> logs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "appointment")
    private Set<Reminder> recordatorios = new LinkedHashSet<>();

    @PrePersist
    private void prePersist() {
        creationDate = Instant.now();
    }
}