DROP DATABASE IF EXISTS citas_utec;

CREATE DATABASE citas_utec CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE citas_utec;

CREATE TABLE rol
(
    id_rol     TINYINT PRIMARY KEY AUTO_INCREMENT,
    nombre     VARCHAR(20) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    is_default BOOLEAN DEFAULT FALSE,
    INDEX idx_nombre (nombre)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


CREATE TABLE usuario
(
    id_usuario    INT PRIMARY KEY AUTO_INCREMENT,
    nombre        VARCHAR(100)        NOT NULL,
    apellido      VARCHAR(100)        NOT NULL,
    correo        VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    id_rol        TINYINT             NOT NULL,
    activo        BOOLEAN   DEFAULT (true),
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    FOREIGN KEY (id_rol) REFERENCES rol (id_rol) ON DELETE CASCADE,
    INDEX idx_email (correo),
    INDEX idx_rol (id_rol),
    INDEX idx_nombre_apellido (nombre, apellido)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


CREATE TABLE profesional
(
    id_profesional INT PRIMARY KEY AUTO_INCREMENT,
    nombre         VARCHAR(150) NOT NULL,
    correo         VARCHAR(255) NOT NULL,
    especialidad   VARCHAR(200) NOT NULL,
    estado         BOOLEAN DEFAULT (TRUE)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


CREATE TABLE servicio
(
    id_servicio     INT PRIMARY KEY AUTO_INCREMENT,
    nombre_servicio VARCHAR(250) NOT NULL,
    duracion        INT          NOT NULL,
    hora_inicio     TIME         NOT NULL,
    hora_fin        TIME         NOT NULL,
    descripcion     TEXT         NOT NULL,
    id_profesional  INT          NOT NULL,
    FOREIGN KEY (id_profesional) REFERENCES profesional (id_profesional),
    INDEX idx_profesional (id_profesional)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


CREATE TABLE cita
(
    id_cita        INT PRIMARY KEY AUTO_INCREMENT,
    id_estudiante  INT  NOT NULL,
    id_profesional INT  NOT NULL,
    fecha_cita     DATE NOT NULL,
    hora_inicio    TIME NOT NULL,
    hora_fin       TIME NOT NULL,
    estado         ENUM (
        'PENDIENTE',
        'CONFIRMADA',
        'EN_ESPERA',
        'EN_ATENCION',
        'COMPLETADA',
        'CANCELADA',
        'SIN_ASISTENCIA',
        'REAGENDADA',
        'SUSPENDIDA'
        )               NOT NULL DEFAULT 'PENDIENTE',
    motivo         TEXT NOT NULL,
    fecha_creacion DATETIME      DEFAULT (NOW()),
    FOREIGN KEY (id_estudiante) REFERENCES usuario (id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_profesional) REFERENCES profesional (id_profesional) ON DELETE CASCADE,
    INDEX idx_cita (id_cita)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


CREATE TABLE bitacora
(
    id_bitacora  INT PRIMARY KEY AUTO_INCREMENT,
    id_cita      INT         NOT NULL,
    id_usuario   INT         NOT NULL,
    accion       VARCHAR(50) NOT NULL,
    fecha_accion DATETIME    NOT NULL DEFAULT (NOW()),
    comentario   TEXT        NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_cita) REFERENCES cita (id_cita) ON DELETE CASCADE,
    INDEX idx_usuario (id_usuario),
    INDEX idx_cita (id_cita),
    INDEX idx_fecha (fecha_accion)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


CREATE TABLE recordatorio
(
    id_recordatorio INT PRIMARY KEY AUTO_INCREMENT,
    id_cita         INT                                                            NOT NULL,
    fecha_envio     DATETIME                                                       NOT NULL,
    tipo            ENUM ('EMAIL','SMS','WHATSAPP','PUSH','TELEFONO','WEB')        NOT NULL,
    estado_envio    ENUM ('PENDIENTE','ENVIADO','ENTREGADO','FALLIDO','CANCELADO') NOT NULL DEFAULT 'PENDIENTE',
    FOREIGN KEY (id_cita) REFERENCES cita (id_cita) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


-- BULK de datos de prueba
INSERT INTO rol (nombre, is_default)
VALUES ('ADMIN', TRUE),
       ('PROFESIONAL', TRUE),
       ('ESTUDIANTE', TRUE),
       ('AUDITOR', TRUE),
       ('USUARIO', TRUE);

INSERT INTO usuario (nombre, apellido, correo, password_hash, id_rol, activo)
VALUES ('Admin', 'WebApp', 'admin@webapp-citas.utec.edu.sv',
        '$2a$10$3NKAhmELuJrGzvo5WG90eu3oJvACrOTrUWgdNhfXnNac/u3cQc/Gu', 1, 1),
       ('Carlos Daniel', 'Castillo Sosa', '2929712023@utec.edu.sv', '$2a$10$abcdef1234567890abcdexxxxxx', 3, 1),
       ('Elías Rafael', 'Corcio', '2939342023@utec.edu.sv', '$2a$10$abcdef1234567890abcdexxxxxx', 3, 1),
       ('Ana Saraí', 'Rivas', '2921632023@utec.edu.sv', '$2a$10$abcdef1234567890abcdexxxxxx', 3, 1),
       ('Carlos Edenilson', 'Alberto Pineda', '2900032023@utec.edu.sv', '$2a$10$abcdef1234567890abcdexxxxxx', 3, 1),
       ('María', 'López', 'maria.lopez@utec.edu.sv', '$2a$10$abcdef1234567890abcdeyyyyyy', 2, 1),
       ('Jorge', 'Hernández', 'jorge.hernandez@utec.edu.sv', '$2a$10$abcdef1234567890abcdezxxxxx', 1, 1),
       ('Ana', 'Martínez', 'ana.martinez@utec.edu.sv', '$2a$10$abcdef1234567890abcdemmmmmm', 3, 1),
       ('Luis', 'Gómez', 'luis.gomez@utec.edu.sv', '$2a$10$abcdef1234567890abcdetttttt', 2, 0),
       ('Sofía', 'Castro', 'sofia.castro@utec.edu.sv', '$2a$10$abcdef1234567890abcdessssss', 1, 1),
       ('Daniel', 'Pérez', 'daniel.perez@utec.edu.sv', '$2a$10$abcdef1234567890abcdexppppp', 3, 1),
       ('Fernanda', 'Vega', 'fernanda.vega@utec.edu.sv', '$2a$10$abcdef1234567890abcdeqqqqqq', 2, 1),
       ('Ricardo', 'Flores', 'ricardo.flores@utec.edu.sv', '$2a$10$abcdef1234567890abcdexrrrrr', 3, 0),
       ('Elena', 'Ortiz', 'elena.ortiz@utec.edu.sv', '$2a$10$abcdef1234567890abcdessaaaaa', 3, 1);

INSERT INTO profesional (nombre, correo, especialidad, estado)
VALUES ('Dr. Alberto Molina', 'alberto.molina@utec.edu.sv', 'Psicología Clínica', 1),
       ('Dra. Paula Rivas', 'paula.rivas@utec.edu.sv', 'Psiquiatría', 1),
       ('Lic. Teresa Aguilar', 'teresa.aguilar@utec.edu.sv', 'Psicopedagogía', 1),
       ('Dr. Diego Carranza', 'diego.carranza@utec.edu.sv', 'Psicoanálisis', 1),
       ('Lic. Karla Mendoza', 'karla.mendoza@utec.edu.sv', 'Terapia Familiar', 1),
       ('Dr. Ernesto Salas', 'ernesto.salas@utec.edu.sv', 'Terapia Cognitiva', 1),
       ('Lic. Ramón Castillo', 'ramon.castillo@utec.edu.sv', 'Psicología General', 1);

INSERT INTO cita (id_estudiante, id_profesional, fecha_cita, hora_inicio, hora_fin, estado, motivo)
VALUES (11, 3, '2025-01-15', '09:00:00', '09:45:00', 'PENDIENTE', 'Consulta inicial de evaluación'),
       (14, 1, '2025-01-16', '10:00:00', '10:50:00', 'COMPLETADA', 'Sesión de seguimiento'),
       (14, 5, '2025-01-17', '14:00:00', '14:45:00', 'CANCELADA', 'Problemas de ansiedad'),
       (14, 2, '2025-01-18', '11:00:00', '11:50:00', 'PENDIENTE', 'Evaluación psiquiátrica'),
       (13, 7, '2025-01-20', '08:00:00', '08:40:00', 'COMPLETADA', 'Terapia familiar'),
       (14, 7, '2025-01-21', '13:00:00', '13:45:00', 'PENDIENTE', 'Problemas de conducta infantil'),
       (17, 4, '2025-01-22', '15:00:00', '15:50:00', 'PENDIENTE', 'Consulta de orientación'),
       (17, 3, '2025-01-23', '16:00:00', '16:40:00', 'COMPLETADA', 'Seguimiento psicológico'),
       (19, 2, '2025-01-24', '09:30:00', '10:15:00', 'CANCELADA', 'Terapia de análisis'),
       (20, 1, '2025-01-25', '17:00:00', '17:45:00', 'PENDIENTE', 'Problemas de estrés');


CREATE OR REPLACE VIEW vw_reporte_citas AS
SELECT c.id_cita                         AS id_cita,
       u.id_usuario                      AS id_estudiante,
       CONCAT(u.nombre, ' ', u.apellido) AS estudiante,
       u.correo                          AS estudiante_correo,

       p.id_profesional                  AS id_profesional,
       p.nombre                          AS profesional,
       p.especialidad                    AS profesional_especialidad,
       p.correo                          AS profesional_correo,

       c.fecha_cita,
       c.hora_inicio,
       c.hora_fin,
       c.estado                          AS estado_cita,
       c.motivo
FROM cita c
         INNER JOIN usuario u ON u.id_usuario = c.id_estudiante
         INNER JOIN profesional p ON p.id_profesional = c.id_profesional
         INNER JOIN rol r ON u.id_rol = r.id_rol
WHERE r.nombre = 'ESTUDIANTE';
