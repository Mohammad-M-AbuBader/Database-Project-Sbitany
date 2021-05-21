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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `productCode` int NOT NULL AUTO_INCREMENT,
  `productName` varchar(40) NOT NULL,
  `manufacturerCompany` varchar(40) NOT NULL,
  `purchasingPrice` int NOT NULL,
  `sellingPrice` int NOT NULL,
  `catogresId` int NOT NULL,
  `parCode` int DEFAULT NULL,
  `descriptions` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`productCode`),
  KEY `product_ibfk_1` (`catogresId`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`catogresId`) REFERENCES `categories` (`categoriesId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=181 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (150,'Fridge-LG','LG',6250,8540,1,2103456705,'4 Doors/676 Ltr'),(151,'Fridge-LG','LG',4000,5580,1,2103456706,'2 Doors/510 Lt'),(152,'Fridge-Beko','Beko',2200,3980,1,2103456707,'Bottom Mount/574'),(153,'Fridge-Beko','Beko',3800,5970,1,2103456708,'4 Doors/580 Lt'),(154,'Fridge-Midea','Midea',2500,3790,1,2103456709,'4 Doors/544 Lt'),(155,'Fridge-Magic','Magic',3450,4170,1,2103456710,'4 Doors/577 Lt'),(156,'Fridge-Smart','Smart',2400,3310,1,2103456711,'4 Doors/577 Lt'),(157,'Oven-Beko','Beko',1750,2290,2,2103456712,'5 Burners/90*60cm'),(158,'Oven-Magic','Magic',1200,1790,2,2103456713,'4 Burners/90*60cm'),(159,'Oven-GOZNEY','Gozney',1920,2460,2,2103456714,'Gas Oven/Green Color'),(160,'Washer-Beko','Beko',1200,1790,3,2103456715,'Front Load/KG Silver'),(161,'Washer-LG','LG',1150,1790,3,2103456716,'Twin Tub/ 14Kg'),(162,'Washer-Midea','Midea',1250,1700,3,2103456717,'Front Load/8 Kg'),(163,'Dryer-LG','LG',3800,4260,4,2103456718,'9Kg Silver'),(164,'Dryer-Smart','Smart',1550,1890,4,2103456719,'8Kg Dark Grey'),(165,'TV-LG','LG',1450,1790,5,2103456720,'Led 43'),(166,'TV-LG','LG',2150,2650,5,2103456721,'Led 55'),(167,'TV-TCL','TCL',10000,11500,5,2103456722,'65 Black'),(168,'Laptop-ASUS','ASUS',850,1190,6,2103456723,'I3/G5/8RAM'),(169,'Laptop-ASUS','ASUS',1770,2090,6,2103456724,'I5/G7/8RAM'),(170,'Samsung S10','Samsung',2150,2690,7,2103456725,'128GB/8GB'),(171,'Samsung Note10','Samsung',2940,3390,7,2103456726,'256GB/8GB'),(172,'Microwave-LG','LG',400,650,8,2103456727,'Black 25L'),(173,'Microwave-Midea','Midea',440,680,8,2103456728,'Black 25L'),(174,'Microwave-Midea','Midea',620,800,8,2103456729,'Silver 30L'),(175,'Blender-Midea','Midea',185,250,9,2103456730,'Silver'),(176,'Blender-Magic','Magic',95,130,9,2103456731,'White'),(177,'Conditioner-Electra','Electra',3500,4330,10,2103456732,'Wifi/White'),(178,'Conditioner-Midea','Midea',5150,6170,10,2103456733,'White'),(179,'Heater-Midea','Midea',125,180,11,2103456734,'Black'),(180,'Heater-Magic','Magic',370,500,11,2103456735,'GAS/Black');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-21 20:13:57
