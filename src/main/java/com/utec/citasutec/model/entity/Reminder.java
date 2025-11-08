package com.utec.citasutec.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "recordatorio", indexes = {
    @Index(name = "id_cita", columnList = "id_cita")
})
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recordatorio", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_cita", nullable = false)
    private Appointment appointment;

    @NotNull
    @Column(name = "fecha_envio", nullable = false)
    private Instant sendDate;

    @NotNull
    @Lob
    @Column(name = "tipo", nullable = false)
    private String type;

    @NotNull
    @ColumnDefault("'PENDIENTE'")
    @Lob
    @Column(name = "estado_envio", nullable = false)
    private String sendState;

}