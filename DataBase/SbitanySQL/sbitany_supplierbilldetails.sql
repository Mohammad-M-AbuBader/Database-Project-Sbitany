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
-- Table structure for table `supplierbilldetails`
--

DROP TABLE IF EXISTS `supplierbilldetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplierbilldetails` (
  `SupplierBillID` int NOT NULL,
  `productCode` int NOT NULL,
  `purchasingPrice` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`SupplierBillID`,`productCode`),
  KEY `supplierbilldetails_ibfk_2` (`productCode`),
  CONSTRAINT `supplierbilldetails_ibfk_1` FOREIGN KEY (`SupplierBillID`) REFERENCES `supplierbill` (`SupplierBillID`) ON DELETE CASCADE,
  CONSTRAINT `supplierbilldetails_ibfk_2` FOREIGN KEY (`productCode`) REFERENCES `product` (`productCode`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplierbilldetails`
--

LOCK TABLES `supplierbilldetails` WRITE;
/*!40000 ALTER TABLE `supplierbilldetails` DISABLE KEYS */;
INSERT INTO `supplierbilldetails` VALUES (250,161,1150,7),(250,163,3800,9),(250,165,1450,9),(251,158,1200,7),(251,180,370,15),(252,170,2150,6),(253,168,850,7),(253,169,1770,6),(254,173,440,35),(255,152,2200,5),(255,153,3800,6),(255,157,1750,7),(256,150,6250,13),(257,176,95,15),(258,171,2940,4),(259,168,850,10),(260,174,620,32),(260,175,185,20),(260,179,125,17),(261,152,2200,9),(261,160,1200,27);
/*!40000 ALTER TABLE `supplierbilldetails` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-21 20:13:58
