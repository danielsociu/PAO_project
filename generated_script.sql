CREATE DATABASE  IF NOT EXISTS `pao_project` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `pao_project`;
-- MariaDB dump 10.19  Distrib 10.5.10-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: pao_project
-- ------------------------------------------------------
-- Server version	10.5.10-MariaDB

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

--
-- Table structure for table `absence`
--

DROP TABLE IF EXISTS `absence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `absence` (
  `id_absence` int(6) unsigned NOT NULL AUTO_INCREMENT,
  `pid_student` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pid_teacher` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id_subject` int(6) unsigned NOT NULL,
  `id_class` int(6) unsigned NOT NULL,
  `date` datetime DEFAULT NULL,
  `motivated` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id_absence`),
  KEY `pid_student` (`pid_student`),
  KEY `pid_teacher` (`pid_teacher`),
  KEY `id_subject` (`id_subject`),
  KEY `id_class` (`id_class`),
  CONSTRAINT `absence_ibfk_1` FOREIGN KEY (`pid_student`) REFERENCES `student` (`pid_student`),
  CONSTRAINT `absence_ibfk_2` FOREIGN KEY (`pid_teacher`) REFERENCES `teacher` (`pid_teacher`),
  CONSTRAINT `absence_ibfk_3` FOREIGN KEY (`id_subject`) REFERENCES `subject` (`id_subject`),
  CONSTRAINT `absence_ibfk_4` FOREIGN KEY (`id_class`) REFERENCES `class` (`id_class`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `absence`
--

LOCK TABLES `absence` WRITE;
/*!40000 ALTER TABLE `absence` DISABLE KEYS */;
INSERT INTO `absence` VALUES (1,'53453','23423423',1,1,'2021-05-26 00:00:00','');
/*!40000 ALTER TABLE `absence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class` (
  `id_class` int(6) unsigned NOT NULL AUTO_INCREMENT,
  `id_school` int(6) unsigned NOT NULL,
  `id_program` int(6) unsigned NOT NULL,
  `year` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `year_period` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `letter` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id_class`),
  KEY `id_school` (`id_school`),
  KEY `id_program` (`id_program`),
  CONSTRAINT `class_ibfk_1` FOREIGN KEY (`id_school`) REFERENCES `school` (`id_school`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `class_ibfk_2` FOREIGN KEY (`id_program`) REFERENCES `program` (`id_program`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES (1,1,1,'11','2020-2021','A'),(2,1,1,'12','2020-2021','A');
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_subjects`
--

DROP TABLE IF EXISTS `class_subjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class_subjects` (
  `id_class` int(6) unsigned NOT NULL,
  `id_subject` int(6) unsigned NOT NULL,
  `pid_teacher` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  UNIQUE KEY `id_class` (`id_class`,`pid_teacher`,`id_subject`),
  KEY `pid_teacher` (`pid_teacher`),
  KEY `id_subject` (`id_subject`),
  CONSTRAINT `class_subjects_ibfk_1` FOREIGN KEY (`id_class`) REFERENCES `class` (`id_class`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `class_subjects_ibfk_2` FOREIGN KEY (`pid_teacher`) REFERENCES `teacher` (`pid_teacher`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `class_subjects_ibfk_3` FOREIGN KEY (`id_subject`) REFERENCES `subject` (`id_subject`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_subjects`
--

LOCK TABLES `class_subjects` WRITE;
/*!40000 ALTER TABLE `class_subjects` DISABLE KEYS */;
INSERT INTO `class_subjects` VALUES (1,1,'23423423');
/*!40000 ALTER TABLE `class_subjects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grade`
--

DROP TABLE IF EXISTS `grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grade` (
  `id_grade` int(6) unsigned NOT NULL AUTO_INCREMENT,
  `pid_student` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pid_teacher` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id_subject` int(6) unsigned NOT NULL,
  `id_class` int(6) unsigned NOT NULL,
  `score` decimal(6,3) NOT NULL,
  `date` datetime DEFAULT NULL,
  `evaluation_method` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_grade`),
  KEY `pid_student` (`pid_student`),
  KEY `pid_teacher` (`pid_teacher`),
  KEY `id_subject` (`id_subject`),
  KEY `id_class` (`id_class`),
  CONSTRAINT `grade_ibfk_1` FOREIGN KEY (`pid_student`) REFERENCES `student` (`pid_student`),
  CONSTRAINT `grade_ibfk_2` FOREIGN KEY (`pid_teacher`) REFERENCES `teacher` (`pid_teacher`),
  CONSTRAINT `grade_ibfk_3` FOREIGN KEY (`id_subject`) REFERENCES `subject` (`id_subject`),
  CONSTRAINT `grade_ibfk_4` FOREIGN KEY (`id_class`) REFERENCES `class` (`id_class`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grade`
--

LOCK TABLES `grade` WRITE;
/*!40000 ALTER TABLE `grade` DISABLE KEYS */;
INSERT INTO `grade` VALUES (1,'21312312','23423423',1,1,8.500,'2021-05-26 00:00:00','test');
/*!40000 ALTER TABLE `grade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `program`
--

DROP TABLE IF EXISTS `program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `program` (
  `id_program` int(6) unsigned NOT NULL AUTO_INCREMENT,
  `id_school` int(6) unsigned NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `number_years` int(6) DEFAULT NULL,
  PRIMARY KEY (`id_program`),
  KEY `id_school` (`id_school`),
  CONSTRAINT `program_ibfk_1` FOREIGN KEY (`id_school`) REFERENCES `school` (`id_school`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program`
--

LOCK TABLES `program` WRITE;
/*!40000 ALTER TABLE `program` DISABLE KEYS */;
INSERT INTO `program` VALUES (1,1,'Informatics',4),(2,1,'Mathematics',4);
/*!40000 ALTER TABLE `program` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `school`
--

DROP TABLE IF EXISTS `school`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school` (
  `id_school` int(6) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id_school`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `school`
--

LOCK TABLES `school` WRITE;
/*!40000 ALTER TABLE `school` DISABLE KEYS */;
INSERT INTO `school` VALUES (1,'Base School');
/*!40000 ALTER TABLE `school` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `pid_student` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_school` int(6) unsigned NOT NULL,
  `id_class` int(6) unsigned NOT NULL,
  `first_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `birth_date` datetime DEFAULT NULL,
  PRIMARY KEY (`pid_student`),
  KEY `id_school` (`id_school`),
  KEY `id_class` (`id_class`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`id_school`) REFERENCES `school` (`id_school`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_ibfk_2` FOREIGN KEY (`id_class`) REFERENCES `class` (`id_class`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('21312312',1,1,'Bob','Ross','1950-05-05 00:00:00'),('53453',1,1,'George','Orwell','1980-01-01 00:00:00');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subject` (
  `id_subject` int(6) unsigned NOT NULL AUTO_INCREMENT,
  `id_school` int(6) unsigned NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `domain` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_subject`),
  KEY `id_school` (`id_school`),
  CONSTRAINT `subject_ibfk_1` FOREIGN KEY (`id_school`) REFERENCES `school` (`id_school`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES (1,1,'Info','CS'),(2,1,'Maths','Real');
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `pid_teacher` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_school` int(6) unsigned NOT NULL,
  `first_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `birth_date` datetime DEFAULT NULL,
  PRIMARY KEY (`pid_teacher`),
  KEY `id_school` (`id_school`),
  CONSTRAINT `teacher_ibfk_1` FOREIGN KEY (`id_school`) REFERENCES `school` (`id_school`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES ('1231231',1,'Dan','Brown','1999-06-06 00:00:00'),('23423423',1,'Albert','Einstein','1999-01-01 00:00:00');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-26  8:55:52
