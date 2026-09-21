DROP DATABASE IF EXISTS `colegiosilviano_dev`;
CREATE DATABASE IF NOT EXISTS `colegiosilviano_dev` 
  DEFAULT CHARACTER SET utf8mb4 
  COLLATE utf8mb4_unicode_ci;

USE `colegiosilviano_dev`;

-- ==========================================
-- 1. ENTIDADES BASE Y PERSONAS
-- ==========================================

DROP TABLE IF EXISTS `persona`;
CREATE TABLE `persona` (
  `idpersona` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre_completo` VARCHAR(150) NOT NULL,
  `apellido_completo` VARCHAR(150) NOT NULL,
  /* Reemplazo de ENUM: Valores sugeridos -> 'MASCULINO', 'FEMENINO', 'OTRO' */
  `sexo` VARCHAR(20) NOT NULL,
  `fecha_nacimiento` DATE NOT NULL,
  `cedula` VARCHAR(20) DEFAULT NULL,
  `partida_nacimiento` VARCHAR(30) DEFAULT NULL,
  `direccion` VARCHAR(300) NOT NULL,
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idpersona`),
  UNIQUE KEY `uk_persona_cedula` (`cedula`),
  INDEX `idx_persona_apellidos` (`apellido_completo`, `nombre_completo`),
  CONSTRAINT `chk_persona_sexo` CHECK (`sexo` IN ('MASCULINO', 'FEMENINO', 'OTRO'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `tutor`;
CREATE TABLE `tutor` (
  `idtutor` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idpersona` INT UNSIGNED NOT NULL,
  `ocupacion` VARCHAR(100) DEFAULT NULL,
  `telefono_principal` VARCHAR(15) NOT NULL,
  `telefono_secundario` VARCHAR(15) DEFAULT NULL,
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idtutor`),
  UNIQUE KEY `uk_tutor_persona` (`idpersona`),
  CONSTRAINT `fk_tutor_persona` FOREIGN KEY (`idpersona`) 
    REFERENCES `persona` (`idpersona`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `estudiante`;
CREATE TABLE `estudiante` (
  `idpersona` INT UNSIGNED NOT NULL,
  `cod_estudiante` VARCHAR(20) NOT NULL,
  `codigo_MINED` VARCHAR(30) DEFAULT NULL,
  `estado` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1 = Activo, 0 = Inactivo',
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idpersona`),
  UNIQUE KEY `uk_estudiante_codigo` (`cod_estudiante`),
  UNIQUE KEY `uk_estudiante_mined` (`codigo_MINED`),
  CONSTRAINT `fk_estudiante_persona` FOREIGN KEY (`idpersona`) 
    REFERENCES `persona` (`idpersona`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `estudiante_tutor`;
CREATE TABLE `estudiante_tutor` (
  `idpersona_estudiante` INT UNSIGNED NOT NULL,
  `idtutor` INT UNSIGNED NOT NULL,
  /* Reemplazo de ENUM: Valores sugeridos -> 'MADRE', 'PADRE', 'TUTOR_LEGAL', 'ABUELO', 'TIO', 'OTRO' */
  `parentesco` VARCHAR(30) NOT NULL,
  `es_representante_legal` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '1 = Si, 0 = No',
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idpersona_estudiante`, `idtutor`),
  CONSTRAINT `fk_et_estudiante` FOREIGN KEY (`idpersona_estudiante`) 
    REFERENCES `estudiante` (`idpersona`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_et_tutor` FOREIGN KEY (`idtutor`) 
    REFERENCES `tutor` (`idtutor`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `docente`;
CREATE TABLE `docente` (
  `idpersona` INT UNSIGNED NOT NULL,
  `cod_docente` VARCHAR(20) NOT NULL,
  `especialidad` VARCHAR(100) DEFAULT NULL,
  `estado` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1 = Activo, 0 = Inactivo',
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idpersona`),
  UNIQUE KEY `uk_docente_codigo` (`cod_docente`),
  CONSTRAINT `fk_docente_persona` FOREIGN KEY (`idpersona`) 
    REFERENCES `persona` (`idpersona`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `idusuario` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idpersona` INT UNSIGNED NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  `password_hash` VARCHAR(255) NOT NULL,
  /* Reemplazo de ENUM: Valores sugeridos -> 'ADMIN', 'SECRETARIA', 'DOCENTE', 'ESTUDIANTE', 'TUTOR' */
  `tipo_usuario` VARCHAR(30) NOT NULL,
  `correo` VARCHAR(100) NOT NULL,
  `estado` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1 = Activo, 0 = Inactivo',
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idusuario`),
  UNIQUE KEY `uk_usuario_username` (`username`),
  UNIQUE KEY `uk_usuario_correo` (`correo`),
  CONSTRAINT `fk_usuario_persona` FOREIGN KEY (`idpersona`) 
    REFERENCES `persona` (`idpersona`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==========================================
-- 2. ESTRUCTURA ACADÉMICA BASE
-- ==========================================

DROP TABLE IF EXISTS `nivel_educativo`;
CREATE TABLE `nivel_educativo` (
  `idnivel` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  `descripcion` VARCHAR(150) DEFAULT NULL,
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idnivel`),
  UNIQUE KEY `uk_nivel_nombre` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `grado`;
CREATE TABLE `grado` (
  `idGrado` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idnivel` INT UNSIGNED NOT NULL,
  `nombre` VARCHAR(50) NOT NULL,
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idGrado`),
  UNIQUE KEY `uk_grado_nivel_nombre` (`idnivel`, `nombre`),
  CONSTRAINT `fk_grado_nivel` FOREIGN KEY (`idnivel`) 
    REFERENCES `nivel_educativo` (`idnivel`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `asignatura`;
CREATE TABLE `asignatura` (
  `idAsignatura` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(60) NOT NULL,
  `codigo` VARCHAR(10) NOT NULL,
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idAsignatura`),
  UNIQUE KEY `uk_asignatura_codigo` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `catalogo_salon`;
CREATE TABLE `catalogo_salon` (
  `idcatalogo_salon` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre_salon` VARCHAR(50) NOT NULL COMMENT 'Ej: Aula 101, Lab Cómputo A',
  `capacidad` SMALLINT UNSIGNED NOT NULL DEFAULT 35,
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idcatalogo_salon`),
  CONSTRAINT `chk_capacidad_positiva` CHECK (`capacidad` > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `salon`;
CREATE TABLE `salon` (
  `idSalon` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idGrado` INT UNSIGNED NOT NULL,
  `idcatalogo_salon` INT UNSIGNED NOT NULL,
  `anio_lectivo` YEAR NOT NULL COMMENT 'Permite dimensionar la oferta por ciclo escolar',
  /* Reemplazo de ENUM: Valores sugeridos -> 'MANANA', 'TARDE', 'SABATINO', 'NOCTURNO' */
  `turno` VARCHAR(20) NOT NULL,
  `seccion` VARCHAR(5) NOT NULL DEFAULT 'A',
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idSalon`),
  UNIQUE KEY `uk_salon_catalogo_turno_anio` (`idcatalogo_salon`, `turno`, `anio_lectivo`),
  UNIQUE KEY `uk_grado_seccion_turno_anio` (`idGrado`, `seccion`, `turno`, `anio_lectivo`),
  CONSTRAINT `fk_salon_grado` FOREIGN KEY (`idGrado`) 
    REFERENCES `grado` (`idGrado`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_salon_catalogo` FOREIGN KEY (`idcatalogo_salon`) 
    REFERENCES `catalogo_salon` (`idcatalogo_salon`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_salon_turno` CHECK (`turno` IN ('MANANA', 'TARDE', 'SABATINO', 'NOCTURNO'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==========================================
-- 3. PLANIFICACIÓN Y MATRÍCULA
-- ==========================================

DROP TABLE IF EXISTS `plan_de_estudio`;
CREATE TABLE `plan_de_estudio` (
  `idPlan_de_estudio` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idGrado` INT UNSIGNED NOT NULL,
  `anio_lectivo` YEAR NOT NULL,
  `estado` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1 = Vigente, 0 = Inactivo',
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idPlan_de_estudio`),
  UNIQUE KEY `uk_plan_grado_anio` (`idGrado`, `anio_lectivo`),
  CONSTRAINT `fk_plan_grado` FOREIGN KEY (`idGrado`) 
    REFERENCES `grado` (`idGrado`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `matricula`;
CREATE TABLE `matricula` (
  `idmatricula` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idpersona` INT UNSIGNED NOT NULL,
  `idSalon` INT UNSIGNED NOT NULL,
  `anio_lectivo` YEAR NOT NULL,
  `costo_matricula` DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  `fecha_matricula` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  /* Reemplazo de ENUM: Valores sugeridos -> 'ACTIVA', 'RETIRADO', 'SUSPENDIDO', 'CANCELADA' */
  `estado` VARCHAR(20) NOT NULL DEFAULT 'ACTIVA',
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idmatricula`),
  UNIQUE KEY `uk_estudiante_anio` (`idpersona`, `anio_lectivo`),
  INDEX `idx_matricula_salon_anio` (`idSalon`, `anio_lectivo`, `estado`),
  CONSTRAINT `fk_mat_estudiante` FOREIGN KEY (`idpersona`) 
    REFERENCES `estudiante` (`idpersona`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_mat_salon` FOREIGN KEY (`idSalon`) 
    REFERENCES `salon` (`idSalon`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_costo_matricula` CHECK (`costo_matricula` >= 0),
  CONSTRAINT `chk_matricula_estado` CHECK (`estado` IN ('ACTIVA', 'RETIRADO', 'SUSPENDIDO', 'CANCELADA'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==========================================
-- 4. EVALUACIONES Y CALIFICACIONES
-- ==========================================

DROP TABLE IF EXISTS `periodo_evaluativo`;
CREATE TABLE `periodo_evaluativo` (
  `idperiodo_evaluativo` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre_periodo` VARCHAR(30) NOT NULL,
  `numero_periodo` TINYINT UNSIGNED NOT NULL,
  `anio_escolar` YEAR NOT NULL,
  `estado` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1 = Activo, 0 = Cerrado',
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idperiodo_evaluativo`),
  UNIQUE KEY `uk_periodo_anio_numero` (`anio_escolar`, `numero_periodo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `detalle_plan_de_estudio`;
CREATE TABLE `detalle_plan_de_estudio` (
  `iddetalle_plan_de_estudio` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idPlan_de_estudio` INT UNSIGNED NOT NULL,
  `idAsignatura` INT UNSIGNED NOT NULL,
  `idDocente` INT UNSIGNED NOT NULL,
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`iddetalle_plan_de_estudio`),
  UNIQUE KEY `uk_plan_asignatura` (`idPlan_de_estudio`, `idAsignatura`),
  CONSTRAINT `fk_det_plan` FOREIGN KEY (`idPlan_de_estudio`) 
    REFERENCES `plan_de_estudio` (`idPlan_de_estudio`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_det_asig` FOREIGN KEY (`idAsignatura`) 
    REFERENCES `asignatura` (`idAsignatura`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_det_docente` FOREIGN KEY (`idDocente`) 
    REFERENCES `docente` (`idpersona`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `calificaciones`;
CREATE TABLE `calificaciones` (
  `idcalificacion` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idmatricula` INT UNSIGNED NOT NULL,
  `iddetalle_plan_de_estudio` INT UNSIGNED NOT NULL,
  `idperiodo_evaluativo` INT UNSIGNED NOT NULL,
  `acumulado` DECIMAL(5,2) NOT NULL DEFAULT 0.00,
  `examen` DECIMAL(5,2) NOT NULL DEFAULT 0.00,
  `nota_final` DECIMAL(5,2) GENERATED ALWAYS AS (`acumulado` + `examen`) STORED,
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idcalificacion`),
  UNIQUE KEY `uk_nota_periodo_plan` (`idmatricula`, `iddetalle_plan_de_estudio`, `idperiodo_evaluativo`),
  CONSTRAINT `fk_calif_matricula` FOREIGN KEY (`idmatricula`) 
    REFERENCES `matricula` (`idmatricula`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_calif_detalle_plan` FOREIGN KEY (`iddetalle_plan_de_estudio`) 
    REFERENCES `detalle_plan_de_estudio` (`iddetalle_plan_de_estudio`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_calif_periodo` FOREIGN KEY (`idperiodo_evaluativo`) 
    REFERENCES `periodo_evaluativo` (`idperiodo_evaluativo`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_acumulado_rango` CHECK (`acumulado` BETWEEN 0.00 AND 60.00),
  CONSTRAINT `chk_examen_rango` CHECK (`examen` BETWEEN 0.00 AND 40.00)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==========================================
-- 5. MÓDULO FINANCIERO
-- ==========================================

DROP TABLE IF EXISTS `catalogo_tarifa`;
CREATE TABLE `catalogo_tarifa` (
  `idtarifa` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idnivel` INT UNSIGNED DEFAULT NULL,
  `idGrado` INT UNSIGNED DEFAULT NULL,
  `anio_lectivo` YEAR NOT NULL,
  `concepto` VARCHAR(100) NOT NULL,
  `monto` DECIMAL(10,2) NOT NULL,
  `es_obligatorio` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1 = Arancel obligatorio, 0 = Opcional',
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idtarifa`),
  UNIQUE KEY `uk_tarifa_ambito` (`anio_lectivo`, `concepto`, `idnivel`, `idGrado`),
  CONSTRAINT `fk_tarifa_nivel` FOREIGN KEY (`idnivel`) 
    REFERENCES `nivel_educativo` (`idnivel`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_tarifa_grado` FOREIGN KEY (`idGrado`) 
    REFERENCES `grado` (`idGrado`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_monto_tarifa` CHECK (`monto` >= 0),
  CONSTRAINT `chk_tarifa_jerarquia` CHECK ((`idnivel` IS NOT NULL) OR (`idGrado` IS NOT NULL))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `pago`;
CREATE TABLE `pago` (
  `idpago` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idmatricula` INT UNSIGNED NOT NULL,
  `num_recibo` VARCHAR(30) NOT NULL,
  `fecha_pago` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  /* Reemplazo de ENUM: Valores sugeridos -> 'EFECTIVO', 'TRANSFERENCIA', 'TARJETA', 'DEPOSITO' */
  `tipo_pago` VARCHAR(30) NOT NULL,
  `monto_total` DECIMAL(10,2) NOT NULL,
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idpago`),
  UNIQUE KEY `uk_pago_num_recibo` (`num_recibo`),
  INDEX `idx_pago_fecha` (`fecha_pago`),
  CONSTRAINT `fk_pago_matricula` FOREIGN KEY (`idmatricula`) 
    REFERENCES `matricula` (`idmatricula`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_monto_pago` CHECK (`monto_total` >= 0),
  CONSTRAINT `chk_pago_tipo` CHECK (`tipo_pago` IN ('EFECTIVO', 'TRANSFERENCIA', 'TARJETA', 'DEPOSITO'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `detalle_pago`;
CREATE TABLE `detalle_pago` (
  `iddetalle_pago` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idpago` INT UNSIGNED NOT NULL,
  `idtarifa` INT UNSIGNED DEFAULT NULL,
  `concepto` VARCHAR(100) NOT NULL,
  `monto` DECIMAL(10,2) NOT NULL,
  `creado_por` VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
  `creado_el` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` VARCHAR(50) DEFAULT NULL,
  `actualizado_el` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`iddetalle_pago`),
  CONSTRAINT `fk_detpago_cabecera` FOREIGN KEY (`idpago`) 
    REFERENCES `pago` (`idpago`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_detpago_tarifa` FOREIGN KEY (`idtarifa`) 
    REFERENCES `catalogo_tarifa` (`idtarifa`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_monto_detpago` CHECK (`monto` >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==========================================
-- 6. CARGA INICIAL / LIMPIEZA
-- ==========================================

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE `detalle_pago`;
TRUNCATE TABLE `pago`;
TRUNCATE TABLE `catalogo_tarifa`;
TRUNCATE TABLE `calificaciones`;
TRUNCATE TABLE `detalle_plan_de_estudio`;
TRUNCATE TABLE `periodo_evaluativo`;
TRUNCATE TABLE `matricula`;
TRUNCATE TABLE `plan_de_estudio`;
TRUNCATE TABLE `salon`;
TRUNCATE TABLE `catalogo_salon`;
TRUNCATE TABLE `asignatura`;
TRUNCATE TABLE `grado`;
TRUNCATE TABLE `nivel_educativo`;
TRUNCATE TABLE `usuario`;
TRUNCATE TABLE `docente`;
TRUNCATE TABLE `estudiante_tutor`;
TRUNCATE TABLE `estudiante`;
TRUNCATE TABLE `tutor`;
TRUNCATE TABLE `persona`;

SET FOREIGN_KEY_CHECKS = 1;
-- ==========================================
-- 1. POBLADO DE PERSONAS Y ROLES
-- ==========================================

INSERT INTO `persona` 
  (`idpersona`, `nombre_completo`, `apellido_completo`, `sexo`, `fecha_nacimiento`, `cedula`, `partida_nacimiento`, `direccion`, `creado_por`) 
VALUES
  (1, 'Carlos Alberto', 'Mendoza López', 'MASCULINO', '1980-05-12', '001-120580-0001U', NULL, 'Barrio Central, Managua', 'ADMIN'),
  (2, 'María Elena', 'García Gutiérrez', 'FEMENINO', '1985-08-25', '001-250885-0002A', NULL, 'Reparto San Juan, Managua', 'ADMIN'),
  (3, 'Roberto José', 'Martínez Silva', 'MASCULINO', '1978-11-03', '001-031178-0003B', NULL, 'Villa Fontana, Managua', 'ADMIN'),
  (4, 'Ana Lucía', 'Torres Morales', 'FEMENINO', '1990-02-14', '001-140290-0004C', NULL, 'Ciudad Sandino, Managua', 'ADMIN'),
  (5, 'Fernando José', 'Brennans Ruiz', 'MASCULINO', '1982-09-30', '001-300982-0005D', NULL, 'Bello Horizonte, Managua', 'ADMIN'),
  (6, 'Sofia Beatris', 'Reyes Castillo', 'FEMENINO', '2010-04-15', NULL, 'PN-2010-00123', 'Barrio Central, Managua', 'ADMIN'),
  (7, 'Mateo Alexander', 'Mendoza García', 'MASCULINO', '2012-07-20', NULL, 'PN-2012-00456', 'Barrio Central, Managua', 'ADMIN'),
  (8, 'Valeria Isabella', 'Martínez Torres', 'FEMENINO', '2008-12-10', '001-101208-1001X', 'PN-2008-00789', 'Villa Fontana, Managua', 'ADMIN'),
  (9, 'Lucas Gabriel', 'García Ruiz', 'MASCULINO', '2011-01-05', NULL, 'PN-2011-00987', 'Reparto San Juan, Managua', 'ADMIN'),
  (10, 'Admin', 'Sistema Silviano', 'MASCULINO', '1995-06-01', '001-010695-0000A', NULL, 'Oficina Central Colegio', 'ADMIN');

INSERT INTO `tutor` 
  (`idtutor`, `idpersona`, `ocupacion`, `telefono_principal`, `telefono_secundario`, `creado_por`) 
VALUES
  (1, 1, 'Ingeniero Civil', '88881111', '22221111', 'ADMIN'),
  (2, 2, 'Contadora Pública', '88882222', NULL, 'ADMIN');

INSERT INTO `estudiante` 
  (`idpersona`, `cod_estudiante`, `codigo_MINED`, `estado`, `creado_por`) 
VALUES
  (6, 'EST-2026-001', 'MINED-6661', 1, 'ADMIN'),
  (7, 'EST-2026-002', 'MINED-6662', 1, 'ADMIN');

INSERT INTO `estudiante_tutor` 
  (`idpersona_estudiante`, `idtutor`, `parentesco`, `es_representante_legal`, `creado_por`) 
VALUES
  (6, 1, 'PADRE', 1, 'ADMIN'),
  (7, 2, 'MADRE', 1, 'ADMIN');

INSERT INTO `docente` 
  (`idpersona`, `cod_docente`, `especialidad`, `estado`, `creado_por`) 
VALUES
  (4, 'DOC-001', 'Licenciatura en Matemáticas', 1, 'ADMIN'),
  (5, 'DOC-002', 'Licenciatura en Lengua y Literatura', 1, 'ADMIN');

INSERT INTO `usuario` 
  (`idusuario`, `idpersona`, `username`, `password_hash`, `tipo_usuario`, `correo`, `estado`, `creado_por`) 
VALUES
  (1, 10, 'admin', '$2a$12$eImiTXuWVxfM37uY4JANjOL.884488448844884488448844884488', 'ADMIN', 'admin@colegiosilviano.edu.ni', 1, 'ADMIN'),
  (2, 4, 'atorres', '$2a$12$eImiTXuWVxfM37uY4JANjOL.884488448844884488448844884488', 'DOCENTE', 'atorres@colegiosilviano.edu.ni', 1, 'ADMIN');

-- ==========================================
-- 2. ESTRUCTURA ACADÉMICA Y INFRAESTRUCTURA
-- ==========================================

INSERT INTO `nivel_educativo` 
  (`idnivel`, `nombre`, `descripcion`, `creado_por`) 
VALUES
  (1, 'Primaria', 'Educación Básica Primaria de 1er a 6to Grado', 'ADMIN'),
  (2, 'Secundaria', 'Educación Secundaria de 7mo a 11mo Año', 'ADMIN');

INSERT INTO `grado` 
  (`idGrado`, `idnivel`, `nombre`, `creado_por`) 
VALUES
  (1, 1, '5to Grado', 'ADMIN'),
  (2, 2, '7mo Año', 'ADMIN');

INSERT INTO `asignatura` 
  (`idAsignatura`, `nombre`, `codigo`, `creado_por`) 
VALUES
  (1, 'Matemáticas', 'MAT-101', 'ADMIN'),
  (2, 'Lengua y Literatura', 'LEN-101', 'ADMIN');

INSERT INTO `catalogo_salon` 
  (`idcatalogo_salon`, `nombre_salon`, `capacidad`, `creado_por`) 
VALUES
  (1, 'Aula A-101 (Pabellón Primaria)', 35, 'ADMIN'),
  (2, 'Aula B-201 (Pabellón Secundaria)', 40, 'ADMIN');

-- Ajuste Crítico: Inserción de anio_lectivo alineado a la oferta del salón
INSERT INTO `salon` 
  (`idSalon`, `idGrado`, `idcatalogo_salon`, `anio_lectivo`, `turno`, `seccion`, `creado_por`) 
VALUES
  (1, 1, 1, 2026, 'MANANA', 'A', 'ADMIN'),
  (2, 2, 2, 2026, 'MANANA', 'A', 'ADMIN');

-- ==========================================
-- 3. PLANIFICACIÓN Y MATRÍCULA (AÑO LECTIVO 2026)
-- ==========================================

INSERT INTO `plan_de_estudio` 
  (`idPlan_de_estudio`, `idGrado`, `anio_lectivo`, `estado`, `creado_por`) 
VALUES
  (1, 1, 2026, 1, 'ADMIN'),
  (2, 2, 2026, 1, 'ADMIN');

-- Ajuste Crítico: Corrección del año lectivo y fechas de matriculación a 2026
INSERT INTO `matricula` 
  (`idmatricula`, `idpersona`, `idSalon`, `anio_lectivo`, `costo_matricula`, `fecha_matricula`, `estado`, `creado_por`) 
VALUES
  (1, 6, 1, 2026, 1200.00, '2026-01-15 08:30:00', 'ACTIVA', 'ADMIN'),
  (2, 7, 2, 2026, 1500.00, '2026-01-16 09:15:00', 'ACTIVA', 'ADMIN');

-- ==========================================
-- 4. EVALUACIONES Y CALIFICACIONES
-- ==========================================

INSERT INTO `periodo_evaluativo` 
  (`idperiodo_evaluativo`, `nombre_periodo`, `numero_periodo`, `anio_escolar`, `estado`, `creado_por`) 
VALUES
  (1, 'I Bloque', 1, 2026, 1, 'ADMIN'),
  (2, 'II Bloque', 2, 2026, 1, 'ADMIN');

INSERT INTO `detalle_plan_de_estudio` 
  (`iddetalle_plan_de_estudio`, `idPlan_de_estudio`, `idAsignatura`, `idDocente`, `creado_por`) 
VALUES
  (1, 1, 1, 4, 'ADMIN'),
  (2, 2, 2, 5, 'ADMIN');

INSERT INTO `calificaciones` 
  (`idcalificacion`, `idmatricula`, `iddetalle_plan_de_estudio`, `idperiodo_evaluativo`, `acumulado`, `examen`, `creado_por`) 
VALUES
  (1, 1, 1, 1, 55.00, 38.00, 'ADMIN'),
  (2, 2, 2, 1, 48.00, 32.00, 'ADMIN');

-- ==========================================
-- 5. MÓDULO FINANCIERO Y COBROS
-- ==========================================

INSERT INTO `catalogo_tarifa` 
  (`idtarifa`, `idnivel`, `anio_lectivo`, `concepto`, `monto`, `creado_por`) 
VALUES
  (1, 1, 2026, 'MENSUALIDAD FEBRERO', 1000.00, 'ADMIN'),
  (2, 2, 2026, 'MENSUALIDAD FEBRERO', 1300.00, 'ADMIN');

-- Ajuste Crítico: Fechas de pago coherentes con el ciclo escolar 2026
INSERT INTO `pago` 
  (`idpago`, `idmatricula`, `num_recibo`, `fecha_pago`, `tipo_pago`, `monto_total`, `creado_por`) 
VALUES
  (1, 1, 'REC-2026-0001', '2026-02-05 10:00:00', 'EFECTIVO', 1000.00, 'ADMIN'),
  (2, 2, 'REC-2026-0002', '2026-02-06 11:30:00', 'TRANSFERENCIA', 1300.00, 'ADMIN');

-- Ajuste Crítico: Completado el registro cortado e inyectadas relaciones correctas
INSERT INTO `detalle_pago` 
  (`iddetalle_pago`, `idpago`, `idtarifa`, `concepto`, `monto`, `creado_por`) 
VALUES
  (1, 1, 1, 'Mensualidad de Febrero 2026 - Primaria', 1000.00, 'ADMIN'),
  (2, 2, 2, 'Mensualidad de Febrero 2026 - Secundaria', 1300.00, 'ADMIN');

-- Reactivar validación de llaves foráneas
SET FOREIGN_KEY_CHECKS = 1;

-- Ajuste de Autoincrementales
ALTER TABLE `persona` AUTO_INCREMENT = 11;
ALTER TABLE `pago` AUTO_INCREMENT = 3;
DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_procesar_emision_recibo`$$

USE `colegiosilviano_dev`;

DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_procesar_emision_recibo`$$

CREATE PROCEDURE `sp_procesar_emision_recibo`(
    IN `p_idpersona` INT,              -- ID del Estudiante
    IN `p_idSalon` INT,                -- Salón asignado para el año lectivo
    IN `p_anio_lectivo` YEAR,
    IN `p_concepto` VARCHAR(100),       -- Ej: 'MATRICULA', 'MENSUALIDAD'
    IN `p_tipo_pago` VARCHAR(20),       -- Ej: 'EFECTIVO', 'TRANSFERENCIA', 'TARJETA'
    IN `p_monto` DECIMAL(10,2),
    IN `p_idtarifa` INT,               -- ID opcional del catálogo tarifario
    IN `p_fecha_transaccion` DATETIME,
    IN `p_usuario` VARCHAR(50),        -- Usuario del sistema que emite el recibo
    OUT `p_recibo_generado` VARCHAR(30)
)
BEGIN
    DECLARE v_idmatricula INT;
    DECLARE v_siguiente_numero INT DEFAULT 1;
    DECLARE v_num_recibo_nuevo VARCHAR(30);

    -- Manejo de excepciones: Deshace la transacción en caso de falla
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    -- =====================================================
    -- PASO 1: OBTENER O REGISTRAR LA MATRÍCULA ACADÉMICA
    -- =====================================================
    -- Busca si el estudiante ya tiene una matrícula activa para el año lectivo
    SELECT `idmatricula` INTO v_idmatricula
    FROM `matricula`
    WHERE `idpersona` = p_idpersona AND `anio_lectivo` = p_anio_lectivo
    LIMIT 1;

    -- Si no existe matrícula previa y el concepto es MATRICULA, se crea automáticamente
    IF v_idmatricula IS NULL THEN
        IF UPPER(p_concepto) LIKE '%MATRI%' THEN
            INSERT INTO `matricula` (
                `idpersona`, 
                `idSalon`, 
                `anio_lectivo`, 
                `costo_matricula`, 
                `fecha_matricula`,
                `estado`,
                `creado_por`
            ) VALUES (
                p_idpersona, 
                p_idSalon, 
                p_anio_lectivo, 
                p_monto, 
                COALESCE(p_fecha_transaccion, CURRENT_TIMESTAMP),
                'ACTIVA',
                p_usuario
            );
            
            SET v_idmatricula = LAST_INSERT_ID();
        ELSE
            SIGNAL SQLSTATE '45000' 
            SET MESSAGE_TEXT = 'Error: El estudiante no cuenta con una matrícula activa para este año lectivo.';
        END IF;
    END IF;

    -- =====================================================
    -- : GENERACIÓN DEL CORRELATIVO BLOQUEADO (FOR UPDATE)
    -- Format: REC-2026-000001
    -- =====================================================
    SELECT IFNULL(MAX(CAST(SUBSTRING(num_recibo, 10) AS UNSIGNED)), 0) + 1 
    INTO v_siguiente_numero
    FROM `pago`
    WHERE `num_recibo` LIKE CONCAT('REC-', p_anio_lectivo, '-%')
    FOR UPDATE;

    SET v_num_recibo_nuevo = CONCAT('REC-', p_anio_lectivo, '-', LPAD(v_siguiente_numero, 6, '0'));
    SET p_recibo_generado = v_num_recibo_nuevo;

    -- =====================================================
    -- PASO 3: REGISTRO EN CABECERA DE PAGO
    -- =====================================================
    INSERT INTO `pago` (
        `idmatricula`, 
        `num_recibo`, 
        `fecha_pago`, 
        `tipo_pago`,
        `monto_total`,
        `creado_por`
    ) VALUES (
        v_idmatricula, 
        v_num_recibo_nuevo, 
        COALESCE(p_fecha_transaccion, CURRENT_TIMESTAMP), 
        UPPER(p_tipo_pago),
        p_monto,
        p_usuario
    );

    -- =====================================================
    -- PASO 4: REGISTRO EN DETALLE DE PAGO
    -- =====================================================
    INSERT INTO `detalle_pago` (
        `idpago`, 
        `idtarifa`,
        `concepto`, 
        `monto`,
        `creado_por`
    ) VALUES (
        LAST_INSERT_ID(), 
        p_idtarifa,
        p_concepto, 
        p_monto,
        p_usuario
    );

    COMMIT;
END$$

DELIMITER ;

-- ==========================================
-- VISTA DE ARQUEO DIARIO DE CAJA
-- ==========================================
CREATE OR REPLACE VIEW `vw_arqueo_caja_diario` AS
SELECT 
    DATE(p.`fecha_pago`) AS `fecha_emision`,
    p.`tipo_pago`,
    p.`creado_por` AS `cajero`,
    COUNT(DISTINCT p.`idpago`) AS `total_recibos`,
    SUM(p.`monto_total`) AS `total_recaudado`
FROM `pago` p
GROUP BY DATE(p.`fecha_pago`), p.`tipo_pago`, p.`creado_por`;
