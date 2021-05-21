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
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch` (
  `branchID` int NOT NULL AUTO_INCREMENT,
  `branchName` varchar(40) NOT NULL,
  `branchPhone` varchar(50) DEFAULT NULL,
  `cityID` int NOT NULL,
  `streetName` varchar(40) DEFAULT NULL,
  `regionName` varchar(40) DEFAULT NULL,
  `bulldingNumber` int DEFAULT NULL,
  PRIMARY KEY (`branchID`),
  KEY `branch_ibfk_2` (`cityID`),
  CONSTRAINT `branch_ibfk_2` FOREIGN KEY (`cityID`) REFERENCES `city` (`cityID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch`
--

LOCK TABLES `branch` WRITE;
/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` VALUES (1,'Main Company','02-123-1100',11,'Ramallah-Jerusalem St','Al-Ram',220),(2,'Sbitany Al-Irsal','02-123-0010',7,'Al-lrsal St',' AL-Irsal',120),(3,'Sbitany Sale Al Hisbeh St','02-123-0030',7,'Al Hisbeh St','Ramallah',115),(4,'Sbitany Sale Jerusalem-Hebron St','02-123-0060',1,'Jerusalem-Hebron St','Bethlehem',287),(5,'Sbitany Al Madbaseh St','02-123-0080',1,'Al Madbaseh St','Bethlehem',152),(6,'Sbitany Al-Dahriyeh St','02-123-0120',2,'Al-Dahriyeh St','Hebron',285),(7,'Sbitany Sale Rafidia','02-123-0150',5,'Rafidia St','Nablus',224),(8,'Sbitany Jenin','02-123-0160',3,'Main St','Jenin',143),(9,'Sbitany Jericho','02-123-0170',4,'Jerusalem St','Jericho',321),(10,'Sbitany Al-Ram','02-123-0190',11,'Ramallah St','Jerusalem Al-Ram',159);
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-21 20:13:59
