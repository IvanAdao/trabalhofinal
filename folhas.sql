-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: folha_salarial
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `alertas_ia`
--

DROP TABLE IF EXISTS `alertas_ia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alertas_ia` (
  `id_alerta` bigint NOT NULL AUTO_INCREMENT,
  `id_alteracao` bigint DEFAULT NULL,
  `tipo_alerta` varchar(255) DEFAULT NULL,
  `risco` enum('BAIXO','MÉDIO','ALTO') DEFAULT NULL,
  `data_detectado` datetime DEFAULT NULL,
  PRIMARY KEY (`id_alerta`),
  KEY `FKb5nrhsrkoww36vosyfcgcirf2` (`id_alteracao`),
  CONSTRAINT `FKb5nrhsrkoww36vosyfcgcirf2` FOREIGN KEY (`id_alteracao`) REFERENCES `alteracoes_folha` (`id_alteracao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alertas_ia`
--

LOCK TABLES `alertas_ia` WRITE;
/*!40000 ALTER TABLE `alertas_ia` DISABLE KEYS */;
/*!40000 ALTER TABLE `alertas_ia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alteracoes_folha`
--

DROP TABLE IF EXISTS `alteracoes_folha`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alteracoes_folha` (
  `id_alteracao` bigint NOT NULL AUTO_INCREMENT,
  `id_servidor` int DEFAULT NULL,
  `id_utilizador` int DEFAULT NULL,
  `campo_modificado` varchar(255) DEFAULT NULL,
  `valor_anterior` varchar(255) DEFAULT NULL,
  `valor_novo` varchar(255) DEFAULT NULL,
  `motivo` varchar(255) DEFAULT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  `ip_origem` varchar(255) DEFAULT NULL,
  `alerta_activado` tinyint(1) DEFAULT '0',
  `id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_alteracao`),
  KEY `id_servidor` (`id_servidor`),
  KEY `id_utilizador` (`id_utilizador`),
  KEY `FKt3ewuqm42nqwmh4o7l4b9n17v` (`id`),
  CONSTRAINT `alteracoes_folha_ibfk_1` FOREIGN KEY (`id_servidor`) REFERENCES `servidor_publico` (`id_servidor`),
  CONSTRAINT `alteracoes_folha_ibfk_2` FOREIGN KEY (`id_utilizador`) REFERENCES `utilizador` (`id_utilizador`),
  CONSTRAINT `FKt3ewuqm42nqwmh4o7l4b9n17v` FOREIGN KEY (`id`) REFERENCES `sec_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alteracoes_folha`
--

LOCK TABLES `alteracoes_folha` WRITE;
/*!40000 ALTER TABLE `alteracoes_folha` DISABLE KEYS */;
/*!40000 ALTER TABLE `alteracoes_folha` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `investigacao`
--

DROP TABLE IF EXISTS `investigacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `investigacao` (
  `id_investigacao` bigint NOT NULL AUTO_INCREMENT,
  `id_servidor` int DEFAULT NULL,
  `data_abertura` datetime DEFAULT NULL,
  `estado` enum('EM_ANALISE','FECHADO','ARQUIVADO') DEFAULT NULL,
  `comentarios` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_investigacao`),
  KEY `id_servidor` (`id_servidor`),
  CONSTRAINT `investigacao_ibfk_1` FOREIGN KEY (`id_servidor`) REFERENCES `servidor_publico` (`id_servidor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `investigacao`
--

LOCK TABLES `investigacao` WRITE;
/*!40000 ALTER TABLE `investigacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `investigacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prova_de_vida`
--

DROP TABLE IF EXISTS `prova_de_vida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prova_de_vida` (
  `id_prova` bigint NOT NULL AUTO_INCREMENT,
  `id_servidor` int DEFAULT NULL,
  `metodo` enum('FACIAL','VOZ','EXTERNO_INE') DEFAULT NULL,
  `resultado` enum('VALIDO','INVALIDO') DEFAULT NULL,
  `data_verificacao` datetime DEFAULT NULL,
  PRIMARY KEY (`id_prova`),
  KEY `id_servidor` (`id_servidor`),
  CONSTRAINT `prova_de_vida_ibfk_1` FOREIGN KEY (`id_servidor`) REFERENCES `servidor_publico` (`id_servidor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prova_de_vida`
--

LOCK TABLES `prova_de_vida` WRITE;
/*!40000 ALTER TABLE `prova_de_vida` DISABLE KEYS */;
/*!40000 ALTER TABLE `prova_de_vida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relatorio_forense`
--

DROP TABLE IF EXISTS `relatorio_forense`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relatorio_forense` (
  `id_relatorio` bigint NOT NULL AUTO_INCREMENT,
  `id_auditor` int DEFAULT NULL,
  `tipo_relatorio` varchar(255) DEFAULT NULL,
  `parametros` varchar(255) DEFAULT NULL,
  `data_exportacao` datetime DEFAULT NULL,
  PRIMARY KEY (`id_relatorio`),
  KEY `id_auditor` (`id_auditor`),
  CONSTRAINT `relatorio_forense_ibfk_1` FOREIGN KEY (`id_auditor`) REFERENCES `utilizador` (`id_utilizador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relatorio_forense`
--

LOCK TABLES `relatorio_forense` WRITE;
/*!40000 ALTER TABLE `relatorio_forense` DISABLE KEYS */;
/*!40000 ALTER TABLE `relatorio_forense` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id_role` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_role`),
  UNIQUE KEY `nome` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN'),(3,'AUDITOR'),(2,'RH'),(4,'SERVIDOR');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sec_roles`
--

DROP TABLE IF EXISTS `sec_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sec_roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sec_roles`
--

LOCK TABLES `sec_roles` WRITE;
/*!40000 ALTER TABLE `sec_roles` DISABLE KEYS */;
INSERT INTO `sec_roles` VALUES (5,'ADMIN'),(6,'RH'),(7,'AUDITOR'),(8,'SERVIDOR');
/*!40000 ALTER TABLE `sec_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sec_user_roles`
--

DROP TABLE IF EXISTS `sec_user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sec_user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_sec_user_roles_role_id` (`role_id`),
  CONSTRAINT `FK_sec_user_roles_role_id` FOREIGN KEY (`role_id`) REFERENCES `sec_roles` (`id`),
  CONSTRAINT `FK_sec_user_roles_user_id` FOREIGN KEY (`user_id`) REFERENCES `sec_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sec_user_roles`
--

LOCK TABLES `sec_user_roles` WRITE;
/*!40000 ALTER TABLE `sec_user_roles` DISABLE KEYS */;
INSERT INTO `sec_user_roles` VALUES (1,5),(2,6),(3,7),(4,8);
/*!40000 ALTER TABLE `sec_user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sec_users`
--

DROP TABLE IF EXISTS `sec_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sec_users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK2tl68wcy9sc2m71l7inyh3a72` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sec_users`
--

LOCK TABLES `sec_users` WRITE;
/*!40000 ALTER TABLE `sec_users` DISABLE KEYS */;
INSERT INTO `sec_users` VALUES (1,_binary '','$2a$12$EzQcaUH68AM7ZaxVA1dUh.PF2AAELE88A455yzEinsO6X.708tTjm','admin'),(2,_binary '','$2a$10$XfgdXmr26I.XjZCKYurY0O38vPiSbfFQ6JCNDdv9BTwhdL6G2NbgC','jigen'),(3,_binary '','$2a$10$ObPz4ZKoPoh0rd0yzW2GtedeOnVwFkMkD9nqwZUqG9DYyd1KOmgTW','junior'),(4,_binary '','$2a$10$9ILAnEwlvbq1kmVuxftNmO79aumTyHI4Z7paaUKg1JZzHdDAvRjS.','fili');
/*!40000 ALTER TABLE `sec_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servidor_publico`
--

DROP TABLE IF EXISTS `servidor_publico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servidor_publico` (
  `id_servidor` int NOT NULL AUTO_INCREMENT,
  `nome_completo` varchar(255) DEFAULT NULL,
  `nuit` varchar(255) DEFAULT NULL,
  `nif` varchar(255) DEFAULT NULL,
  `cargo` varchar(255) DEFAULT NULL,
  `orgao` varchar(255) DEFAULT NULL,
  `salario_base` double DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_servidor`),
  UNIQUE KEY `nuit` (`nuit`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servidor_publico`
--

LOCK TABLES `servidor_publico` WRITE;
/*!40000 ALTER TABLE `servidor_publico` DISABLE KEYS */;
INSERT INTO `servidor_publico` VALUES (1,'João Silva','123456789','987654321','Analista','Financeiro',35000,'Ativo'),(2,'Ivan','55533','12344566','Ministro','ministerio',NULL,'Activo');
/*!40000 ALTER TABLE `servidor_publico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessao`
--

DROP TABLE IF EXISTS `sessao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessao` (
  `id_sessao` bigint NOT NULL AUTO_INCREMENT,
  `id_utilizador` int DEFAULT NULL,
  `ip_acesso` varchar(255) DEFAULT NULL,
  `localizacao` varchar(255) DEFAULT NULL,
  `hora_login` datetime DEFAULT NULL,
  `hora_logout` datetime DEFAULT NULL,
  `id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_sessao`),
  KEY `id_utilizador` (`id_utilizador`),
  KEY `FKesjxslt1otvr2y8g6o9it941x` (`id`),
  CONSTRAINT `FKesjxslt1otvr2y8g6o9it941x` FOREIGN KEY (`id`) REFERENCES `sec_users` (`id`),
  CONSTRAINT `sessao_ibfk_1` FOREIGN KEY (`id_utilizador`) REFERENCES `utilizador` (`id_utilizador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessao`
--

LOCK TABLES `sessao` WRITE;
/*!40000 ALTER TABLE `sessao` DISABLE KEYS */;
/*!40000 ALTER TABLE `sessao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utilizador`
--

DROP TABLE IF EXISTS `utilizador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utilizador` (
  `id_utilizador` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `activo` tinyint(1) DEFAULT '1',
  `ultimo_login` datetime DEFAULT NULL,
  PRIMARY KEY (`id_utilizador`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilizador`
--

LOCK TABLES `utilizador` WRITE;
/*!40000 ALTER TABLE `utilizador` DISABLE KEYS */;
INSERT INTO `utilizador` VALUES (1,'Administrador','admin','admin123','admin@email.com',1,'2025-07-09 10:16:50'),(2,'Recursos Humanos','rh','$2a$10$...','rh@email.com',1,'2025-07-10 12:29:17'),(3,'Auditor','auditor','$2a$10$...','auditor@email.com',1,'2025-07-10 12:29:17'),(4,'Servidor','servidor','$2a$10$...','servidor@email.com',1,'2025-07-10 12:29:17');
/*!40000 ALTER TABLE `utilizador` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-14 15:25:05
