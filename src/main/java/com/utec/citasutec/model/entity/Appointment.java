package com.utec.citasutec.model.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cita", indexes = {
    @Index(name = "id_estudiante", columnList = "id_estudiante"),
    @Index(name = "id_profesional", columnList = "id_profesional")
})
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita", nullable = false)
    private Integer id;

    @NotNull(message = "El estudiante no puede ser nulo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_estudiante", nullable = false)
    private User user;

    @NotNull(message = "El profesional no puede ser nulo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_profesional", nullable = false)
    private Professional professional;

    @NotNull(message = "La fecha de la cita no puede ser nula")
    @Column(name = "fecha_cita", nullable = false)
    private LocalDate appointmentDate;

    @NotNull(message = "La hora de inicio de la cita no puede ser nula")
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime startTime;

    @NotNull(message = "La hora de fin de la cita no puede ser nula")
    @Column(name = "hora_fin", nullable = false)
    private LocalTime endTime;

    @NotNull(message = "El estado de la cita no puede ser nulo")
    @ColumnDefault("'PENDIENTE'")
    @Lob
    @Column(name = "estado", nullable = false)
    private String state;

    @NotNull(message = "El motivo de la cita no puede ser nulo")
    @Lob
    @Column(name = "motivo", nullable = false)
    private String reason;

    @Column(name = "fecha_creacion")
    private Instant creationDate;

    @OneToMany(mappedBy = "appointment")
    @Builder.Default
    private Set<Log> logs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "appointment")
    @Builder.Default
    private Set<Reminder> recordatorios = new HashSet<>();

    @PrePersist
    private void prePersist() {
        creationDate = Instant.now();
    }
}