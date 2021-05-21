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
-- Table structure for table `customerbilldetails`
--

DROP TABLE IF EXISTS `customerbilldetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customerbilldetails` (
  `customerBillID` int NOT NULL,
  `productCode` int NOT NULL,
  `sellingPrice` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`customerBillID`,`productCode`),
  KEY `customerbilldetails_ibfk_2` (`productCode`),
  CONSTRAINT `customerbilldetails_ibfk_1` FOREIGN KEY (`customerBillID`) REFERENCES `customerbill` (`customerBillID`) ON DELETE CASCADE,
  CONSTRAINT `customerbilldetails_ibfk_2` FOREIGN KEY (`productCode`) REFERENCES `product` (`productCode`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerbilldetails`
--

LOCK TABLES `customerbilldetails` WRITE;
/*!40000 ALTER TABLE `customerbilldetails` DISABLE KEYS */;
INSERT INTO `customerbilldetails` VALUES (1001,150,5840,1),(1001,165,1790,2),(1002,168,1190,1),(1003,157,2290,1),(1004,175,250,2),(1004,177,4330,1),(1005,153,5970,1),(1005,160,1790,1),(1006,167,11500,1),(1007,170,2690,2),(1008,170,2690,1),(1009,163,4260,1),(1009,174,800,1),(1010,155,4170,1),(1010,164,1890,1),(1010,172,650,1),(1011,161,1790,1),(1012,178,6170,2),(1013,166,2650,2),(1014,162,1700,1),(1014,175,250,2),(1015,180,500,1),(1016,169,2090,1),(1016,179,180,2),(1017,159,2460,1),(1018,156,3310,1),(1018,162,1700,1);
/*!40000 ALTER TABLE `customerbilldetails` ENABLE KEYS */;
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
