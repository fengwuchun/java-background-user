-- MySQL dump 10.13  Distrib 8.4.0, for macos13.2 (arm64)
--
-- Host: localhost    Database: city_db_restore
-- ------------------------------------------------------
-- Server version	9.7.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ 'c3c51a7c-7946-11f1-a490-6afebebb6df1:1-425';

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `cid` int NOT NULL,
  `cname` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'水果'),(2,'蔬菜'),(3,'肉'),(4,'水产'),(5,'乳制品'),(6,'蔬菜5'),(7,'蔬菜6'),(8,'蔬菜7'),(9,'蔬菜8'),(10,'蔬菜9'),(11,'蔬菜10'),(12,'蔬菜11'),(13,'蔬菜12'),(14,'蔬菜13'),(15,'蔬菜14'),(16,'蔬菜15'),(17,'蔬菜16'),(18,'蔬菜17'),(19,'蔬菜18'),(20,'蔬菜19'),(21,'蔬菜20'),(22,'蔬菜21'),(23,'蔬菜22'),(24,'蔬菜23'),(25,'蔬菜24'),(26,'蔬菜25'),(27,'蔬菜26'),(28,'蔬菜27'),(29,'蔬菜28'),(30,'蔬菜29'),(31,'蔬菜30'),(32,'蔬菜31'),(33,'蔬菜32'),(34,'蔬菜33'),(35,'蔬菜34'),(36,'蔬菜35'),(37,'蔬菜36'),(38,'蔬菜37'),(39,'蔬菜38'),(40,'蔬菜39'),(41,'蔬菜40'),(42,'蔬菜41'),(43,'蔬菜42'),(44,'蔬菜43'),(45,'蔬菜44'),(46,'蔬菜45'),(47,'蔬菜46'),(48,'蔬菜47'),(49,'蔬菜48'),(50,'蔬菜49'),(51,'蔬菜50'),(52,'蔬菜51'),(53,'蔬菜52'),(54,'蔬菜53'),(55,'蔬菜54'),(56,'蔬菜55'),(57,'蔬菜56'),(58,'蔬菜57'),(59,'蔬菜58'),(60,'蔬菜59'),(61,'蔬菜60'),(62,'蔬菜61'),(63,'蔬菜62'),(64,'蔬菜63'),(65,'蔬菜64'),(66,'蔬菜65'),(67,'蔬菜66'),(68,'蔬菜67'),(69,'蔬菜68'),(70,'蔬菜69'),(71,'蔬菜70'),(72,'蔬菜71'),(73,'蔬菜72'),(74,'蔬菜73'),(75,'蔬菜74'),(76,'蔬菜75'),(77,'蔬菜76'),(78,'蔬菜77'),(79,'蔬菜78'),(80,'蔬菜79'),(81,'蔬菜80'),(82,'蔬菜81'),(83,'蔬菜82'),(84,'蔬菜83'),(85,'蔬菜84'),(86,'蔬菜85'),(87,'蔬菜86'),(88,'蔬菜87'),(89,'蔬菜88'),(90,'蔬菜89'),(91,'蔬菜90'),(92,'蔬菜91'),(93,'蔬菜92'),(94,'蔬菜93'),(95,'蔬菜94'),(96,'蔬菜95'),(97,'蔬菜96'),(98,'蔬菜97'),(99,'蔬菜98'),(100,'蔬菜99');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `population` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'湖南','1000'),(3,'北京','100'),(4,'南京','400'),(5,'长沙','1000'),(6,'广州','1600');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods` (
  `gid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES (1,'水果'),(2,'蔬菜'),(3,'肉类'),(4,'水产'),(5,'水果'),(6,'蔬菜'),(7,'肉类'),(8,'水产');
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `myCategory`
--

DROP TABLE IF EXISTS `myCategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `myCategory` (
  `cid` int NOT NULL AUTO_INCREMENT,
  `cname` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `myCategory`
--

LOCK TABLES `myCategory` WRITE;
/*!40000 ALTER TABLE `myCategory` DISABLE KEYS */;
INSERT INTO `myCategory` VALUES (1,'蔬菜0'),(2,'蔬菜1'),(3,'蔬菜2'),(4,'蔬菜3'),(5,'蔬菜4'),(6,'蔬菜5'),(7,'蔬菜6'),(8,'蔬菜7'),(9,'蔬菜8'),(10,'蔬菜9'),(11,'蔬菜10'),(12,'蔬菜11'),(13,'蔬菜12'),(14,'蔬菜13'),(15,'蔬菜14'),(16,'蔬菜15'),(17,'蔬菜16'),(18,'蔬菜17'),(19,'蔬菜18'),(20,'蔬菜19'),(21,'蔬菜20'),(22,'蔬菜21'),(23,'蔬菜22'),(24,'蔬菜23'),(25,'蔬菜24'),(26,'蔬菜25'),(27,'蔬菜26'),(28,'蔬菜27'),(29,'蔬菜28'),(30,'蔬菜29'),(31,'蔬菜30'),(32,'蔬菜31'),(33,'蔬菜32'),(34,'蔬菜33'),(35,'蔬菜34'),(36,'蔬菜35'),(37,'蔬菜36'),(38,'蔬菜37'),(39,'蔬菜38'),(40,'蔬菜39'),(41,'蔬菜40'),(42,'蔬菜41'),(43,'蔬菜42'),(44,'蔬菜43'),(45,'蔬菜44'),(46,'蔬菜45'),(47,'蔬菜46'),(48,'蔬菜47'),(49,'蔬菜48'),(50,'蔬菜49'),(51,'蔬菜50'),(52,'蔬菜51'),(53,'蔬菜52'),(54,'蔬菜53'),(55,'蔬菜54'),(56,'蔬菜55'),(57,'蔬菜56'),(58,'蔬菜57'),(59,'蔬菜58'),(60,'蔬菜59'),(61,'蔬菜60'),(62,'蔬菜61'),(63,'蔬菜62'),(64,'蔬菜63'),(65,'蔬菜64'),(66,'蔬菜65'),(67,'蔬菜66'),(68,'蔬菜67'),(69,'蔬菜68'),(70,'蔬菜69'),(71,'蔬菜70'),(72,'蔬菜71'),(73,'蔬菜72'),(74,'蔬菜73'),(75,'蔬菜74'),(76,'蔬菜75'),(77,'蔬菜76'),(78,'蔬菜77'),(79,'蔬菜78'),(80,'蔬菜79'),(81,'蔬菜80'),(82,'蔬菜81'),(83,'蔬菜82'),(84,'蔬菜83'),(85,'蔬菜84'),(86,'蔬菜85'),(87,'蔬菜86'),(88,'蔬菜87'),(89,'蔬菜88'),(90,'蔬菜89'),(91,'蔬菜90'),(92,'蔬菜91'),(93,'蔬菜92'),(94,'蔬菜93'),(95,'蔬菜94'),(96,'蔬菜95'),(97,'蔬菜96'),(98,'蔬菜97'),(99,'蔬菜98'),(100,'蔬菜99'),(101,'水果'),(102,'水果');
/*!40000 ALTER TABLE `myCategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `myuser`
--

DROP TABLE IF EXISTS `myuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `myuser` (
  `uid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `myuser`
--

LOCK TABLES `myuser` WRITE;
/*!40000 ALTER TABLE `myuser` DISABLE KEYS */;
INSERT INTO `myuser` VALUES (1,'tom1','111'),(2,'jack','123'),(3,'Tim','234'),(4,'sami','121'),(7,'tom','111'),(8,'tom','111'),(9,'tom','111'),(10,'tom','111'),(11,'tom','111'),(12,'zansan','123456');
/*!40000 ALTER TABLE `myuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `oid` varchar(20) NOT NULL,
  `totalprice` double DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderItem`
--

DROP TABLE IF EXISTS `orderItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderItem` (
  `oid` varchar(20) DEFAULT NULL,
  `pid` bigint DEFAULT NULL,
  KEY `po` (`pid`),
  KEY `oo` (`oid`),
  CONSTRAINT `oo` FOREIGN KEY (`oid`) REFERENCES `order` (`oid`),
  CONSTRAINT `po` FOREIGN KEY (`pid`) REFERENCES `proudcts` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderItem`
--

LOCK TABLES `orderItem` WRITE;
/*!40000 ALTER TABLE `orderItem` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `xing` varchar(20) NOT NULL,
  `ming` varchar(20) NOT NULL,
  `city` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`xing`,`ming`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES ('zhang','三','湖南'),('zhang','四','湖南'),('王','四','湖北');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proudcts`
--

DROP TABLE IF EXISTS `proudcts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proudcts` (
  `pid` bigint NOT NULL AUTO_INCREMENT,
  `pname` varchar(20) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`pid`),
  KEY `cp` (`category_id`),
  CONSTRAINT `cp` FOREIGN KEY (`category_id`) REFERENCES `category` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proudcts`
--

LOCK TABLES `proudcts` WRITE;
/*!40000 ALTER TABLE `proudcts` DISABLE KEYS */;
INSERT INTO `proudcts` VALUES (1,'西瓜',3,1),(2,'猪肉',12,3),(3,'虾',40,4),(4,'西红柿',5,2),(5,'西瓜',3,1),(6,'猪肉',12,3),(7,'虾',40,4),(8,'西红柿',5,2),(9,'袜子',10,NULL);
/*!40000 ALTER TABLE `proudcts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `rid` int NOT NULL AUTO_INCREMENT,
  `rname` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`rid`),
  UNIQUE KEY `rname` (`rname`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (5,'护士'),(6,'教师'),(7,'皇上');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `sid` int NOT NULL AUTO_INCREMENT,
  `cname` varchar(10) NOT NULL,
  `score` int DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'张三',80),(2,'',80),(3,'',80),(4,'null',80);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `uname` varchar(20) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `sex` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'zs',18,1),(2,'lisi',20,0),(3,'ww',24,1),(4,'zl',23,0),(5,'lq',15,0),(6,'hh',12,1),(7,'wzx',60,NULL),(8,'lb',NULL,NULL),(9,'   mm   ',20,1);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `adress` varchar(20) DEFAULT NULL,
  `salary` mediumtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'790029232@qq.com','sj','123456',25,'湖南','4074'),(3,'1234567@qq.com','wz','123456',28,'湖南','4280'),(4,'西瓜','2元','甜',23,'湖南','4179'),(5,'西瓜','2元','甜',29,'湖南','3056'),(6,'1234567@qq.com','西瓜','123456',28,'湖南','3744'),(7,'1234567@qq.com','西瓜','123456',28,'湖南','4550');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-19 17:57:42
