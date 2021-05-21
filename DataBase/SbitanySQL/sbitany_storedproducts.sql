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
-- Table structure for table `storedproducts`
--

DROP TABLE IF EXISTS `storedproducts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storedproducts` (
  `storageID` int NOT NULL,
  `productCode` int NOT NULL,
  `productQuantity` int NOT NULL,
  PRIMARY KEY (`storageID`,`productCode`),
  KEY `productCode` (`productCode`),
  CONSTRAINT `storedproducts_ibfk_1` FOREIGN KEY (`storageID`) REFERENCES `storages` (`storageID`),
  CONSTRAINT `storedproducts_ibfk_2` FOREIGN KEY (`productCode`) REFERENCES `product` (`productCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storedproducts`
--

LOCK TABLES `storedproducts` WRITE;
/*!40000 ALTER TABLE `storedproducts` DISABLE KEYS */;
INSERT INTO `storedproducts` VALUES (1,150,2),(1,152,14),(1,157,3),(1,158,7),(1,160,13),(1,161,7),(1,163,3),(1,165,5),(1,168,7),(1,169,6),(1,170,6),(1,173,10),(1,174,32),(1,175,10),(1,176,15),(1,179,10),(1,180,7),(2,150,2),(2,153,6),(2,165,4),(2,171,2),(2,173,7),(5,150,9),(5,180,6),(6,160,6),(6,173,9),(7,168,5),(7,173,4),(7,175,10),(8,171,2),(8,180,2),(9,157,4),(9,160,8),(9,163,6),(9,168,5),(10,173,5),(10,179,7);
/*!40000 ALTER TABLE `storedproducts` ENABLE KEYS */;
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
