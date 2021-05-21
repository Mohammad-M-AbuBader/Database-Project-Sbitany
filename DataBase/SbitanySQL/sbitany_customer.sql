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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customerID` int NOT NULL AUTO_INCREMENT,
  `customerName` varchar(40) NOT NULL,
  `cardID` int NOT NULL,
  `cityID` int NOT NULL,
  `villageID` int DEFAULT NULL,
  `streetName` varchar(40) DEFAULT NULL,
  `regionName` varchar(40) DEFAULT NULL,
  `bulldingNumber` int DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`customerID`),
  KEY `customer_ibfk_1` (`cityID`),
  KEY `customer_ibfk_2` (`villageID`),
  CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`cityID`) REFERENCES `city` (`cityID`) ON DELETE CASCADE,
  CONSTRAINT `customer_ibfk_2` FOREIGN KEY (`villageID`) REFERENCES `village` (`villageID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=221 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (200,'Ata Hussan Ameer',984712315,1,3,'Street 1','Region1',13,'5921351281'),(201,'Raed Ali Tawfiq',984417816,1,4,'Street2','Region2',22,'5927352282'),(202,'Ammar Ata Read',984132317,7,23,'Street 3','Region3',16,'5928551283'),(203,'Salem Ahmad Hussam',984715818,7,24,'Street 4','Region4',15,'5925181284'),(204,'Salman Muhammad Taqi',984712319,5,19,'Street 5','Region5',13,'5921241285'),(205,'Samir Attia Goha',981841620,5,20,'Street 6','Region6',55,'5621351228'),(206,'Hani Atef Khalil',964912321,3,11,'Street 7','Region7',12,'5921351287'),(207,'Hosni Samer Taha',984714256,3,NULL,NULL,NULL,74,'5621123288'),(208,'Taqi Hosni  Ata',984712453,2,7,'Street 9','Region9',62,'5921351289'),(209,'Taha Omar Muhannad',812741135,2,7,'Street 10','Region10',61,'5624514290'),(210,'Ataf Mohammed Ahmed',812748241,7,24,'Street 11','Region11XA',92,'5651244791'),(211,'Walid Ahmed Qassem',812714313,7,23,'Street 12','RegionBAC',124,'5925721292'),(212,'Basem Loay Khaled',812712368,4,15,'Street 13','Region2DX',93,'5959612393'),(213,'Abdullah Ibrahim Mahmoud',812797513,4,NULL,NULL,NULL,21,'5624215794'),(214,'AbdulRahman Mohammed Khaled',812741350,1,3,'Street 15','Region1IJ',25,'5923435695'),(215,'Raed Saher Munther',812511241,1,NULL,NULL,NULL,73,'5923423566'),(216,'Rami Taha Ali',812784172,7,NULL,NULL,NULL,81,'5652461297'),(217,'Amro Mahmoud Hamed',812213713,7,NULL,NULL,NULL,32,'5935246298'),(218,'Sami Samir Selim',812795725,2,7,'Street 19','RegionSZX',26,'5921351299'),(219,'Maher Kamal Helmy',812741145,1,NULL,'Street 20','RegionZWQ',72,'5697351300'),(220,'Jamal Bara Dahoud',812745186,7,26,'Street 21','RegionYOP',42,'5621351999');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
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
