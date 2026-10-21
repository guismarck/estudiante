-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: colegiosilviano_dev
-- ------------------------------------------------------
-- Server version	5.7.17-log
-- UNLOCK TABLES;
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- 1. Desbloqueo preventivo de sesión y desactivación temporal de checks
UNLOCK TABLES;
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';

DROP DATABASE IF EXISTS `colegiosilviano_dev`;
CREATE DATABASE IF NOT EXISTS `colegiosilviano_dev`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE `colegiosilviano_dev`;

--
-- Table structure for table `asignatura`
--

DROP TABLE IF EXISTS `asignatura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asignatura` (
  `idAsignatura` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `codigo` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idAsignatura`),
  UNIQUE KEY `uk_asignatura_codigo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asignatura`
--

LOCK TABLES `asignatura` WRITE;
/*!40000 ALTER TABLE `asignatura` DISABLE KEYS */;
INSERT INTO `asignatura` VALUES 
-- ==============================================================================
-- POBLADO DE CATÁLOGO DE ASIGNATURAS (PRIMARIA Y SECUNDARIA)
-- Sistema de Gestión Académica - Control de Plan de Estudios
-- ==============================================================================
-- ==============================================================================
-- EDUCACIÓN PRIMARIA
-- ==============================================================================
(1,  'Lengua y Literatura ',           'LEN-PRI-101', 'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(2,  'Matemáticas',                    'MAT-PRI-101', 'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(3,  'Ciencias Naturales',             'CNT-PRI-101', 'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(4,  'Estudios Sociales',              'ESS-PRI-101', 'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(5,  'Lengua Extranjera - Inglés ',     'ING-PRI-101', 'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(6,  'Educación Física y Deportes ',   'EDF-PRI-101', 'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(7,  'Expresión Artística y Cultural ', 'ART-PRI-101', 'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(8,  'Informática y Tecnología ',       'TEC-PRI-101', 'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(9,  'Educación en Valores y Cívica ',  'CIV-PRI-101', 'admin_system', '2026-10-16 20:11:45', NULL, NULL),

-- ==============================================================================
-- EDUCACIÓN SECUNDARIA
-- ==============================================================================
(10, 'Física General',                            'FIS-SEC-201', 'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(11, 'Química General',                           'QUI-SEC-201', 'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(12, 'Geografía Universal y Nacional',           'GEO-SEC-201', 'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(13, 'Historia Universal y Ciencias Sociales',    'HIS-SEC-201', 'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(14, 'Sociología y Economía',                     'SOC-SEC-201', 'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(15, 'Filosofía y Lógica',                        'FIL-SEC-201', 'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(16, 'Talleres de Arte, Música y Teatro',         'ART-SEC-201', 'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(17, 'Orientación Vocacional y Desarrollo Humano','OVD-SEC-201', 'admin_system', '2026-10-16 20:11:45', NULL, NULL);
/*!40000 ALTER TABLE `asignatura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `autorizacion_extemporanea`
--

DROP TABLE IF EXISTS `autorizacion_extemporanea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `autorizacion_extemporanea` (
  `idautorizacion` int unsigned NOT NULL AUTO_INCREMENT,
  `iddetalle_plan_de_estudio` int unsigned NOT NULL COMMENT 'Asignatura y docente especifico autorizados',
  `idperiodo_evaluativo` int unsigned NOT NULL COMMENT 'Periodo evaluativo al que se concede la prorroga',
  `fecha_expiracion` datetime NOT NULL COMMENT 'Fecha y hora limite exacta hasta la cual se permite editar',
  `motivo` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Justificacion academica o administrativa',
  `estado` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1: Vigente/Habilitada, 0: Revocada/Inactiva',
  `autorizado_por` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Usuario administrador o director que concede el permiso',
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idautorizacion`),
  KEY `idx_busqueda_permiso` (`iddetalle_plan_de_estudio`,`idperiodo_evaluativo`,`estado`,`fecha_expiracion`),
  KEY `fk_aut_periodo` (`idperiodo_evaluativo`),
  CONSTRAINT `fk_aut_detalle` FOREIGN KEY (`iddetalle_plan_de_estudio`) REFERENCES `detalle_plan_de_estudio` (`iddetalle_plan_de_estudio`) ON UPDATE CASCADE,
  CONSTRAINT `fk_aut_periodo` FOREIGN KEY (`idperiodo_evaluativo`) REFERENCES `periodo_evaluativo` (`idperiodo_evaluativo`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autorizacion_extemporanea`
--

LOCK TABLES `autorizacion_extemporanea` WRITE;
/*!40000 ALTER TABLE `autorizacion_extemporanea` DISABLE KEYS */;
INSERT INTO `autorizacion_extemporanea` VALUES (1,1,1,'2026-10-19 20:11:46','Revisión y corrección de acta por reclamo justificado de nota',1,'director_academico','2026-10-16 20:11:46');
/*!40000 ALTER TABLE `autorizacion_extemporanea` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `calificaciones`
--

DROP TABLE IF EXISTS `calificaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calificaciones` (
  `idcalificacion` bigint(20) NOT NULL AUTO_INCREMENT,
  `idmatricula` int unsigned NOT NULL,
  `iddetalle_plan_de_estudio` int unsigned NOT NULL,
  `idperiodo_evaluativo` int unsigned NOT NULL,
  `acumulado` decimal(5,2) NOT NULL DEFAULT '0.00',
  `examen` decimal(5,2) NOT NULL DEFAULT '0.00',
  `nota_final` decimal(5,2) GENERATED ALWAYS AS ((`acumulado` + `examen`)) STORED,
  `conducta` varchar(4) COLLATE utf8mb4_unicode_ci DEFAULT 'E' COMMENT 'Evaluación cualitativa o nota de conducta',
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idcalificacion`),
  UNIQUE KEY `uk_nota_periodo_plan` (`idmatricula`,`iddetalle_plan_de_estudio`,`idperiodo_evaluativo`),
  KEY `fk_calif_detalle_plan` (`iddetalle_plan_de_estudio`),
  KEY `fk_calif_periodo` (`idperiodo_evaluativo`),
  CONSTRAINT `fk_calif_detalle_plan` FOREIGN KEY (`iddetalle_plan_de_estudio`) REFERENCES `detalle_plan_de_estudio` (`iddetalle_plan_de_estudio`) ON UPDATE CASCADE,
  CONSTRAINT `fk_calif_matricula` FOREIGN KEY (`idmatricula`) REFERENCES `matricula` (`idmatricula`) ON UPDATE CASCADE,
  CONSTRAINT `fk_calif_periodo` FOREIGN KEY (`idperiodo_evaluativo`) REFERENCES `periodo_evaluativo` (`idperiodo_evaluativo`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calificaciones`
--

LOCK TABLES `calificaciones` WRITE;
/*!40000 ALTER TABLE `calificaciones` DISABLE KEYS */;
INSERT INTO `calificaciones` (`idcalificacion`, `idmatricula`, `iddetalle_plan_de_estudio`, `idperiodo_evaluativo`, `acumulado`, `examen`, `conducta`, `creado_por`, `creado_el`, `actualizado_por`, `actualizado_el`) VALUES (1,1,1,2,55.00,30.00,'58','docente_cmendoza','2026-10-16 20:11:46','SISTEMA_DOCENTE','2026-10-17 19:05:09'),(2,2,1,2,48.00,22.00,'52','docente_cmendoza','2026-10-16 20:11:46','SISTEMA_DOCENTE','2026-10-17 19:05:09'),(3,3,1,2,35.00,41.00,'60','docente_cmendoza','2026-10-16 20:11:46','SISTEMA_DOCENTE','2026-10-17 19:40:06'),(4,1,2,2,40.00,39.00,'70','docente_agarcia','2026-10-16 20:11:46','SISTEMA_DOCENTE','2026-10-18 15:33:40'),(5,2,2,2,40.00,35.00,'75','docente_agarcia','2026-10-16 20:11:46','SISTEMA_DOCENTE','2026-10-18 15:33:40'),(6,4,1,2,30.00,45.00,'70','SISTEMA_DOCENTE','2026-10-17 19:05:09','SISTEMA_DOCENTE','2026-10-17 19:40:32'),(7,3,2,2,20.00,28.00,'55','SISTEMA_DOCENTE','2026-10-18 15:33:40','SISTEMA_DOCENTE','2026-10-18 16:05:11'),(8,4,2,2,35.00,42.00,'65','SISTEMA_DOCENTE','2026-10-18 15:33:40','SISTEMA_DOCENTE',NULL);
/*!40000 ALTER TABLE `calificaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalogo_requisito`
--

DROP TABLE IF EXISTS `catalogo_requisito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `catalogo_requisito` (
  `idrequisito` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `es_obligatorio` tinyint(1) NOT NULL DEFAULT '0',
  `tipo_ingreso` enum('NUEVO INGRESO','REINGRESO','AMBOS') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'AMBOS',
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idrequisito`),
  UNIQUE KEY `uk_requisito_nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogo_requisito`
--

LOCK TABLES `catalogo_requisito` WRITE;
/*!40000 ALTER TABLE `catalogo_requisito` DISABLE KEYS */;
INSERT INTO `catalogo_requisito` VALUES (1,'Partida de Nacimiento (Original y Copia)',0,'AMBOS',1,NULL,'2026-10-13 19:51:57'),(2,'Fotografía Tamaño Carnet Reciente',1,'AMBOS',1,NULL,'2026-10-13 19:51:57'),(3,'Boletín de Calificaciones (Original y Copia)',1,'AMBOS',1,NULL,'2026-10-13 19:51:57'),(4,'Hoja de Traslado (Firmada y Sellada)',0,'AMBOS',1,NULL,'2026-10-13 19:51:57'),(5,'Constancia de Conducta',0,'AMBOS',1,NULL,'2026-10-13 19:51:57'),(6,'Fotocopia de Cédula / DNI de Tutores',0,'AMBOS',1,NULL,'2026-10-13 19:51:57');
/*!40000 ALTER TABLE `catalogo_requisito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalogo_salon`
--

DROP TABLE IF EXISTS `catalogo_salon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `catalogo_salon` (
  `idcatalogo_salon` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre_salon` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `capacidad` int(11) NOT NULL,
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idcatalogo_salon`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogo_salon`
--

LOCK TABLES `catalogo_salon` WRITE;
/*!40000 ALTER TABLE `catalogo_salon` DISABLE KEYS */;
INSERT INTO `catalogo_salon` VALUES 
-- ------------------------------------------------------------------------------
-- Pabellón A: Educación Primaria (Planta Única)
-- ------------------------------------------------------------------------------
(1, 'Aula 01 - Pabellón Primaria',                     35, 'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(2, 'Aula 02 - Pabellón Primaria',                     35, 'admin_system', '2026-10-16 20:11:45', NULL, NULL),

-- ------------------------------------------------------------------------------
-- Pabellón B: Educación Secundaria (Planta Única)
-- ------------------------------------------------------------------------------
(3, 'Aula 03 - Pabellón Secundaria',                   35, 'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(4, 'Aula 04 - Pabellón Secundaria',                   35, 'admin_system', '2026-10-16 20:11:45', NULL, NULL),

-- ------------------------------------------------------------------------------
-- Módulo de Especialidades y Laboratorios
-- ------------------------------------------------------------------------------
(5, 'Laboratorio de Cómputo',                          30, 'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(6, 'Laboratorio de Ciencias (Multidisciplinario)',    25, 'admin_system', '2026-10-16 20:11:45', NULL, NULL);


/*!40000 ALTER TABLE `catalogo_salon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalogo_tarifa`
--

DROP TABLE IF EXISTS `catalogo_tarifa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `catalogo_tarifa` (
  `id_tarifa` int unsigned NOT NULL AUTO_INCREMENT,
  `idnivel` int unsigned DEFAULT NULL,
  `id_grado` int unsigned DEFAULT NULL,
  `anio_lectivo` int NOT NULL,
  `concepto` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `es_obligatorio` tinyint(1) NOT NULL DEFAULT '1',
  `mes_aplicacion` int DEFAULT NULL,
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_tarifa`),
  UNIQUE KEY `uk_tarifa_nivel_mes_anio` (`idnivel`,`anio_lectivo`,`mes_aplicacion`,`concepto`),
  KEY `fk_tarifa__grado` (`id_grado`),
  CONSTRAINT `fk_tarifa__grado` FOREIGN KEY (`id_grado`) REFERENCES `grado` (`idGrado`) ON UPDATE CASCADE,
  CONSTRAINT `fk_tarifa__nivel` FOREIGN KEY (`idnivel`) REFERENCES `nivel_educativo` (`idnivel`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Catálogo de aranceles institucionales';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogo_tarifa`
--

LOCK TABLES `catalogo_tarifa` WRITE;
/*!40000 ALTER TABLE `catalogo_tarifa` DISABLE KEYS */;
INSERT INTO `catalogo_tarifa` VALUES (1,1,NULL,2026,'MATRICULA PRIMARIA REGULAR 2026',3500.00,1,NULL,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(2,1,NULL,2026,'MENSUALIDAD FEBRERO PRIMARIA REGULAR 2026',1300.00,1,2,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(3,1,NULL,2026,'MENSUALIDAD MARZO PRIMARIA REGULAR 2026',1300.00,1,3,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(4,1,NULL,2026,'MENSUALIDAD ABRIL PRIMARIA REGULAR 2026',1300.00,1,4,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(5,1,NULL,2026,'MENSUALIDAD MAYO PRIMARIA REGULAR 2026',1300.00,1,5,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(6,1,NULL,2026,'MENSUALIDAD JUNIO PRIMARIA REGULAR 2026',1300.00,1,6,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(7,1,NULL,2026,'MENSUALIDAD JULIO PRIMARIA REGULAR 2026',1300.00,1,7,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(8,1,NULL,2026,'MENSUALIDAD AGOSTO PRIMARIA REGULAR 2026',1300.00,1,8,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(9,1,NULL,2026,'MENSUALIDAD SEPTIEMBRE PRIMARIA REGULAR 2026',1300.00,1,9,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(10,1,NULL,2026,'MENSUALIDAD OCTUBRE PRIMARIA REGULAR 2026',1300.00,1,10,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(11,1,NULL,2026,'MENSUALIDAD NOVIEMBRE PRIMARIA REGULAR 2026',1300.00,1,11,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(12,2,NULL,2026,'MATRICULA SECUNDARIA REGULAR 2026',4500.00,1,NULL,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(13,2,NULL,2026,'MENSUALIDAD FEBRERO SECUNDARIA REGULAR 2026',1800.00,1,2,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(14,2,NULL,2026,'MENSUALIDAD MARZO SECUNDARIA REGULAR 2026',1800.00,1,3,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(15,2,NULL,2026,'MENSUALIDAD ABRIL SECUNDARIA REGULAR 2026',1800.00,1,4,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(16,2,NULL,2026,'MENSUALIDAD MAYO SECUNDARIA REGULAR 2026',1800.00,1,5,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(17,2,NULL,2026,'MENSUALIDAD JUNIO SECUNDARIA REGULAR 2026',1800.00,1,6,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(18,2,NULL,2026,'MENSUALIDAD JULIO SECUNDARIA REGULAR 2026',1800.00,1,7,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(19,2,NULL,2026,'MENSUALIDAD AGOSTO SECUNDARIA REGULAR 2026',1800.00,1,8,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(20,2,NULL,2026,'MENSUALIDAD SEPTIEMBRE SECUNDARIA REGULAR 2026',1800.00,1,9,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(21,2,NULL,2026,'MENSUALIDAD OCTUBRE SECUNDARIA REGULAR 2026',1800.00,1,10,'ADMIN','2026-10-11 18:41:14',NULL,NULL),(22,2,NULL,2026,'MENSUALIDAD NOVIEMBRE SECUNDARIA REGULAR 2026',1800.00,1,11,'ADMIN','2026-10-11 18:41:14',NULL,NULL);
/*!40000 ALTER TABLE `catalogo_tarifa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_pago`
--

DROP TABLE IF EXISTS `detalle_pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalle_pago` (
  `iddetalle_pago` int unsigned NOT NULL AUTO_INCREMENT,
  `idpago` int unsigned NOT NULL,
  `id_tarifa` int unsigned DEFAULT NULL,
  `concepto` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`iddetalle_pago`),
  KEY `idx_detpago_tarifa_monto` (`id_tarifa`,`idpago`,`monto`),
  KEY `fk_detpago_cabecera` (`idpago`),
  CONSTRAINT `fk_detpago_cabecera` FOREIGN KEY (`idpago`) REFERENCES `pago` (`idpago`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_detpago_tarifa` FOREIGN KEY (`id_tarifa`) REFERENCES `catalogo_tarifa` (`id_tarifa`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_pago`
--

LOCK TABLES `detalle_pago` WRITE;
/*!40000 ALTER TABLE `detalle_pago` DISABLE KEYS */;
INSERT INTO `detalle_pago` VALUES (1,1,1,'MATRICULA PRIMARIA REGULAR 2026',1000.00,'Cajero_Sistema','2026-10-11 18:49:23',NULL,NULL),(2,2,1,'MATRICULA PRIMARIA REGULAR 2026',500.00,'Cajero_Sistema','2026-10-11 19:50:39',NULL,NULL),(3,3,1,'MATRICULA PRIMARIA REGULAR 2026',600.00,'Cajero_Sistema','2026-10-11 21:04:29',NULL,NULL),(4,4,1,'MATRICULA PRIMARIA REGULAR 2026',400.00,'Cajero_Sistema','2026-10-11 21:10:35',NULL,NULL),(5,5,1,'MATRICULA PRIMARIA REGULAR 2026',1500.00,'Cajero_Sistema','2026-10-12 21:01:14',NULL,NULL),(6,6,1,'MATRICULA PRIMARIA REGULAR 2026',2000.00,'Cajero_Sistema','2026-10-12 21:04:37',NULL,NULL),(7,7,1,'MATRICULA PRIMARIA REGULAR 2026',1000.00,'Cajero_Sistema','2026-10-14 21:01:50',NULL,NULL),(8,8,1,'MATRICULA PRIMARIA REGULAR 2026',3500.00,'Cajero_Sistema','2026-10-14 22:36:36',NULL,NULL);
/*!40000 ALTER TABLE `detalle_pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_plan_de_estudio`
--

DROP TABLE IF EXISTS `detalle_plan_de_estudio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalle_plan_de_estudio` (
  `iddetalle_plan_de_estudio` int unsigned NOT NULL AUTO_INCREMENT,
  `idPlan_de_estudio` int unsigned NOT NULL,
  `idAsignatura` int unsigned NOT NULL,
  `idDocente` int unsigned NOT NULL,
  `idSalon` int unsigned DEFAULT NULL,
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`iddetalle_plan_de_estudio`),
  UNIQUE KEY `uk_plan_asignatura_salon` (`idPlan_de_estudio`,`idAsignatura`,`idSalon`),
  KEY `fk_det_asig` (`idAsignatura`),
  KEY `fk_det_docente` (`idDocente`),
  KEY `idx_detalle_plan_salon` (`idSalon`),
  CONSTRAINT `fk_det_asig` FOREIGN KEY (`idAsignatura`) REFERENCES `asignatura` (`idAsignatura`) ON UPDATE CASCADE,
  CONSTRAINT `fk_det_docente` FOREIGN KEY (`idDocente`) REFERENCES `docente` (`idpersona`) ON UPDATE CASCADE,
  CONSTRAINT `fk_det_plan` FOREIGN KEY (`idPlan_de_estudio`) REFERENCES `plan_de_estudio` (`idPlan_de_estudio`) ON UPDATE CASCADE,
  CONSTRAINT `fk_det_salon` FOREIGN KEY (`idSalon`) REFERENCES `salon` (`idSalon`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_plan_de_estudio`
--

LOCK TABLES `detalle_plan_de_estudio` WRITE;
/*!40000 ALTER TABLE `detalle_plan_de_estudio` DISABLE KEYS */;
INSERT INTO `detalle_plan_de_estudio` VALUES (1,1,1,1,1,'admin_system','2026-10-16 20:11:46','admin_system','2026-10-16 20:29:24'),(2,1,2,2,1,'admin_system','2026-10-16 20:11:46','admin_system','2026-10-16 20:29:24'),(3,8,1,2,3,'admin','2026-10-19 20:56:57',NULL,NULL),(4,1,3,1,2,'admin','2026-10-19 21:24:43',NULL,NULL);
/*!40000 ALTER TABLE `detalle_plan_de_estudio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detallepago`
--

DROP TABLE IF EXISTS `detallepago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detallepago` (
  `iddetalle_pago` int(11) NOT NULL AUTO_INCREMENT,
  `costo_mesualidad` float DEFAULT NULL,
  `descuento` float DEFAULT NULL,
  `iva` float DEFAULT NULL,
  `subtota` float DEFAULT NULL,
  `total` float DEFAULT NULL,
  `idpago` int(11) DEFAULT NULL,
  PRIMARY KEY (`iddetalle_pago`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detallepago`
--

LOCK TABLES `detallepago` WRITE;
/*!40000 ALTER TABLE `detallepago` DISABLE KEYS */;
/*!40000 ALTER TABLE `detallepago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docente`
--

DROP TABLE IF EXISTS `docente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `docente` (
  `idpersona` int unsigned NOT NULL,
  `cod_docente` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `especialidad` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idpersona`),
  UNIQUE KEY `uk_docente_codigo` (`cod_docente`),
  CONSTRAINT `fk_docente_persona` FOREIGN KEY (`idpersona`) REFERENCES `persona` (`idpersona`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docente`
--

LOCK TABLES `docente` WRITE;
/*!40000 ALTER TABLE `docente` DISABLE KEYS */;
INSERT INTO `docente` VALUES 
(1,  'DOC-2026-001', 'Lic. Matemáticas y Física',               1, 'admin_system', '2026-10-16 20:11:45', NULL, NULL), -- Carlos Mendoza
(2,  'DOC-2026-002', 'Lic. Lengua y Literatura Castellana',     1, 'admin_system', '2026-10-16 20:11:45', NULL, NULL), -- Ana García
(10, 'DOC-2026-003', 'Lic. Ciencias Naturales y Biología',       1, 'admin_system', '2026-10-20 08:00:00', NULL, NULL), -- Mario Estrada
(11, 'DOC-2026-004', 'Lic. Ciencias Sociales y Geografía',       1, 'admin_system', '2026-10-20 08:00:00', NULL, NULL), -- Claudia Ramos
(12, 'DOC-2026-005', 'Lic. Lengua Extranjera (Inglés)',          1, 'admin_system', '2026-10-20 08:00:00', NULL, NULL), -- Jorge Blandón
(13, 'DOC-2026-006', 'Lic. Educación Primaria Integral',         1, 'admin_system', '2026-10-20 08:00:00', NULL, NULL), -- Martha Rivas
(14, 'DOC-2026-007', 'Lic. Educación Física y Deportes',        1, 'admin_system', '2026-10-20 08:00:00', NULL, NULL), -- Gabriel Jarquín
(15, 'DOC-2026-008', 'Lic. Expresión Artística y Cultural',      1, 'admin_system', '2026-10-20 08:00:00', NULL, NULL), -- Xiomara Argüello
(16, 'DOC-2026-009', 'Ing. Computación e Informática Educativa', 1, 'admin_system', '2026-10-20 08:00:00', NULL, NULL), -- Ricardo Mairena
(43, 'DOC-2026-010', 'Lic. Química y Física Aplicada',           1, 'admin_system', '2026-10-20 08:00:00', NULL, NULL), -- Héctor Úbeda
(44, 'DOC-2026-011', 'Lic. Psicopedagogía y Orientación',        1, 'admin_system', '2026-10-20 08:00:00', NULL, NULL), -- Patricia Zamora
(45, 'DOC-2026-012', 'Lic. Formación Cívica y Valores',          1, 'admin_system', '2026-10-20 08:00:00', NULL, NULL);  -- Francisco Mayorga;
/*!40000 ALTER TABLE `docente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docenteplandeestudio`
--

DROP TABLE IF EXISTS `docenteplandeestudio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `docenteplandeestudio` (
  `idDocente_plan_de_estudio` int(11) NOT NULL AUTO_INCREMENT,
  `idpersona` int(11) DEFAULT NULL,
  `idSalon` int unsigned DEFAULT NULL,
  PRIMARY KEY (`idDocente_plan_de_estudio`),
  KEY `FKmpn9nax1ygkjq147mcahg1rqd` (`idSalon`),
  CONSTRAINT `FKmpn9nax1ygkjq147mcahg1rqd` FOREIGN KEY (`idSalon`) REFERENCES `salon` (`idSalon`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docenteplandeestudio`
--

LOCK TABLES `docenteplandeestudio` WRITE;
/*!40000 ALTER TABLE `docenteplandeestudio` DISABLE KEYS */;
/*!40000 ALTER TABLE `docenteplandeestudio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estudiante`
--

DROP TABLE IF EXISTS `estudiante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estudiante` (
  `idpersona` int unsigned NOT NULL,
  `cod_estudiante` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `codigo_MINED` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1 = Activo, 0 = Inactivo',
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idpersona`),
  UNIQUE KEY `uk_estudiante_codigo` (`cod_estudiante`),
  UNIQUE KEY `uk_estudiante_mined` (`codigo_MINED`),
  CONSTRAINT `fk_estudiante_persona` FOREIGN KEY (`idpersona`) REFERENCES `persona` (`idpersona`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiante`
--

LOCK TABLES `estudiante` WRITE;
/*!40000 ALTER TABLE `estudiante` DISABLE KEYS */;
INSERT INTO `estudiante` VALUES (3,'EST-2026-003','MINED-2012-0055',1,'admin_system','2026-10-16 22:06:49','admin_system','2026-10-16 22:18:15'),(4,'EST-2026-001','MINED-2012-0033',1,'ADMIN','2026-10-04 20:41:50','admin_system','2026-10-16 22:18:15'),(5,'EST-2026-002','MINED-2012-0044',1,'ADMIN','2026-10-04 20:41:50','admin_system','2026-10-16 22:18:15'),(6,'EST-2026-006','MINED-2012-0066',1,'admin_system','2026-10-17 11:39:32',NULL,NULL),(7,'EST-2026-007','est-006',1,'admin_system','2026-10-17 11:39:32','ADMIN','2026-10-17 12:45:24'),(8,'EST-2026-008','MINED-2012-0088',1,'admin_system','2026-10-17 11:39:32',NULL,NULL),(18,'fwefw','est-2226',1,NULL,'2026-10-09 23:38:28','ADMIN','2026-10-14 22:25:57');
/*!40000 ALTER TABLE `estudiante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estudiante_tutor`
--

DROP TABLE IF EXISTS `estudiante_tutor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estudiante_tutor` (
  `idpersona_estudiante` int unsigned NOT NULL,
  `idtutor` int unsigned NOT NULL,
  `parentesco` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `es_representante_legal` tinyint(1) NOT NULL DEFAULT '0',
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idpersona_estudiante`,`idtutor`),
  KEY `fk_et_tutor` (`idtutor`),
  CONSTRAINT `fk_et_estudiante` FOREIGN KEY (`idpersona_estudiante`) REFERENCES `estudiante` (`idpersona`) ON UPDATE CASCADE,
  CONSTRAINT `fk_et_tutor` FOREIGN KEY (`idtutor`) REFERENCES `tutor` (`idtutor`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiante_tutor`
--

LOCK TABLES `estudiante_tutor` WRITE;
/*!40000 ALTER TABLE `estudiante_tutor` DISABLE KEYS */;
INSERT INTO `estudiante_tutor` VALUES (4,1,'PADRE',1,'ADMIN','2026-10-04 20:41:50',NULL,NULL),(5,2,'MADRE',1,'ADMIN','2026-10-04 20:41:50',NULL,NULL),(5,4,'PADRE',1,'ADMIN','2026-10-14 21:03:27',NULL,NULL),(7,6,'PADRE',1,'ADMIN','2026-10-17 12:45:24',NULL,NULL),(18,3,'PADRE',1,'ADMIN','2026-10-13 20:29:09',NULL,NULL),(18,5,'MADRE',1,'ADMIN','2026-10-14 22:25:57',NULL,NULL);
/*!40000 ALTER TABLE `estudiante_tutor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grado`
--

DROP TABLE IF EXISTS `grado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grado` (
  `idGrado` int unsigned NOT NULL AUTO_INCREMENT,
  `idnivel` int unsigned NOT NULL,
  `nombre` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idGrado`),
  UNIQUE KEY `uk_grado_nivel_nombre` (`idnivel`,`nombre`),
  CONSTRAINT `fk_grado_nivel` FOREIGN KEY (`idnivel`) REFERENCES `nivel_educativo` (`idnivel`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grado`
--

LOCK TABLES `grado` WRITE;
/*!40000 ALTER TABLE `grado` DISABLE KEYS */;
INSERT INTO `grado` VALUES (1,1,'7mo Grado','admin_system','2026-10-16 20:11:45',NULL,NULL),(2,1,'8vo Grado','admin_system','2026-10-16 20:11:45',NULL,NULL),(3,2,'1er Grado','admin_system','2026-10-18 19:02:29',NULL,NULL),(4,2,'2do Grado','admin_system','2026-10-18 19:02:29',NULL,NULL),(5,2,'3er Grado','admin_system','2026-10-18 19:02:29',NULL,NULL),(6,2,'4to Grado','admin_system','2026-10-18 19:02:29',NULL,NULL),(7,2,'5to Grado','admin_system','2026-10-18 19:02:29',NULL,NULL),(8,2,'6to Grado','admin_system','2026-10-18 19:02:29',NULL,NULL),(9,1,'9no Grado','admin_system','2026-10-18 19:02:29',NULL,NULL),(10,1,'10mo Grado','admin_system','2026-10-18 19:02:29',NULL,NULL),(11,1,'11mo Grado','admin_system','2026-10-18 19:02:29',NULL,NULL);
/*!40000 ALTER TABLE `grado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matricula`
--

DROP TABLE IF EXISTS `matricula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matricula` (
  `idmatricula` int unsigned NOT NULL AUTO_INCREMENT,
  `idpersona` int unsigned NOT NULL,
  `idSalon` int unsigned NOT NULL,
  `tipo_ingreso` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'NUEVO INGRESO',
  `anio_lectivo` smallint(6) NOT NULL,
  `costo_matricula` decimal(10,2) NOT NULL DEFAULT '0.00',
  `fecha_matricula` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `estado` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ACTIVA',
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idmatricula`),
  UNIQUE KEY `uk_estudiante_anio` (`idpersona`,`anio_lectivo`),
  KEY `idx_matricula_salon_anio` (`idSalon`,`anio_lectivo`,`estado`),
  CONSTRAINT `fk_mat_estudiante` FOREIGN KEY (`idpersona`) REFERENCES `estudiante` (`idpersona`),
  CONSTRAINT `fk_mat_persona` FOREIGN KEY (`idpersona`) REFERENCES `persona` (`idpersona`) ON UPDATE CASCADE,
  CONSTRAINT `fk_mat_salon` FOREIGN KEY (`idSalon`) REFERENCES `salon` (`idSalon`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matricula`
--

LOCK TABLES `matricula` WRITE;
/*!40000 ALTER TABLE `matricula` DISABLE KEYS */;
INSERT INTO `matricula` VALUES (1,3,1,'NUEVO INGRESO',2026,50.00,'2026-10-16 20:11:46','ACTIVA','admin_system','2026-10-16 20:11:46',NULL,NULL),(2,4,1,'REINGRESO',2026,45.00,'2026-10-16 20:11:46','ACTIVA','admin_system','2026-10-16 20:11:46',NULL,NULL),(3,5,1,'NUEVO INGRESO',2026,50.00,'2026-10-16 20:11:46','ACTIVA','admin_system','2026-10-16 20:11:46',NULL,NULL),(4,7,1,'REINGRESO',2026,350.00,'2026-10-17 12:45:24','ACTIVA','ADMIN','2026-10-17 12:45:24',NULL,NULL);
/*!40000 ALTER TABLE `matricula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matricula_requisito`
--

DROP TABLE IF EXISTS `matricula_requisito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matricula_requisito` (
  `idmatricula` int unsigned NOT NULL,
  `idrequisito` int unsigned NOT NULL,
  `entregado` tinyint(1) NOT NULL DEFAULT '1',
  `fecha_recepcion` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `observacion` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idmatricula`,`idrequisito`),
  KEY `fk_mr_requisito` (`idrequisito`),
  CONSTRAINT `fk_mr_matricula` FOREIGN KEY (`idmatricula`) REFERENCES `matricula` (`idmatricula`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_mr_requisito` FOREIGN KEY (`idrequisito`) REFERENCES `catalogo_requisito` (`idrequisito`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matricula_requisito`
--

LOCK TABLES `matricula_requisito` WRITE;
/*!40000 ALTER TABLE `matricula_requisito` DISABLE KEYS */;
INSERT INTO `matricula_requisito` VALUES (1,1,1,'2026-10-14 22:25:57',NULL),(1,2,1,'2026-10-14 22:25:57',NULL),(1,3,1,'2026-10-14 22:25:57',NULL),(1,4,1,'2026-10-14 22:25:57',NULL),(1,5,1,'2026-10-14 22:25:57',NULL),(1,6,1,'2026-10-14 22:25:57',NULL),(2,1,1,'2026-10-14 21:03:27',NULL),(2,2,1,'2026-10-14 21:03:27',NULL),(2,3,1,'2026-10-14 21:03:27',NULL),(2,4,1,'2026-10-14 21:03:27',NULL),(2,5,1,'2026-10-14 21:03:27',NULL),(2,6,1,'2026-10-14 21:03:27',NULL),(4,1,1,'2026-10-17 12:45:24',NULL),(4,2,1,'2026-10-17 12:45:24',NULL),(4,3,1,'2026-10-17 12:45:24',NULL),(4,4,1,'2026-10-17 12:45:24',NULL),(4,5,1,'2026-10-17 12:45:24',NULL),(4,6,1,'2026-10-17 12:45:24',NULL);
/*!40000 ALTER TABLE `matricula_requisito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nivel_educativo`
--

DROP TABLE IF EXISTS `nivel_educativo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nivel_educativo` (
  `idnivel` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `descripcion` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idnivel`),
  UNIQUE KEY `uk_nivel_nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nivel_educativo`
--

LOCK TABLES `nivel_educativo` WRITE;
/*!40000 ALTER TABLE `nivel_educativo` DISABLE KEYS */;
INSERT INTO `nivel_educativo` VALUES (1,'Educación Primaria Regular','Educación básica obligatoria enfocada en el desarrollo de competencias lectoras, matemáticas y ciencias (1ro a 6to Grado).','admin_system','2026-10-16 20:11:45',NULL,NULL),(2,'Educación Secundaria Regular','Educación media enfocada en competencias científicas, humanísticas y formación ciudadana (7mo a 11mo / 12mo Grado).','admin_system','2026-10-16 20:11:45',NULL,NULL);
/*!40000 ALTER TABLE `nivel_educativo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago`
--

DROP TABLE IF EXISTS `pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pago` (
  `idpago` int unsigned NOT NULL AUTO_INCREMENT,
  `idpersona` int unsigned NOT NULL,
  `idmatricula` int unsigned DEFAULT NULL,
  `num_recibo` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_pago` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tipo_pago` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `monto_total` decimal(10,2) NOT NULL,
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `concepto` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `monto` decimal(10,2) NOT NULL,
  PRIMARY KEY (`idpago`),
  UNIQUE KEY `uk_pago_num_recibo` (`num_recibo`),
  KEY `idx_pago_fecha` (`fecha_pago`),
  KEY `fk_pago_persona` (`idpersona`),
  KEY `fk_pago_matricula` (`idmatricula`),
  CONSTRAINT `fk_pago_matricula` FOREIGN KEY (`idmatricula`) REFERENCES `matricula` (`idmatricula`) ON UPDATE CASCADE,
  CONSTRAINT `fk_pago_persona` FOREIGN KEY (`idpersona`) REFERENCES `persona` (`idpersona`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago`
--

LOCK TABLES `pago` WRITE;
/*!40000 ALTER TABLE `pago` DISABLE KEYS */;
INSERT INTO `pago` VALUES (1,7,NULL,'REC-2026-000001','2026-10-11 18:49:22','EFECTIVO',1000.00,'Cajero_Sistema','2026-10-11 18:49:22',NULL,NULL,NULL,0.00),(2,7,NULL,'REC-2026-000002','2026-10-11 19:50:39','EFECTIVO',500.00,'Cajero_Sistema','2026-10-11 19:50:39',NULL,NULL,NULL,0.00),(3,7,NULL,'REC-2026-000003','2026-10-11 21:04:29','EFECTIVO',600.00,'Cajero_Sistema','2026-10-11 21:04:29',NULL,NULL,NULL,0.00),(4,7,NULL,'REC-2026-000004','2026-10-11 21:10:35','EFECTIVO',400.00,'Cajero_Sistema','2026-10-11 21:10:35',NULL,NULL,NULL,0.00),(5,18,NULL,'REC-2026-000005','2026-10-12 21:01:14','EFECTIVO',1500.00,'Cajero_Sistema','2026-10-12 21:01:14',NULL,NULL,NULL,0.00),(6,18,NULL,'REC-2026-000006','2026-10-12 21:04:37','EFECTIVO',2000.00,'Cajero_Sistema','2026-10-12 21:04:37',NULL,NULL,NULL,0.00),(7,7,NULL,'REC-2026-000007','2026-10-14 21:01:50','EFECTIVO',1000.00,'Cajero_Sistema','2026-10-14 21:01:50',NULL,NULL,NULL,0.00),(8,6,NULL,'REC-2026-000008','2026-10-14 22:36:36','EFECTIVO',3500.00,'Cajero_Sistema','2026-10-14 22:36:36',NULL,NULL,NULL,0.00);
/*!40000 ALTER TABLE `pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parcial`
--

DROP TABLE IF EXISTS `parcial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parcial` (
  `idParcial` int(11) NOT NULL AUTO_INCREMENT,
  `parcial` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idParcial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parcial`
--

LOCK TABLES `parcial` WRITE;
/*!40000 ALTER TABLE `parcial` DISABLE KEYS */;
/*!40000 ALTER TABLE `parcial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `periodo_evaluativo`
--

DROP TABLE IF EXISTS `periodo_evaluativo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `periodo_evaluativo` (
  `idperiodo_evaluativo` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre_periodo` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `numero_periodo` int NOT NULL,
  `anio_escolar` int NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_fin` date NOT NULL,
  `fecha_cierre_docente` datetime NOT NULL COMMENT 'Fecha y hora limite para que los docentes ingresen/editen notas',
  `estado` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1: Activo/Abierto, 0: Inactivo/Cerrado manualmente',
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idperiodo_evaluativo`),
  UNIQUE KEY `uk_periodo_anio_numero` (`anio_escolar`,`numero_periodo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `periodo_evaluativo`
--

LOCK TABLES `periodo_evaluativo` WRITE;
/*!40000 ALTER TABLE `periodo_evaluativo` DISABLE KEYS */;
INSERT INTO `periodo_evaluativo` VALUES 
-- Periodo 1: Enero a Marzo (Cerrado)
(1, 'I Semestre',   1, 2026, '2026-01-15', '2026-03-31', '2026-04-05 23:59:59', 0, 'admin_system', '2026-10-16 20:11:46', NULL, NULL),

-- Periodo 2: Abril a Junio (Cerrado)
(2, 'II Semestre',  2, 2026, '2026-04-01', '2026-06-30', '2026-07-05 23:59:59', 0, 'admin_system', '2026-10-16 20:11:46', NULL, NULL),

-- Periodo 3: Julio a Septiembre (Cerrado)
(3, 'III Semestre', 3, 2026, '2026-07-01', '2026-09-30', '2026-10-05 23:59:59', 0, 'admin_system', '2026-10-16 20:11:46', NULL, NULL),

-- Periodo 4: Octubre a Diciembre (Activo / En Cursamiento)
(4, 'IV Semestre',  4, 2026, '2026-10-01', '2026-11-18', '2026-12-04 23:59:59', 1, 'admin_system', '2026-10-16 20:11:46', NULL, NULL);
/*!40000 ALTER TABLE `periodo_evaluativo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona`
--

DROP TABLE IF EXISTS `persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persona` (
  `idpersona` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre_completo` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `apellido_completo` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sexo` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `cedula` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `partida_nacimiento` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `direccion` varchar(300) COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefono` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idpersona`),
  UNIQUE KEY `uk_persona_cedula` (`cedula`),
  KEY `idx_persona_apellidos` (`apellido_completo`,`nombre_completo`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona`
--

LOCK TABLES `persona` WRITE;
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
INSERT INTO `persona` VALUES 
-- ==============================================================================
-- PERSONAL DOCENTE Y ADMINISTRATIVO (IDs 1, 2, 10 - 16, 43 - 45)
-- ==============================================================================
(1,  'Carlos Alberto',       'Mendoza López',       'MASCULINO', '1985-05-12', '001-120585-0001A', NULL,               'Managua, Nicaragua',       '88881111', 'cmendoza@colegio.edu',       'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(2,  'Ana Sofía',            'García Gutiérrez',    'FEMENINO',  '1990-08-22', '001-220890-0002B', NULL,               'Managua, Nicaragua',       '88882222', 'agarcia@colegio.edu',        'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(10, 'Mario José',           'Estrada Bermúdez',    'MASCULINO', '1982-03-14', '001-140382-0010M', NULL,               'Ciudad Sandino, Managua',  '87654321', 'mestrada@colegio.edu',       'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(11, 'Claudia Patricia',     'Ramos Fonseca',       'FEMENINO',  '1988-11-03', '001-031188-0011C', NULL,               'Managua, Nicaragua',       '87654322', 'cramos@colegio.edu',         'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(12, 'Jorge Luis',           'Blandón Sequeira',    'MASCULINO', '1979-07-19', '001-190779-0012J', NULL,               'Ciudad Sandino, Managua',  '87654323', 'jblandon@colegio.edu',       'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(13, 'Martha Lorena',        'Rivas Castillo',      'FEMENINO',  '1992-01-25', '001-250192-0013M', NULL,               'Managua, Nicaragua',       '87654324', 'mrivas@colegio.edu',         'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(14, 'Gabriel Antonio',      'Jarquín Jarquín',     'MASCULINO', '1987-09-12', '001-120987-0014G', NULL,               'Ciudad Sandino, Managua',  '87654325', 'gjarquin@colegio.edu',       'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(15, 'Xiomara del Carmen',   'Argüello Urbina',     'FEMENINO',  '1984-04-30', '001-300484-0015X', NULL,               'Managua, Nicaragua',       '87654326', 'xarguello@colegio.edu',      'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(16, 'Ricardo Alfonso',      'Mairena Suazo',       'MASCULINO', '1975-12-08', '001-081275-0016R', NULL,               'Managua, Nicaragua',       '87654327', 'rmairena@colegio.edu',       'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(43, 'Héctor Luis',          'Úbeda Sevilla',       'MASCULINO', '1981-06-17', '001-170681-0043H', NULL,               'Ciudad Sandino, Managua',  '87654354', 'hubeda@colegio.edu',         'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(44, 'Patricia del Carmen',  'Zamora Calero',       'FEMENINO',  '1986-10-05', '001-051086-0044P', NULL,               'Managua, Nicaragua',       '87654355', 'pzamora@colegio.edu',        'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(45, 'Francisco Javier',     'Mayorga Bermúdez',    'MASCULINO', '1993-02-28', '001-280293-0045F', NULL,               'Ciudad Sandino, Managua',  '87654356', 'fmayorga@colegio.edu',       'admin_system', '2026-10-20 08:00:00', NULL, NULL),

-- ==============================================================================
-- PADRES DE FAMILIA / TUTORES LEGALES (IDs 9, 17 - 22, 41, 42)
-- ==============================================================================
(9,  'Juan Rodolfo',         'Guzmán Méndez',       'MASCULINO', '1980-01-01', '001-120589-0025Y', NULL,               'Ciudad Sandino, Managua',  '78775464', 'jrguzman@tutor.edu',         'ADMIN',        '2026-10-17 12:45:24', 'ADMIN', '2026-10-20 09:00:00'),
(17, 'José Francisco',       'Pérez Montenegro',    'MASCULINO', '1980-05-15', '001-150580-0017J', NULL,               'Managua, Nicaragua',       '87654328', 'jperez_tutor@gmail.com',     'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(18, 'Karla Vanessa',        'Ruiz Morales',        'FEMENINO',  '1983-08-20', '001-200883-0018K', NULL,               'Managua, Nicaragua',       '87654329', 'kruiz_tutor@gmail.com',      'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(19, 'Luis Alberto',         'Silva Obando',        'MASCULINO', '1978-11-11', '001-111178-0019L', NULL,               'Managua, Nicaragua',       '87654330', 'lsilva_tutor@gmail.com',     'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(20, 'Brenda María',         'Torres Blandón',      'FEMENINO',  '1985-03-09', '001-090385-0020B', NULL,               'Managua, Nicaragua',       '87654331', 'btorres_tutor@gmail.com',    'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(21, 'Denis Eduardo',        'Gómez Espinoza',      'MASCULINO', '1981-09-27', '001-270981-0021D', NULL,               'Managua, Nicaragua',       '87654332', 'dgomez_tutor@gmail.com',     'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(22, 'Sandra Isabel',        'Díaz Mayorga',        'FEMENINO',  '1986-12-14', '001-141286-0022S', NULL,               'Managua, Nicaragua',       '87654333', 'sdiaz_tutor@gmail.com',      'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(41, 'Rodrigo José',         'Sevilla Úbeda',       'MASCULINO', '1977-04-18', '001-180477-0041R', NULL,               'Ciudad Sandino, Managua',  '87654352', 'rsevilla_tutor@gmail.com',   'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(42, 'Esperanza del Socorro','Calero Zamora',       'FEMENINO',  '1982-01-22', '001-220182-0042E', NULL,               'Ciudad Sandino, Managua',  '87654353', 'ecalero_tutor@gmail.com',    'admin_system', '2026-10-20 08:00:00', NULL, NULL),

-- ==============================================================================
-- ESTUDIANTES DE PRIMARIA Y SECUNDARIA (IDs 3 - 8, 23 - 40)
-- ==============================================================================
(3,  'Juan José',            'Pérez Ruiz',          'MASCULINO', '2012-03-15', NULL,               'PN-2012-9988', 'Managua, Nicaragua',       '88883333', 'jperez@estudiante.edu',      'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(4,  'María Elena',          'Silva Torres',        'FEMENINO',  '2012-07-10', NULL,               'PN-2012-9989', 'Managua, Nicaragua',       '88884444', 'msilva@estudiante.edu',      'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(5,  'Roberto Carlos',       'Gómez Díaz',          'MASCULINO', '2012-11-05', NULL,               'PN-2012-9990', 'Managua, Nicaragua',       '88885555', 'rgomez@estudiante.edu',      'admin_system', '2026-10-16 20:11:45', NULL, NULL),
(6,  'William',              'Guzmán Méndez',       'MASCULINO', '1999-11-09', '001-091199-1021F', NULL,           'Ciudad Sandino, Managua',  '12345467', 'wguzman@estudiante.edu',     'admin_system', '2026-10-17 11:37:52', NULL, NULL),
(7,  'Joel',                 'Méndez Morales',      'MASCULINO', '2000-02-16', '001-160200-0012J', NULL,           'Ciudad Sandino, Managua',  '76543210', 'jmendez@estudiante.edu',     'admin_system', '2026-10-17 11:37:52', 'ADMIN', '2026-10-17 12:45:24'),
(8,  'Juan',                 'López Hernández',     'MASCULINO', '2003-01-08', '001-080103-0014L', NULL,           'Managua, Nicaragua',       '45781265', 'jlopez@estudiante.edu',      'admin_system', '2026-10-17 11:37:52', NULL, NULL),
(23, 'Alejandro José',       'Pérez Ruiz',          'MASCULINO', '2010-02-18', NULL,               'PN-2010-1001', 'Managua, Nicaragua',       '87654334', 'aperez@estudiante.edu',      'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(24, 'Sofía Isabel',         'Silva Torres',        'FEMENINO',  '2010-06-21', NULL,               'PN-2010-1002', 'Managua, Nicaragua',       '87654335', 'ssilva@estudiante.edu',      'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(25, 'Fernando Luis',        'Gómez Díaz',          'MASCULINO', '2010-10-12', NULL,               'PN-2010-1003', 'Managua, Nicaragua',       '87654336', 'fgomez@estudiante.edu',      'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(26, 'Andrea Lucía',         'Estrada Ramos',       'FEMENINO',  '2015-04-05', NULL,               'PN-2015-2001', 'Ciudad Sandino, Managua',  '87654337', 'aestrada@estudiante.edu',    'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(27, 'David Emmanuel',       'Jarquín Rivas',       'MASCULINO', '2015-08-14', NULL,               'PN-2015-2002', 'Ciudad Sandino, Managua',  '87654338', 'djarquin@estudiante.edu',    'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(28, 'Camila Antonella',     'Blandón Argüello',    'FEMENINO',  '2016-01-30', NULL,               'PN-2016-2003', 'Ciudad Sandino, Managua',  '87654339', 'cblandon@estudiante.edu',    'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(29, 'Mateo Alexander',      'Mairena Argüello',    'MASCULINO', '2016-05-22', NULL,               'PN-2016-2004', 'Managua, Nicaragua',       '87654340', 'mmairena@estudiante.edu',    'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(30, 'Lucía Fernanda',       'Pérez Ruiz',          'FEMENINO',  '2017-09-11', NULL,               'PN-2017-3001', 'Managua, Nicaragua',       '87654341', 'lperez@estudiante.edu',      'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(31, 'Santiago Gabriel',     'Silva Torres',        'MASCULINO', '2017-12-03', NULL,               'PN-2017-3002', 'Managua, Nicaragua',       '87654342', 'sgilva@estudiante.edu',      'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(32, 'Valeria Beatriz',      'Gómez Díaz',          'FEMENINO',  '2018-03-25', NULL,               'PN-2018-3003', 'Managua, Nicaragua',       '87654343', 'vgomez@estudiante.edu',      'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(33, 'Diego Alonso',         'Estrada Ramos',       'MASCULINO', '2011-07-16', NULL,               'PN-2011-1004', 'Ciudad Sandino, Managua',  '87654344', 'destrada@estudiante.edu',    'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(34, 'Natalia Guadalupe',    'Jarquín Rivas',       'FEMENINO',  '2011-11-08', NULL,               'PN-2011-1005', 'Ciudad Sandino, Managua',  '87654345', 'njarquin@estudiante.edu',    'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(35, 'Samuel Esteban',       'Blandón Argüello',    'MASCULINO', '2009-02-19', '001-190209-1000A', NULL,           'Ciudad Sandino, Managua',  '87654346', 'sblandon@estudiante.edu',    'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(36, 'Victoria Alejandra',   'Mairena Argüello',    'FEMENINO',  '2009-06-27', '001-270609-1000B', NULL,           'Managua, Nicaragua',       '87654347', 'vmairena@estudiante.edu',    'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(37, 'Daniel Enrique',       'Zamora Úbeda',        'MASCULINO', '2008-10-14', '001-141008-1000C', NULL,           'Ciudad Sandino, Managua',  '87654348', 'dzamora@estudiante.edu',     'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(38, 'Isaac Benjamin',       'Úbeda Zamora',        'MASCULINO', '2008-12-01', '001-011208-1000D', NULL,           'Ciudad Sandino, Managua',  '87654349', 'iubeda@estudiante.edu',      'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(39, 'Genesis Abigail',      'Sevilla Calero',      'FEMENINO',  '2009-04-10', '001-100409-1000E', NULL,           'Managua, Nicaragua',       '87654350', 'gsevilla@estudiante.edu',    'admin_system', '2026-10-20 08:00:00', NULL, NULL),
(40, 'Cristopher Alexander', 'Calero Sevilla',      'MASCULINO', '2009-08-03', '001-030809-1000F', NULL,           'Managua, Nicaragua',       '87654351', 'ccalero@estudiante.edu',     'admin_system', '2026-10-20 08:00:00', NULL, NULL);
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_de_estudio`
--

DROP TABLE IF EXISTS `plan_de_estudio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan_de_estudio` (
  `idPlan_de_estudio` int unsigned NOT NULL AUTO_INCREMENT,
  `idGrado` int unsigned NOT NULL,
  `anio_lectivo` int NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idPlan_de_estudio`),
  UNIQUE KEY `uk_plan_grado_anio` (`idGrado`,`anio_lectivo`),
  CONSTRAINT `fk_plan_grado` FOREIGN KEY (`idGrado`) REFERENCES `grado` (`idGrado`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_de_estudio`
--

LOCK TABLES `plan_de_estudio` WRITE;
/*!40000 ALTER TABLE `plan_de_estudio` DISABLE KEYS */;
INSERT INTO `plan_de_estudio` VALUES 
-- ==============================================================================
-- EDUCACIÓN PRIMARIA (Grados 1 al 6)
-- ==============================================================================
(1,  1,  2026, 1, 'admin_system', '2026-10-16 20:11:45', NULL, NULL), -- 1.º Grado Primaria
(2,  2,  2026, 1, 'admin_system', '2026-10-18 19:03:01', NULL, NULL), -- 2.º Grado Primaria
(3,  3,  2026, 1, 'admin_system', '2026-10-18 19:03:01', NULL, NULL), -- 3.º Grado Primaria
(4,  4,  2026, 1, 'admin_system', '2026-10-18 19:03:01', NULL, NULL), -- 4.º Grado Primaria
(5,  5,  2026, 1, 'admin_system', '2026-10-18 19:03:01', NULL, NULL), -- 5.º Grado Primaria
(6,  6,  2026, 1, 'admin_system', '2026-10-18 19:03:01', NULL, NULL), -- 6.º Grado Primaria

-- ==============================================================================
-- EDUCACIÓN SECUNDARIA (Grados 7 al 11 / 1.º a 5.º Año)
-- ==============================================================================
(7,  7,  2026, 1, 'admin_system', '2026-10-18 19:03:01', NULL, NULL), -- 7.º Grado (1.º Año Secundaria)
(8,  8,  2026, 1, 'admin_system', '2026-10-18 19:03:01', NULL, NULL), -- 8.º Grado (2.º Año Secundaria)
(9,  9,  2026, 1, 'admin_system', '2026-10-18 19:03:01', NULL, NULL), -- 9.º Grado (3.º Año Secundaria)
(10, 10, 2026, 1, 'admin_system', '2026-10-18 19:03:01', NULL, NULL), -- 10.º Grado (4.º Año Secundaria)
(11, 11, 2026, 1, 'admin_system', '2026-10-18 19:03:01', NULL, NULL);  -- 11.º Grado (5.º Año Secundaria)
/*!40000 ALTER TABLE `plan_de_estudio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `codigo` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `descripcion` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_sec_roles__codigo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES 
(1,'DIRECTOR','Administrador General','SYSTEM','2026-10-18 11:33:23',NULL,NULL),
(2,'DOCENTE','Docente de Asignatura','SYSTEM','2026-10-18 11:33:23',NULL,NULL),
(3,'CAJERO','Cajero del Colegio','SYSTEM','2026-10-18 15:39:15',NULL,NULL),
(4,'SECRETARIA','Secretaria General','SYSTEM','2026-10-18 15:39:15',NULL,NULL);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles_usuario`
--

DROP TABLE IF EXISTS `roles_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles_usuario` (
  `usuario_id` int unsigned NOT NULL,
  `rol_id` smallint(5) unsigned NOT NULL,
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`usuario_id`,`rol_id`),
  KEY `fk_roles_usuario__rol` (`rol_id`),
  CONSTRAINT `fk_roles_usuario__rol` FOREIGN KEY (`rol_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_roles_usuario__usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_usuario`
--

LOCK TABLES `roles_usuario` WRITE;
/*!40000 ALTER TABLE `roles_usuario` DISABLE KEYS */;
INSERT INTO `roles_usuario` VALUES 
(1,1,'SYSTEM','2026-10-18 11:33:35'),
(2,2,'SYSTEM','2026-10-18 11:33:35'),
(3,2,'SYSTEM','2026-10-18 15:49:29');
/*!40000 ALTER TABLE `roles_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salon`
--

DROP TABLE IF EXISTS `salon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salon` (
  `idSalon` int unsigned NOT NULL AUTO_INCREMENT,
  `idGrado` int unsigned NOT NULL,
  `idcatalogo_salon` int unsigned NOT NULL,
  `anio_lectivo` smallint(6) NOT NULL,
  `turno` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `seccion` varchar(5) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'A',
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idSalon`),
  UNIQUE KEY `uk_salon_catalogo_turno_anio` (`idcatalogo_salon`,`turno`,`anio_lectivo`),
  UNIQUE KEY `uk_grado_seccion_turno_anio` (`idGrado`,`seccion`,`turno`,`anio_lectivo`),
  CONSTRAINT `fk_salon_catalogo` FOREIGN KEY (`idcatalogo_salon`) REFERENCES `catalogo_salon` (`idcatalogo_salon`) ON UPDATE CASCADE,
  CONSTRAINT `fk_salon_grado` FOREIGN KEY (`idGrado`) REFERENCES `grado` (`idGrado`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salon`
--

LOCK TABLES `salon` WRITE;
/*!40000 ALTER TABLE `salon` DISABLE KEYS */;
INSERT INTO `salon` VALUES 
-- ==============================================================================
-- TURNO MAÑANA: EDUCACIÓN PRIMARIA COMPLETA (Grados 1 al 6)
-- ==============================================================================
(1, 1, 1, 2026, 'MAÑANA', 'A', 'admin_system', CURRENT_TIMESTAMP, NULL, NULL), -- 1.º Primaria -> Aula 01 (Pabellón Primaria)
(2, 2, 2, 2026, 'MAÑANA', 'A', 'admin_system', CURRENT_TIMESTAMP, NULL, NULL), -- 2.º Primaria -> Aula 02 (Pabellón Primaria)
(3, 3, 3, 2026, 'MAÑANA', 'A', 'admin_system', CURRENT_TIMESTAMP, NULL, NULL), -- 3.º Primaria -> Aula 03 (Pabellón Secundaria)
(4, 4, 4, 2026, 'MAÑANA', 'A', 'admin_system', CURRENT_TIMESTAMP, NULL, NULL), -- 4.º Primaria -> Aula 04 (Pabellón Secundaria)
(5, 5, 5, 2026, 'MAÑANA', 'A', 'admin_system', CURRENT_TIMESTAMP, NULL, NULL), -- 5.º Primaria -> Lab. Cómputo
(6, 6, 6, 2026, 'MAÑANA', 'A', 'admin_system', CURRENT_TIMESTAMP, NULL, NULL), -- 6.º Primaria -> Lab. Ciencias

-- ==============================================================================
-- TURNO TARDE: EDUCACIÓN SECUNDARIA COMPLETA (Grados 7 al 11 / 1.º a 5.º Año)
-- ==============================================================================
(7,  7,  1, 2026, 'TARDE',  'A', 'admin_system', CURRENT_TIMESTAMP, NULL, NULL), -- 7.º Grado (1.º Año Sec) -> Aula 01
(8,  8,  2, 2026, 'TARDE',  'A', 'admin_system', CURRENT_TIMESTAMP, NULL, NULL), -- 8.º Grado (2.º Año Sec) -> Aula 02
(9,  9,  3, 2026, 'TARDE',  'A', 'admin_system', CURRENT_TIMESTAMP, NULL, NULL), -- 9.º Grado (3.º Año Sec) -> Aula 03
(10, 10, 4, 2026, 'TARDE',  'A', 'admin_system', CURRENT_TIMESTAMP, NULL, NULL), -- 10.º Grado (4.º Año Sec) -> Aula 04
(11, 11, 5, 2026, 'TARDE',  'A', 'admin_system', CURRENT_TIMESTAMP, NULL, NULL);  -- 11.º Grado (5.º Año Sec) -> Lab. Cómputo;
/*!40000 ALTER TABLE `salon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutor`
--

DROP TABLE IF EXISTS `tutor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tutor` (
  `idtutor` int unsigned NOT NULL AUTO_INCREMENT,
  `idpersona` int unsigned NOT NULL,
  `ocupacion` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idtutor`),
  UNIQUE KEY `uk_tutor_persona` (`idpersona`),
  CONSTRAINT `fk_tutor_persona` FOREIGN KEY (`idpersona`) REFERENCES `persona` (`idpersona`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutor`
--

LOCK TABLES `tutor` WRITE;
/*!40000 ALTER TABLE `tutor` DISABLE KEYS */;
INSERT INTO `tutor` VALUES 
(1,1,'Ingeniero Civil','ADMIN','2026-10-04 20:41:50',NULL,NULL),
(2,2,'Contadora Pública','ADMIN','2026-10-04 20:41:50',NULL,NULL),
(3,19,NULL,'ADMIN','2026-10-13 20:29:09',NULL,NULL),
(4,20,NULL,'ADMIN','2026-10-14 21:03:27',NULL,NULL),
(5,21,NULL,'ADMIN','2026-10-14 22:25:57',NULL,NULL),
(6,9,NULL,'ADMIN','2026-10-17 12:45:24',NULL,NULL);
/*!40000 ALTER TABLE `tutor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `idpersona` int unsigned DEFAULT NULL COMMENT 'Relación opcional 1:1 con persona',
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password_hash` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `estado` int(11) NOT NULL,
  `creado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_el` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_por` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actualizado_el` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_sec_usuarios__username` (`username`),
  UNIQUE KEY `uq_sec_usuarios__idpersona` (`idpersona`),
  CONSTRAINT `FKljksow7wxxf7ci137cx7so8pj` FOREIGN KEY (`idpersona`) REFERENCES `persona` (`idpersona`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES 
(1,6,'admin','$2a$12$NS9qBgjsCjbwqwuU4KPIQeEaJQOrYBTVh7Ap6bIlzEIL2o2i0.lM.',1,'SYSTEM','2026-10-18 11:32:09',NULL,NULL),
(2,2,'AGARCIA','$2a$12$NS9qBgjsCjbwqwuU4KPIQeEaJQOrYBTVh7Ap6bIlzEIL2o2i0.lM.',1,'SYSTEM','2026-10-18 11:32:09',NULL,'2026-10-18 14:24:12'),
(3,1,'CMENDOZA','$2a$12$NS9qBgjsCjbwqwuU4KPIQeEaJQOrYBTVh7Ap6bIlzEIL2o2i0.lM.',1,'SYSTEM','2026-10-18 15:39:10',NULL,NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `vw_arqueo_caja_diario`
--

DROP TABLE IF EXISTS `vw_arqueo_caja_diario`;
/*!50001 DROP VIEW IF EXISTS `vw_arqueo_caja_diario`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vw_arqueo_caja_diario` AS SELECT 
 1 AS `fecha_emision`,
 1 AS `tipo_pago`,
 1 AS `cajero`,
 1 AS `total_recibos`,
 1 AS `total_recaudado`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vw_estatus_solvencia`
--

DROP TABLE IF EXISTS `vw_estatus_solvencia`;
/*!50001 DROP VIEW IF EXISTS `vw_estatus_solvencia`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vw_estatus_solvencia` AS SELECT 
 1 AS `idpersona`,
 1 AS `id_tarifa`,
 1 AS `arancel`,
 1 AS `monto_oficial`,
 1 AS `total_pagado`,
 1 AS `saldo_pendiente`,
 1 AS `estado`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vw_hoja_matricula_oficial`
--

DROP TABLE IF EXISTS `vw_hoja_matricula_oficial`;
/*!50001 DROP VIEW IF EXISTS `vw_hoja_matricula_oficial`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vw_hoja_matricula_oficial` AS SELECT 
 1 AS `idmatricula`,
 1 AS `anio_lectivo`,
 1 AS `codigo_estudiante`,
 1 AS `codigo_mined`,
 1 AS `nivel_academico`,
 1 AS `grado`,
 1 AS `nombre_salon`,
 1 AS `seccion`,
 1 AS `turno`,
 1 AS `salon_completo`,
 1 AS `nombres`,
 1 AS `apellidos`,
 1 AS `estudiante_nombre_completo`,
 1 AS `fecha_nacimiento`,
 1 AS `direccion`,
 1 AS `sexo`,
 1 AS `tutor_nombre`,
 1 AS `tutor_cedula`,
 1 AS `tutor_telefono`,
 1 AS `tutor_email`,
 1 AS `tutor_parentesco`,
 1 AS `costo_matricula`,
 1 AS `tipo_ingreso`,
 1 AS `estado_matricula`,
 1 AS `fecha_matricula`,
 1 AS `usuario_registro`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vw_periodos_estado_pantalla`
--

DROP TABLE IF EXISTS `vw_periodos_estado_pantalla`;
/*!50001 DROP VIEW IF EXISTS `vw_periodos_estado_pantalla`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vw_periodos_estado_pantalla` AS SELECT 
 1 AS `idperiodo_evaluativo`,
 1 AS `nombre_periodo`,
 1 AS `numero_periodo`,
 1 AS `anio_escolar`,
 1 AS `fecha_inicio`,
 1 AS `fecha_fin`,
 1 AS `fecha_cierre_docente`,
 1 AS `estado_manual`,
 1 AS `permite_edicion`,
 1 AS `mensaje_pantalla`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'colegiosilviano_dev'
--
/*!50003 DROP PROCEDURE IF EXISTS `sp_guardar_matricula_completa` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_guardar_matricula_completa`(
    -- Parámetros Estudiante
    IN  p_idpersona_estudiante INT UNSIGNED, -- NULL si es estudiante nuevo
    IN  p_est_nombres          VARCHAR(150),
    IN  p_est_apellidos        VARCHAR(150),
    IN  p_est_codigo_mined     VARCHAR(30),
    
    -- Parámetros Tutor
    IN  p_idpersona_tutor      INT UNSIGNED, -- NULL si es tutor nuevo
    IN  p_tutor_nombre_completo VARCHAR(150),
    IN  p_tutor_cedula         VARCHAR(20),
    IN  p_tutor_telefono       VARCHAR(20),
    IN  p_tutor_email          VARCHAR(100),
    IN  p_parentesco           VARCHAR(30),
    IN  p_es_representante     TINYINT(1),   -- 1 si es apoderado/financiero
    
    -- Parámetros Matrícula
    IN  p_idSalon              INT UNSIGNED,
    IN  p_anio_lectivo         YEAR,
    IN  p_tipo_ingreso         VARCHAR(20),  -- 'NUEVO_INGRESO' o 'REINGRESO'
    IN  p_costo_matricula      DECIMAL(10,2),
    IN  p_estado_matricula     VARCHAR(20),  -- 'ACTIVA', 'PRE_MATRICULADO', etc.
    
    -- Parámetro Requisitos (Acepta '[1,2,3]' o '1,2,3')
    IN  p_requisitos_str       VARCHAR(500),
    
    -- Auditoría
    IN  p_usuario              VARCHAR(50),
    
    -- Salida
    OUT p_idmatricula_out      INT UNSIGNED
)
BEGIN
    DECLARE v_idpersona_est INT UNSIGNED;
    DECLARE v_idpersona_tut INT UNSIGNED;
    DECLARE v_idtutor INT UNSIGNED;
    DECLARE v_idmatricula INT UNSIGNED;
    DECLARE v_cod_estudiante VARCHAR(20);
    DECLARE v_requisitos_limpios VARCHAR(500);

    -- Control de Excepciones: Rollback automático ante cualquier error SQL
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    -- ------------------------------------------------------------
    -- A. GESTIÓN DEL ESTUDIANTE (Creación o Actualización)
    -- ------------------------------------------------------------
    IF p_idpersona_estudiante IS NOT NULL AND p_idpersona_estudiante > 0 THEN
        SET v_idpersona_est = p_idpersona_estudiante;
        
        UPDATE `persona`
        SET `nombre_completo`   = p_est_nombres,
            `apellido_completo` = p_est_apellidos,
            `actualizado_por`   = p_usuario
        WHERE `idpersona` = v_idpersona_est;

        UPDATE `estudiante`
        SET `codigo_MINED`    = p_est_codigo_mined,
            `actualizado_por` = p_usuario
        WHERE `idpersona` = v_idpersona_est;
    ELSE
        INSERT INTO `persona` (
            `nombre_completo`, `apellido_completo`, `sexo`, 
            `fecha_nacimiento`, `direccion`, `creado_por`
        )
        VALUES (
            p_est_nombres, p_est_apellidos, 'NO ESPECIFICADO', 
            CURDATE(), 'PENDIENTE', p_usuario
        );
        
        SET v_idpersona_est = LAST_INSERT_ID();
        SET v_cod_estudiante = CONCAT('EST-', LPAD(v_idpersona_est, 6, '0'));
        
        INSERT INTO `estudiante` (`idpersona`, `cod_estudiante`, `codigo_MINED`, `estado`, `creado_por`)
        VALUES (v_idpersona_est, v_cod_estudiante, p_est_codigo_mined, 1, p_usuario);
    END IF;

    -- ------------------------------------------------------------
    -- B. GESTIÓN DEL TUTOR / APODERADO (Creación o Actualización)
    -- ------------------------------------------------------------
    IF p_idpersona_tutor IS NOT NULL AND p_idpersona_tutor > 0 THEN
        SET v_idpersona_tut = p_idpersona_tutor;
        
        UPDATE `persona`
        SET `nombre_completo` = p_tutor_nombre_completo,
            `cedula`          = p_tutor_cedula,
            `telefono`        = p_tutor_telefono,
            `email`           = p_tutor_email,
            `actualizado_por` = p_usuario
        WHERE `idpersona` = v_idpersona_tut;
        
        SELECT `idtutor` INTO v_idtutor FROM `tutor` WHERE `idpersona` = v_idpersona_tut;
    ELSE
        -- Buscar si el tutor ya existe por cédula
        IF p_tutor_cedula IS NOT NULL AND TRIM(p_tutor_cedula) != '' THEN
            SELECT `idpersona` INTO v_idpersona_tut FROM `persona` WHERE `cedula` = p_tutor_cedula LIMIT 1;
        END IF;

        IF v_idpersona_tut IS NOT NULL THEN
            UPDATE `persona`
            SET `nombre_completo` = p_tutor_nombre_completo,
                `telefono`        = p_tutor_telefono,
                `email`           = p_tutor_email,
                `actualizado_por` = p_usuario
            WHERE `idpersona` = v_idpersona_tut;

            SELECT `idtutor` INTO v_idtutor FROM `tutor` WHERE `idpersona` = v_idpersona_tut;
            IF v_idtutor IS NULL THEN
                INSERT INTO `tutor` (`idpersona`, `creado_por`) VALUES (v_idpersona_tut, p_usuario);
                SET v_idtutor = LAST_INSERT_ID();
            END IF;
        ELSE
            INSERT INTO `persona` (
                `nombre_completo`, `apellido_completo`, `sexo`, 
                `fecha_nacimiento`, `cedula`, `telefono`, `email`, 
                `direccion`, `creado_por`
            )
            VALUES (
                p_tutor_nombre_completo, '', 'NO ESPECIFICADO', 
                '1980-01-01', p_tutor_cedula, p_tutor_telefono, p_tutor_email, 
                'PENDIENTE', p_usuario
            );
            
            SET v_idpersona_tut = LAST_INSERT_ID();
            
            INSERT INTO `tutor` (`idpersona`, `creado_por`) VALUES (v_idpersona_tut, p_usuario);
            SET v_idtutor = LAST_INSERT_ID();
        END IF;
    END IF;

    -- Asignación o Actualización de la relación Estudiante - Tutor
    INSERT INTO `estudiante_tutor` (
        `idpersona_estudiante`, `idtutor`, `parentesco`, 
        `es_representante_legal`, `creado_por`
    )
    VALUES (v_idpersona_est, v_idtutor, p_parentesco, p_es_representante, p_usuario)
    ON DUPLICATE KEY UPDATE 
        `parentesco`             = VALUES(`parentesco`),
        `es_representante_legal` = VALUES(`es_representante_legal`),
        `actualizado_por`        = p_usuario;

    -- ------------------------------------------------------------
    -- C. GESTIÓN DE LA MATRÍCULA ACADÉMICA
    -- ------------------------------------------------------------
    INSERT INTO `matricula` (
        `idpersona`, `idSalon`, `anio_lectivo`, 
        `tipo_ingreso`, `costo_matricula`, `estado`, `creado_por`
    )
    VALUES (
        v_idpersona_est, p_idSalon, p_anio_lectivo, 
        p_tipo_ingreso, p_costo_matricula, p_estado_matricula, p_usuario
    )
    ON DUPLICATE KEY UPDATE
        `idSalon`          = VALUES(`idSalon`),
        `tipo_ingreso`     = VALUES(`tipo_ingreso`),
        `costo_matricula`  = VALUES(`costo_matricula`),
        `estado`           = VALUES(`estado`),
        `actualizado_por`  = p_usuario,
        `idmatricula`      = LAST_INSERT_ID(`idmatricula`);

    SET v_idmatricula = LAST_INSERT_ID();
    SET p_idmatricula_out = v_idmatricula;

    -- ------------------------------------------------------------
    -- D. GESTIÓN DE REQUISITOS FÍSICOS PRESENTADOS (Agnóstico a la versión)
    -- ------------------------------------------------------------
    DELETE FROM `matricula_requisito` WHERE `idmatricula` = v_idmatricula;

    IF p_requisitos_str IS NOT NULL AND LENGTH(TRIM(p_requisitos_str)) > 0 THEN
        -- Limpia corchetes, comillas y espacios (Ej: '[1, 2, 3]' -> '1,2,3')
        SET v_requisitos_limpios = REPLACE(REPLACE(REPLACE(REPLACE(p_requisitos_str, '[', ''), ']', ''), '"', ''), ' ', '');

        IF LENGTH(v_requisitos_limpios) > 0 THEN
            INSERT INTO `matricula_requisito` (`idmatricula`, `idrequisito`, `entregado`)
            SELECT 
                v_idmatricula,
                cr.`idrequisito`,
                1
            FROM `catalogo_requisito` cr
            WHERE FIND_IN_SET(cr.`idrequisito`, v_requisitos_limpios) > 0;
        END IF;
    END IF;

    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_otorgar_prorroga_docente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_otorgar_prorroga_docente`(
    IN p_iddetalle_plan_de_estudio INT UNSIGNED,
    IN p_idperiodo_evaluativo INT UNSIGNED,
    IN p_horas_prorroga INT,
    IN p_motivo VARCHAR(255),
    IN p_autorizado_por VARCHAR(50)
)
BEGIN
    -- Desactivar prorrogas anteriores vigentes para la misma materia y periodo
    UPDATE `autorizacion_extemporanea`
    SET `estado` = 0
    WHERE `iddetalle_plan_de_estudio` = p_iddetalle_plan_de_estudio
      AND `idperiodo_evaluativo` = p_idperiodo_evaluativo
      AND `estado` = 1;

    -- Registrar la nueva autorizacion sumando las horas concedidas a la fecha/hora actual
    INSERT INTO `autorizacion_extemporanea` (
        `iddetalle_plan_de_estudio`,
        `idperiodo_evaluativo`,
        `fecha_expiracion`,
        `motivo`,
        `estado`,
        `autorizado_por`
    ) VALUES (
        p_iddetalle_plan_de_estudio,
        p_idperiodo_evaluativo,
        DATE_ADD(NOW(), INTERVAL p_horas_prorroga HOUR),
        p_motivo,
        1,
        p_autorizado_por
    );
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_procesar_emision_recibo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_procesar_emision_recibo`(
    IN `p_idpersona` INT UNSIGNED,       -- ID de la Persona / Estudiante
    IN `p_anio_lectivo` YEAR,            -- Año lectivo del pago
    IN `p_concepto` VARCHAR(100),        -- Ej: 'PRE-MATRICULA', 'MENSUALIDAD FEBRERO'
    IN `p_tipo_pago` VARCHAR(30),        -- 'EFECTIVO', 'TRANSFERENCIA', 'TARJETA', 'DEPOSITO'
    IN `p_monto` DECIMAL(10,2),          -- Monto a abonar en este recibo
    IN `p_idtarifa` INT UNSIGNED,        -- ID del catálogo de tarifas
    IN `p_usuario` VARCHAR(50),          -- Usuario de caja/sistema
    OUT `p_recibo_generado` VARCHAR(30), -- Número de recibo generado (REC-2026-000001)
    OUT `p_saldo_restante` DECIMAL(10,2) -- Saldo que le queda por pagar de la tarifa tras este abono
)
BEGIN
    -- Declaración de variables internas
    DECLARE v_siguiente_numero INT DEFAULT 1;
    DECLARE v_num_recibo_nuevo VARCHAR(30);
    DECLARE v_costo_tarifa DECIMAL(10,2) DEFAULT 0.00;
    DECLARE v_monto_pagado_previo DECIMAL(10,2) DEFAULT 0.00;
    DECLARE v_saldo_pendiente DECIMAL(10,2) DEFAULT 0.00;
    DECLARE v_idpago_nuevo INT UNSIGNED;

    -- Manejo de Excepciones: Deshace cambios ante cualquier falla SQL
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    -- 1. Obtener el costo total del arancel/tarifa desde el catálogo
    SELECT MAX(`monto`) INTO v_costo_tarifa
    FROM `catalogo_tarifa`
    WHERE `id_tarifa` = p_idtarifa
      AND `anio_lectivo` = p_anio_lectivo;

    IF v_costo_tarifa IS NULL THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error de Negocio: La tarifa especificada no existe en el catálogo para el año lectivo indicado.';
    END IF;

    -- 2. Calcular el histórico de abonos realizados por la PERSONA para esta tarifa específica
    SELECT IFNULL(SUM(dp.`monto`), 0.00) INTO v_monto_pagado_previo
    FROM `detalle_pago` dp
    INNER JOIN `pago` p ON dp.`idpago` = p.`idpago`
    WHERE p.`idpersona` = p_idpersona
      AND dp.`id_tarifa` = p_idtarifa;

    -- Calcular el saldo pendiente actual antes de registrar este abono
    SET v_saldo_pendiente = v_costo_tarifa - v_monto_pagado_previo;

    -- 3. VALIDACIONES DE NEGOCIO
    IF v_saldo_pendiente <= 0.00 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error de Negocio: La tarifa/arancel seleccionada ya se encuentra totalmente cancelada.';
    ELSEIF p_monto > v_saldo_pendiente THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error de Negocio: El monto ingresado excede el saldo pendiente de la tarifa.';
    END IF;

    -- 4. Generar el correlativo del número de recibo (REC-2026-000001)
    SELECT IFNULL(MAX(CAST(SUBSTRING(num_recibo, 10) AS UNSIGNED)), 0) + 1 
    INTO v_siguiente_numero
    FROM `pago`
    WHERE `num_recibo` LIKE CONCAT('REC-', p_anio_lectivo, '-%')
    FOR UPDATE;

    SET v_num_recibo_nuevo = CONCAT('REC-', p_anio_lectivo, '-', LPAD(v_siguiente_numero, 6, '0'));

    -- 5. Inserción de la cabecera del pago (Estructura DDL limpia)
    INSERT INTO `pago` (
        `idpersona`,
        `num_recibo`,
        `fecha_pago`,
        `tipo_pago`,
        `monto_total`,
        `creado_por`
    ) VALUES (
        p_idpersona,
        v_num_recibo_nuevo,
        NOW(),
        UPPER(TRIM(p_tipo_pago)),
        p_monto, -- Es el total de dinero ingresado en este recibo
        p_usuario
    );

    SET v_idpago_nuevo = LAST_INSERT_ID();

    -- 6. Inserción del detalle del pago
    INSERT INTO `detalle_pago` (
        `idpago`, 
        `id_tarifa`, 
        `concepto`, 
        `monto`, 
        `creado_por`
    ) VALUES (
        v_idpago_nuevo, 
        p_idtarifa, 
        TRIM(p_concepto), 
        p_monto, 
        p_usuario
    );

    -- 7. Asignación de variables de salida
    SET p_recibo_generado = v_num_recibo_nuevo;
    
    SET p_saldo_restante = v_saldo_pendiente - p_monto;

    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `vw_arqueo_caja_diario`
--

/*!50001 DROP VIEW IF EXISTS `vw_arqueo_caja_diario`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_arqueo_caja_diario` AS select cast(`p`.`fecha_pago` as date) AS `fecha_emision`,`p`.`tipo_pago` AS `tipo_pago`,`p`.`creado_por` AS `cajero`,count(distinct `p`.`idpago`) AS `total_recibos`,sum(`p`.`monto_total`) AS `total_recaudado` from `pago` `p` group by cast(`p`.`fecha_pago` as date),`p`.`tipo_pago`,`p`.`creado_por` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vw_estatus_solvencia`
--

/*!50001 DROP VIEW IF EXISTS `vw_estatus_solvencia`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_estatus_solvencia` AS select `p`.`idpersona` AS `idpersona`,`dp`.`id_tarifa` AS `id_tarifa`,`ct`.`concepto` AS `arancel`,`ct`.`monto` AS `monto_oficial`,sum(`dp`.`monto`) AS `total_pagado`,(`ct`.`monto` - sum(`dp`.`monto`)) AS `saldo_pendiente`,if((sum(`dp`.`monto`) >= `ct`.`monto`),'CANCELADO','PENDIENTE') AS `estado` from ((`pago` `p` join `detalle_pago` `dp` on((`p`.`idpago` = `dp`.`idpago`))) join `catalogo_tarifa` `ct` on((`dp`.`id_tarifa` = `ct`.`id_tarifa`))) group by `p`.`idpersona`,`dp`.`id_tarifa`,`ct`.`concepto`,`ct`.`monto` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vw_hoja_matricula_oficial`
--

/*!50001 DROP VIEW IF EXISTS `vw_hoja_matricula_oficial`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_hoja_matricula_oficial` AS select `m`.`idmatricula` AS `idmatricula`,`m`.`anio_lectivo` AS `anio_lectivo`,`e`.`cod_estudiante` AS `codigo_estudiante`,`e`.`codigo_MINED` AS `codigo_mined`,`ne`.`nombre` AS `nivel_academico`,`g`.`nombre` AS `grado`,`cs`.`nombre_salon` AS `nombre_salon`,`s`.`seccion` AS `seccion`,`s`.`turno` AS `turno`,concat(`s`.`seccion`,' - ',`cs`.`nombre_salon`,' (',`s`.`turno`,')') AS `salon_completo`,`pe`.`nombre_completo` AS `nombres`,`pe`.`apellido_completo` AS `apellidos`,concat(`pe`.`nombre_completo`,' ',`pe`.`apellido_completo`) AS `estudiante_nombre_completo`,`pe`.`fecha_nacimiento` AS `fecha_nacimiento`,`pe`.`direccion` AS `direccion`,`pe`.`sexo` AS `sexo`,`pt`.`nombre_completo` AS `tutor_nombre`,coalesce(`pt`.`cedula`,'NO REGISTRADA') AS `tutor_cedula`,coalesce(`pt`.`telefono`,'NO REGISTRADO') AS `tutor_telefono`,coalesce(`pt`.`email`,'NO REGISTRADO') AS `tutor_email`,`et`.`parentesco` AS `tutor_parentesco`,`m`.`costo_matricula` AS `costo_matricula`,`m`.`tipo_ingreso` AS `tipo_ingreso`,`m`.`estado` AS `estado_matricula`,`m`.`fecha_matricula` AS `fecha_matricula`,`m`.`creado_por` AS `usuario_registro` from (((((((((`matricula` `m` join `persona` `pe` on((`m`.`idpersona` = `pe`.`idpersona`))) join `estudiante` `e` on((`pe`.`idpersona` = `e`.`idpersona`))) join `salon` `s` on((`m`.`idSalon` = `s`.`idSalon`))) join `catalogo_salon` `cs` on((`s`.`idcatalogo_salon` = `cs`.`idcatalogo_salon`))) join `grado` `g` on((`s`.`idGrado` = `g`.`idGrado`))) join `nivel_educativo` `ne` on((`g`.`idnivel` = `ne`.`idnivel`))) left join `estudiante_tutor` `et` on(((`e`.`idpersona` = `et`.`idpersona_estudiante`) and (`et`.`es_representante_legal` = 1)))) left join `tutor` `t` on((`et`.`idtutor` = `t`.`idtutor`))) left join `persona` `pt` on((`t`.`idpersona` = `pt`.`idpersona`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vw_periodos_estado_pantalla`
--

/*!50001 DROP VIEW IF EXISTS `vw_periodos_estado_pantalla`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_periodos_estado_pantalla` AS select `periodo_evaluativo`.`idperiodo_evaluativo` AS `idperiodo_evaluativo`,`periodo_evaluativo`.`nombre_periodo` AS `nombre_periodo`,`periodo_evaluativo`.`numero_periodo` AS `numero_periodo`,`periodo_evaluativo`.`anio_escolar` AS `anio_escolar`,`periodo_evaluativo`.`fecha_inicio` AS `fecha_inicio`,`periodo_evaluativo`.`fecha_fin` AS `fecha_fin`,`periodo_evaluativo`.`fecha_cierre_docente` AS `fecha_cierre_docente`,`periodo_evaluativo`.`estado` AS `estado_manual`,(case when ((`periodo_evaluativo`.`estado` = 1) and (now() <= `periodo_evaluativo`.`fecha_cierre_docente`)) then 1 else 0 end) AS `permite_edicion`,(case when (`periodo_evaluativo`.`estado` = 0) then 'Cerrado por Administración' when (now() > `periodo_evaluativo`.`fecha_cierre_docente`) then 'Plazo Vencido' when (now() < `periodo_evaluativo`.`fecha_inicio`) then 'No Iniciado' else 'Habilitado' end) AS `mensaje_pantalla` from `periodo_evaluativo` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-20 19:59:48
