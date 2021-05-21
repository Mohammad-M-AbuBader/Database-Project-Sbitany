-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: sbitany
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `village`
--

DROP TABLE IF EXISTS `village`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `village` (
  `villageID` int NOT NULL AUTO_INCREMENT,
  `villageName` varchar(40) NOT NULL,
  `cityID` int NOT NULL,
  PRIMARY KEY (`villageID`),
  KEY `cityID_idx` (`cityID`),
  CONSTRAINT `cityID` FOREIGN KEY (`cityID`) REFERENCES `city` (`cityID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `village`
--

LOCK TABLES `village` WRITE;
/*!40000 ALTER TABLE `village` DISABLE KEYS */;
INSERT INTO `village` VALUES (3,'Bitter',1),(4,'Al-Abidiya',1),(5,'Al Khader',1),(6,'Dar-Salah',1),(7,'Dura',2),(8,'Idhna',2),(9,'al-samou',2),(10,'Yatta',2),(11,'Jaba',3),(12,'Burqin',3),(13,'Arrabah',3),(14,'Ajjah',3),(15,'Al-Auja',4),(16,'Al-Jiftlik',4),(17,'Fasayil',4),(18,'Aqabat Jaber',4),(19,'Aqraba',5),(20,'Beita',5),(21,'Huwara',5),(22,'Jammain',5),(23,'Beir Zeit',7),(24,'Beit Liqya',7),(25,'Silwad',7),(26,'Deir Qaddis',7),(27,'Attil',10),(28,'Anabta',10),(29,'Bal\'a',10),(30,'Qaffin',10);
/*!40000 ALTER TABLE `village` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-21  0:00:37
