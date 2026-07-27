CREATE DATABASE  IF NOT EXISTS `colegiosilviano_dev` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `colegiosilviano_dev`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: colegiosilviano_dev
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `asipnatura`
--

DROP TABLE IF EXISTS `asipnatura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asipnatura` (
  `idAsignatura` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idAsignatura`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asipnatura`
--

LOCK TABLES `asipnatura` WRITE;
/*!40000 ALTER TABLE `asipnatura` DISABLE KEYS */;
INSERT INTO `asipnatura` VALUES (1,'Matematicas'),(2,'Español'),(3,'Ingles'),(4,'Geografia');
/*!40000 ALTER TABLE `asipnatura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `baririo`
--

DROP TABLE IF EXISTS `baririo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `baririo` (
  `idbarrio` int NOT NULL AUTO_INCREMENT,
  `pais` varchar(45) DEFAULT NULL,
  `departamento` varchar(45) DEFAULT NULL,
  `municipio` varchar(45) DEFAULT NULL,
  `cumunidad` varchar(45) DEFAULT NULL,
  `idpersona` int DEFAULT NULL,
  PRIMARY KEY (`idbarrio`),
  KEY `idpersona_idx` (`idpersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `baririo`
--

LOCK TABLES `baririo` WRITE;
/*!40000 ALTER TABLE `baririo` DISABLE KEYS */;
/*!40000 ALTER TABLE `baririo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calificaciones`
--

DROP TABLE IF EXISTS `calificaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calificaciones` (
  `idcalificaciones` int NOT NULL AUTO_INCREMENT,
  `iddetalle_plan_de_estudio` int DEFAULT NULL,
  `acumulado` float DEFAULT NULL,
  `examen` float DEFAULT NULL,
  `idparcial` int DEFAULT NULL,
  PRIMARY KEY (`idcalificaciones`),
  KEY `iddetalle_plan_de_estudio` (`iddetalle_plan_de_estudio`),
  KEY `idparcial` (`idparcial`),
  CONSTRAINT `calificaciones_ibfk_1` FOREIGN KEY (`iddetalle_plan_de_estudio`) REFERENCES `detalle_plan_de_estudio` (`iddetalle_plan_de_estudio`),
  CONSTRAINT `calificaciones_ibfk_2` FOREIGN KEY (`idparcial`) REFERENCES `parcial` (`idParcial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calificaciones`
--

LOCK TABLES `calificaciones` WRITE;
/*!40000 ALTER TABLE `calificaciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `calificaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalaogo_salon`
--

DROP TABLE IF EXISTS `catalaogo_salon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalaogo_salon` (
  `idcatalogo_salon` int NOT NULL AUTO_INCREMENT,
  `nombre_salon` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idcatalogo_salon`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalaogo_salon`
--

LOCK TABLES `catalaogo_salon` WRITE;
/*!40000 ALTER TABLE `catalaogo_salon` DISABLE KEYS */;
INSERT INTO `catalaogo_salon` VALUES (1,'Salon A'),(2,'Salon B');
/*!40000 ALTER TABLE `catalaogo_salon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_pago`
--

DROP TABLE IF EXISTS `detalle_pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_pago` (
  `iddetalle_pago` int NOT NULL AUTO_INCREMENT,
  `costo` float DEFAULT NULL,
  `descuento` float DEFAULT NULL,
  `iva` float DEFAULT NULL,
  `subtotal` float DEFAULT NULL,
  `total` float DEFAULT NULL,
  `idpago` int DEFAULT NULL,
  PRIMARY KEY (`iddetalle_pago`),
  KEY `idpago` (`idpago`),
  CONSTRAINT `detalle_pago_ibfk_1` FOREIGN KEY (`idpago`) REFERENCES `pago` (`idpago`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_pago`
--

LOCK TABLES `detalle_pago` WRITE;
/*!40000 ALTER TABLE `detalle_pago` DISABLE KEYS */;
INSERT INTO `detalle_pago` VALUES (1,350,50,0.15,250,500,1),(2,400,20,0.15,300,450,2),(3,350,50,0.15,250,500,4),(4,350,50,0.15,250,500,5),(5,350,50,0.15,250,500,2),(6,350,50,0.15,250,500,5);
/*!40000 ALTER TABLE `detalle_pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_plan_de_estudio`
--

DROP TABLE IF EXISTS `detalle_plan_de_estudio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_plan_de_estudio` (
  `iddetalle_plan_de_estudio` int NOT NULL AUTO_INCREMENT,
  `idPlan_de_estudio` int DEFAULT NULL,
  `idAsignatura` int DEFAULT NULL,
  `semestre` varchar(20) DEFAULT NULL,
  `corte_evaluatico` varchar(20) DEFAULT NULL,
  `idperiodo_evaluativo` int DEFAULT NULL,
  `idDocente_plan_de_estudio` int DEFAULT NULL,
  PRIMARY KEY (`iddetalle_plan_de_estudio`),
  KEY `idAsignatura` (`idAsignatura`),
  KEY `idPlan_de_estudio` (`idPlan_de_estudio`),
  KEY `idperiodo_evaluativo` (`idperiodo_evaluativo`),
  KEY `detalle_plan_de_estudio_ibfk_4` (`idDocente_plan_de_estudio`),
  CONSTRAINT `detalle_plan_de_estudio_ibfk_1` FOREIGN KEY (`idAsignatura`) REFERENCES `asipnatura` (`idAsignatura`),
  CONSTRAINT `detalle_plan_de_estudio_ibfk_2` FOREIGN KEY (`idPlan_de_estudio`) REFERENCES `plan_de_estudio` (`idPlan_de_estudio`),
  CONSTRAINT `detalle_plan_de_estudio_ibfk_3` FOREIGN KEY (`idperiodo_evaluativo`) REFERENCES `periodo_evaluativo` (`idperiodo_evaluativo`),
  CONSTRAINT `detalle_plan_de_estudio_ibfk_4` FOREIGN KEY (`idDocente_plan_de_estudio`) REFERENCES `docente_plan_de_estudio` (`idDocente_plan_de_estudio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_plan_de_estudio`
--

LOCK TABLES `detalle_plan_de_estudio` WRITE;
/*!40000 ALTER TABLE `detalle_plan_de_estudio` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalle_plan_de_estudio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docente`
--

DROP TABLE IF EXISTS `docente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `docente` (
  `estado` bit(1) DEFAULT NULL,
  `idpersona` int NOT NULL,
  PRIMARY KEY (`idpersona`),
  KEY `idpersona_idx` (`idpersona`),
  CONSTRAINT `idpersona` FOREIGN KEY (`idpersona`) REFERENCES `persona` (`idpersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docente`
--

LOCK TABLES `docente` WRITE;
/*!40000 ALTER TABLE `docente` DISABLE KEYS */;
INSERT INTO `docente` VALUES (_binary '\0',4),(_binary '\0',6),(_binary '\0',11);
/*!40000 ALTER TABLE `docente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docente_plan_de_estudio`
--

DROP TABLE IF EXISTS `docente_plan_de_estudio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `docente_plan_de_estudio` (
  `idDocente_plan_de_estudio` int NOT NULL AUTO_INCREMENT,
  `idpersona` int DEFAULT NULL,
  `idSalon` int DEFAULT NULL,
  PRIMARY KEY (`idDocente_plan_de_estudio`),
  KEY `idSalon` (`idSalon`),
  KEY `idpersona` (`idpersona`),
  CONSTRAINT `docente_plan_de_estudio_ibfk_1` FOREIGN KEY (`idSalon`) REFERENCES `salon` (`idSalon`),
  CONSTRAINT `docente_plan_de_estudio_ibfk_2` FOREIGN KEY (`idpersona`) REFERENCES `docente` (`idpersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docente_plan_de_estudio`
--

LOCK TABLES `docente_plan_de_estudio` WRITE;
/*!40000 ALTER TABLE `docente_plan_de_estudio` DISABLE KEYS */;
/*!40000 ALTER TABLE `docente_plan_de_estudio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estudiante`
--

DROP TABLE IF EXISTS `estudiante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estudiante` (
  `cod_estudiante` varchar(45) NOT NULL,
  `codigo_MINED` varchar(50) DEFAULT NULL,
  `nombre_tutor` varchar(50) DEFAULT NULL,
  `estado` bit(1) DEFAULT NULL,
  `idpersona` int NOT NULL,
  PRIMARY KEY (`idpersona`),
  KEY `idpersona` (`idpersona`),
  CONSTRAINT `estudiante_ibfk_1` FOREIGN KEY (`idpersona`) REFERENCES `persona` (`idpersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiante`
--

LOCK TABLES `estudiante` WRITE;
/*!40000 ALTER TABLE `estudiante` DISABLE KEYS */;
INSERT INTO `estudiante` VALUES ('mexico001','mied001','Esperaza Gonzalez',_binary '',1),('code','minedES','Juan vades',_binary '',2),('tes ','tes ','mama',_binary '\0',12),('estudian02','mined02','yoni',_binary '',35),('code04','code04','de mamam',_binary '',36),('m1re','m1re','papa',_binary '',42);
/*!40000 ALTER TABLE `estudiante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grado`
--

DROP TABLE IF EXISTS `grado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grado` (
  `idGrado` int NOT NULL AUTO_INCREMENT,
  `categoria` varchar(45) DEFAULT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idGrado`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grado`
--

LOCK TABLES `grado` WRITE;
/*!40000 ALTER TABLE `grado` DISABLE KEYS */;
INSERT INTO `grado` VALUES (4,'Primaria','Tercero'),(5,'Primaria','cuarto'),(6,'Primaria ','quinto'),(7,'Primaria ','sexto');
/*!40000 ALTER TABLE `grado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matricula`
--

DROP TABLE IF EXISTS `matricula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matricula` (
  `idmatricula` int NOT NULL AUTO_INCREMENT,
  `idGrado` int DEFAULT NULL,
  `idPlan_de_estudio` int DEFAULT NULL,
  `idpersona` int DEFAULT NULL,
  `turno` varchar(45) DEFAULT NULL,
  `costo_matricula` float DEFAULT NULL,
  `idpago` int DEFAULT NULL,
  PRIMARY KEY (`idmatricula`),
  KEY `idPlan_de_estudio` (`idPlan_de_estudio`),
  KEY `idGrado` (`idGrado`),
  KEY `idpersona` (`idpersona`),
  KEY `idpago` (`idpago`),
  CONSTRAINT `matricula_ibfk_1` FOREIGN KEY (`idPlan_de_estudio`) REFERENCES `plan_de_estudio` (`idPlan_de_estudio`),
  CONSTRAINT `matricula_ibfk_2` FOREIGN KEY (`idGrado`) REFERENCES `grado` (`idGrado`),
  CONSTRAINT `matricula_ibfk_3` FOREIGN KEY (`idpersona`) REFERENCES `estudiante` (`idpersona`),
  CONSTRAINT `matricula_ibfk_4` FOREIGN KEY (`idpago`) REFERENCES `pago` (`idpago`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matricula`
--

LOCK TABLES `matricula` WRITE;
/*!40000 ALTER TABLE `matricula` DISABLE KEYS */;
INSERT INTO `matricula` VALUES (8,4,1,1,'vespertino',300,1),(12,5,2,2,'matutino',100,2),(24,5,NULL,42,'matutino',200,2),(25,6,NULL,12,'vespertino',300,1);
/*!40000 ALTER TABLE `matricula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago`
--

DROP TABLE IF EXISTS `pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pago` (
  `idpago` int NOT NULL AUTO_INCREMENT,
  `concepto` varchar(50) DEFAULT NULL,
  `fecha_pago` date DEFAULT NULL,
  `tipo_pago` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idpago`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago`
--

LOCK TABLES `pago` WRITE;
/*!40000 ALTER TABLE `pago` DISABLE KEYS */;
INSERT INTO `pago` VALUES (1,'pago de matricula','2023-05-20','targeta'),(2,'pago de matricula','2024-05-23','efectivo'),(4,'pago retrasado','2023-05-09','efectivo'),(5,'pago retrasado','2023-05-09','efectivo');
/*!40000 ALTER TABLE `pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parcial`
--

DROP TABLE IF EXISTS `parcial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parcial` (
  `idParcial` int NOT NULL AUTO_INCREMENT,
  `parcial` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idParcial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `periodo_evaluativo` (
  `idperiodo_evaluativo` int NOT NULL AUTO_INCREMENT,
  `NombrePeriodo` varchar(20) DEFAULT NULL,
  `NumeroPeriodo` int DEFAULT NULL,
  `AñoEscolar` date DEFAULT NULL,
  `estado` bit(1) DEFAULT NULL,
  PRIMARY KEY (`idperiodo_evaluativo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `periodo_evaluativo`
--

LOCK TABLES `periodo_evaluativo` WRITE;
/*!40000 ALTER TABLE `periodo_evaluativo` DISABLE KEYS */;
/*!40000 ALTER TABLE `periodo_evaluativo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona`
--

DROP TABLE IF EXISTS `persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persona` (
  `idpersona` int NOT NULL AUTO_INCREMENT,
  `nombre_completo` varchar(50) DEFAULT NULL,
  `apellido_completo` varchar(50) DEFAULT NULL,
  `sexo` varchar(45) DEFAULT NULL,
  `direccion` varchar(500) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `partidad_nacimiento` varchar(10) DEFAULT NULL,
  `cedula` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idpersona`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona`
--

LOCK TABLES `persona` WRITE;
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
INSERT INTO `persona` VALUES (1,'Guismarck Josue ','Nuñez Gonazalez',NULL,'jinotega','2024-05-31','SI','010-497-0002C'),(2,'Pedro','Albberto',NULL,'managua','2024-05-31','5i','010041995'),(4,'Mario Antonio','Barrera','M','Managua, nicaragua','1995-04-20','si','001-000000-000L'),(6,'ARMANDO RELAJO','BARRERA','M','DEL PALO TE EMBROCO UNA CUADRA','2024-04-24','LA RAJA','001-000000-000L'),(11,'guismarck josue tes','gonzalez','M','managua,nicaragua tes','1997-04-24','si','001-000000-000L'),(12,'mario martines ','lopes',NULL,'de por allli','2024-05-08','si','0104970002c'),(13,NULL,NULL,' M','TES ','1995-05-01','TES ','TES '),(14,'','',NULL,'',NULL,'',''),(15,'','',NULL,'',NULL,'',''),(16,'','',NULL,'',NULL,'',''),(17,'test-1','test-1',NULL,'test-1','2024-05-10','','test-1'),(22,'tes ','tes ',NULL,'tes ','2024-04-01','si','tes '),(28,NULL,NULL,NULL,NULL,'2024-05-07','no',NULL),(29,'tes ',NULL,NULL,'tes','2024-05-10','no',''),(30,'juan pan crasio',NULL,NULL,'managua ','2024-05-09','si','010408445'),(31,'guismarck Josue ',NULL,NULL,'managua ','2024-05-02',NULL,'0100497'),(32,'carlo alvertio','rodigyes ',NULL,'de managua ','2024-05-08',NULL,'0109484474'),(33,'pedro pancrasio','del pipe ',NULL,'managua ','2023-12-01',NULL,''),(34,'josue pancrasio','de los moentes ',NULL,'de paloi ','2024-05-23',NULL,'tes '),(35,'guismarck Josue ','Nuñes gonzale',NULL,'managua ','1997-04-01','si','0104970002C'),(36,'Josue David ','reyes reyes ',NULL,'de usa ','2009-01-01','no','0404474343'),(42,'mariela del carmen ','sabrosa',NULL,'de managua','2020-04-01','si','435434gfg45455');
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_de_estudio`
--

DROP TABLE IF EXISTS `plan_de_estudio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan_de_estudio` (
  `idPlan_de_estudio` int NOT NULL AUTO_INCREMENT,
  `año_electivo` date DEFAULT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `periodo` varchar(20) DEFAULT NULL,
  `estado` bit(1) DEFAULT NULL,
  `idSalon` int DEFAULT NULL,
  PRIMARY KEY (`idPlan_de_estudio`),
  KEY `idsalon_idx` (`idSalon`),
  CONSTRAINT `idsalon` FOREIGN KEY (`idSalon`) REFERENCES `salon` (`idSalon`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_de_estudio`
--

LOCK TABLES `plan_de_estudio` WRITE;
/*!40000 ALTER TABLE `plan_de_estudio` DISABLE KEYS */;
INSERT INTO `plan_de_estudio` VALUES (1,'2024-05-29','2024-05-29','2024-12-29','primer',_binary '',1),(2,'2020-05-29','2020-05-29','2020-12-29','segundo',_binary '',2);
/*!40000 ALTER TABLE `plan_de_estudio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salon`
--

DROP TABLE IF EXISTS `salon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salon` (
  `idSalon` int NOT NULL AUTO_INCREMENT,
  `idGrado` int DEFAULT NULL,
  `turmo` varchar(45) DEFAULT NULL,
  `idcatalogo_salon` int DEFAULT NULL,
  PRIMARY KEY (`idSalon`),
  KEY `idGrado_idx` (`idGrado`),
  KEY `idcatalogo__salon_idx` (`idcatalogo_salon`),
  CONSTRAINT `idcatalogo__salon` FOREIGN KEY (`idcatalogo_salon`) REFERENCES `catalaogo_salon` (`idcatalogo_salon`),
  CONSTRAINT `idGrado` FOREIGN KEY (`idGrado`) REFERENCES `grado` (`idGrado`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salon`
--

LOCK TABLES `salon` WRITE;
/*!40000 ALTER TABLE `salon` DISABLE KEYS */;
INSERT INTO `salon` VALUES (1,4,'matutino',1),(2,5,'matutino',2);
/*!40000 ALTER TABLE `salon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_evaluacion`
--

DROP TABLE IF EXISTS `tipo_evaluacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_evaluacion` (
  `idtipo_evaluacion` int NOT NULL AUTO_INCREMENT,
  `iddetalle_plan_de_estudio` int DEFAULT NULL,
  `tipo_evaluacion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idtipo_evaluacion`),
  KEY `iddetalle_plan_de_estudio_idx` (`iddetalle_plan_de_estudio`),
  CONSTRAINT `iddetalle_plan_de_estudio` FOREIGN KEY (`iddetalle_plan_de_estudio`) REFERENCES `detalle_plan_de_estudio` (`iddetalle_plan_de_estudio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_evaluacion`
--

LOCK TABLES `tipo_evaluacion` WRITE;
/*!40000 ALTER TABLE `tipo_evaluacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipo_evaluacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idusuario` int NOT NULL AUTO_INCREMENT,
  `idpersona` int DEFAULT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `contraseña` varchar(50) DEFAULT NULL,
  `tipo_usuario` varchar(50) DEFAULT NULL,
  `correo` varchar(200) DEFAULT NULL,
  `estado` bit(1) DEFAULT NULL,
  PRIMARY KEY (`idusuario`),
  KEY `idpersona` (`idpersona`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`idpersona`) REFERENCES `docente` (`idpersona`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,4,'jdreyes','12345','DEVELOPER','jdreyes@gmail.com',_binary '\0'),(2,6,'jdreyes','12345','DEVELOPER','jdreyes@gmail.com',_binary '\0'),(7,NULL,'guismarck','123456','tester','guismarck01gmail.com',_binary ''),(8,NULL,'guismarck','123456','tester','guismarck01gmail.com',_binary ''),(10,6,'guismarck','123456','tester','guismarck01gmail.com',_binary '\0'),(11,11,'tes-1','tes','tes','tes',_binary '\0');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-27  5:40:45
