USE keycloakDB;
-- Generated using MySQL Dump in IntelliJ IDEA - right click on the database and select Import/Export - >Export with mysqldump
-- MariaDB dump 10.19  Distrib 10.6.12-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: keycloakDB
-- ------------------------------------------------------
-- Server version	8.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ADMIN_EVENT_ENTITY`
--

DROP TABLE IF EXISTS `ADMIN_EVENT_ENTITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ADMIN_EVENT_ENTITY` (
                                      `ID` varchar(36) NOT NULL,
                                      `ADMIN_EVENT_TIME` bigint DEFAULT NULL,
                                      `REALM_ID` varchar(255) DEFAULT NULL,
                                      `OPERATION_TYPE` varchar(255) DEFAULT NULL,
                                      `AUTH_REALM_ID` varchar(255) DEFAULT NULL,
                                      `AUTH_CLIENT_ID` varchar(255) DEFAULT NULL,
                                      `AUTH_USER_ID` varchar(255) DEFAULT NULL,
                                      `IP_ADDRESS` varchar(255) DEFAULT NULL,
                                      `RESOURCE_PATH` text,
                                      `REPRESENTATION` text,
                                      `ERROR` varchar(255) DEFAULT NULL,
                                      `RESOURCE_TYPE` varchar(64) DEFAULT NULL,
                                      PRIMARY KEY (`ID`),
                                      KEY `IDX_ADMIN_EVENT_TIME` (`REALM_ID`,`ADMIN_EVENT_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ADMIN_EVENT_ENTITY`
--

LOCK TABLES `ADMIN_EVENT_ENTITY` WRITE;
/*!40000 ALTER TABLE `ADMIN_EVENT_ENTITY` DISABLE KEYS */;
/*!40000 ALTER TABLE `ADMIN_EVENT_ENTITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ASSOCIATED_POLICY`
--

DROP TABLE IF EXISTS `ASSOCIATED_POLICY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ASSOCIATED_POLICY` (
                                     `POLICY_ID` varchar(36) NOT NULL,
                                     `ASSOCIATED_POLICY_ID` varchar(36) NOT NULL,
                                     PRIMARY KEY (`POLICY_ID`,`ASSOCIATED_POLICY_ID`),
                                     KEY `IDX_ASSOC_POL_ASSOC_POL_ID` (`ASSOCIATED_POLICY_ID`),
                                     CONSTRAINT `FK_FRSR5S213XCX4WNKOG82SSRFY` FOREIGN KEY (`ASSOCIATED_POLICY_ID`) REFERENCES `RESOURCE_SERVER_POLICY` (`ID`),
                                     CONSTRAINT `FK_FRSRPAS14XCX4WNKOG82SSRFY` FOREIGN KEY (`POLICY_ID`) REFERENCES `RESOURCE_SERVER_POLICY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ASSOCIATED_POLICY`
--

LOCK TABLES `ASSOCIATED_POLICY` WRITE;
/*!40000 ALTER TABLE `ASSOCIATED_POLICY` DISABLE KEYS */;
/*!40000 ALTER TABLE `ASSOCIATED_POLICY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AUTHENTICATION_EXECUTION`
--

DROP TABLE IF EXISTS `AUTHENTICATION_EXECUTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AUTHENTICATION_EXECUTION` (
                                            `ID` varchar(36) NOT NULL,
                                            `ALIAS` varchar(255) DEFAULT NULL,
                                            `AUTHENTICATOR` varchar(36) DEFAULT NULL,
                                            `REALM_ID` varchar(36) DEFAULT NULL,
                                            `FLOW_ID` varchar(36) DEFAULT NULL,
                                            `REQUIREMENT` int DEFAULT NULL,
                                            `PRIORITY` int DEFAULT NULL,
                                            `AUTHENTICATOR_FLOW` bit(1) NOT NULL DEFAULT b'0',
                                            `AUTH_FLOW_ID` varchar(36) DEFAULT NULL,
                                            `AUTH_CONFIG` varchar(36) DEFAULT NULL,
                                            PRIMARY KEY (`ID`),
                                            KEY `IDX_AUTH_EXEC_REALM_FLOW` (`REALM_ID`,`FLOW_ID`),
                                            KEY `IDX_AUTH_EXEC_FLOW` (`FLOW_ID`),
                                            CONSTRAINT `FK_AUTH_EXEC_FLOW` FOREIGN KEY (`FLOW_ID`) REFERENCES `AUTHENTICATION_FLOW` (`ID`),
                                            CONSTRAINT `FK_AUTH_EXEC_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AUTHENTICATION_EXECUTION`
--

LOCK TABLES `AUTHENTICATION_EXECUTION` WRITE;
/*!40000 ALTER TABLE `AUTHENTICATION_EXECUTION` DISABLE KEYS */;
INSERT INTO `AUTHENTICATION_EXECUTION` VALUES ('03987eac-b61a-44d3-b2ba-1b4a2a7bab07',NULL,'direct-grant-validate-otp','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','d7c43b60-21e2-4e00-b6b6-3ba2638e51c3',0,20,'\0',NULL,NULL),('07edc0f0-4328-409f-997a-65f1a734c9a2',NULL,NULL,'0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1a254027-a1ff-4433-ba58-02763c405b13',2,20,'','3d6910b3-2cf6-4896-b396-3f5a59e133a1',NULL),('0e05b3ed-b9e3-40a3-80b7-221382fb9d41',NULL,NULL,'31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','fb0c2ad3-fde6-4fab-94a6-b8716c2063a7',1,40,'','e6231775-6ed8-4d2d-a821-116387f1091c',NULL),('112d0dc6-2b3c-4850-b93a-ee78a66afc05',NULL,'auth-otp-form','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','5be9720d-a2de-45c0-a12f-b36969a4e010',0,20,'\0',NULL,NULL),('11427b84-e46e-440b-98f6-8d066406ab52',NULL,'auth-cookie','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','be9390f9-79bb-45d3-a64a-83363361426c',2,10,'\0',NULL,NULL),('14eedad2-3684-4d45-90ef-a61733b09be9',NULL,'reset-credentials-choose-user','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','7eaf199c-da4c-41fb-96e8-ac5f8f6e8ac8',0,10,'\0',NULL,NULL),('1cf3ef37-ac2a-4550-9dd0-87773f1e7f1f',NULL,'auth-username-password-form','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1e942c05-1ccf-4e00-ae77-795d453305eb',0,10,'\0',NULL,NULL),('1de36d44-4bb5-447a-96d4-31e844d20255',NULL,'registration-page-form','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','35b2c286-e6ac-460a-965a-ed59ceda5bcc',0,10,'','4f542d5f-6d4f-407c-a0ef-9462bbdc4b07',NULL),('25407a51-306c-4290-9208-c1b39055e4d4',NULL,NULL,'31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','1148c4c2-2b4b-44d0-87c6-caf93b3ff4c6',0,20,'','ac95f1eb-13c1-4d79-97de-685f49cc393c',NULL),('27db1a04-f40a-4eeb-be65-fce72aa21f93',NULL,'reset-otp','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','e6231775-6ed8-4d2d-a821-116387f1091c',0,20,'\0',NULL,NULL),('2841dd84-d4fc-4221-9268-1d1fa7d3c118',NULL,'registration-password-action','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','4f542d5f-6d4f-407c-a0ef-9462bbdc4b07',0,50,'\0',NULL,NULL),('2a22ff22-6a3b-4216-8ccd-4084450b22d2',NULL,'registration-password-action','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','8c8a5a59-de79-4b2a-970c-296bef9edf51',0,50,'\0',NULL,NULL),('2f3581c2-6b0d-4889-a661-fe1857a8dec7',NULL,'auth-cookie','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','e32d377b-79a7-411b-bb2a-b6437f19b770',2,10,'\0',NULL,NULL),('2f513cc7-fe56-4366-b701-79b4c57dc267',NULL,'client-secret','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','1bdeee37-80a5-49af-9f5d-e23f547fe2f0',2,10,'\0',NULL,NULL),('30f8e119-23d4-4ad3-ab79-356fd6963874',NULL,'auth-otp-form','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','9c03bbe6-5306-429b-8848-439a4258dae2',0,20,'\0',NULL,NULL),('32bef2ad-7dae-4444-80aa-25530bb81ee6',NULL,'conditional-user-configured','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','9c03bbe6-5306-429b-8848-439a4258dae2',0,10,'\0',NULL,NULL),('37a927b5-c0e2-47b5-be97-3448ba98b321',NULL,'registration-profile-action','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','4f542d5f-6d4f-407c-a0ef-9462bbdc4b07',0,40,'\0',NULL,NULL),('3f2cea50-ff28-44cb-892e-25e1ae1afca1',NULL,'registration-terms-and-conditions','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','4f542d5f-6d4f-407c-a0ef-9462bbdc4b07',3,70,'\0',NULL,NULL),('4297fe08-cdd8-47e4-a8cc-c5eaae77850b',NULL,'idp-review-profile','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','ba39135d-c20a-4cf8-8287-b53bc5ee2fb6',0,10,'\0',NULL,'f4a6e721-642d-4f69-a288-5117026cfb0f'),('44435610-ae87-4b2a-a6a1-a04a9dcdbdb7',NULL,'client-secret-jwt','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','a0f0f47f-9fb3-4ccd-9a26-03bca5eeedc3',2,30,'\0',NULL,NULL),('450fee49-6070-4bce-b52c-7a1949daf19c',NULL,NULL,'0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','ba39135d-c20a-4cf8-8287-b53bc5ee2fb6',0,20,'','1a254027-a1ff-4433-ba58-02763c405b13',NULL),('4718bb46-9139-4fc6-b1ec-42f26e26e241',NULL,NULL,'31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','76ccffb8-3b64-47a1-8c3d-3277d34cf963',1,20,'','9e543473-e951-4d96-85ac-dfe2ea4106b7',NULL),('4be83fab-5f60-49a5-b3b4-012ebce3ee9e',NULL,'direct-grant-validate-username','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','c0498828-7e44-4b44-b560-9d4866bb9aac',0,10,'\0',NULL,NULL),('52921763-787b-4571-ac21-125cb0c950f3',NULL,'registration-user-creation','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','4f542d5f-6d4f-407c-a0ef-9462bbdc4b07',0,20,'\0',NULL,NULL),('5387ec59-c8c4-4e72-b853-6ebb268f55a9',NULL,'reset-otp','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','67d4442a-9781-4259-a1bc-5a4169e44a74',0,20,'\0',NULL,NULL),('54ba155f-2aab-405b-b8b9-d89d898ed214',NULL,'idp-username-password-form','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','8d6716e5-e8d8-4add-9ed9-050f6a812064',0,10,'\0',NULL,NULL),('593aef8f-2227-4c5d-8e0f-d13ba7af2acf',NULL,NULL,'31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','a80513bc-2185-4e7d-a386-89dc829e68e3',2,20,'','1148c4c2-2b4b-44d0-87c6-caf93b3ff4c6',NULL),('59c909f0-20e3-47dc-a371-66cbd14ca878',NULL,'registration-recaptcha-action','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','8c8a5a59-de79-4b2a-970c-296bef9edf51',3,60,'\0',NULL,NULL),('63895c56-c92c-4201-8e68-adc82a80351b',NULL,'reset-password','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','fb0c2ad3-fde6-4fab-94a6-b8716c2063a7',0,30,'\0',NULL,NULL),('640c2f46-f966-4097-baba-d24743d8227f',NULL,'auth-otp-form','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','9b8d40d9-f77a-4fec-9603-955b3f91f7fb',0,20,'\0',NULL,NULL),('64cd01f9-d696-41c6-a437-147998c6c602',NULL,'idp-create-user-if-unique','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','a80513bc-2185-4e7d-a386-89dc829e68e3',2,10,'\0',NULL,'dcbd9ee4-89bb-4f8d-8a95-df3f7355ab43'),('668720ea-0e1b-41ca-b905-9907541e3302',NULL,'reset-credential-email','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','7eaf199c-da4c-41fb-96e8-ac5f8f6e8ac8',0,20,'\0',NULL,NULL),('67253f1f-22ea-4cf3-b697-3a7c0ff3fb24',NULL,'idp-email-verification','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','ac95f1eb-13c1-4d79-97de-685f49cc393c',2,10,'\0',NULL,NULL),('6a6867c9-a4f8-4652-90f3-c511096c34b3',NULL,'conditional-user-configured','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','d7c43b60-21e2-4e00-b6b6-3ba2638e51c3',0,10,'\0',NULL,NULL),('7748514a-053e-40e8-9299-207275f92538',NULL,NULL,'0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','7eaf199c-da4c-41fb-96e8-ac5f8f6e8ac8',1,40,'','67d4442a-9781-4259-a1bc-5a4169e44a74',NULL),('80519b90-d266-4924-9c60-c3ab37846fc6',NULL,'direct-grant-validate-password','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','c0498828-7e44-4b44-b560-9d4866bb9aac',0,20,'\0',NULL,NULL),('819d1c98-6b74-4b56-b72f-dca82dd43a42',NULL,'direct-grant-validate-otp','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','462f36e3-d962-4ee9-8ed9-3081ab44eb9c',0,20,'\0',NULL,NULL),('8a70c761-301e-428f-9efc-bc2771c517fc',NULL,NULL,'31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','be9390f9-79bb-45d3-a64a-83363361426c',2,30,'','76ccffb8-3b64-47a1-8c3d-3277d34cf963',NULL),('8c48ec3e-9fd3-4c79-be63-53c83adf19b3',NULL,'client-jwt','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','1bdeee37-80a5-49af-9f5d-e23f547fe2f0',2,20,'\0',NULL,NULL),('90ae84f5-f474-46f6-be5b-2a7c0cca9852',NULL,NULL,'0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','3d6910b3-2cf6-4896-b396-3f5a59e133a1',0,20,'','d755720b-95c0-4727-b1fc-243695f10f1f',NULL),('95ab0ceb-d0c2-4293-9218-bd17e2d65c2e',NULL,'idp-username-password-form','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','8d597691-2849-49f6-956d-3545813d5b8e',0,10,'\0',NULL,NULL),('984977d8-6240-4720-b9df-5fa5f5244e54',NULL,'direct-grant-validate-password','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','c6481670-4293-4d25-b7b1-e4cf14bc29ea',0,20,'\0',NULL,NULL),('9a6839e7-8e12-4abb-afaf-1d5cef794589',NULL,NULL,'31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','c6481670-4293-4d25-b7b1-e4cf14bc29ea',1,30,'','462f36e3-d962-4ee9-8ed9-3081ab44eb9c',NULL),('9c87acc9-0b70-42cd-8c72-460ec75c5db5',NULL,NULL,'31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','ac95f1eb-13c1-4d79-97de-685f49cc393c',2,20,'','8d6716e5-e8d8-4add-9ed9-050f6a812064',NULL),('9d98b5cc-b432-4dde-b163-1c7a8d21a350',NULL,'conditional-user-configured','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','5be9720d-a2de-45c0-a12f-b36969a4e010',0,10,'\0',NULL,NULL),('a4a4ba58-f788-4cae-ac3c-ad4ce09ed82c',NULL,'identity-provider-redirector','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','e32d377b-79a7-411b-bb2a-b6437f19b770',2,25,'\0',NULL,NULL),('a643e250-15b9-4789-bd98-56b7c90b16ed',NULL,'idp-review-profile','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','733cf779-8f6b-42df-aace-de51507201d3',0,10,'\0',NULL,'3b82bd8f-7ecc-4cf3-8f90-28ac312630ab'),('a8f94221-eebc-4b18-8cf1-b82627423260',NULL,'idp-confirm-link','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','3d6910b3-2cf6-4896-b396-3f5a59e133a1',0,10,'\0',NULL,NULL),('ae08902c-88af-416b-abd2-55d74c48fd38',NULL,'client-secret','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','a0f0f47f-9fb3-4ccd-9a26-03bca5eeedc3',2,10,'\0',NULL,NULL),('b5a418e2-7152-479b-95c6-4f372487544d',NULL,'conditional-user-configured','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','462f36e3-d962-4ee9-8ed9-3081ab44eb9c',0,10,'\0',NULL,NULL),('b73f9a8c-bcd7-4ac7-b241-5ca07a6f6eba',NULL,NULL,'0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','8d597691-2849-49f6-956d-3545813d5b8e',1,20,'','5be9720d-a2de-45c0-a12f-b36969a4e010',NULL),('bae4664a-db3e-499b-99f4-93cc5b09012d',NULL,'reset-password','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','7eaf199c-da4c-41fb-96e8-ac5f8f6e8ac8',0,30,'\0',NULL,NULL),('bb5de8f8-271d-43c0-9fc0-48c70a8373e7',NULL,'docker-http-basic-authenticator','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','e241bd16-f27e-4f1b-9afa-b876226f2905',0,10,'\0',NULL,NULL),('c5c8ef0c-3387-4b0c-9029-8668bb60d663',NULL,NULL,'0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1e942c05-1ccf-4e00-ae77-795d453305eb',1,20,'','9b8d40d9-f77a-4fec-9603-955b3f91f7fb',NULL),('c9d9a134-f00f-4f0d-bc28-2c99bcfc8573',NULL,'idp-confirm-link','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','1148c4c2-2b4b-44d0-87c6-caf93b3ff4c6',0,10,'\0',NULL,NULL),('ca921c7d-c1ce-40ff-800d-118f767bdf29',NULL,'direct-grant-validate-username','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','c6481670-4293-4d25-b7b1-e4cf14bc29ea',0,10,'\0',NULL,NULL),('cb884e97-280a-4c63-ab17-a1274923955f',NULL,NULL,'0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','d755720b-95c0-4727-b1fc-243695f10f1f',2,20,'','8d597691-2849-49f6-956d-3545813d5b8e',NULL),('cde1cbf8-9700-4948-94ed-b171daa67a60',NULL,'auth-username-password-form','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','76ccffb8-3b64-47a1-8c3d-3277d34cf963',0,10,'\0',NULL,NULL),('cee82cc1-36d0-4aad-a199-06e09d7e128a',NULL,NULL,'31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','733cf779-8f6b-42df-aace-de51507201d3',0,20,'','a80513bc-2185-4e7d-a386-89dc829e68e3',NULL),('d0158027-014d-4e01-bf2d-29e319e9403e',NULL,'client-secret-jwt','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','1bdeee37-80a5-49af-9f5d-e23f547fe2f0',2,30,'\0',NULL,NULL),('d1946bfc-4112-4a61-a669-2ab0707947cb',NULL,NULL,'31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','8d6716e5-e8d8-4add-9ed9-050f6a812064',1,20,'','9c03bbe6-5306-429b-8848-439a4258dae2',NULL),('d1b43834-055a-4775-89b1-6ca0ff6f132c',NULL,'conditional-user-configured','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','9b8d40d9-f77a-4fec-9603-955b3f91f7fb',0,10,'\0',NULL,NULL),('d1d53717-5185-4a0b-b797-cc08e34a5349',NULL,'registration-user-creation','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','8c8a5a59-de79-4b2a-970c-296bef9edf51',0,20,'\0',NULL,NULL),('d3b19bb6-0029-448c-bab9-dccaf5902463',NULL,'idp-email-verification','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','d755720b-95c0-4727-b1fc-243695f10f1f',2,10,'\0',NULL,NULL),('d3ee396f-42bf-4398-a3ba-6eb84ff73387',NULL,'conditional-user-configured','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','e6231775-6ed8-4d2d-a821-116387f1091c',0,10,'\0',NULL,NULL),('d8bf8528-5d73-4c9a-ad0b-e9dcdb08caa6',NULL,'idp-create-user-if-unique','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1a254027-a1ff-4433-ba58-02763c405b13',2,10,'\0',NULL,'a432a4e6-b155-4bdd-b420-bd1e64eac43e'),('d92be0db-0e97-41f0-8954-5791f7385815',NULL,'conditional-user-configured','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','9e543473-e951-4d96-85ac-dfe2ea4106b7',0,10,'\0',NULL,NULL),('dc634c39-af77-4eca-9ea7-2b6110144578',NULL,'client-x509','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','1bdeee37-80a5-49af-9f5d-e23f547fe2f0',2,40,'\0',NULL,NULL),('ded7d12a-5850-419e-a782-ca38606b1ce8',NULL,'reset-credentials-choose-user','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','fb0c2ad3-fde6-4fab-94a6-b8716c2063a7',0,10,'\0',NULL,NULL),('e33dc9f8-2b2f-499e-b64e-6aabb3aa22f7',NULL,NULL,'0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','e32d377b-79a7-411b-bb2a-b6437f19b770',2,30,'','1e942c05-1ccf-4e00-ae77-795d453305eb',NULL),('e5820264-cb22-43f8-bc86-ad93f797b662',NULL,'reset-credential-email','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','fb0c2ad3-fde6-4fab-94a6-b8716c2063a7',0,20,'\0',NULL,NULL),('e6ebb6e9-792a-40d1-9eec-f288a2308c67',NULL,'auth-spnego','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','e32d377b-79a7-411b-bb2a-b6437f19b770',3,20,'\0',NULL,NULL),('e733560c-f7ad-47b2-8604-8a050e08cd23',NULL,'http-basic-authenticator','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','82a6b1b7-6d48-42be-b25a-5b0c2bbb887c',0,10,'\0',NULL,NULL),('ed06bd92-17c8-4aba-a0c5-a10d871e4e6a',NULL,'docker-http-basic-authenticator','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','10d0ec81-6450-4859-9ada-7a549bb929f9',0,10,'\0',NULL,NULL),('ed89a149-5b53-41e5-9166-e745fd2e10e6',NULL,'auth-otp-form','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','9e543473-e951-4d96-85ac-dfe2ea4106b7',0,20,'\0',NULL,NULL),('f25470dd-2c27-4f13-82ad-585967d42fb6',NULL,'auth-spnego','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','be9390f9-79bb-45d3-a64a-83363361426c',3,20,'\0',NULL,NULL),('f2f689f7-8011-4e82-81f1-0828d237f181',NULL,'conditional-user-configured','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','67d4442a-9781-4259-a1bc-5a4169e44a74',0,10,'\0',NULL,NULL),('f472ced3-f86c-4361-8179-aefbc5660f84',NULL,'http-basic-authenticator','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','0b886a5f-bbea-4e9a-98b1-b698a27dacbd',0,10,'\0',NULL,NULL),('fa08ff83-d6c1-4f07-a5ac-d7743a373169',NULL,'registration-page-form','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','54999abc-689c-4cef-87d3-288767117ed4',0,10,'','8c8a5a59-de79-4b2a-970c-296bef9edf51',NULL),('fa97ab7c-328d-47ec-b585-0918ae7eea13',NULL,'client-x509','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','a0f0f47f-9fb3-4ccd-9a26-03bca5eeedc3',2,40,'\0',NULL,NULL),('fbf9c15d-bf04-474e-a0ce-95d754da55c7',NULL,NULL,'0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','c0498828-7e44-4b44-b560-9d4866bb9aac',1,30,'','d7c43b60-21e2-4e00-b6b6-3ba2638e51c3',NULL),('fc238150-9a60-4457-ae6b-3a32d618ecfe',NULL,'registration-recaptcha-action','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','4f542d5f-6d4f-407c-a0ef-9462bbdc4b07',3,60,'\0',NULL,NULL),('fca3c4f1-95ec-41f3-8fda-3585a7c3c4dd',NULL,'identity-provider-redirector','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','be9390f9-79bb-45d3-a64a-83363361426c',2,25,'\0',NULL,NULL),('fd0e16d1-5c01-4bbf-9fa7-98eae3449d32',NULL,'registration-profile-action','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','8c8a5a59-de79-4b2a-970c-296bef9edf51',0,40,'\0',NULL,NULL),('ffa500fd-9d57-479f-8e18-e15dc0908bc3',NULL,'client-jwt','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','a0f0f47f-9fb3-4ccd-9a26-03bca5eeedc3',2,20,'\0',NULL,NULL);
/*!40000 ALTER TABLE `AUTHENTICATION_EXECUTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AUTHENTICATION_FLOW`
--

DROP TABLE IF EXISTS `AUTHENTICATION_FLOW`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AUTHENTICATION_FLOW` (
                                       `ID` varchar(36) NOT NULL,
                                       `ALIAS` varchar(255) DEFAULT NULL,
                                       `DESCRIPTION` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                                       `REALM_ID` varchar(36) DEFAULT NULL,
                                       `PROVIDER_ID` varchar(36) NOT NULL DEFAULT 'basic-flow',
                                       `TOP_LEVEL` bit(1) NOT NULL DEFAULT b'0',
                                       `BUILT_IN` bit(1) NOT NULL DEFAULT b'0',
                                       PRIMARY KEY (`ID`),
                                       KEY `IDX_AUTH_FLOW_REALM` (`REALM_ID`),
                                       CONSTRAINT `FK_AUTH_FLOW_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AUTHENTICATION_FLOW`
--

LOCK TABLES `AUTHENTICATION_FLOW` WRITE;
/*!40000 ALTER TABLE `AUTHENTICATION_FLOW` DISABLE KEYS */;
INSERT INTO `AUTHENTICATION_FLOW` VALUES ('0b886a5f-bbea-4e9a-98b1-b698a27dacbd','saml ecp','SAML ECP Profile Authentication Flow','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','basic-flow','',''),('10d0ec81-6450-4859-9ada-7a549bb929f9','docker auth','Used by Docker clients to authenticate against the IDP','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','basic-flow','',''),('1148c4c2-2b4b-44d0-87c6-caf93b3ff4c6','Handle Existing Account','Handle what to do if there is existing account with same email/username like authenticated identity provider','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','basic-flow','\0',''),('1a254027-a1ff-4433-ba58-02763c405b13','User creation or linking','Flow for the existing/non-existing user alternatives','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','basic-flow','\0',''),('1bdeee37-80a5-49af-9f5d-e23f547fe2f0','clients','Base authentication for clients','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','client-flow','',''),('1e942c05-1ccf-4e00-ae77-795d453305eb','forms','Username, password, otp and other auth forms.','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','basic-flow','\0',''),('35b2c286-e6ac-460a-965a-ed59ceda5bcc','registration','registration flow','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','basic-flow','',''),('3d6910b3-2cf6-4896-b396-3f5a59e133a1','Handle Existing Account','Handle what to do if there is existing account with same email/username like authenticated identity provider','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','basic-flow','\0',''),('462f36e3-d962-4ee9-8ed9-3081ab44eb9c','Direct Grant - Conditional OTP','Flow to determine if the OTP is required for the authentication','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','basic-flow','\0',''),('4f542d5f-6d4f-407c-a0ef-9462bbdc4b07','registration form','registration form','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','form-flow','\0',''),('54999abc-689c-4cef-87d3-288767117ed4','registration','registration flow','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','basic-flow','',''),('5be9720d-a2de-45c0-a12f-b36969a4e010','First broker login - Conditional OTP','Flow to determine if the OTP is required for the authentication','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','basic-flow','\0',''),('67d4442a-9781-4259-a1bc-5a4169e44a74','Reset - Conditional OTP','Flow to determine if the OTP should be reset or not. Set to REQUIRED to force.','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','basic-flow','\0',''),('733cf779-8f6b-42df-aace-de51507201d3','first broker login','Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','basic-flow','',''),('76ccffb8-3b64-47a1-8c3d-3277d34cf963','forms','Username, password, otp and other auth forms.','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','basic-flow','\0',''),('7eaf199c-da4c-41fb-96e8-ac5f8f6e8ac8','reset credentials','Reset credentials for a user if they forgot their password or something','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','basic-flow','',''),('82a6b1b7-6d48-42be-b25a-5b0c2bbb887c','saml ecp','SAML ECP Profile Authentication Flow','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','basic-flow','',''),('8c8a5a59-de79-4b2a-970c-296bef9edf51','registration form','registration form','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','form-flow','\0',''),('8d597691-2849-49f6-956d-3545813d5b8e','Verify Existing Account by Re-authentication','Reauthentication of existing account','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','basic-flow','\0',''),('8d6716e5-e8d8-4add-9ed9-050f6a812064','Verify Existing Account by Re-authentication','Reauthentication of existing account','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','basic-flow','\0',''),('9b8d40d9-f77a-4fec-9603-955b3f91f7fb','Browser - Conditional OTP','Flow to determine if the OTP is required for the authentication','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','basic-flow','\0',''),('9c03bbe6-5306-429b-8848-439a4258dae2','First broker login - Conditional OTP','Flow to determine if the OTP is required for the authentication','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','basic-flow','\0',''),('9e543473-e951-4d96-85ac-dfe2ea4106b7','Browser - Conditional OTP','Flow to determine if the OTP is required for the authentication','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','basic-flow','\0',''),('a0f0f47f-9fb3-4ccd-9a26-03bca5eeedc3','clients','Base authentication for clients','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','client-flow','',''),('a80513bc-2185-4e7d-a386-89dc829e68e3','User creation or linking','Flow for the existing/non-existing user alternatives','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','basic-flow','\0',''),('ac95f1eb-13c1-4d79-97de-685f49cc393c','Account verification options','Method with which to verity the existing account','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','basic-flow','\0',''),('ba39135d-c20a-4cf8-8287-b53bc5ee2fb6','first broker login','Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','basic-flow','',''),('be9390f9-79bb-45d3-a64a-83363361426c','browser','browser based authentication','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','basic-flow','',''),('c0498828-7e44-4b44-b560-9d4866bb9aac','direct grant','OpenID Connect Resource Owner Grant','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','basic-flow','',''),('c6481670-4293-4d25-b7b1-e4cf14bc29ea','direct grant','OpenID Connect Resource Owner Grant','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','basic-flow','',''),('d755720b-95c0-4727-b1fc-243695f10f1f','Account verification options','Method with which to verity the existing account','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','basic-flow','\0',''),('d7c43b60-21e2-4e00-b6b6-3ba2638e51c3','Direct Grant - Conditional OTP','Flow to determine if the OTP is required for the authentication','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','basic-flow','\0',''),('e241bd16-f27e-4f1b-9afa-b876226f2905','docker auth','Used by Docker clients to authenticate against the IDP','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','basic-flow','',''),('e32d377b-79a7-411b-bb2a-b6437f19b770','browser','browser based authentication','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','basic-flow','',''),('e6231775-6ed8-4d2d-a821-116387f1091c','Reset - Conditional OTP','Flow to determine if the OTP should be reset or not. Set to REQUIRED to force.','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','basic-flow','\0',''),('fb0c2ad3-fde6-4fab-94a6-b8716c2063a7','reset credentials','Reset credentials for a user if they forgot their password or something','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','basic-flow','','');
/*!40000 ALTER TABLE `AUTHENTICATION_FLOW` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AUTHENTICATOR_CONFIG`
--

DROP TABLE IF EXISTS `AUTHENTICATOR_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AUTHENTICATOR_CONFIG` (
                                        `ID` varchar(36) NOT NULL,
                                        `ALIAS` varchar(255) DEFAULT NULL,
                                        `REALM_ID` varchar(36) DEFAULT NULL,
                                        PRIMARY KEY (`ID`),
                                        KEY `IDX_AUTH_CONFIG_REALM` (`REALM_ID`),
                                        CONSTRAINT `FK_AUTH_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AUTHENTICATOR_CONFIG`
--

LOCK TABLES `AUTHENTICATOR_CONFIG` WRITE;
/*!40000 ALTER TABLE `AUTHENTICATOR_CONFIG` DISABLE KEYS */;
INSERT INTO `AUTHENTICATOR_CONFIG` VALUES ('3b82bd8f-7ecc-4cf3-8f90-28ac312630ab','review profile config','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5'),('a432a4e6-b155-4bdd-b420-bd1e64eac43e','create unique user config','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f'),('dcbd9ee4-89bb-4f8d-8a95-df3f7355ab43','create unique user config','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5'),('f4a6e721-642d-4f69-a288-5117026cfb0f','review profile config','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f');
/*!40000 ALTER TABLE `AUTHENTICATOR_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AUTHENTICATOR_CONFIG_ENTRY`
--

DROP TABLE IF EXISTS `AUTHENTICATOR_CONFIG_ENTRY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AUTHENTICATOR_CONFIG_ENTRY` (
                                              `AUTHENTICATOR_ID` varchar(36) NOT NULL,
                                              `VALUE` longtext,
                                              `NAME` varchar(255) NOT NULL,
                                              PRIMARY KEY (`AUTHENTICATOR_ID`,`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AUTHENTICATOR_CONFIG_ENTRY`
--

LOCK TABLES `AUTHENTICATOR_CONFIG_ENTRY` WRITE;
/*!40000 ALTER TABLE `AUTHENTICATOR_CONFIG_ENTRY` DISABLE KEYS */;
INSERT INTO `AUTHENTICATOR_CONFIG_ENTRY` VALUES ('3b82bd8f-7ecc-4cf3-8f90-28ac312630ab','missing','update.profile.on.first.login'),('a432a4e6-b155-4bdd-b420-bd1e64eac43e','false','require.password.update.after.registration'),('dcbd9ee4-89bb-4f8d-8a95-df3f7355ab43','false','require.password.update.after.registration'),('f4a6e721-642d-4f69-a288-5117026cfb0f','missing','update.profile.on.first.login');
/*!40000 ALTER TABLE `AUTHENTICATOR_CONFIG_ENTRY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BROKER_LINK`
--

DROP TABLE IF EXISTS `BROKER_LINK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BROKER_LINK` (
                               `IDENTITY_PROVIDER` varchar(255) NOT NULL,
                               `STORAGE_PROVIDER_ID` varchar(255) DEFAULT NULL,
                               `REALM_ID` varchar(36) NOT NULL,
                               `BROKER_USER_ID` varchar(255) DEFAULT NULL,
                               `BROKER_USERNAME` varchar(255) DEFAULT NULL,
                               `TOKEN` text,
                               `USER_ID` varchar(255) NOT NULL,
                               PRIMARY KEY (`IDENTITY_PROVIDER`,`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BROKER_LINK`
--

LOCK TABLES `BROKER_LINK` WRITE;
/*!40000 ALTER TABLE `BROKER_LINK` DISABLE KEYS */;
/*!40000 ALTER TABLE `BROKER_LINK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT`
--

DROP TABLE IF EXISTS `CLIENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT` (
                          `ID` varchar(36) NOT NULL,
                          `ENABLED` bit(1) NOT NULL DEFAULT b'0',
                          `FULL_SCOPE_ALLOWED` bit(1) NOT NULL DEFAULT b'0',
                          `CLIENT_ID` varchar(255) DEFAULT NULL,
                          `NOT_BEFORE` int DEFAULT NULL,
                          `PUBLIC_CLIENT` bit(1) NOT NULL DEFAULT b'0',
                          `SECRET` varchar(255) DEFAULT NULL,
                          `BASE_URL` varchar(255) DEFAULT NULL,
                          `BEARER_ONLY` bit(1) NOT NULL DEFAULT b'0',
                          `MANAGEMENT_URL` varchar(255) DEFAULT NULL,
                          `SURROGATE_AUTH_REQUIRED` bit(1) NOT NULL DEFAULT b'0',
                          `REALM_ID` varchar(36) DEFAULT NULL,
                          `PROTOCOL` varchar(255) DEFAULT NULL,
                          `NODE_REREG_TIMEOUT` int DEFAULT '0',
                          `FRONTCHANNEL_LOGOUT` bit(1) NOT NULL DEFAULT b'0',
                          `CONSENT_REQUIRED` bit(1) NOT NULL DEFAULT b'0',
                          `NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                          `SERVICE_ACCOUNTS_ENABLED` bit(1) NOT NULL DEFAULT b'0',
                          `CLIENT_AUTHENTICATOR_TYPE` varchar(255) DEFAULT NULL,
                          `ROOT_URL` varchar(255) DEFAULT NULL,
                          `DESCRIPTION` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                          `REGISTRATION_TOKEN` varchar(255) DEFAULT NULL,
                          `STANDARD_FLOW_ENABLED` bit(1) NOT NULL DEFAULT b'1',
                          `IMPLICIT_FLOW_ENABLED` bit(1) NOT NULL DEFAULT b'0',
                          `DIRECT_ACCESS_GRANTS_ENABLED` bit(1) NOT NULL DEFAULT b'0',
                          `ALWAYS_DISPLAY_IN_CONSOLE` bit(1) NOT NULL DEFAULT b'0',
                          PRIMARY KEY (`ID`),
                          UNIQUE KEY `UK_B71CJLBENV945RB6GCON438AT` (`REALM_ID`,`CLIENT_ID`),
                          KEY `IDX_CLIENT_ID` (`CLIENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT`
--

LOCK TABLES `CLIENT` WRITE;
/*!40000 ALTER TABLE `CLIENT` DISABLE KEYS */;
INSERT INTO `CLIENT` VALUES ('138c5771-aaf5-495f-8480-05f1504a6c3a','','','dodgame-api',0,'',NULL,'','\0','','\0','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','openid-connect',-1,'','\0','dodgame-api','\0','client-secret','${authBaseUrl}','',NULL,'','\0','','\0'),('1ff4cae9-f76a-4e4b-a700-c272c99710d6','','\0','dodgame-realm',0,'\0',NULL,NULL,'',NULL,'\0','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f',NULL,0,'\0','\0','dodgame Realm','\0','client-secret',NULL,NULL,NULL,'','\0','\0','\0'),('28f1393f-9c6f-4b5e-b45b-e7708d58f86a','','\0','security-admin-console',0,'',NULL,'/admin/master/console/','\0',NULL,'\0','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','openid-connect',0,'\0','\0','${client_security-admin-console}','\0','client-secret','${authAdminUrl}',NULL,NULL,'','\0','\0','\0'),('32b8ea4f-6f72-47aa-97be-c6d651fab48f','','\0','admin-cli',0,'',NULL,NULL,'\0',NULL,'\0','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','openid-connect',0,'\0','\0','${client_admin-cli}','\0','client-secret',NULL,NULL,NULL,'\0','\0','','\0'),('34eac0d0-6d27-4bc5-931d-dcd506948309','','\0','account',0,'',NULL,'/realms/dodgame/account/','\0',NULL,'\0','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','openid-connect',0,'\0','\0','${client_account}','\0','client-secret','${authBaseUrl}',NULL,NULL,'','\0','\0','\0'),('48b200f0-dff2-4bb4-b869-1524705753f9','','\0','realm-management',0,'\0',NULL,NULL,'',NULL,'\0','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','openid-connect',0,'\0','\0','${client_realm-management}','\0','client-secret',NULL,NULL,NULL,'','\0','\0','\0'),('529e6128-c1bc-4008-ac4d-6d645f6fe9d4','','\0','account',0,'',NULL,'/realms/master/account/','\0',NULL,'\0','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','openid-connect',0,'\0','\0','${client_account}','\0','client-secret','${authBaseUrl}',NULL,NULL,'','\0','\0','\0'),('950de219-4129-4d3b-8919-0987a1a13174','','\0','master-realm',0,'\0',NULL,NULL,'',NULL,'\0','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f',NULL,0,'\0','\0','master Realm','\0','client-secret',NULL,NULL,NULL,'','\0','\0','\0'),('ced0633a-f3d9-4c75-b85d-1c62fb955164','','\0','account-console',0,'',NULL,'/realms/dodgame/account/','\0','','\0','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','openid-connect',0,'\0','\0','${client_account-console}','\0','client-secret','${authBaseUrl}','',NULL,'','\0','\0','\0'),('d43f3531-9d53-401b-a17e-59e6e6664b6a','','\0','account-console',0,'',NULL,'/realms/master/account/','\0',NULL,'\0','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','openid-connect',0,'\0','\0','${client_account-console}','\0','client-secret','${authBaseUrl}',NULL,NULL,'','\0','\0','\0'),('dc5d94e6-2255-47c0-abbe-03a224d39661','','\0','broker',0,'\0',NULL,NULL,'',NULL,'\0','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','openid-connect',0,'\0','\0','${client_broker}','\0','client-secret',NULL,NULL,NULL,'','\0','\0','\0'),('de191c36-54f9-4ce6-a3a6-695d1d3b7583','','\0','security-admin-console',0,'',NULL,'/admin/dodgame/console/','\0',NULL,'\0','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','openid-connect',0,'\0','\0','${client_security-admin-console}','\0','client-secret','${authAdminUrl}',NULL,NULL,'','\0','\0','\0'),('f32fc198-396e-4ace-9bf5-3da773b22b89','','\0','admin-cli',0,'',NULL,NULL,'\0',NULL,'\0','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','openid-connect',0,'\0','\0','${client_admin-cli}','\0','client-secret',NULL,NULL,NULL,'\0','\0','','\0'),('f5155791-97e6-48e4-8049-652547f6e9ff','','\0','broker',0,'\0',NULL,NULL,'',NULL,'\0','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','openid-connect',0,'\0','\0','${client_broker}','\0','client-secret',NULL,NULL,NULL,'','\0','\0','\0');
/*!40000 ALTER TABLE `CLIENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_ATTRIBUTES`
--

DROP TABLE IF EXISTS `CLIENT_ATTRIBUTES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_ATTRIBUTES` (
                                     `CLIENT_ID` varchar(36) NOT NULL,
                                     `NAME` varchar(255) NOT NULL,
                                     `VALUE` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
                                     PRIMARY KEY (`CLIENT_ID`,`NAME`),
                                     KEY `IDX_CLIENT_ATT_BY_NAME_VALUE` (`NAME`),
                                     CONSTRAINT `FK3C47C64BEACCA966` FOREIGN KEY (`CLIENT_ID`) REFERENCES `CLIENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_ATTRIBUTES`
--

LOCK TABLES `CLIENT_ATTRIBUTES` WRITE;
/*!40000 ALTER TABLE `CLIENT_ATTRIBUTES` DISABLE KEYS */;
INSERT INTO `CLIENT_ATTRIBUTES` VALUES ('138c5771-aaf5-495f-8480-05f1504a6c3a','backchannel.logout.revoke.offline.tokens','false'),('138c5771-aaf5-495f-8480-05f1504a6c3a','backchannel.logout.session.required','false'),('138c5771-aaf5-495f-8480-05f1504a6c3a','client.secret.creation.time','1692625999'),('138c5771-aaf5-495f-8480-05f1504a6c3a','display.on.consent.screen','false'),('138c5771-aaf5-495f-8480-05f1504a6c3a','login_theme','dodgame'),('138c5771-aaf5-495f-8480-05f1504a6c3a','oauth2.device.authorization.grant.enabled','false'),('138c5771-aaf5-495f-8480-05f1504a6c3a','oidc.ciba.grant.enabled','false'),('138c5771-aaf5-495f-8480-05f1504a6c3a','post.logout.redirect.uris','+'),('28f1393f-9c6f-4b5e-b45b-e7708d58f86a','pkce.code.challenge.method','S256'),('28f1393f-9c6f-4b5e-b45b-e7708d58f86a','post.logout.redirect.uris','+'),('34eac0d0-6d27-4bc5-931d-dcd506948309','post.logout.redirect.uris','+'),('48b200f0-dff2-4bb4-b869-1524705753f9','post.logout.redirect.uris','+'),('529e6128-c1bc-4008-ac4d-6d645f6fe9d4','post.logout.redirect.uris','+'),('ced0633a-f3d9-4c75-b85d-1c62fb955164','backchannel.logout.revoke.offline.tokens','false'),('ced0633a-f3d9-4c75-b85d-1c62fb955164','backchannel.logout.session.required','true'),('ced0633a-f3d9-4c75-b85d-1c62fb955164','display.on.consent.screen','false'),('ced0633a-f3d9-4c75-b85d-1c62fb955164','oauth2.device.authorization.grant.enabled','false'),('ced0633a-f3d9-4c75-b85d-1c62fb955164','oidc.ciba.grant.enabled','false'),('ced0633a-f3d9-4c75-b85d-1c62fb955164','pkce.code.challenge.method','S256'),('ced0633a-f3d9-4c75-b85d-1c62fb955164','post.logout.redirect.uris','+'),('d43f3531-9d53-401b-a17e-59e6e6664b6a','pkce.code.challenge.method','S256'),('d43f3531-9d53-401b-a17e-59e6e6664b6a','post.logout.redirect.uris','+'),('dc5d94e6-2255-47c0-abbe-03a224d39661','post.logout.redirect.uris','+'),('de191c36-54f9-4ce6-a3a6-695d1d3b7583','pkce.code.challenge.method','S256'),('de191c36-54f9-4ce6-a3a6-695d1d3b7583','post.logout.redirect.uris','+'),('f32fc198-396e-4ace-9bf5-3da773b22b89','post.logout.redirect.uris','+');
/*!40000 ALTER TABLE `CLIENT_ATTRIBUTES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_AUTH_FLOW_BINDINGS`
--

DROP TABLE IF EXISTS `CLIENT_AUTH_FLOW_BINDINGS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_AUTH_FLOW_BINDINGS` (
                                             `CLIENT_ID` varchar(36) NOT NULL,
                                             `FLOW_ID` varchar(36) DEFAULT NULL,
                                             `BINDING_NAME` varchar(255) NOT NULL,
                                             PRIMARY KEY (`CLIENT_ID`,`BINDING_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_AUTH_FLOW_BINDINGS`
--

LOCK TABLES `CLIENT_AUTH_FLOW_BINDINGS` WRITE;
/*!40000 ALTER TABLE `CLIENT_AUTH_FLOW_BINDINGS` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT_AUTH_FLOW_BINDINGS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_INITIAL_ACCESS`
--

DROP TABLE IF EXISTS `CLIENT_INITIAL_ACCESS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_INITIAL_ACCESS` (
                                         `ID` varchar(36) NOT NULL,
                                         `REALM_ID` varchar(36) NOT NULL,
                                         `TIMESTAMP` int DEFAULT NULL,
                                         `EXPIRATION` int DEFAULT NULL,
                                         `COUNT` int DEFAULT NULL,
                                         `REMAINING_COUNT` int DEFAULT NULL,
                                         PRIMARY KEY (`ID`),
                                         KEY `IDX_CLIENT_INIT_ACC_REALM` (`REALM_ID`),
                                         CONSTRAINT `FK_CLIENT_INIT_ACC_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_INITIAL_ACCESS`
--

LOCK TABLES `CLIENT_INITIAL_ACCESS` WRITE;
/*!40000 ALTER TABLE `CLIENT_INITIAL_ACCESS` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT_INITIAL_ACCESS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_NODE_REGISTRATIONS`
--

DROP TABLE IF EXISTS `CLIENT_NODE_REGISTRATIONS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_NODE_REGISTRATIONS` (
                                             `CLIENT_ID` varchar(36) NOT NULL,
                                             `VALUE` int DEFAULT NULL,
                                             `NAME` varchar(255) NOT NULL,
                                             PRIMARY KEY (`CLIENT_ID`,`NAME`),
                                             CONSTRAINT `FK4129723BA992F594` FOREIGN KEY (`CLIENT_ID`) REFERENCES `CLIENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_NODE_REGISTRATIONS`
--

LOCK TABLES `CLIENT_NODE_REGISTRATIONS` WRITE;
/*!40000 ALTER TABLE `CLIENT_NODE_REGISTRATIONS` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT_NODE_REGISTRATIONS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_SCOPE`
--

DROP TABLE IF EXISTS `CLIENT_SCOPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_SCOPE` (
                                `ID` varchar(36) NOT NULL,
                                `NAME` varchar(255) DEFAULT NULL,
                                `REALM_ID` varchar(36) DEFAULT NULL,
                                `DESCRIPTION` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                                `PROTOCOL` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`ID`),
                                UNIQUE KEY `UK_CLI_SCOPE` (`REALM_ID`,`NAME`),
                                KEY `IDX_REALM_CLSCOPE` (`REALM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_SCOPE`
--

LOCK TABLES `CLIENT_SCOPE` WRITE;
/*!40000 ALTER TABLE `CLIENT_SCOPE` DISABLE KEYS */;
INSERT INTO `CLIENT_SCOPE` VALUES ('0492c70e-dbb9-44fd-b926-36b2a480e707','phone','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','OpenID Connect built-in scope: phone','openid-connect'),('09575535-6b7f-404f-8400-526497b4792a','web-origins','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','OpenID Connect scope for add allowed web origins to the access token','openid-connect'),('0fa8d901-ba5d-45b6-a6ec-62461a8a8d07','profile','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','OpenID Connect built-in scope: profile','openid-connect'),('112981ef-8abb-4615-9064-11e6b03d12db','web-origins','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','OpenID Connect scope for add allowed web origins to the access token','openid-connect'),('269709f9-050d-4363-b800-007a66bdc433','address','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','OpenID Connect built-in scope: address','openid-connect'),('2b91a3e9-3562-48c9-8464-c4d021b6a48d','profile','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','OpenID Connect built-in scope: profile','openid-connect'),('2e4d99ab-3601-45d1-849e-b383678ec646','phone','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','OpenID Connect built-in scope: phone','openid-connect'),('32ffc623-cc8a-495c-8fba-61c36becefd0','microprofile-jwt','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','Microprofile - JWT built-in scope','openid-connect'),('3c466413-4163-4125-bf25-e85b9c2d9ad0','microprofile-jwt','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','Microprofile - JWT built-in scope','openid-connect'),('5a7014a9-93d9-43e0-9bc5-a389338dcf28','role_list','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','SAML role list','saml'),('937da9b0-a93c-427b-b5ff-db162f988583','role_list','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','SAML role list','saml'),('9f6f73b8-23d0-4cb4-8d8b-95c033287f0d','roles','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','OpenID Connect scope for add user roles to the access token','openid-connect'),('a07a0f75-6b5b-47e5-a788-0a7f56e8fada','offline_access','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','OpenID Connect built-in scope: offline_access','openid-connect'),('c1514a42-f14c-48a6-a441-b3cf107d7edc','email','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','OpenID Connect built-in scope: email','openid-connect'),('c564f5be-0d07-4bd2-90b4-32b3452493cb','offline_access','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','OpenID Connect built-in scope: offline_access','openid-connect'),('c73b5dfb-50e1-4649-a20b-2cd7ceadeb54','roles','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','OpenID Connect scope for add user roles to the access token','openid-connect'),('dfd5755c-e278-4e23-ade7-d1516fc94a7d','acr','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','OpenID Connect scope for add acr (authentication context class reference) to the token','openid-connect'),('e980a100-790b-48c9-b77c-413b2e8f8e2c','address','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','OpenID Connect built-in scope: address','openid-connect'),('ecfcd03c-f091-4d6f-80a1-0b69716ce804','acr','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','OpenID Connect scope for add acr (authentication context class reference) to the token','openid-connect'),('fe396b62-1b79-4557-9e57-6e0522bdf506','email','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','OpenID Connect built-in scope: email','openid-connect');
/*!40000 ALTER TABLE `CLIENT_SCOPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_SCOPE_ATTRIBUTES`
--

DROP TABLE IF EXISTS `CLIENT_SCOPE_ATTRIBUTES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_SCOPE_ATTRIBUTES` (
                                           `SCOPE_ID` varchar(36) NOT NULL,
                                           `VALUE` text,
                                           `NAME` varchar(255) NOT NULL,
                                           PRIMARY KEY (`SCOPE_ID`,`NAME`),
                                           KEY `IDX_CLSCOPE_ATTRS` (`SCOPE_ID`),
                                           CONSTRAINT `FK_CL_SCOPE_ATTR_SCOPE` FOREIGN KEY (`SCOPE_ID`) REFERENCES `CLIENT_SCOPE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_SCOPE_ATTRIBUTES`
--

LOCK TABLES `CLIENT_SCOPE_ATTRIBUTES` WRITE;
/*!40000 ALTER TABLE `CLIENT_SCOPE_ATTRIBUTES` DISABLE KEYS */;
INSERT INTO `CLIENT_SCOPE_ATTRIBUTES` VALUES ('0492c70e-dbb9-44fd-b926-36b2a480e707','${phoneScopeConsentText}','consent.screen.text'),('0492c70e-dbb9-44fd-b926-36b2a480e707','true','display.on.consent.screen'),('0492c70e-dbb9-44fd-b926-36b2a480e707','true','include.in.token.scope'),('09575535-6b7f-404f-8400-526497b4792a','','consent.screen.text'),('09575535-6b7f-404f-8400-526497b4792a','false','display.on.consent.screen'),('09575535-6b7f-404f-8400-526497b4792a','false','include.in.token.scope'),('0fa8d901-ba5d-45b6-a6ec-62461a8a8d07','${profileScopeConsentText}','consent.screen.text'),('0fa8d901-ba5d-45b6-a6ec-62461a8a8d07','true','display.on.consent.screen'),('0fa8d901-ba5d-45b6-a6ec-62461a8a8d07','true','include.in.token.scope'),('112981ef-8abb-4615-9064-11e6b03d12db','','consent.screen.text'),('112981ef-8abb-4615-9064-11e6b03d12db','false','display.on.consent.screen'),('112981ef-8abb-4615-9064-11e6b03d12db','false','include.in.token.scope'),('269709f9-050d-4363-b800-007a66bdc433','${addressScopeConsentText}','consent.screen.text'),('269709f9-050d-4363-b800-007a66bdc433','true','display.on.consent.screen'),('269709f9-050d-4363-b800-007a66bdc433','true','include.in.token.scope'),('2b91a3e9-3562-48c9-8464-c4d021b6a48d','${profileScopeConsentText}','consent.screen.text'),('2b91a3e9-3562-48c9-8464-c4d021b6a48d','true','display.on.consent.screen'),('2b91a3e9-3562-48c9-8464-c4d021b6a48d','true','include.in.token.scope'),('2e4d99ab-3601-45d1-849e-b383678ec646','${phoneScopeConsentText}','consent.screen.text'),('2e4d99ab-3601-45d1-849e-b383678ec646','true','display.on.consent.screen'),('2e4d99ab-3601-45d1-849e-b383678ec646','true','include.in.token.scope'),('32ffc623-cc8a-495c-8fba-61c36becefd0','false','display.on.consent.screen'),('32ffc623-cc8a-495c-8fba-61c36becefd0','true','include.in.token.scope'),('3c466413-4163-4125-bf25-e85b9c2d9ad0','false','display.on.consent.screen'),('3c466413-4163-4125-bf25-e85b9c2d9ad0','true','include.in.token.scope'),('5a7014a9-93d9-43e0-9bc5-a389338dcf28','${samlRoleListScopeConsentText}','consent.screen.text'),('5a7014a9-93d9-43e0-9bc5-a389338dcf28','true','display.on.consent.screen'),('937da9b0-a93c-427b-b5ff-db162f988583','${samlRoleListScopeConsentText}','consent.screen.text'),('937da9b0-a93c-427b-b5ff-db162f988583','true','display.on.consent.screen'),('9f6f73b8-23d0-4cb4-8d8b-95c033287f0d','${rolesScopeConsentText}','consent.screen.text'),('9f6f73b8-23d0-4cb4-8d8b-95c033287f0d','true','display.on.consent.screen'),('9f6f73b8-23d0-4cb4-8d8b-95c033287f0d','false','include.in.token.scope'),('a07a0f75-6b5b-47e5-a788-0a7f56e8fada','${offlineAccessScopeConsentText}','consent.screen.text'),('a07a0f75-6b5b-47e5-a788-0a7f56e8fada','true','display.on.consent.screen'),('c1514a42-f14c-48a6-a441-b3cf107d7edc','${emailScopeConsentText}','consent.screen.text'),('c1514a42-f14c-48a6-a441-b3cf107d7edc','true','display.on.consent.screen'),('c1514a42-f14c-48a6-a441-b3cf107d7edc','true','include.in.token.scope'),('c564f5be-0d07-4bd2-90b4-32b3452493cb','${offlineAccessScopeConsentText}','consent.screen.text'),('c564f5be-0d07-4bd2-90b4-32b3452493cb','true','display.on.consent.screen'),('c73b5dfb-50e1-4649-a20b-2cd7ceadeb54','${rolesScopeConsentText}','consent.screen.text'),('c73b5dfb-50e1-4649-a20b-2cd7ceadeb54','true','display.on.consent.screen'),('c73b5dfb-50e1-4649-a20b-2cd7ceadeb54','false','include.in.token.scope'),('dfd5755c-e278-4e23-ade7-d1516fc94a7d','false','display.on.consent.screen'),('dfd5755c-e278-4e23-ade7-d1516fc94a7d','false','include.in.token.scope'),('e980a100-790b-48c9-b77c-413b2e8f8e2c','${addressScopeConsentText}','consent.screen.text'),('e980a100-790b-48c9-b77c-413b2e8f8e2c','true','display.on.consent.screen'),('e980a100-790b-48c9-b77c-413b2e8f8e2c','true','include.in.token.scope'),('ecfcd03c-f091-4d6f-80a1-0b69716ce804','false','display.on.consent.screen'),('ecfcd03c-f091-4d6f-80a1-0b69716ce804','false','include.in.token.scope'),('fe396b62-1b79-4557-9e57-6e0522bdf506','${emailScopeConsentText}','consent.screen.text'),('fe396b62-1b79-4557-9e57-6e0522bdf506','true','display.on.consent.screen'),('fe396b62-1b79-4557-9e57-6e0522bdf506','true','include.in.token.scope');
/*!40000 ALTER TABLE `CLIENT_SCOPE_ATTRIBUTES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_SCOPE_CLIENT`
--

DROP TABLE IF EXISTS `CLIENT_SCOPE_CLIENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_SCOPE_CLIENT` (
                                       `CLIENT_ID` varchar(255) NOT NULL,
                                       `SCOPE_ID` varchar(255) NOT NULL,
                                       `DEFAULT_SCOPE` bit(1) NOT NULL DEFAULT b'0',
                                       PRIMARY KEY (`CLIENT_ID`,`SCOPE_ID`),
                                       KEY `IDX_CLSCOPE_CL` (`CLIENT_ID`),
                                       KEY `IDX_CL_CLSCOPE` (`SCOPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_SCOPE_CLIENT`
--

LOCK TABLES `CLIENT_SCOPE_CLIENT` WRITE;
/*!40000 ALTER TABLE `CLIENT_SCOPE_CLIENT` DISABLE KEYS */;
INSERT INTO `CLIENT_SCOPE_CLIENT` VALUES ('138c5771-aaf5-495f-8480-05f1504a6c3a','112981ef-8abb-4615-9064-11e6b03d12db',''),('138c5771-aaf5-495f-8480-05f1504a6c3a','269709f9-050d-4363-b800-007a66bdc433','\0'),('138c5771-aaf5-495f-8480-05f1504a6c3a','2b91a3e9-3562-48c9-8464-c4d021b6a48d',''),('138c5771-aaf5-495f-8480-05f1504a6c3a','2e4d99ab-3601-45d1-849e-b383678ec646','\0'),('138c5771-aaf5-495f-8480-05f1504a6c3a','32ffc623-cc8a-495c-8fba-61c36becefd0','\0'),('138c5771-aaf5-495f-8480-05f1504a6c3a','9f6f73b8-23d0-4cb4-8d8b-95c033287f0d',''),('138c5771-aaf5-495f-8480-05f1504a6c3a','c1514a42-f14c-48a6-a441-b3cf107d7edc',''),('138c5771-aaf5-495f-8480-05f1504a6c3a','c564f5be-0d07-4bd2-90b4-32b3452493cb','\0'),('138c5771-aaf5-495f-8480-05f1504a6c3a','ecfcd03c-f091-4d6f-80a1-0b69716ce804',''),('28f1393f-9c6f-4b5e-b45b-e7708d58f86a','0492c70e-dbb9-44fd-b926-36b2a480e707','\0'),('28f1393f-9c6f-4b5e-b45b-e7708d58f86a','09575535-6b7f-404f-8400-526497b4792a',''),('28f1393f-9c6f-4b5e-b45b-e7708d58f86a','0fa8d901-ba5d-45b6-a6ec-62461a8a8d07',''),('28f1393f-9c6f-4b5e-b45b-e7708d58f86a','3c466413-4163-4125-bf25-e85b9c2d9ad0','\0'),('28f1393f-9c6f-4b5e-b45b-e7708d58f86a','a07a0f75-6b5b-47e5-a788-0a7f56e8fada','\0'),('28f1393f-9c6f-4b5e-b45b-e7708d58f86a','c73b5dfb-50e1-4649-a20b-2cd7ceadeb54',''),('28f1393f-9c6f-4b5e-b45b-e7708d58f86a','dfd5755c-e278-4e23-ade7-d1516fc94a7d',''),('28f1393f-9c6f-4b5e-b45b-e7708d58f86a','e980a100-790b-48c9-b77c-413b2e8f8e2c','\0'),('28f1393f-9c6f-4b5e-b45b-e7708d58f86a','fe396b62-1b79-4557-9e57-6e0522bdf506',''),('32b8ea4f-6f72-47aa-97be-c6d651fab48f','0492c70e-dbb9-44fd-b926-36b2a480e707','\0'),('32b8ea4f-6f72-47aa-97be-c6d651fab48f','09575535-6b7f-404f-8400-526497b4792a',''),('32b8ea4f-6f72-47aa-97be-c6d651fab48f','0fa8d901-ba5d-45b6-a6ec-62461a8a8d07',''),('32b8ea4f-6f72-47aa-97be-c6d651fab48f','3c466413-4163-4125-bf25-e85b9c2d9ad0','\0'),('32b8ea4f-6f72-47aa-97be-c6d651fab48f','a07a0f75-6b5b-47e5-a788-0a7f56e8fada','\0'),('32b8ea4f-6f72-47aa-97be-c6d651fab48f','c73b5dfb-50e1-4649-a20b-2cd7ceadeb54',''),('32b8ea4f-6f72-47aa-97be-c6d651fab48f','dfd5755c-e278-4e23-ade7-d1516fc94a7d',''),('32b8ea4f-6f72-47aa-97be-c6d651fab48f','e980a100-790b-48c9-b77c-413b2e8f8e2c','\0'),('32b8ea4f-6f72-47aa-97be-c6d651fab48f','fe396b62-1b79-4557-9e57-6e0522bdf506',''),('34eac0d0-6d27-4bc5-931d-dcd506948309','112981ef-8abb-4615-9064-11e6b03d12db',''),('34eac0d0-6d27-4bc5-931d-dcd506948309','269709f9-050d-4363-b800-007a66bdc433','\0'),('34eac0d0-6d27-4bc5-931d-dcd506948309','2b91a3e9-3562-48c9-8464-c4d021b6a48d',''),('34eac0d0-6d27-4bc5-931d-dcd506948309','2e4d99ab-3601-45d1-849e-b383678ec646','\0'),('34eac0d0-6d27-4bc5-931d-dcd506948309','32ffc623-cc8a-495c-8fba-61c36becefd0','\0'),('34eac0d0-6d27-4bc5-931d-dcd506948309','9f6f73b8-23d0-4cb4-8d8b-95c033287f0d',''),('34eac0d0-6d27-4bc5-931d-dcd506948309','c1514a42-f14c-48a6-a441-b3cf107d7edc',''),('34eac0d0-6d27-4bc5-931d-dcd506948309','c564f5be-0d07-4bd2-90b4-32b3452493cb','\0'),('34eac0d0-6d27-4bc5-931d-dcd506948309','ecfcd03c-f091-4d6f-80a1-0b69716ce804',''),('48b200f0-dff2-4bb4-b869-1524705753f9','112981ef-8abb-4615-9064-11e6b03d12db',''),('48b200f0-dff2-4bb4-b869-1524705753f9','269709f9-050d-4363-b800-007a66bdc433','\0'),('48b200f0-dff2-4bb4-b869-1524705753f9','2b91a3e9-3562-48c9-8464-c4d021b6a48d',''),('48b200f0-dff2-4bb4-b869-1524705753f9','2e4d99ab-3601-45d1-849e-b383678ec646','\0'),('48b200f0-dff2-4bb4-b869-1524705753f9','32ffc623-cc8a-495c-8fba-61c36becefd0','\0'),('48b200f0-dff2-4bb4-b869-1524705753f9','9f6f73b8-23d0-4cb4-8d8b-95c033287f0d',''),('48b200f0-dff2-4bb4-b869-1524705753f9','c1514a42-f14c-48a6-a441-b3cf107d7edc',''),('48b200f0-dff2-4bb4-b869-1524705753f9','c564f5be-0d07-4bd2-90b4-32b3452493cb','\0'),('48b200f0-dff2-4bb4-b869-1524705753f9','ecfcd03c-f091-4d6f-80a1-0b69716ce804',''),('529e6128-c1bc-4008-ac4d-6d645f6fe9d4','0492c70e-dbb9-44fd-b926-36b2a480e707','\0'),('529e6128-c1bc-4008-ac4d-6d645f6fe9d4','09575535-6b7f-404f-8400-526497b4792a',''),('529e6128-c1bc-4008-ac4d-6d645f6fe9d4','0fa8d901-ba5d-45b6-a6ec-62461a8a8d07',''),('529e6128-c1bc-4008-ac4d-6d645f6fe9d4','3c466413-4163-4125-bf25-e85b9c2d9ad0','\0'),('529e6128-c1bc-4008-ac4d-6d645f6fe9d4','a07a0f75-6b5b-47e5-a788-0a7f56e8fada','\0'),('529e6128-c1bc-4008-ac4d-6d645f6fe9d4','c73b5dfb-50e1-4649-a20b-2cd7ceadeb54',''),('529e6128-c1bc-4008-ac4d-6d645f6fe9d4','dfd5755c-e278-4e23-ade7-d1516fc94a7d',''),('529e6128-c1bc-4008-ac4d-6d645f6fe9d4','e980a100-790b-48c9-b77c-413b2e8f8e2c','\0'),('529e6128-c1bc-4008-ac4d-6d645f6fe9d4','fe396b62-1b79-4557-9e57-6e0522bdf506',''),('950de219-4129-4d3b-8919-0987a1a13174','0492c70e-dbb9-44fd-b926-36b2a480e707','\0'),('950de219-4129-4d3b-8919-0987a1a13174','09575535-6b7f-404f-8400-526497b4792a',''),('950de219-4129-4d3b-8919-0987a1a13174','0fa8d901-ba5d-45b6-a6ec-62461a8a8d07',''),('950de219-4129-4d3b-8919-0987a1a13174','3c466413-4163-4125-bf25-e85b9c2d9ad0','\0'),('950de219-4129-4d3b-8919-0987a1a13174','a07a0f75-6b5b-47e5-a788-0a7f56e8fada','\0'),('950de219-4129-4d3b-8919-0987a1a13174','c73b5dfb-50e1-4649-a20b-2cd7ceadeb54',''),('950de219-4129-4d3b-8919-0987a1a13174','dfd5755c-e278-4e23-ade7-d1516fc94a7d',''),('950de219-4129-4d3b-8919-0987a1a13174','e980a100-790b-48c9-b77c-413b2e8f8e2c','\0'),('950de219-4129-4d3b-8919-0987a1a13174','fe396b62-1b79-4557-9e57-6e0522bdf506',''),('ced0633a-f3d9-4c75-b85d-1c62fb955164','112981ef-8abb-4615-9064-11e6b03d12db',''),('ced0633a-f3d9-4c75-b85d-1c62fb955164','269709f9-050d-4363-b800-007a66bdc433','\0'),('ced0633a-f3d9-4c75-b85d-1c62fb955164','2b91a3e9-3562-48c9-8464-c4d021b6a48d',''),('ced0633a-f3d9-4c75-b85d-1c62fb955164','2e4d99ab-3601-45d1-849e-b383678ec646','\0'),('ced0633a-f3d9-4c75-b85d-1c62fb955164','32ffc623-cc8a-495c-8fba-61c36becefd0','\0'),('ced0633a-f3d9-4c75-b85d-1c62fb955164','9f6f73b8-23d0-4cb4-8d8b-95c033287f0d',''),('ced0633a-f3d9-4c75-b85d-1c62fb955164','c1514a42-f14c-48a6-a441-b3cf107d7edc',''),('ced0633a-f3d9-4c75-b85d-1c62fb955164','c564f5be-0d07-4bd2-90b4-32b3452493cb','\0'),('ced0633a-f3d9-4c75-b85d-1c62fb955164','ecfcd03c-f091-4d6f-80a1-0b69716ce804',''),('d43f3531-9d53-401b-a17e-59e6e6664b6a','0492c70e-dbb9-44fd-b926-36b2a480e707','\0'),('d43f3531-9d53-401b-a17e-59e6e6664b6a','09575535-6b7f-404f-8400-526497b4792a',''),('d43f3531-9d53-401b-a17e-59e6e6664b6a','0fa8d901-ba5d-45b6-a6ec-62461a8a8d07',''),('d43f3531-9d53-401b-a17e-59e6e6664b6a','3c466413-4163-4125-bf25-e85b9c2d9ad0','\0'),('d43f3531-9d53-401b-a17e-59e6e6664b6a','a07a0f75-6b5b-47e5-a788-0a7f56e8fada','\0'),('d43f3531-9d53-401b-a17e-59e6e6664b6a','c73b5dfb-50e1-4649-a20b-2cd7ceadeb54',''),('d43f3531-9d53-401b-a17e-59e6e6664b6a','dfd5755c-e278-4e23-ade7-d1516fc94a7d',''),('d43f3531-9d53-401b-a17e-59e6e6664b6a','e980a100-790b-48c9-b77c-413b2e8f8e2c','\0'),('d43f3531-9d53-401b-a17e-59e6e6664b6a','fe396b62-1b79-4557-9e57-6e0522bdf506',''),('dc5d94e6-2255-47c0-abbe-03a224d39661','112981ef-8abb-4615-9064-11e6b03d12db',''),('dc5d94e6-2255-47c0-abbe-03a224d39661','269709f9-050d-4363-b800-007a66bdc433','\0'),('dc5d94e6-2255-47c0-abbe-03a224d39661','2b91a3e9-3562-48c9-8464-c4d021b6a48d',''),('dc5d94e6-2255-47c0-abbe-03a224d39661','2e4d99ab-3601-45d1-849e-b383678ec646','\0'),('dc5d94e6-2255-47c0-abbe-03a224d39661','32ffc623-cc8a-495c-8fba-61c36becefd0','\0'),('dc5d94e6-2255-47c0-abbe-03a224d39661','9f6f73b8-23d0-4cb4-8d8b-95c033287f0d',''),('dc5d94e6-2255-47c0-abbe-03a224d39661','c1514a42-f14c-48a6-a441-b3cf107d7edc',''),('dc5d94e6-2255-47c0-abbe-03a224d39661','c564f5be-0d07-4bd2-90b4-32b3452493cb','\0'),('dc5d94e6-2255-47c0-abbe-03a224d39661','ecfcd03c-f091-4d6f-80a1-0b69716ce804',''),('de191c36-54f9-4ce6-a3a6-695d1d3b7583','112981ef-8abb-4615-9064-11e6b03d12db',''),('de191c36-54f9-4ce6-a3a6-695d1d3b7583','269709f9-050d-4363-b800-007a66bdc433','\0'),('de191c36-54f9-4ce6-a3a6-695d1d3b7583','2b91a3e9-3562-48c9-8464-c4d021b6a48d',''),('de191c36-54f9-4ce6-a3a6-695d1d3b7583','2e4d99ab-3601-45d1-849e-b383678ec646','\0'),('de191c36-54f9-4ce6-a3a6-695d1d3b7583','32ffc623-cc8a-495c-8fba-61c36becefd0','\0'),('de191c36-54f9-4ce6-a3a6-695d1d3b7583','9f6f73b8-23d0-4cb4-8d8b-95c033287f0d',''),('de191c36-54f9-4ce6-a3a6-695d1d3b7583','c1514a42-f14c-48a6-a441-b3cf107d7edc',''),('de191c36-54f9-4ce6-a3a6-695d1d3b7583','c564f5be-0d07-4bd2-90b4-32b3452493cb','\0'),('de191c36-54f9-4ce6-a3a6-695d1d3b7583','ecfcd03c-f091-4d6f-80a1-0b69716ce804',''),('f32fc198-396e-4ace-9bf5-3da773b22b89','112981ef-8abb-4615-9064-11e6b03d12db',''),('f32fc198-396e-4ace-9bf5-3da773b22b89','269709f9-050d-4363-b800-007a66bdc433','\0'),('f32fc198-396e-4ace-9bf5-3da773b22b89','2b91a3e9-3562-48c9-8464-c4d021b6a48d',''),('f32fc198-396e-4ace-9bf5-3da773b22b89','2e4d99ab-3601-45d1-849e-b383678ec646','\0'),('f32fc198-396e-4ace-9bf5-3da773b22b89','32ffc623-cc8a-495c-8fba-61c36becefd0','\0'),('f32fc198-396e-4ace-9bf5-3da773b22b89','9f6f73b8-23d0-4cb4-8d8b-95c033287f0d',''),('f32fc198-396e-4ace-9bf5-3da773b22b89','c1514a42-f14c-48a6-a441-b3cf107d7edc',''),('f32fc198-396e-4ace-9bf5-3da773b22b89','c564f5be-0d07-4bd2-90b4-32b3452493cb','\0'),('f32fc198-396e-4ace-9bf5-3da773b22b89','ecfcd03c-f091-4d6f-80a1-0b69716ce804',''),('f5155791-97e6-48e4-8049-652547f6e9ff','0492c70e-dbb9-44fd-b926-36b2a480e707','\0'),('f5155791-97e6-48e4-8049-652547f6e9ff','09575535-6b7f-404f-8400-526497b4792a',''),('f5155791-97e6-48e4-8049-652547f6e9ff','0fa8d901-ba5d-45b6-a6ec-62461a8a8d07',''),('f5155791-97e6-48e4-8049-652547f6e9ff','3c466413-4163-4125-bf25-e85b9c2d9ad0','\0'),('f5155791-97e6-48e4-8049-652547f6e9ff','a07a0f75-6b5b-47e5-a788-0a7f56e8fada','\0'),('f5155791-97e6-48e4-8049-652547f6e9ff','c73b5dfb-50e1-4649-a20b-2cd7ceadeb54',''),('f5155791-97e6-48e4-8049-652547f6e9ff','dfd5755c-e278-4e23-ade7-d1516fc94a7d',''),('f5155791-97e6-48e4-8049-652547f6e9ff','e980a100-790b-48c9-b77c-413b2e8f8e2c','\0'),('f5155791-97e6-48e4-8049-652547f6e9ff','fe396b62-1b79-4557-9e57-6e0522bdf506','');
/*!40000 ALTER TABLE `CLIENT_SCOPE_CLIENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_SCOPE_ROLE_MAPPING`
--

DROP TABLE IF EXISTS `CLIENT_SCOPE_ROLE_MAPPING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_SCOPE_ROLE_MAPPING` (
                                             `SCOPE_ID` varchar(36) NOT NULL,
                                             `ROLE_ID` varchar(36) NOT NULL,
                                             PRIMARY KEY (`SCOPE_ID`,`ROLE_ID`),
                                             KEY `IDX_CLSCOPE_ROLE` (`SCOPE_ID`),
                                             KEY `IDX_ROLE_CLSCOPE` (`ROLE_ID`),
                                             CONSTRAINT `FK_CL_SCOPE_RM_SCOPE` FOREIGN KEY (`SCOPE_ID`) REFERENCES `CLIENT_SCOPE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_SCOPE_ROLE_MAPPING`
--

LOCK TABLES `CLIENT_SCOPE_ROLE_MAPPING` WRITE;
/*!40000 ALTER TABLE `CLIENT_SCOPE_ROLE_MAPPING` DISABLE KEYS */;
INSERT INTO `CLIENT_SCOPE_ROLE_MAPPING` VALUES ('a07a0f75-6b5b-47e5-a788-0a7f56e8fada','69bf757a-2809-4086-8c0d-757c5428db36'),('c564f5be-0d07-4bd2-90b4-32b3452493cb','b09cea9f-6a62-4ae0-b12d-7e1514a81794');
/*!40000 ALTER TABLE `CLIENT_SCOPE_ROLE_MAPPING` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_SESSION`
--

DROP TABLE IF EXISTS `CLIENT_SESSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_SESSION` (
                                  `ID` varchar(36) NOT NULL,
                                  `CLIENT_ID` varchar(36) DEFAULT NULL,
                                  `REDIRECT_URI` varchar(255) DEFAULT NULL,
                                  `STATE` varchar(255) DEFAULT NULL,
                                  `TIMESTAMP` int DEFAULT NULL,
                                  `SESSION_ID` varchar(36) DEFAULT NULL,
                                  `AUTH_METHOD` varchar(255) DEFAULT NULL,
                                  `REALM_ID` varchar(255) DEFAULT NULL,
                                  `AUTH_USER_ID` varchar(36) DEFAULT NULL,
                                  `CURRENT_ACTION` varchar(36) DEFAULT NULL,
                                  PRIMARY KEY (`ID`),
                                  KEY `IDX_CLIENT_SESSION_SESSION` (`SESSION_ID`),
                                  CONSTRAINT `FK_B4AO2VCVAT6UKAU74WBWTFQO1` FOREIGN KEY (`SESSION_ID`) REFERENCES `USER_SESSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_SESSION`
--

LOCK TABLES `CLIENT_SESSION` WRITE;
/*!40000 ALTER TABLE `CLIENT_SESSION` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT_SESSION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_SESSION_AUTH_STATUS`
--

DROP TABLE IF EXISTS `CLIENT_SESSION_AUTH_STATUS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_SESSION_AUTH_STATUS` (
                                              `AUTHENTICATOR` varchar(36) NOT NULL,
                                              `STATUS` int DEFAULT NULL,
                                              `CLIENT_SESSION` varchar(36) NOT NULL,
                                              PRIMARY KEY (`CLIENT_SESSION`,`AUTHENTICATOR`),
                                              CONSTRAINT `AUTH_STATUS_CONSTRAINT` FOREIGN KEY (`CLIENT_SESSION`) REFERENCES `CLIENT_SESSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_SESSION_AUTH_STATUS`
--

LOCK TABLES `CLIENT_SESSION_AUTH_STATUS` WRITE;
/*!40000 ALTER TABLE `CLIENT_SESSION_AUTH_STATUS` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT_SESSION_AUTH_STATUS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_SESSION_NOTE`
--

DROP TABLE IF EXISTS `CLIENT_SESSION_NOTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_SESSION_NOTE` (
                                       `NAME` varchar(255) NOT NULL,
                                       `VALUE` varchar(255) DEFAULT NULL,
                                       `CLIENT_SESSION` varchar(36) NOT NULL,
                                       PRIMARY KEY (`CLIENT_SESSION`,`NAME`),
                                       CONSTRAINT `FK5EDFB00FF51C2736` FOREIGN KEY (`CLIENT_SESSION`) REFERENCES `CLIENT_SESSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_SESSION_NOTE`
--

LOCK TABLES `CLIENT_SESSION_NOTE` WRITE;
/*!40000 ALTER TABLE `CLIENT_SESSION_NOTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT_SESSION_NOTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_SESSION_PROT_MAPPER`
--

DROP TABLE IF EXISTS `CLIENT_SESSION_PROT_MAPPER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_SESSION_PROT_MAPPER` (
                                              `PROTOCOL_MAPPER_ID` varchar(36) NOT NULL,
                                              `CLIENT_SESSION` varchar(36) NOT NULL,
                                              PRIMARY KEY (`CLIENT_SESSION`,`PROTOCOL_MAPPER_ID`),
                                              CONSTRAINT `FK_33A8SGQW18I532811V7O2DK89` FOREIGN KEY (`CLIENT_SESSION`) REFERENCES `CLIENT_SESSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_SESSION_PROT_MAPPER`
--

LOCK TABLES `CLIENT_SESSION_PROT_MAPPER` WRITE;
/*!40000 ALTER TABLE `CLIENT_SESSION_PROT_MAPPER` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT_SESSION_PROT_MAPPER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_SESSION_ROLE`
--

DROP TABLE IF EXISTS `CLIENT_SESSION_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_SESSION_ROLE` (
                                       `ROLE_ID` varchar(255) NOT NULL,
                                       `CLIENT_SESSION` varchar(36) NOT NULL,
                                       PRIMARY KEY (`CLIENT_SESSION`,`ROLE_ID`),
                                       CONSTRAINT `FK_11B7SGQW18I532811V7O2DV76` FOREIGN KEY (`CLIENT_SESSION`) REFERENCES `CLIENT_SESSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_SESSION_ROLE`
--

LOCK TABLES `CLIENT_SESSION_ROLE` WRITE;
/*!40000 ALTER TABLE `CLIENT_SESSION_ROLE` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT_SESSION_ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT_USER_SESSION_NOTE`
--

DROP TABLE IF EXISTS `CLIENT_USER_SESSION_NOTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENT_USER_SESSION_NOTE` (
                                            `NAME` varchar(255) NOT NULL,
                                            `VALUE` text,
                                            `CLIENT_SESSION` varchar(36) NOT NULL,
                                            PRIMARY KEY (`CLIENT_SESSION`,`NAME`),
                                            CONSTRAINT `FK_CL_USR_SES_NOTE` FOREIGN KEY (`CLIENT_SESSION`) REFERENCES `CLIENT_SESSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT_USER_SESSION_NOTE`
--

LOCK TABLES `CLIENT_USER_SESSION_NOTE` WRITE;
/*!40000 ALTER TABLE `CLIENT_USER_SESSION_NOTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENT_USER_SESSION_NOTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `COMPONENT`
--

DROP TABLE IF EXISTS `COMPONENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COMPONENT` (
                             `ID` varchar(36) NOT NULL,
                             `NAME` varchar(255) DEFAULT NULL,
                             `PARENT_ID` varchar(36) DEFAULT NULL,
                             `PROVIDER_ID` varchar(36) DEFAULT NULL,
                             `PROVIDER_TYPE` varchar(255) DEFAULT NULL,
                             `REALM_ID` varchar(36) DEFAULT NULL,
                             `SUB_TYPE` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`ID`),
                             KEY `IDX_COMPONENT_REALM` (`REALM_ID`),
                             KEY `IDX_COMPONENT_PROVIDER_TYPE` (`PROVIDER_TYPE`),
                             CONSTRAINT `FK_COMPONENT_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COMPONENT`
--

LOCK TABLES `COMPONENT` WRITE;
/*!40000 ALTER TABLE `COMPONENT` DISABLE KEYS */;
INSERT INTO `COMPONENT` VALUES ('06447974-08c0-471d-ae80-5a90efcdf156','Max Clients Limit','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','max-clients','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','anonymous'),('10a8390e-1041-4433-b946-1b045125cafd','Full Scope Disabled','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','scope','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','anonymous'),('1a3e2b4c-3eb1-4342-804f-ba1daa00eb8e','rsa-enc-generated','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','rsa-enc-generated','org.keycloak.keys.KeyProvider','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5',NULL),('1d38094a-205d-4cc6-b86f-e70db0132827','rsa-enc-generated','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','rsa-enc-generated','org.keycloak.keys.KeyProvider','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f',NULL),('1ec73086-c126-468b-a78f-6560951ba3ca','rsa-generated','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','rsa-generated','org.keycloak.keys.KeyProvider','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f',NULL),('351621d1-c221-4303-8c65-5e81c5480407','Allowed Protocol Mapper Types','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','allowed-protocol-mappers','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','anonymous'),('3a7956e4-5515-4ab3-9cb3-8df8cf3af048','Allowed Client Scopes','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','allowed-client-templates','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','anonymous'),('46e7a8c5-736a-42f3-870d-d1362dce741e','Allowed Client Scopes','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','allowed-client-templates','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','authenticated'),('5391bd07-1aef-4710-8d5d-ce97aceaf463',NULL,'31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','declarative-user-profile','org.keycloak.userprofile.UserProfileProvider','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5',NULL),('54c507ba-a29d-4d24-9942-3424b114f00e','Allowed Protocol Mapper Types','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','allowed-protocol-mappers','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','authenticated'),('61c21fab-869e-427f-a95d-c4687ce69776','Allowed Protocol Mapper Types','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','allowed-protocol-mappers','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','authenticated'),('68ac1078-386b-4fa7-b472-1600ab55e8db','Allowed Client Scopes','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','allowed-client-templates','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','authenticated'),('6e1deb28-2966-47b7-9359-aaf9b036364f','Trusted Hosts','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','trusted-hosts','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','anonymous'),('7da802b2-896c-4143-a127-a11dfc52f412','aes-generated','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','aes-generated','org.keycloak.keys.KeyProvider','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f',NULL),('821a27d9-6d4e-4dfc-b602-5bcdb5f2161d','Allowed Client Scopes','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','allowed-client-templates','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','anonymous'),('8ad660d6-b3c5-4c46-890f-997a5b814057','hmac-generated','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','hmac-generated','org.keycloak.keys.KeyProvider','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f',NULL),('9794dd47-2507-4ae9-ad95-9257645a29f5','Consent Required','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','consent-required','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','anonymous'),('b76477e8-7d38-4e20-a679-8c2ce9a91e3c','rsa-generated','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','rsa-generated','org.keycloak.keys.KeyProvider','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5',NULL),('bcbdd2e1-4a28-4cac-9e52-1676092c7735','Max Clients Limit','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','max-clients','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','anonymous'),('c0b41459-5928-430a-a813-5c732d4328c2','Trusted Hosts','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','trusted-hosts','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','anonymous'),('d74e4ff3-0d10-40c0-98b5-8c5fccb9e786','Allowed Protocol Mapper Types','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','allowed-protocol-mappers','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','anonymous'),('d9eaab87-f9a7-4fb2-b5c3-d6a756b2346d','aes-generated','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','aes-generated','org.keycloak.keys.KeyProvider','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5',NULL),('f615bf18-8924-4fb3-9e26-4d86d171551a','Full Scope Disabled','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','scope','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','anonymous'),('f67bf368-f39c-4349-9298-5830968d66a0','Consent Required','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','consent-required','org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','anonymous'),('f9ba86ef-cebe-41d8-a9db-a3c2e784742a','hmac-generated','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','hmac-generated','org.keycloak.keys.KeyProvider','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5',NULL);
/*!40000 ALTER TABLE `COMPONENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `COMPONENT_CONFIG`
--

DROP TABLE IF EXISTS `COMPONENT_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COMPONENT_CONFIG` (
                                    `ID` varchar(36) NOT NULL,
                                    `COMPONENT_ID` varchar(36) NOT NULL,
                                    `NAME` varchar(255) NOT NULL,
                                    `VALUE` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                                    PRIMARY KEY (`ID`),
                                    KEY `IDX_COMPO_CONFIG_COMPO` (`COMPONENT_ID`),
                                    CONSTRAINT `FK_COMPONENT_CONFIG` FOREIGN KEY (`COMPONENT_ID`) REFERENCES `COMPONENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COMPONENT_CONFIG`
--

LOCK TABLES `COMPONENT_CONFIG` WRITE;
/*!40000 ALTER TABLE `COMPONENT_CONFIG` DISABLE KEYS */;
INSERT INTO `COMPONENT_CONFIG` VALUES ('0154fa18-769a-441d-ab01-b8ddc96fc072','61c21fab-869e-427f-a95d-c4687ce69776','allowed-protocol-mapper-types','saml-role-list-mapper'),('046f8402-3538-41b7-b20d-86519f27873a','d9eaab87-f9a7-4fb2-b5c3-d6a756b2346d','priority','100'),('05e8012c-7d00-4ee2-8519-b4a72e8640c9','d74e4ff3-0d10-40c0-98b5-8c5fccb9e786','allowed-protocol-mapper-types','oidc-usermodel-property-mapper'),('0aa0945f-275d-4da6-9225-900287a3a1d8','351621d1-c221-4303-8c65-5e81c5480407','allowed-protocol-mapper-types','saml-user-property-mapper'),('12a4ba21-0751-4939-86b1-54053d60645d','06447974-08c0-471d-ae80-5a90efcdf156','max-clients','200'),('1938f29d-56e2-4a0c-b170-e82f0a2cb29e','f9ba86ef-cebe-41d8-a9db-a3c2e784742a','kid','21105024-940b-4747-a80e-0328bf40f2b2'),('1c4b1a98-0ae6-46cd-9fe4-fec32342d7c2','b76477e8-7d38-4e20-a679-8c2ce9a91e3c','privateKey','MIIEowIBAAKCAQEAyTftkt/nKN2La2MM0m0d5/INBYLTIzc6xqmgqAD2CFKP/g7PamVqkyaoo9IrgNoof2aRPg1RM+RMCMy4XZnwgGP4LnoMMT6NS3FR2fYWBWeQNbS+/9TTns4WNIQ3vQE8c9sol93lUhs+/D0pgsQR8xrFOQxOS/LCbR0aylaXl7iX+xkdf7YcQ8XAGI/ZbsnvRKZsKgNgDcKcCF8vzZZd81nz3q219hoAR7RuAIjLMSSftrJ97gQ9bYKUpqRjXcFQjWxOkICGykISb+v1HECPX1FdF2DhhX9XQcYobs8sxeAHHdX52mqWIC5yy1jYh0F6nmX8e9UjixZsjVOgYEHWhwIDAQABAoIBAAVvm9z0bI/HkG0pw15p8T1jZiOoeaDBbLaSPZNcV0k5RceUydizfbIVAMhQNLmp6x8p+7GD9ZYjzEQOl8p7jqpBSSKkTHEGIhYQLShoxAaIWjaBOAm8S61GHwZzLfhOsL3mhU25Aml57/Urh9QPDPcW2Y4lfXYCzGrbNu6RcU35c+m3LuQaa6Rw4Z8a3uqksEKt9EBSrzNiumG/qGknY5T1PZPnwLlw5TWmKA3Jcn5gvQrdQY6o6IM4ZFDkFbKW+xprkZhgZdA/by2lDgGadOyAOvvYAi2uZVrw4Lmk+0OlpmruoTBsZ+yP5Ayh2qYajRFH0x/Z+5TTmjcCmMkxkV0CgYEA52CpT+4m6ihF2Ndal/be+tX9VBzf4JgAtR5bE0RHaOtFnCtqvxYz7BggXxA8lZS8BMds58N/bICAlLFL8GlXn3q78DiFfGxkD+wVFxasbuDdtpNflqvAbETgE+55nzG8YpcfCJDD02J1h9kuiA/P2d+6/4CTRJZZxoJBZntZTG0CgYEA3qGnBY56VsgREImmUtFCqeoJ6INHkfUnOOavSQaN0g/8Q0qsf2nk+P8DzG8/zmXKpmo0PzTKMgAuGFqAfPqIPrcQqh4lFtpTNlb7bVyg2NrS3c+zNaPyYyrYZGKXNgJ5ql7M5yiZMOy90VmP/n0y2aqDT7wgn96D9muKjl28bkMCgYEAlv0GIAnqTR2wztth3A8W3ZttMEmTcw3eUFMZ3c3Nx9LYXxwaEc6cXXI1FbQTxQ0FUR0OnYN/EMAeqGm2g5FKw+Ck6hMIUPSuq5FFBgceyVBmfrFXTIySClU74OXbah5br/6sGrvVfoke52K7scCXw7bzYa526+gtd5Qit4zA7lUCgYAhxDrk6Ns9VlLgQ0BqxzppMwoVrfOo+5nbU4G6fbf86FfGjQjkulb0PRmCZE01LNMzsmACRit0sfpLz041pnoLqRBp/PB7Ktn8H+06hRYQTgA1jE8OD7OvvsvbTejJVE7HvGEvnkS92upfWkowuyR/RxFrgokgNkulkGIPfcAaqQKBgGiC9IutBC5UPhk/NGdrFKPyP2j+CSUch5bjhzNT1mxlbef4nbAKEL7kA2gEb6vilPOgm/i+Y9lm5Mxs5Mw6D7RDf5saADMDoAKf49yHtw5NzkH5WjDa1ASUwgDOE8DybKuUdY3Ry0+3uFh5UrUdpywkTTWDF/SZpycPJmXVlbSg'),('1ea2766a-682c-484a-a56b-5043f3691e0e','54c507ba-a29d-4d24-9942-3424b114f00e','allowed-protocol-mapper-types','saml-user-attribute-mapper'),('1ef3f503-8773-4b0c-95d9-2c0318a39944','b76477e8-7d38-4e20-a679-8c2ce9a91e3c','certificate','MIICnTCCAYUCBgGKddRfqzANBgkqhkiG9w0BAQsFADASMRAwDgYDVQQDDAdkb2RnYW1lMB4XDTIzMDkwODE3MjQyN1oXDTMzMDkwODE3MjYwN1owEjEQMA4GA1UEAwwHZG9kZ2FtZTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMk37ZLf5yjdi2tjDNJtHefyDQWC0yM3OsapoKgA9ghSj/4Oz2plapMmqKPSK4DaKH9mkT4NUTPkTAjMuF2Z8IBj+C56DDE+jUtxUdn2FgVnkDW0vv/U057OFjSEN70BPHPbKJfd5VIbPvw9KYLEEfMaxTkMTkvywm0dGspWl5e4l/sZHX+2HEPFwBiP2W7J70SmbCoDYA3CnAhfL82WXfNZ896ttfYaAEe0bgCIyzEkn7ayfe4EPW2ClKakY13BUI1sTpCAhspCEm/r9RxAj19RXRdg4YV/V0HGKG7PLMXgBx3V+dpqliAucstY2IdBep5l/HvVI4sWbI1ToGBB1ocCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAOenjYN4xMCVOSwhy3PDzGgjSwOVyD3DohuXjw13IWpEyXdqemNN+Lrkcn57I+uMCWt9LHeKIbaWcvbQjCGQt0qNSksx6yj2yRVN7ldtKhTe18wWBJPRcxh3N/v0Y0HFloeeB0SyJlm6oHCAmhiO4ppmkDQmSJP0iF06EY0urZqcLlq7j9me92n64PqalkvClmpGM14vWi7QMypCQkDX5yN4++rtExYoJhNtIermecXlpOTi6K7UHF1m3GBfYFl1qtXxVX5vwEuVCQhZhRAC4/4aDqoobv35VY5glKuSy9Z/Zq/wq9hmLEEcEUT8kEm8pjRVf+qE9f54QqT1h/42YsA=='),('2112f6de-20ac-4875-991e-215a430e1aa5','1ec73086-c126-468b-a78f-6560951ba3ca','privateKey','MIIEowIBAAKCAQEAh+GoFHBL75hQaan6K/PnHs/btNzRXZI6L5zFaGVzAX1wGgnfYX0rjJKSGbXctNafHGd21717j36p879RlWGJ8L6fG4fjW+kEWyLbIGmLqvPxuFs/rJO8HaOHPy1eg/xfPnQXaYAo5iwWcYqo2jvTdLVaVQXV/qE/i/nutlnr1ZaYozcdGQLCqt7Un5Nnp7oaavA3HdZmlcLyXVY06wJ0hcsnTzHdgRcKygLR1giDHmhHrNJhiAscnHuwsrDt5IeeA0zOlnxId6tt/DKgXK5dBnh5xO/yBbos8rwrJhqnRzb3ebw9yKp6xKttnTZ+yMcWlvO5jy6cr5xKzsd67AnJjwIDAQABAoIBABmXq1gaJHs+hVq5bIiNuiin/poMgxRsH7BrBxVYEjakIRKObHfIowSpcIIOKkEXMUWgyRXuNtD6itkBXkTT566jwITnba+Ybj0aDgqHRoqOwqa17QK8mpA8hhMkoOocBPZW2DgDLvDXOeztsErWPMf9BkQAQuNgDH95Nfg7bHtAST+/t5a9ZJjW3FG2IQMSCcqFY97KCthxqon0K7WuxNldD1SjtztdaiG+X2qJA4hScwqYw6suLDp3obBDNm95mGdch+At29NGQtvYAxnCvW55P9ek1yIoHTrbtVZdKCxuQRUdCrJHvd5B46Xd1DeBIfNNVDs2x2rpdWkA22rjF6UCgYEAveV6HZO57kvR6np62x/JFcmNPhbIPGGb2wq9yLjLp+yIAbuZ+E6u4s3LfSzWO50lzG6Q9aVjO4TnxMa1bpFifblkRhRYK/f5q9Iih3DmOqjpmKBQjZhFSMBDSDscOMdvqdPEVYk3K/BkrzRN8QMj8K4U/TZPJqxgUaFFeiP4Ny0CgYEAty6qmvxsgVMq4VGVYv7nM2MCIZSLCRp/XAUwzczO1KSWiXRhEprOqXr6EqNcEX3rxZAT2dPUiJ0iPoeypSu34epCJPbJUsg4G3JFS7TwZXQubFGaaLXah0i3ftEEf0k8Fou4QtpN6+6hkJHhTbXuHM5pLBoX/pxGWMx7biL9uSsCgYB/3x3q+sYSPAYfsdg24+GBhVZR//9YAZZ02nfPyPiCnFk9aXmV5k4HtfKX2PBt9jU0wyNj9rmqo6V6MSCNUuMSrBtkwWBaQ0JhvD+6GoE98O5T82gq4OWOxsDHy94GMe30WwBzZNG9CcSS4lK1v5qdAGxr0f1cmmjFryZcyAnmBQKBgQCtUBPOa4FlooBxijTi4vVwYv6DMv2Q8WcKc0bHkD0oZ0ieaGPnrFxXcajktXbIffDyjOybLZZqYvEhZFYbfA+QSYmnW28DSbf8D9OMW3cPJis+BL8oztYhJSWncK7mly2ozyzY457R2Ic4Ny2JztWon4pWVaHaHZVZMq6dd7HC3QKBgFPULwuFLLwWItuPi8r0KTWiJpXQ2bQOEoPJPbv4yd//IZQF1Vll76tpobsbXliv8ZlJu3jz2KQD/IdonE3jOiw09Vw/67GHN6GkjauA/E3TttJjoN/pmYZRW999R+nnJBsjkL8xsWJZxJMTJaYXEqUna3RGmhDpSniXilsxuNWh'),('2601bff8-be39-4b0f-88ab-bb612a30719f','8ad660d6-b3c5-4c46-890f-997a5b814057','kid','6b6dc384-64c4-40bd-9058-8a8f4387e99a'),('2aa4efc9-f5b3-4200-bd6e-f819c8d30dbc','1d38094a-205d-4cc6-b86f-e70db0132827','algorithm','RSA-OAEP'),('2d9a2f19-3f13-4993-8da3-441286b717a0','7da802b2-896c-4143-a127-a11dfc52f412','secret','Q6q4Nix0RET3Xc51vJh1hw'),('2fb4df45-b41f-46ac-af2b-429e1478f87e','1d38094a-205d-4cc6-b86f-e70db0132827','certificate','MIICmzCCAYMCBgGKddRNFzANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjMwOTA4MTcyNDIyWhcNMzMwOTA4MTcyNjAyWjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC5VtqMVQQ2NMvD9iNHy9QVMmFtviCay9DygwwkFpFd7Kr/tZrRe/rIR7PNJbwHJ/lSzzwPDojU5O2sHyVLnqzwlziJst8lAqRXOevBQCZsmv9c1epv7H0JvQ4wwOh7/S+QSkhSA2azjtQ0TdeQS7aWK42pLIb7nV6exiMQbKIi3PbXHhxky7DDjxixjJj/8BOBsq473veOIEZuwzy+0xAFdEAz9tMjTS2KNQp2FSFBWe+WXRfjL7k40QldcrUQclW9Bw5aiWr+pLLjxrI5gTz2MXBFywBXXksgfWvY0ejngO5NuZxsUPZ+V7tb5rB0Y/sxIMy/AAPSTpgHg8PnSOADAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAJwcqs9phbShPqoINmeIJuELprKOhzIMvV+RG+pNC7/ZGucZztzg9L2L2IiHWCh7RPC2VwIYJRmGTb1PpGBfOfguu3hcsfrWII+dnSBkWgJTFvyUhJH4VpJCsSqoNS3i85jKRHCU+CRuQX0bQ9Cih1Mmn0I7ZMaMLULQHoQzhkkE2B/owJ5u/QvycToTipc0R/N/5xcMvLNJzi+zevYkc+h0oSxFIBg+jjWTzMQRWgU3pCafHg3OmphD3qnlHcrvqLA01c3i8TFELfw3/UPUGzzQTWuLCWrh82pMa7gaRWSRt+PcPRSF/8dNiiTKpN4qJV9AdiLpcD3xiaes7RfEg7U='),('3001a915-561a-4769-b796-b58ae64d5b7a','d74e4ff3-0d10-40c0-98b5-8c5fccb9e786','allowed-protocol-mapper-types','oidc-sha256-pairwise-sub-mapper'),('31c4de1a-01ae-48ca-9b76-18b91cb661ae','b76477e8-7d38-4e20-a679-8c2ce9a91e3c','priority','100'),('3879350d-648f-46fb-af00-d62f51477548','54c507ba-a29d-4d24-9942-3424b114f00e','allowed-protocol-mapper-types','oidc-sha256-pairwise-sub-mapper'),('3d5881be-d645-4261-862d-8f0c3948ca1a','d74e4ff3-0d10-40c0-98b5-8c5fccb9e786','allowed-protocol-mapper-types','oidc-address-mapper'),('3f33f456-35f6-4d89-bac3-0827a6a74fc3','d74e4ff3-0d10-40c0-98b5-8c5fccb9e786','allowed-protocol-mapper-types','saml-user-attribute-mapper'),('48f5cd4b-7052-47f6-84ff-0235d342b3e2','1ec73086-c126-468b-a78f-6560951ba3ca','certificate','MIICmzCCAYMCBgGKddRJ+jANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjMwOTA4MTcyNDIxWhcNMzMwOTA4MTcyNjAxWjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCH4agUcEvvmFBpqfor8+cez9u03NFdkjovnMVoZXMBfXAaCd9hfSuMkpIZtdy01p8cZ3bXvXuPfqnzv1GVYYnwvp8bh+Nb6QRbItsgaYuq8/G4Wz+sk7wdo4c/LV6D/F8+dBdpgCjmLBZxiqjaO9N0tVpVBdX+oT+L+e62WevVlpijNx0ZAsKq3tSfk2enuhpq8Dcd1maVwvJdVjTrAnSFyydPMd2BFwrKAtHWCIMeaEes0mGICxyce7CysO3kh54DTM6WfEh3q238MqBcrl0GeHnE7/IFuizyvCsmGqdHNvd5vD3IqnrEq22dNn7IxxaW87mPLpyvnErOx3rsCcmPAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAGRHAh0DYeufSzestSI3zpUfRrxAV0O4JXYJzPhcLvsNBAYDGiNrSQz9QK4tlCZr48qy8m9B4mOxVWZh8DFf8SvVsKUSdoLlE/Fpd5AlZr9/RCAMTJK4lu1n9px77tiy7LarfPZ3U7WtLaVYAtH5spcqqI6dwvW9rinmyg/Z4q9bqtV5esYzzqQHYYJ0d/eAuc8fT7fDBS9/ti8G/LmZ24jg3/iy4yZlYy1VehyOiPLtS/zV7GlYDoqRo1N5jHrTMyfFAqZQZFeuMY5BpRZisZ+I1uXGC7tGDisA0SD4cJddQ6y/WcBvsduzGUVweEVV4iqOA7Ab8oBelyBDoCYehkE='),('4a8bd22c-5b86-47eb-a957-a8af1f8db586','61c21fab-869e-427f-a95d-c4687ce69776','allowed-protocol-mapper-types','saml-user-property-mapper'),('524bef42-5118-4cbe-a080-b80635973ec5','61c21fab-869e-427f-a95d-c4687ce69776','allowed-protocol-mapper-types','oidc-usermodel-property-mapper'),('57093d29-14fc-4b55-8b2a-b9babf4681c3','1a3e2b4c-3eb1-4342-804f-ba1daa00eb8e','certificate','MIICnTCCAYUCBgGKddRhyjANBgkqhkiG9w0BAQsFADASMRAwDgYDVQQDDAdkb2RnYW1lMB4XDTIzMDkwODE3MjQyN1oXDTMzMDkwODE3MjYwN1owEjEQMA4GA1UEAwwHZG9kZ2FtZTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAI9i1oXVhlv+d4jH1rN1cFy3zYWKWdfyaltTM7l2ij0P9DPJfwcsFuQDb6bxVlYIKny25lb0+DvNWzvMNt7V2XtCDSy4Gjalm8LeB64H+XGuTQS736PH8RpO2gOYCcwfiW0tKRHjrTm+PAo6pR0p6ZgQUHpfCbHPmmAS8KvIP/J0IZteD/jIGtKOFplFs6D5i4h9Tcg7Zc4gBKLymOBMD0PDFF2q6vEQJCGbTxlvFwvm+dc8x9HaunUdkaVkOCrm+kIXIfA2THEvLxdaFsx0sn67LMBQbxga1YbsC8RbbKNbpYZEaVu1SPAACps+ZIfNpSG+5HK1uDa3UHklKF4ue8kCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAVUGxor+y7YBJuDUHRFP8mXpdtkSVLjFvP0LF7lyiUBYkCRFO+ibyiVa2R0Xy6MiwfIgyTbB6wOZSZUpl+6yHjAD/Flvup0zGEAhVNiRVcAanjhibqaRfMKzAcVmg1uuzXW85sQ1dn6weMSVKLud/fFkTtfZTDCLylKd3npDgCL3hWEPnbldAdrWBlcl4IAclQREp/Y85qJUDRLEGKQf1+65lugue8i2kRRkvUm/UeZmlCrqhyelRa/1rOreF1rlFLF/sAcoO3e0tP3HBDYKz9z9xxWFUxnaaXvSwX3Wx2oOWvEIf801S1+s3t0yg1+jYpL4g8P1KAU+LhnGSx3EXxg=='),('5a7d6069-9ea5-4619-a112-91e7da855ff4','d9eaab87-f9a7-4fb2-b5c3-d6a756b2346d','kid','321d2487-deff-4485-a0c1-d2f444721ff1'),('5b8314c4-7420-4b99-ba52-b3e430786357','8ad660d6-b3c5-4c46-890f-997a5b814057','priority','100'),('5cec44ad-48fa-43f8-9779-62a76049b58a','c0b41459-5928-430a-a813-5c732d4328c2','client-uris-must-match','true'),('5f14c93f-49da-49b3-92e1-e437d26f9d5b','d74e4ff3-0d10-40c0-98b5-8c5fccb9e786','allowed-protocol-mapper-types','saml-user-property-mapper'),('627bd75d-1bcf-4c38-a84b-da94f3ede428','351621d1-c221-4303-8c65-5e81c5480407','allowed-protocol-mapper-types','saml-role-list-mapper'),('65dad56c-17d8-4845-ac5c-b27fdbb51fd2','61c21fab-869e-427f-a95d-c4687ce69776','allowed-protocol-mapper-types','oidc-full-name-mapper'),('6c3585f5-a88c-4455-a682-d3f06e52ac51','6e1deb28-2966-47b7-9359-aaf9b036364f','host-sending-registration-request-must-match','true'),('6f7427cd-2488-4ad6-afa6-900a583a98c3','7da802b2-896c-4143-a127-a11dfc52f412','kid','55cf9f5d-e488-4459-832e-65069fb6995b'),('6fc61463-329a-4401-919f-1fecd470bdc7','821a27d9-6d4e-4dfc-b602-5bcdb5f2161d','allow-default-scopes','true'),('70b37f4b-747c-4662-a01f-887636112758','68ac1078-386b-4fa7-b472-1600ab55e8db','allow-default-scopes','true'),('70f0f17e-8adb-4bd9-a1a9-62c29450e3cf','7da802b2-896c-4143-a127-a11dfc52f412','priority','100'),('7130e0d9-eaf5-475a-ad6a-ce1f9e83295a','6e1deb28-2966-47b7-9359-aaf9b036364f','client-uris-must-match','true'),('76636e38-fe09-4535-94c8-99af8fd2e6cd','1a3e2b4c-3eb1-4342-804f-ba1daa00eb8e','privateKey','MIIEoQIBAAKCAQEAj2LWhdWGW/53iMfWs3VwXLfNhYpZ1/JqW1MzuXaKPQ/0M8l/BywW5ANvpvFWVggqfLbmVvT4O81bO8w23tXZe0INLLgaNqWbwt4Hrgf5ca5NBLvfo8fxGk7aA5gJzB+JbS0pEeOtOb48CjqlHSnpmBBQel8Jsc+aYBLwq8g/8nQhm14P+Mga0o4WmUWzoPmLiH1NyDtlziAEovKY4EwPQ8MUXarq8RAkIZtPGW8XC+b51zzH0dq6dR2RpWQ4Kub6Qhch8DZMcS8vF1oWzHSyfrsswFBvGBrVhuwLxFtso1ulhkRpW7VI8AAKmz5kh82lIb7kcrW4NrdQeSUoXi57yQIDAQABAoH/HhInHkFJs7rPS2Rv0W/g+9KaO1zEeTXvFp90Nx/mAHcmCOEI3GvmzWLUH3foYO+5lulLYINQGUEwwrMpLfbAwlmsaAXBZe37Lr/JOhMRPi5ivztpLG2e/Gft4xmhVteyphfbjixLBL/tFEiFcOmQTiUXMdsDNN11J7x1PpgnM5GaR3udw7AM685W7nhGQE9EZwieoz/5K8r0qO9M7a140IedxHruo1jYH5AfPPZFQaSsOCnhzopVl2SJINTTGom8S1Ri9AhL8ROG1CitHxsLH9jJIqz7gR3REd6l4fNvmPMkJOCI7g8btD8iq5Va39lxWo+mKb3J2kIl1BdWaF9BAoGBAMbluuxpVgCA9kUIY3COWwj5XI9R1wF2BWk6I2c+yNwlgNq21SACEj36SMkYiMoltTxaii/eYcHg4xydmdL/6tzFZbeVumUPpbz/LE/EW+ExNwC58CTNp/v3tAElByu/WeRqW4skbV18JipPqqTGFEMnw8i31BJLEfup9VObqByJAoGBALiNOCth+3Wxzy6+ILAVqDAXUFH+hoYRXClKPyUUOLaaBmb/0xVFG5npiKV8lDjBIdzzsIFSLq5w8W1GGwmNcuUNnRFngl5vJfvob/VI7UE8jl3HMsBNAMMfaJR90P//vV9nGtw7gnwGdk+FgmuksNMvSJAMGKrl3+xlg50i6BVBAoGBALK36mZToDuDpcYR64hP4e0GY+1EFHXznpkW7IjxGXr3F/lvH8JLGikmpBBOQ1ug1mEM2e55XE3hzTEBTB8UZe1KUkPL/lig3sQcZaynPAf1OP8mFGp9gNwfj1cX9oGQxy0/hFJawQqna4PJYgP9GGffa0UIKT7mxZnk9dPSohRBAoGAChKjIzMI34ah8UItVI8KC/3pw1/qBb1C03oa8jIlWkoT2WFTKxEHjaICdE3VZTedWoruclCC/cBqXPV7mkzfHYNdtEp7Ah586z/nhHLDiFD+0D2EFpzdUskPEzO+yYoy0tc0zxpsZ8qOgI6f7LIjeJNXGY2fdwxu6V8tDNGP60ECgYBm4qdxBt6wRNxl/6zCw259K3WzCjLzPQ9d++gYSjyejp/Pkmboz85nDaMxtI284a8a1caQ4jVjZEOTzGsrOwGdWMsvkXR+KkGmXPDU1DaXSToDbppV2NQ7wSiZMkEDvQYj3wnB2Fgo6iULDk54ACOUGnZaWO5r8wV2c/3cPMwSGg=='),('7a3f33e3-8874-413e-bc35-0444dbb64e7e','d9eaab87-f9a7-4fb2-b5c3-d6a756b2346d','secret','tb8PFVFcXs1ms2yjZqgNBA'),('7ac9e57b-82bb-42a1-9d70-b5d4cb7d51e9','54c507ba-a29d-4d24-9942-3424b114f00e','allowed-protocol-mapper-types','oidc-usermodel-property-mapper'),('7c81051a-85eb-41c9-b90a-e3082f529590','1ec73086-c126-468b-a78f-6560951ba3ca','keyUse','SIG'),('7dc16bcb-9671-4f44-86e7-d76d2146dbfd','1a3e2b4c-3eb1-4342-804f-ba1daa00eb8e','priority','100'),('8337ede0-8a68-486c-a0f2-93c176dd453a','c0b41459-5928-430a-a813-5c732d4328c2','host-sending-registration-request-must-match','true'),('8c163fdc-0e4c-4580-92e4-bcd8071af2dd','bcbdd2e1-4a28-4cac-9e52-1676092c7735','max-clients','200'),('8f95f668-17e6-485a-b479-eecc3b044766','351621d1-c221-4303-8c65-5e81c5480407','allowed-protocol-mapper-types','saml-user-attribute-mapper'),('902f3573-163a-455a-9203-bf4712dba654','54c507ba-a29d-4d24-9942-3424b114f00e','allowed-protocol-mapper-types','oidc-address-mapper'),('95a0f99d-da18-4b47-a458-bf435c79d208','1d38094a-205d-4cc6-b86f-e70db0132827','keyUse','ENC'),('97225848-05d7-4037-9993-7fafe18b3ed9','351621d1-c221-4303-8c65-5e81c5480407','allowed-protocol-mapper-types','oidc-usermodel-attribute-mapper'),('99d1199d-efce-479b-8a54-d4f85dff00e6','8ad660d6-b3c5-4c46-890f-997a5b814057','secret','G9OPoEUM7sY6vRaHLQtQnvnq9Yf5zKiXus9xTjzv6wiQ76_W0OeCRM8f_fjIvDEQHYX8nIdbzM7skXiDLGVRgw'),('a28e79e1-ff16-43bf-9ff4-a0e1c08c7df3','8ad660d6-b3c5-4c46-890f-997a5b814057','algorithm','HS256'),('a42c4da5-1c95-42da-96eb-2e249e98487c','351621d1-c221-4303-8c65-5e81c5480407','allowed-protocol-mapper-types','oidc-address-mapper'),('a7eafdf4-7399-44d1-9a8a-66f90dbd52d4','f9ba86ef-cebe-41d8-a9db-a3c2e784742a','secret','j_tSnNaqu8FcqUtDmynCr2ChctfnWuwqoOnGXiF_JeNvraIr_kP4pYiopxnf2IsxLEyt70Li68SSwY4vocJlig'),('b3f27882-8474-42c9-804d-62109cf4eb17','54c507ba-a29d-4d24-9942-3424b114f00e','allowed-protocol-mapper-types','oidc-full-name-mapper'),('b4886f5d-eb90-4c84-8c53-3e9350914219','61c21fab-869e-427f-a95d-c4687ce69776','allowed-protocol-mapper-types','saml-user-attribute-mapper'),('b536b1dd-b1ca-4c0b-8bac-878149c7b298','d74e4ff3-0d10-40c0-98b5-8c5fccb9e786','allowed-protocol-mapper-types','oidc-full-name-mapper'),('b7a1fc18-3a7b-44e3-83f8-155778ca9da7','46e7a8c5-736a-42f3-870d-d1362dce741e','allow-default-scopes','true'),('b9f2c21b-c219-4713-8858-1acb85e74c9d','d74e4ff3-0d10-40c0-98b5-8c5fccb9e786','allowed-protocol-mapper-types','oidc-usermodel-attribute-mapper'),('bdb12058-f193-4f02-90ca-8d695cf8c25f','3a7956e4-5515-4ab3-9cb3-8df8cf3af048','allow-default-scopes','true'),('ca7d15ca-15f2-4294-afad-ddb497b70e11','351621d1-c221-4303-8c65-5e81c5480407','allowed-protocol-mapper-types','oidc-usermodel-property-mapper'),('cad1e41c-ec23-4533-8a12-dbe5c304eb9d','f9ba86ef-cebe-41d8-a9db-a3c2e784742a','algorithm','HS256'),('cd42ff1c-83d7-44dd-8a99-1c9550af8254','61c21fab-869e-427f-a95d-c4687ce69776','allowed-protocol-mapper-types','oidc-sha256-pairwise-sub-mapper'),('d10807b7-4901-4906-b8d0-cb0f04abe708','351621d1-c221-4303-8c65-5e81c5480407','allowed-protocol-mapper-types','oidc-full-name-mapper'),('d4604f17-ac0c-45c9-90c1-db21399d35ce','61c21fab-869e-427f-a95d-c4687ce69776','allowed-protocol-mapper-types','oidc-address-mapper'),('d5c0e146-0254-4866-9dc6-be8016eeccf5','54c507ba-a29d-4d24-9942-3424b114f00e','allowed-protocol-mapper-types','oidc-usermodel-attribute-mapper'),('d75aa666-67b7-49f2-bdcc-e02fef56cbb3','351621d1-c221-4303-8c65-5e81c5480407','allowed-protocol-mapper-types','oidc-sha256-pairwise-sub-mapper'),('da2f458b-cf89-4972-8e25-67130b56adf4','1d38094a-205d-4cc6-b86f-e70db0132827','privateKey','MIIEowIBAAKCAQEAuVbajFUENjTLw/YjR8vUFTJhbb4gmsvQ8oMMJBaRXeyq/7Wa0Xv6yEezzSW8Byf5Us88Dw6I1OTtrB8lS56s8Jc4ibLfJQKkVznrwUAmbJr/XNXqb+x9Cb0OMMDoe/0vkEpIUgNms47UNE3XkEu2liuNqSyG+51ensYjEGyiItz21x4cZMuww48YsYyY//ATgbKuO973jiBGbsM8vtMQBXRAM/bTI00tijUKdhUhQVnvll0X4y+5ONEJXXK1EHJVvQcOWolq/qSy48ayOYE89jFwRcsAV15LIH1r2NHo54DuTbmcbFD2fle7W+awdGP7MSDMvwAD0k6YB4PD50jgAwIDAQABAoIBACBnQPp7+qqHfICcTfrFdiTmlAYd0ZF+xN9BbiP5Fh03vYXLeG/bnzLY5mSdjkwp/vstZDC1t9KQ28TWBS09ZrsBlU7tyPmthpIBfGhjENS4HKFcfQpobEwBk7OOZpcW4vP1KMO9+Hy4If2rBl8h/aAvqSrEsCzZjKtxtLuwYoB5mQrycQ4/r9X/SmHgJjvrtcm1e5dcpRtJaoAgdA1Vd9OGhYyxVsDO1TDLty14SyP964qpZN4etOzh4hPBt5EQYv3+zIPT+PPOyuywtGesUP0n+pA1VJqXHosaWGXnYC+BMTmhb1oD00Gv3s4SN1RCv7SnMQNJ2Mi4cqPMSq5FrkECgYEA6c9ezr+IL0II3c2rQazNjUAgR+vtZRjEdrin38/EkgiIvkinunvSK4jIaeBNbl75KLbYFaVcSd6l00fa/ugI1KAdvLaASv67ziPbL0p6aOsZAaIZXwjWmXNSfoAyUF9NGLGvfcNg+B4zPDb7sKWuJ8PPZ7DE4FcUqklgzFiiBdMCgYEAyu3XbuAvwNThmi1UlMPU3lfXXTjs9YbGKpDoX56iawT0mWV1LiT3DaHHP7nxJxp6hppqboYiujVrcl9TzjnAPI7F9jONgk+5BQYVZsYUQ3/V5w+nsSCCwN1GBHFW2MTxdgKTo1d+xz7ITPbeoooSjEHdOKw3va0oEan2fqwAbxECgYBhOIQwrf2/wNKVADdM6MDUEi0aGuUsm37d6uyLKQaQvpG1pdpwtZnv1kGXnNg1o7IxFs6FOaxFXxpO31sVkYBSgX28AZsZFBEdp1U1td9ujwtn3XJ1TOSHDCfqR/4j42zYbtINFlfU9CHT0P0ZN9tR157/e8ZxjmQeZsKkVdqRVwKBgDyjC0+/P6WwNuV6DB8T0UTLHLhnbS8EoaxQdo8z+LZf2bMIhRZPx17Ua5RYPH4BxID6HTl/bVsU4VFguMsTqKSvyOmDiBJbPh8JLVXzdFSjG0ia/fHIlCmLNQPY1imZ18j6hiE14NUqpnGRBv+soFQ2ziLEGzfbSPDWR6icPtjxAoGBAID7M6V92IhPeEzlhlXmQmGdjdejp7DDvhpCk4kGFeqECHpgDYye9cGCMiNm23ag28GnfkxkdizyaiK2F0UI6mKT2ON+No3NVve14K6ukuxJQSd15nNAQjJqsFFsLlK5wmPydLRCC9cT18lO9eoz88lsuFEz81aN28CQgLHOT3EF'),('dbb94156-73e6-4204-b510-0538fea79075','1ec73086-c126-468b-a78f-6560951ba3ca','priority','100'),('de89bebb-c903-4318-9f23-cde0a6c78112','61c21fab-869e-427f-a95d-c4687ce69776','allowed-protocol-mapper-types','oidc-usermodel-attribute-mapper'),('debd9b9e-ed6c-48e4-b2ad-e7f377b4a33c','54c507ba-a29d-4d24-9942-3424b114f00e','allowed-protocol-mapper-types','saml-user-property-mapper'),('dfacef26-4ca8-478c-919a-10a9790d07cd','d74e4ff3-0d10-40c0-98b5-8c5fccb9e786','allowed-protocol-mapper-types','saml-role-list-mapper'),('e079891e-b652-48ad-adfd-a4e51f5a7ba6','54c507ba-a29d-4d24-9942-3424b114f00e','allowed-protocol-mapper-types','saml-role-list-mapper'),('f8ea189f-9cd6-46b6-8540-d55a6e761f84','1a3e2b4c-3eb1-4342-804f-ba1daa00eb8e','algorithm','RSA-OAEP'),('fb5673ca-bdbf-4227-91fd-f3955dbb9464','f9ba86ef-cebe-41d8-a9db-a3c2e784742a','priority','100'),('fc9bcde8-0b1d-4fb7-aa8a-4d17024a74ad','1d38094a-205d-4cc6-b86f-e70db0132827','priority','100');
/*!40000 ALTER TABLE `COMPONENT_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `COMPOSITE_ROLE`
--

DROP TABLE IF EXISTS `COMPOSITE_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COMPOSITE_ROLE` (
                                  `COMPOSITE` varchar(36) NOT NULL,
                                  `CHILD_ROLE` varchar(36) NOT NULL,
                                  PRIMARY KEY (`COMPOSITE`,`CHILD_ROLE`),
                                  KEY `IDX_COMPOSITE` (`COMPOSITE`),
                                  KEY `IDX_COMPOSITE_CHILD` (`CHILD_ROLE`),
                                  CONSTRAINT `FK_A63WVEKFTU8JO1PNJ81E7MCE2` FOREIGN KEY (`COMPOSITE`) REFERENCES `KEYCLOAK_ROLE` (`ID`),
                                  CONSTRAINT `FK_GR7THLLB9LU8Q4VQA4524JJY8` FOREIGN KEY (`CHILD_ROLE`) REFERENCES `KEYCLOAK_ROLE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COMPOSITE_ROLE`
--

LOCK TABLES `COMPOSITE_ROLE` WRITE;
/*!40000 ALTER TABLE `COMPOSITE_ROLE` DISABLE KEYS */;
INSERT INTO `COMPOSITE_ROLE` VALUES ('089cbeda-af28-4c3a-b0e1-e77379f5d82e','16abc242-2623-40c1-8553-6fbb0f8746d1'),('089cbeda-af28-4c3a-b0e1-e77379f5d82e','41c90a82-51ca-47e2-af7c-c745f2beef0f'),('089cbeda-af28-4c3a-b0e1-e77379f5d82e','4749ecd0-7f22-47e9-b3bc-d5bcfb9f11e0'),('089cbeda-af28-4c3a-b0e1-e77379f5d82e','4d3fcf9f-d270-4892-b7a6-a30d06f0bc27'),('089cbeda-af28-4c3a-b0e1-e77379f5d82e','5fdaa8ba-b745-463a-8ef0-20a24fe7dba1'),('089cbeda-af28-4c3a-b0e1-e77379f5d82e','72784b1c-31d0-4355-8f2c-cfaca7ea1dea'),('089cbeda-af28-4c3a-b0e1-e77379f5d82e','9977f1e2-22bc-4c80-b099-ab3423ac3f1d'),('089cbeda-af28-4c3a-b0e1-e77379f5d82e','9a23483d-b149-48cc-b313-61593ea6d622'),('089cbeda-af28-4c3a-b0e1-e77379f5d82e','c56f698f-1e19-48d6-b419-b201d2e9e80d'),('089cbeda-af28-4c3a-b0e1-e77379f5d82e','c7f5033f-d308-403a-88ba-8076610ed85a'),('089cbeda-af28-4c3a-b0e1-e77379f5d82e','cd0ea247-8b19-479a-b503-8d862d315960'),('089cbeda-af28-4c3a-b0e1-e77379f5d82e','cf863212-b3e9-47c9-9d31-d21a0f6f2e12'),('089cbeda-af28-4c3a-b0e1-e77379f5d82e','d0cc53e5-c83d-45d5-bf4e-ef14ca703b77'),('089cbeda-af28-4c3a-b0e1-e77379f5d82e','d16f1f30-ee0f-4d31-9a9e-5896a9a108d9'),('089cbeda-af28-4c3a-b0e1-e77379f5d82e','db45abcb-6ba2-4f67-ac79-685177b323ae'),('089cbeda-af28-4c3a-b0e1-e77379f5d82e','df248b6d-25dd-4a0d-850f-86aa32203820'),('089cbeda-af28-4c3a-b0e1-e77379f5d82e','e5809fd7-a888-4565-b7f6-cc07d5e32381'),('089cbeda-af28-4c3a-b0e1-e77379f5d82e','e612a057-f268-4812-9183-e8fda70df1de'),('0fe8c5e1-0f93-49d7-bfb8-3938c89f6d7c','6dcbd598-4c43-4a47-bace-7cef7ad839ed'),('0fe8c5e1-0f93-49d7-bfb8-3938c89f6d7c','f10a55d3-3d28-48f7-b44a-cabcf4e99e5c'),('265b6035-0618-4f76-a050-044986a3b109','c398d42d-3391-49b4-82dd-b726df97ead3'),('4c54591f-c37c-4a8e-ae43-c8573609f6a5','757b7ed1-a8bd-471c-99e4-0108ed3d8aa6'),('4e721d0c-e399-4d03-8704-6cfb2d1421c0','755032b4-aba7-4b0b-ba5b-63cbd467091e'),('4e721d0c-e399-4d03-8704-6cfb2d1421c0','c8b182dc-ab23-4b7b-a5e6-eb546fadadca'),('739d4c36-7d49-4a50-9b3c-5ae23fbfdd94','b39f38f8-e322-40e9-834e-3d3246dd497d'),('8415c6ec-f054-4fe4-a5a5-e2ba97c43412','afcfa009-5a7e-4a77-8f72-4981e185b04e'),('8fdbfbe3-f4d5-426f-8db5-79360f6fb14a','189d957e-1809-4d79-b9b1-60e4c1e8bcf8'),('bff87df8-9dd0-42ef-892c-33e8267244a5','330cde7a-8620-4a9e-98d7-5987b69473a7'),('bff87df8-9dd0-42ef-892c-33e8267244a5','4c54591f-c37c-4a8e-ae43-c8573609f6a5'),('bff87df8-9dd0-42ef-892c-33e8267244a5','69bf757a-2809-4086-8c0d-757c5428db36'),('bff87df8-9dd0-42ef-892c-33e8267244a5','de4df032-d15c-4e80-b7cb-2916694a551d'),('c787b540-29f8-445a-bc0d-d07412be4658','0fe8c5e1-0f93-49d7-bfb8-3938c89f6d7c'),('c787b540-29f8-445a-bc0d-d07412be4658','1239c49e-f23c-4b54-b29c-444da3a0e256'),('c787b540-29f8-445a-bc0d-d07412be4658','157b6bdc-877f-4e55-8520-dfcfcb23e4a3'),('c787b540-29f8-445a-bc0d-d07412be4658','189d957e-1809-4d79-b9b1-60e4c1e8bcf8'),('c787b540-29f8-445a-bc0d-d07412be4658','23626911-39f5-44b1-9c71-ce8186c6c4e1'),('c787b540-29f8-445a-bc0d-d07412be4658','2a1cb718-6bcd-4514-8606-b1ffb9a241f9'),('c787b540-29f8-445a-bc0d-d07412be4658','3191fcc2-8776-400f-ac9d-b7ca5d9f0b30'),('c787b540-29f8-445a-bc0d-d07412be4658','3418dfb6-b6b6-4744-938c-6de5938d12ed'),('c787b540-29f8-445a-bc0d-d07412be4658','365b4a11-6ac9-4e62-bc39-4f14760a5a7c'),('c787b540-29f8-445a-bc0d-d07412be4658','46eff922-acb9-49c4-82e5-64345fcd264c'),('c787b540-29f8-445a-bc0d-d07412be4658','4e721d0c-e399-4d03-8704-6cfb2d1421c0'),('c787b540-29f8-445a-bc0d-d07412be4658','4f88f8c5-8bb0-4698-99dc-48d89d00082d'),('c787b540-29f8-445a-bc0d-d07412be4658','5602e35f-5870-4380-975c-92798e14390e'),('c787b540-29f8-445a-bc0d-d07412be4658','6a0e2da3-f172-4fc0-9ac0-e16c2015a4f4'),('c787b540-29f8-445a-bc0d-d07412be4658','6dc86259-1b7a-4ade-8947-b124889bf08f'),('c787b540-29f8-445a-bc0d-d07412be4658','6dcbd598-4c43-4a47-bace-7cef7ad839ed'),('c787b540-29f8-445a-bc0d-d07412be4658','6f240ff5-8abf-4e85-b720-f54292d0a9b7'),('c787b540-29f8-445a-bc0d-d07412be4658','755032b4-aba7-4b0b-ba5b-63cbd467091e'),('c787b540-29f8-445a-bc0d-d07412be4658','8415c6ec-f054-4fe4-a5a5-e2ba97c43412'),('c787b540-29f8-445a-bc0d-d07412be4658','8b3a6803-dfea-4279-b6c3-18d2a012bbc9'),('c787b540-29f8-445a-bc0d-d07412be4658','8fdbfbe3-f4d5-426f-8db5-79360f6fb14a'),('c787b540-29f8-445a-bc0d-d07412be4658','914e1aac-ce75-4bd7-9a06-dcffa62103db'),('c787b540-29f8-445a-bc0d-d07412be4658','9bfa0632-f779-47c6-bdf0-edb79173eb0c'),('c787b540-29f8-445a-bc0d-d07412be4658','a728a384-0234-4a65-ab06-779c089e3dfd'),('c787b540-29f8-445a-bc0d-d07412be4658','aed89732-bf03-4679-8858-90ff50dc9c65'),('c787b540-29f8-445a-bc0d-d07412be4658','afcfa009-5a7e-4a77-8f72-4981e185b04e'),('c787b540-29f8-445a-bc0d-d07412be4658','b194e1a7-d04c-4478-8d33-31b82304233d'),('c787b540-29f8-445a-bc0d-d07412be4658','ba0008a6-aba7-4bf1-960b-93ee33b5a03e'),('c787b540-29f8-445a-bc0d-d07412be4658','c8b182dc-ab23-4b7b-a5e6-eb546fadadca'),('c787b540-29f8-445a-bc0d-d07412be4658','df5cfea1-8355-438a-8570-a009c7817911'),('c787b540-29f8-445a-bc0d-d07412be4658','ed4a78dd-65d2-4bcc-b1b5-0e3e17318f74'),('c787b540-29f8-445a-bc0d-d07412be4658','eebc1457-de46-49ba-abba-a76ae2a6e0ec'),('c787b540-29f8-445a-bc0d-d07412be4658','ef43ee72-7603-48e1-9456-cd3e2014f66a'),('c787b540-29f8-445a-bc0d-d07412be4658','f0d7109e-8757-4f49-96ea-f27599448043'),('c787b540-29f8-445a-bc0d-d07412be4658','f10a55d3-3d28-48f7-b44a-cabcf4e99e5c'),('c787b540-29f8-445a-bc0d-d07412be4658','faa2ba5e-2200-4600-b3ad-f3a84cb0bf24'),('c787b540-29f8-445a-bc0d-d07412be4658','fc14b5be-de8a-43ae-a31d-318cefd9e11f'),('cb1cffd1-f807-4642-90c5-a2f9481f7a08','b0376f21-a1e3-4a60-aae3-aa985f56cf6e'),('d0cc53e5-c83d-45d5-bf4e-ef14ca703b77','e5809fd7-a888-4565-b7f6-cc07d5e32381'),('df248b6d-25dd-4a0d-850f-86aa32203820','4749ecd0-7f22-47e9-b3bc-d5bcfb9f11e0'),('df248b6d-25dd-4a0d-850f-86aa32203820','c7f5033f-d308-403a-88ba-8076610ed85a'),('f3e73123-f39a-4284-a4d8-cee68cd5e898','26b9da60-3c69-4bc1-b630-0fbce0079614'),('f3e73123-f39a-4284-a4d8-cee68cd5e898','67d35d03-c1cb-414f-935e-b026084ce8cd'),('f3e73123-f39a-4284-a4d8-cee68cd5e898','931dacd8-0f69-447f-b4c4-287e53330216'),('f3e73123-f39a-4284-a4d8-cee68cd5e898','b09cea9f-6a62-4ae0-b12d-7e1514a81794'),('f3e73123-f39a-4284-a4d8-cee68cd5e898','cb1cffd1-f807-4642-90c5-a2f9481f7a08');
/*!40000 ALTER TABLE `COMPOSITE_ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CREDENTIAL`
--

DROP TABLE IF EXISTS `CREDENTIAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CREDENTIAL` (
                              `ID` varchar(36) NOT NULL,
                              `SALT` tinyblob,
                              `TYPE` varchar(255) DEFAULT NULL,
                              `USER_ID` varchar(36) DEFAULT NULL,
                              `CREATED_DATE` bigint DEFAULT NULL,
                              `USER_LABEL` varchar(255) DEFAULT NULL,
                              `SECRET_DATA` longtext,
                              `CREDENTIAL_DATA` longtext,
                              `PRIORITY` int DEFAULT NULL,
                              PRIMARY KEY (`ID`),
                              KEY `IDX_USER_CREDENTIAL` (`USER_ID`),
                              CONSTRAINT `FK_PFYR0GLASQYL0DEI3KL69R6V0` FOREIGN KEY (`USER_ID`) REFERENCES `USER_ENTITY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CREDENTIAL`
--

LOCK TABLES `CREDENTIAL` WRITE;
/*!40000 ALTER TABLE `CREDENTIAL` DISABLE KEYS */;
INSERT INTO `CREDENTIAL` VALUES ('5903a5a3-735d-4e65-8d64-2e337d7f49e5',NULL,'password','9c11662d-04ce-458f-97dd-659de65808d7',1694193968829,NULL,'{\"value\":\"jboSCOH5cuCtwPyJPu711euz86Jp8YatTWMEu08FZnk=\",\"salt\":\"UQH9cJ/mhgjRwHrgzyB+tw==\",\"additionalParameters\":{}}','{\"hashIterations\":27500,\"algorithm\":\"pbkdf2-sha256\",\"additionalParameters\":{}}',10),('7c335ff5-8b56-4d3b-b530-55d75f476a3a',NULL,'password','3f119e2a-a6b8-408d-8885-9f0869fc84ee',1694194006261,NULL,'{\"value\":\"tqoQ3l6FOBQ82NVdcYqNttFDpYTHScMmzGYvBYnPgFA=\",\"salt\":\"nOBKjyY4yrLjL9kAq3M0Ug==\",\"additionalParameters\":{}}','{\"hashIterations\":27500,\"algorithm\":\"pbkdf2-sha256\",\"additionalParameters\":{}}',10);
/*!40000 ALTER TABLE `CREDENTIAL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DATABASECHANGELOG`
--

DROP TABLE IF EXISTS `DATABASECHANGELOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DATABASECHANGELOG` (
                                     `ID` varchar(255) NOT NULL,
                                     `AUTHOR` varchar(255) NOT NULL,
                                     `FILENAME` varchar(255) NOT NULL,
                                     `DATEEXECUTED` datetime NOT NULL,
                                     `ORDEREXECUTED` int NOT NULL,
                                     `EXECTYPE` varchar(10) NOT NULL,
                                     `MD5SUM` varchar(35) DEFAULT NULL,
                                     `DESCRIPTION` varchar(255) DEFAULT NULL,
                                     `COMMENTS` varchar(255) DEFAULT NULL,
                                     `TAG` varchar(255) DEFAULT NULL,
                                     `LIQUIBASE` varchar(20) DEFAULT NULL,
                                     `CONTEXTS` varchar(255) DEFAULT NULL,
                                     `LABELS` varchar(255) DEFAULT NULL,
                                     `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOG`
--

LOCK TABLES `DATABASECHANGELOG` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOG` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOG` VALUES ('1.0.0.Final-KEYCLOAK-5461','sthorger@redhat.com','META-INF/jpa-changelog-1.0.0.Final.xml','2023-09-08 17:17:34',1,'EXECUTED','8:bda77d94bf90182a1e30c24f1c155ec7','createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.0.0.Final-KEYCLOAK-5461','sthorger@redhat.com','META-INF/db2-jpa-changelog-1.0.0.Final.xml','2023-09-08 17:17:34',2,'MARK_RAN','8:1ecb330f30986693d1cba9ab579fa219','createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.1.0.Beta1','sthorger@redhat.com','META-INF/jpa-changelog-1.1.0.Beta1.xml','2023-09-08 17:17:44',3,'EXECUTED','8:cb7ace19bc6d959f305605d255d4c843','delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=CLIENT_ATTRIBUTES; createTable tableName=CLIENT_SESSION_NOTE; createTable tableName=APP_NODE_REGISTRATIONS; addColumn table...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.1.0.Final','sthorger@redhat.com','META-INF/jpa-changelog-1.1.0.Final.xml','2023-09-08 17:17:45',4,'EXECUTED','8:80230013e961310e6872e871be424a63','renameColumn newColumnName=EVENT_TIME, oldColumnName=TIME, tableName=EVENT_ENTITY','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.2.0.Beta1','psilva@redhat.com','META-INF/jpa-changelog-1.2.0.Beta1.xml','2023-09-08 17:18:22',5,'EXECUTED','8:67f4c20929126adc0c8e9bf48279d244','delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.2.0.Beta1','psilva@redhat.com','META-INF/db2-jpa-changelog-1.2.0.Beta1.xml','2023-09-08 17:18:22',6,'MARK_RAN','8:7311018b0b8179ce14628ab412bb6783','delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.2.0.RC1','bburke@redhat.com','META-INF/jpa-changelog-1.2.0.CR1.xml','2023-09-08 17:19:01',7,'EXECUTED','8:037ba1216c3640f8785ee6b8e7c8e3c1','delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.2.0.RC1','bburke@redhat.com','META-INF/db2-jpa-changelog-1.2.0.CR1.xml','2023-09-08 17:19:01',8,'MARK_RAN','8:7fe6ffe4af4df289b3157de32c624263','delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.2.0.Final','keycloak','META-INF/jpa-changelog-1.2.0.Final.xml','2023-09-08 17:19:01',9,'EXECUTED','8:9c136bc3187083a98745c7d03bc8a303','update tableName=CLIENT; update tableName=CLIENT; update tableName=CLIENT','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.3.0','bburke@redhat.com','META-INF/jpa-changelog-1.3.0.xml','2023-09-08 17:19:36',10,'EXECUTED','8:b5f09474dca81fb56a97cf5b6553d331','delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=ADMI...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.4.0','bburke@redhat.com','META-INF/jpa-changelog-1.4.0.xml','2023-09-08 17:19:49',11,'EXECUTED','8:ca924f31bd2a3b219fdcfe78c82dacf4','delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.4.0','bburke@redhat.com','META-INF/db2-jpa-changelog-1.4.0.xml','2023-09-08 17:19:49',12,'MARK_RAN','8:8acad7483e106416bcfa6f3b824a16cd','delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.5.0','bburke@redhat.com','META-INF/jpa-changelog-1.5.0.xml','2023-09-08 17:19:52',13,'EXECUTED','8:9b1266d17f4f87c78226f5055408fd5e','delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.6.1_from15','mposolda@redhat.com','META-INF/jpa-changelog-1.6.1.xml','2023-09-08 17:19:56',14,'EXECUTED','8:d80ec4ab6dbfe573550ff72396c7e910','addColumn tableName=REALM; addColumn tableName=KEYCLOAK_ROLE; addColumn tableName=CLIENT; createTable tableName=OFFLINE_USER_SESSION; createTable tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_US_SES_PK2, tableName=...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.6.1_from16-pre','mposolda@redhat.com','META-INF/jpa-changelog-1.6.1.xml','2023-09-08 17:19:57',15,'MARK_RAN','8:d86eb172171e7c20b9c849b584d147b2','delete tableName=OFFLINE_CLIENT_SESSION; delete tableName=OFFLINE_USER_SESSION','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.6.1_from16','mposolda@redhat.com','META-INF/jpa-changelog-1.6.1.xml','2023-09-08 17:19:57',16,'MARK_RAN','8:5735f46f0fa60689deb0ecdc2a0dea22','dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_US_SES_PK, tableName=OFFLINE_USER_SESSION; dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_CL_SES_PK, tableName=OFFLINE_CLIENT_SESSION; addColumn tableName=OFFLINE_USER_SESSION; update tableName=OF...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.6.1','mposolda@redhat.com','META-INF/jpa-changelog-1.6.1.xml','2023-09-08 17:19:57',17,'EXECUTED','8:d41d8cd98f00b204e9800998ecf8427e','empty','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.7.0','bburke@redhat.com','META-INF/jpa-changelog-1.7.0.xml','2023-09-08 17:20:19',18,'EXECUTED','8:5c1a8fd2014ac7fc43b90a700f117b23','createTable tableName=KEYCLOAK_GROUP; createTable tableName=GROUP_ROLE_MAPPING; createTable tableName=GROUP_ATTRIBUTE; createTable tableName=USER_GROUP_MEMBERSHIP; createTable tableName=REALM_DEFAULT_GROUPS; addColumn tableName=IDENTITY_PROVIDER; ...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.8.0','mposolda@redhat.com','META-INF/jpa-changelog-1.8.0.xml','2023-09-08 17:20:41',19,'EXECUTED','8:1f6c2c2dfc362aff4ed75b3f0ef6b331','addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.8.0-2','keycloak','META-INF/jpa-changelog-1.8.0.xml','2023-09-08 17:20:41',20,'EXECUTED','8:dee9246280915712591f83a127665107','dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.8.0','mposolda@redhat.com','META-INF/db2-jpa-changelog-1.8.0.xml','2023-09-08 17:20:41',21,'MARK_RAN','8:9eb2ee1fa8ad1c5e426421a6f8fdfa6a','addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.8.0-2','keycloak','META-INF/db2-jpa-changelog-1.8.0.xml','2023-09-08 17:20:41',22,'MARK_RAN','8:dee9246280915712591f83a127665107','dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.9.0','mposolda@redhat.com','META-INF/jpa-changelog-1.9.0.xml','2023-09-08 17:20:45',23,'EXECUTED','8:d9fa18ffa355320395b86270680dd4fe','update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=REALM; update tableName=REALM; customChange; dr...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.9.1','keycloak','META-INF/jpa-changelog-1.9.1.xml','2023-09-08 17:20:46',24,'EXECUTED','8:90cff506fedb06141ffc1c71c4a1214c','modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=PUBLIC_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.9.1','keycloak','META-INF/db2-jpa-changelog-1.9.1.xml','2023-09-08 17:20:46',25,'MARK_RAN','8:11a788aed4961d6d29c427c063af828c','modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM','',NULL,'4.20.0',NULL,NULL,'4193367813'),('1.9.2','keycloak','META-INF/jpa-changelog-1.9.2.xml','2023-09-08 17:20:52',26,'EXECUTED','8:a4218e51e1faf380518cce2af5d39b43','createIndex indexName=IDX_USER_EMAIL, tableName=USER_ENTITY; createIndex indexName=IDX_USER_ROLE_MAPPING, tableName=USER_ROLE_MAPPING; createIndex indexName=IDX_USER_GROUP_MAPPING, tableName=USER_GROUP_MEMBERSHIP; createIndex indexName=IDX_USER_CO...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('authz-2.0.0','psilva@redhat.com','META-INF/jpa-changelog-authz-2.0.0.xml','2023-09-08 17:21:25',27,'EXECUTED','8:d9e9a1bfaa644da9952456050f07bbdc','createTable tableName=RESOURCE_SERVER; addPrimaryKey constraintName=CONSTRAINT_FARS, tableName=RESOURCE_SERVER; addUniqueConstraint constraintName=UK_AU8TT6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER; createTable tableName=RESOURCE_SERVER_RESOU...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('authz-2.5.1','psilva@redhat.com','META-INF/jpa-changelog-authz-2.5.1.xml','2023-09-08 17:21:25',28,'EXECUTED','8:d1bf991a6163c0acbfe664b615314505','update tableName=RESOURCE_SERVER_POLICY','',NULL,'4.20.0',NULL,NULL,'4193367813'),('2.1.0-KEYCLOAK-5461','bburke@redhat.com','META-INF/jpa-changelog-2.1.0.xml','2023-09-08 17:21:45',29,'EXECUTED','8:88a743a1e87ec5e30bf603da68058a8c','createTable tableName=BROKER_LINK; createTable tableName=FED_USER_ATTRIBUTE; createTable tableName=FED_USER_CONSENT; createTable tableName=FED_USER_CONSENT_ROLE; createTable tableName=FED_USER_CONSENT_PROT_MAPPER; createTable tableName=FED_USER_CR...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('2.2.0','bburke@redhat.com','META-INF/jpa-changelog-2.2.0.xml','2023-09-08 17:21:51',30,'EXECUTED','8:c5517863c875d325dea463d00ec26d7a','addColumn tableName=ADMIN_EVENT_ENTITY; createTable tableName=CREDENTIAL_ATTRIBUTE; createTable tableName=FED_CREDENTIAL_ATTRIBUTE; modifyDataType columnName=VALUE, tableName=CREDENTIAL; addForeignKeyConstraint baseTableName=FED_CREDENTIAL_ATTRIBU...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('2.3.0','bburke@redhat.com','META-INF/jpa-changelog-2.3.0.xml','2023-09-08 17:21:57',31,'EXECUTED','8:ada8b4833b74a498f376d7136bc7d327','createTable tableName=FEDERATED_USER; addPrimaryKey constraintName=CONSTR_FEDERATED_USER, tableName=FEDERATED_USER; dropDefaultValue columnName=TOTP, tableName=USER_ENTITY; dropColumn columnName=TOTP, tableName=USER_ENTITY; addColumn tableName=IDE...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('2.4.0','bburke@redhat.com','META-INF/jpa-changelog-2.4.0.xml','2023-09-08 17:21:57',32,'EXECUTED','8:b9b73c8ea7299457f99fcbb825c263ba','customChange','',NULL,'4.20.0',NULL,NULL,'4193367813'),('2.5.0','bburke@redhat.com','META-INF/jpa-changelog-2.5.0.xml','2023-09-08 17:21:59',33,'EXECUTED','8:07724333e625ccfcfc5adc63d57314f3','customChange; modifyDataType columnName=USER_ID, tableName=OFFLINE_USER_SESSION','',NULL,'4.20.0',NULL,NULL,'4193367813'),('2.5.0-unicode-oracle','hmlnarik@redhat.com','META-INF/jpa-changelog-2.5.0.xml','2023-09-08 17:21:59',34,'MARK_RAN','8:8b6fd445958882efe55deb26fc541a7b','modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('2.5.0-unicode-other-dbs','hmlnarik@redhat.com','META-INF/jpa-changelog-2.5.0.xml','2023-09-08 17:22:08',35,'EXECUTED','8:29b29cfebfd12600897680147277a9d7','modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('2.5.0-duplicate-email-support','slawomir@dabek.name','META-INF/jpa-changelog-2.5.0.xml','2023-09-08 17:22:09',36,'EXECUTED','8:73ad77ca8fd0410c7f9f15a471fa52bc','addColumn tableName=REALM','',NULL,'4.20.0',NULL,NULL,'4193367813'),('2.5.0-unique-group-names','hmlnarik@redhat.com','META-INF/jpa-changelog-2.5.0.xml','2023-09-08 17:22:10',37,'EXECUTED','8:64f27a6fdcad57f6f9153210f2ec1bdb','addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP','',NULL,'4.20.0',NULL,NULL,'4193367813'),('2.5.1','bburke@redhat.com','META-INF/jpa-changelog-2.5.1.xml','2023-09-08 17:22:10',38,'EXECUTED','8:27180251182e6c31846c2ddab4bc5781','addColumn tableName=FED_USER_CONSENT','',NULL,'4.20.0',NULL,NULL,'4193367813'),('3.0.0','bburke@redhat.com','META-INF/jpa-changelog-3.0.0.xml','2023-09-08 17:22:11',39,'EXECUTED','8:d56f201bfcfa7a1413eb3e9bc02978f9','addColumn tableName=IDENTITY_PROVIDER','',NULL,'4.20.0',NULL,NULL,'4193367813'),('3.2.0-fix','keycloak','META-INF/jpa-changelog-3.2.0.xml','2023-09-08 17:22:11',40,'MARK_RAN','8:91f5522bf6afdc2077dfab57fbd3455c','addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS','',NULL,'4.20.0',NULL,NULL,'4193367813'),('3.2.0-fix-with-keycloak-5416','keycloak','META-INF/jpa-changelog-3.2.0.xml','2023-09-08 17:22:11',41,'MARK_RAN','8:0f01b554f256c22caeb7d8aee3a1cdc8','dropIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS; addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS; createIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS','',NULL,'4.20.0',NULL,NULL,'4193367813'),('3.2.0-fix-offline-sessions','hmlnarik','META-INF/jpa-changelog-3.2.0.xml','2023-09-08 17:22:11',42,'EXECUTED','8:ab91cf9cee415867ade0e2df9651a947','customChange','',NULL,'4.20.0',NULL,NULL,'4193367813'),('3.2.0-fixed','keycloak','META-INF/jpa-changelog-3.2.0.xml','2023-09-08 17:22:45',43,'EXECUTED','8:ceac9b1889e97d602caf373eadb0d4b7','addColumn tableName=REALM; dropPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_PK2, tableName=OFFLINE_CLIENT_SESSION; dropColumn columnName=CLIENT_SESSION_ID, tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_P...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('3.3.0','keycloak','META-INF/jpa-changelog-3.3.0.xml','2023-09-08 17:22:46',44,'EXECUTED','8:84b986e628fe8f7fd8fd3c275c5259f2','addColumn tableName=USER_ENTITY','',NULL,'4.20.0',NULL,NULL,'4193367813'),('authz-3.4.0.CR1-resource-server-pk-change-part1','glavoie@gmail.com','META-INF/jpa-changelog-authz-3.4.0.CR1.xml','2023-09-08 17:22:47',45,'EXECUTED','8:a164ae073c56ffdbc98a615493609a52','addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_RESOURCE; addColumn tableName=RESOURCE_SERVER_SCOPE','',NULL,'4.20.0',NULL,NULL,'4193367813'),('authz-3.4.0.CR1-resource-server-pk-change-part2-KEYCLOAK-6095','hmlnarik@redhat.com','META-INF/jpa-changelog-authz-3.4.0.CR1.xml','2023-09-08 17:22:47',46,'EXECUTED','8:70a2b4f1f4bd4dbf487114bdb1810e64','customChange','',NULL,'4.20.0',NULL,NULL,'4193367813'),('authz-3.4.0.CR1-resource-server-pk-change-part3-fixed','glavoie@gmail.com','META-INF/jpa-changelog-authz-3.4.0.CR1.xml','2023-09-08 17:22:47',47,'MARK_RAN','8:7be68b71d2f5b94b8df2e824f2860fa2','dropIndex indexName=IDX_RES_SERV_POL_RES_SERV, tableName=RESOURCE_SERVER_POLICY; dropIndex indexName=IDX_RES_SRV_RES_RES_SRV, tableName=RESOURCE_SERVER_RESOURCE; dropIndex indexName=IDX_RES_SRV_SCOPE_RES_SRV, tableName=RESOURCE_SERVER_SCOPE','',NULL,'4.20.0',NULL,NULL,'4193367813'),('authz-3.4.0.CR1-resource-server-pk-change-part3-fixed-nodropindex','glavoie@gmail.com','META-INF/jpa-changelog-authz-3.4.0.CR1.xml','2023-09-08 17:23:12',48,'EXECUTED','8:bab7c631093c3861d6cf6144cd944982','addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_POLICY; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_RESOURCE; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, ...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('authn-3.4.0.CR1-refresh-token-max-reuse','glavoie@gmail.com','META-INF/jpa-changelog-authz-3.4.0.CR1.xml','2023-09-08 17:23:12',49,'EXECUTED','8:fa809ac11877d74d76fe40869916daad','addColumn tableName=REALM','',NULL,'4.20.0',NULL,NULL,'4193367813'),('3.4.0','keycloak','META-INF/jpa-changelog-3.4.0.xml','2023-09-08 17:23:34',50,'EXECUTED','8:fac23540a40208f5f5e326f6ceb4d291','addPrimaryKey constraintName=CONSTRAINT_REALM_DEFAULT_ROLES, tableName=REALM_DEFAULT_ROLES; addPrimaryKey constraintName=CONSTRAINT_COMPOSITE_ROLE, tableName=COMPOSITE_ROLE; addPrimaryKey constraintName=CONSTR_REALM_DEFAULT_GROUPS, tableName=REALM...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('3.4.0-KEYCLOAK-5230','hmlnarik@redhat.com','META-INF/jpa-changelog-3.4.0.xml','2023-09-08 17:23:39',51,'EXECUTED','8:2612d1b8a97e2b5588c346e817307593','createIndex indexName=IDX_FU_ATTRIBUTE, tableName=FED_USER_ATTRIBUTE; createIndex indexName=IDX_FU_CONSENT, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CONSENT_RU, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CREDENTIAL, t...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('3.4.1','psilva@redhat.com','META-INF/jpa-changelog-3.4.1.xml','2023-09-08 17:23:39',52,'EXECUTED','8:9842f155c5db2206c88bcb5d1046e941','modifyDataType columnName=VALUE, tableName=CLIENT_ATTRIBUTES','',NULL,'4.20.0',NULL,NULL,'4193367813'),('3.4.2','keycloak','META-INF/jpa-changelog-3.4.2.xml','2023-09-08 17:23:39',53,'EXECUTED','8:2e12e06e45498406db72d5b3da5bbc76','update tableName=REALM','',NULL,'4.20.0',NULL,NULL,'4193367813'),('3.4.2-KEYCLOAK-5172','mkanis@redhat.com','META-INF/jpa-changelog-3.4.2.xml','2023-09-08 17:23:39',54,'EXECUTED','8:33560e7c7989250c40da3abdabdc75a4','update tableName=CLIENT','',NULL,'4.20.0',NULL,NULL,'4193367813'),('4.0.0-KEYCLOAK-6335','bburke@redhat.com','META-INF/jpa-changelog-4.0.0.xml','2023-09-08 17:23:41',55,'EXECUTED','8:87a8d8542046817a9107c7eb9cbad1cd','createTable tableName=CLIENT_AUTH_FLOW_BINDINGS; addPrimaryKey constraintName=C_CLI_FLOW_BIND, tableName=CLIENT_AUTH_FLOW_BINDINGS','',NULL,'4.20.0',NULL,NULL,'4193367813'),('4.0.0-CLEANUP-UNUSED-TABLE','bburke@redhat.com','META-INF/jpa-changelog-4.0.0.xml','2023-09-08 17:23:42',56,'EXECUTED','8:3ea08490a70215ed0088c273d776311e','dropTable tableName=CLIENT_IDENTITY_PROV_MAPPING','',NULL,'4.20.0',NULL,NULL,'4193367813'),('4.0.0-KEYCLOAK-6228','bburke@redhat.com','META-INF/jpa-changelog-4.0.0.xml','2023-09-08 17:23:49',57,'EXECUTED','8:2d56697c8723d4592ab608ce14b6ed68','dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; dropNotNullConstraint columnName=CLIENT_ID, tableName=USER_CONSENT; addColumn tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHO...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('4.0.0-KEYCLOAK-5579-fixed','mposolda@redhat.com','META-INF/jpa-changelog-4.0.0.xml','2023-09-08 17:24:33',58,'EXECUTED','8:3e423e249f6068ea2bbe48bf907f9d86','dropForeignKeyConstraint baseTableName=CLIENT_TEMPLATE_ATTRIBUTES, constraintName=FK_CL_TEMPL_ATTR_TEMPL; renameTable newTableName=CLIENT_SCOPE_ATTRIBUTES, oldTableName=CLIENT_TEMPLATE_ATTRIBUTES; renameColumn newColumnName=SCOPE_ID, oldColumnName...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('authz-4.0.0.CR1','psilva@redhat.com','META-INF/jpa-changelog-authz-4.0.0.CR1.xml','2023-09-08 17:24:44',59,'EXECUTED','8:15cabee5e5df0ff099510a0fc03e4103','createTable tableName=RESOURCE_SERVER_PERM_TICKET; addPrimaryKey constraintName=CONSTRAINT_FAPMT, tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRHO213XCX4WNKOG82SSPMT...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('authz-4.0.0.Beta3','psilva@redhat.com','META-INF/jpa-changelog-authz-4.0.0.Beta3.xml','2023-09-08 17:24:46',60,'EXECUTED','8:4b80200af916ac54d2ffbfc47918ab0e','addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRPO2128CX4WNKOG82SSRFY, referencedTableName=RESOURCE_SERVER_POLICY','',NULL,'4.20.0',NULL,NULL,'4193367813'),('authz-4.2.0.Final','mhajas@redhat.com','META-INF/jpa-changelog-authz-4.2.0.Final.xml','2023-09-08 17:24:49',61,'EXECUTED','8:66564cd5e168045d52252c5027485bbb','createTable tableName=RESOURCE_URIS; addForeignKeyConstraint baseTableName=RESOURCE_URIS, constraintName=FK_RESOURCE_SERVER_URIS, referencedTableName=RESOURCE_SERVER_RESOURCE; customChange; dropColumn columnName=URI, tableName=RESOURCE_SERVER_RESO...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('authz-4.2.0.Final-KEYCLOAK-9944','hmlnarik@redhat.com','META-INF/jpa-changelog-authz-4.2.0.Final.xml','2023-09-08 17:24:50',62,'EXECUTED','8:1c7064fafb030222be2bd16ccf690f6f','addPrimaryKey constraintName=CONSTRAINT_RESOUR_URIS_PK, tableName=RESOURCE_URIS','',NULL,'4.20.0',NULL,NULL,'4193367813'),('4.2.0-KEYCLOAK-6313','wadahiro@gmail.com','META-INF/jpa-changelog-4.2.0.xml','2023-09-08 17:24:50',63,'EXECUTED','8:2de18a0dce10cdda5c7e65c9b719b6e5','addColumn tableName=REQUIRED_ACTION_PROVIDER','',NULL,'4.20.0',NULL,NULL,'4193367813'),('4.3.0-KEYCLOAK-7984','wadahiro@gmail.com','META-INF/jpa-changelog-4.3.0.xml','2023-09-08 17:24:50',64,'EXECUTED','8:03e413dd182dcbd5c57e41c34d0ef682','update tableName=REQUIRED_ACTION_PROVIDER','',NULL,'4.20.0',NULL,NULL,'4193367813'),('4.6.0-KEYCLOAK-7950','psilva@redhat.com','META-INF/jpa-changelog-4.6.0.xml','2023-09-08 17:24:51',65,'EXECUTED','8:d27b42bb2571c18fbe3fe4e4fb7582a7','update tableName=RESOURCE_SERVER_RESOURCE','',NULL,'4.20.0',NULL,NULL,'4193367813'),('4.6.0-KEYCLOAK-8377','keycloak','META-INF/jpa-changelog-4.6.0.xml','2023-09-08 17:24:54',66,'EXECUTED','8:698baf84d9fd0027e9192717c2154fb8','createTable tableName=ROLE_ATTRIBUTE; addPrimaryKey constraintName=CONSTRAINT_ROLE_ATTRIBUTE_PK, tableName=ROLE_ATTRIBUTE; addForeignKeyConstraint baseTableName=ROLE_ATTRIBUTE, constraintName=FK_ROLE_ATTRIBUTE_ID, referencedTableName=KEYCLOAK_ROLE...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('4.6.0-KEYCLOAK-8555','gideonray@gmail.com','META-INF/jpa-changelog-4.6.0.xml','2023-09-08 17:24:55',67,'EXECUTED','8:ced8822edf0f75ef26eb51582f9a821a','createIndex indexName=IDX_COMPONENT_PROVIDER_TYPE, tableName=COMPONENT','',NULL,'4.20.0',NULL,NULL,'4193367813'),('4.7.0-KEYCLOAK-1267','sguilhen@redhat.com','META-INF/jpa-changelog-4.7.0.xml','2023-09-08 17:24:55',68,'EXECUTED','8:f0abba004cf429e8afc43056df06487d','addColumn tableName=REALM','',NULL,'4.20.0',NULL,NULL,'4193367813'),('4.7.0-KEYCLOAK-7275','keycloak','META-INF/jpa-changelog-4.7.0.xml','2023-09-08 17:24:58',69,'EXECUTED','8:6662f8b0b611caa359fcf13bf63b4e24','renameColumn newColumnName=CREATED_ON, oldColumnName=LAST_SESSION_REFRESH, tableName=OFFLINE_USER_SESSION; addNotNullConstraint columnName=CREATED_ON, tableName=OFFLINE_USER_SESSION; addColumn tableName=OFFLINE_USER_SESSION; customChange; createIn...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('4.8.0-KEYCLOAK-8835','sguilhen@redhat.com','META-INF/jpa-changelog-4.8.0.xml','2023-09-08 17:25:00',70,'EXECUTED','8:9e6b8009560f684250bdbdf97670d39e','addNotNullConstraint columnName=SSO_MAX_LIFESPAN_REMEMBER_ME, tableName=REALM; addNotNullConstraint columnName=SSO_IDLE_TIMEOUT_REMEMBER_ME, tableName=REALM','',NULL,'4.20.0',NULL,NULL,'4193367813'),('authz-7.0.0-KEYCLOAK-10443','psilva@redhat.com','META-INF/jpa-changelog-authz-7.0.0.xml','2023-09-08 17:25:01',71,'EXECUTED','8:4223f561f3b8dc655846562b57bb502e','addColumn tableName=RESOURCE_SERVER','',NULL,'4.20.0',NULL,NULL,'4193367813'),('8.0.0-adding-credential-columns','keycloak','META-INF/jpa-changelog-8.0.0.xml','2023-09-08 17:25:02',72,'EXECUTED','8:215a31c398b363ce383a2b301202f29e','addColumn tableName=CREDENTIAL; addColumn tableName=FED_USER_CREDENTIAL','',NULL,'4.20.0',NULL,NULL,'4193367813'),('8.0.0-updating-credential-data-not-oracle-fixed','keycloak','META-INF/jpa-changelog-8.0.0.xml','2023-09-08 17:25:02',73,'EXECUTED','8:83f7a671792ca98b3cbd3a1a34862d3d','update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL','',NULL,'4.20.0',NULL,NULL,'4193367813'),('8.0.0-updating-credential-data-oracle-fixed','keycloak','META-INF/jpa-changelog-8.0.0.xml','2023-09-08 17:25:02',74,'MARK_RAN','8:f58ad148698cf30707a6efbdf8061aa7','update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL','',NULL,'4.20.0',NULL,NULL,'4193367813'),('8.0.0-credential-cleanup-fixed','keycloak','META-INF/jpa-changelog-8.0.0.xml','2023-09-08 17:25:11',75,'EXECUTED','8:79e4fd6c6442980e58d52ffc3ee7b19c','dropDefaultValue columnName=COUNTER, tableName=CREDENTIAL; dropDefaultValue columnName=DIGITS, tableName=CREDENTIAL; dropDefaultValue columnName=PERIOD, tableName=CREDENTIAL; dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; dropColumn ...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('8.0.0-resource-tag-support','keycloak','META-INF/jpa-changelog-8.0.0.xml','2023-09-08 17:25:11',76,'EXECUTED','8:87af6a1e6d241ca4b15801d1f86a297d','addColumn tableName=MIGRATION_MODEL; createIndex indexName=IDX_UPDATE_TIME, tableName=MIGRATION_MODEL','',NULL,'4.20.0',NULL,NULL,'4193367813'),('9.0.0-always-display-client','keycloak','META-INF/jpa-changelog-9.0.0.xml','2023-09-08 17:25:12',77,'EXECUTED','8:b44f8d9b7b6ea455305a6d72a200ed15','addColumn tableName=CLIENT','',NULL,'4.20.0',NULL,NULL,'4193367813'),('9.0.0-drop-constraints-for-column-increase','keycloak','META-INF/jpa-changelog-9.0.0.xml','2023-09-08 17:25:12',78,'MARK_RAN','8:2d8ed5aaaeffd0cb004c046b4a903ac5','dropUniqueConstraint constraintName=UK_FRSR6T700S9V50BU18WS5PMT, tableName=RESOURCE_SERVER_PERM_TICKET; dropUniqueConstraint constraintName=UK_FRSR6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER_RESOURCE; dropPrimaryKey constraintName=CONSTRAINT_O...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('9.0.0-increase-column-size-federated-fk','keycloak','META-INF/jpa-changelog-9.0.0.xml','2023-09-08 17:25:27',79,'EXECUTED','8:e290c01fcbc275326c511633f6e2acde','modifyDataType columnName=CLIENT_ID, tableName=FED_USER_CONSENT; modifyDataType columnName=CLIENT_REALM_CONSTRAINT, tableName=KEYCLOAK_ROLE; modifyDataType columnName=OWNER, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=CLIENT_ID, ta...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('9.0.0-recreate-constraints-after-column-increase','keycloak','META-INF/jpa-changelog-9.0.0.xml','2023-09-08 17:25:27',80,'MARK_RAN','8:c9db8784c33cea210872ac2d805439f8','addNotNullConstraint columnName=CLIENT_ID, tableName=OFFLINE_CLIENT_SESSION; addNotNullConstraint columnName=OWNER, tableName=RESOURCE_SERVER_PERM_TICKET; addNotNullConstraint columnName=REQUESTER, tableName=RESOURCE_SERVER_PERM_TICKET; addNotNull...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('9.0.1-add-index-to-client.client_id','keycloak','META-INF/jpa-changelog-9.0.1.xml','2023-09-08 17:25:28',81,'EXECUTED','8:95b676ce8fc546a1fcfb4c92fae4add5','createIndex indexName=IDX_CLIENT_ID, tableName=CLIENT','',NULL,'4.20.0',NULL,NULL,'4193367813'),('9.0.1-KEYCLOAK-12579-drop-constraints','keycloak','META-INF/jpa-changelog-9.0.1.xml','2023-09-08 17:25:28',82,'MARK_RAN','8:38a6b2a41f5651018b1aca93a41401e5','dropUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP','',NULL,'4.20.0',NULL,NULL,'4193367813'),('9.0.1-KEYCLOAK-12579-add-not-null-constraint','keycloak','META-INF/jpa-changelog-9.0.1.xml','2023-09-08 17:25:29',83,'EXECUTED','8:3fb99bcad86a0229783123ac52f7609c','addNotNullConstraint columnName=PARENT_GROUP, tableName=KEYCLOAK_GROUP','',NULL,'4.20.0',NULL,NULL,'4193367813'),('9.0.1-KEYCLOAK-12579-recreate-constraints','keycloak','META-INF/jpa-changelog-9.0.1.xml','2023-09-08 17:25:29',84,'MARK_RAN','8:64f27a6fdcad57f6f9153210f2ec1bdb','addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP','',NULL,'4.20.0',NULL,NULL,'4193367813'),('9.0.1-add-index-to-events','keycloak','META-INF/jpa-changelog-9.0.1.xml','2023-09-08 17:25:30',85,'EXECUTED','8:ab4f863f39adafd4c862f7ec01890abc','createIndex indexName=IDX_EVENT_TIME, tableName=EVENT_ENTITY','',NULL,'4.20.0',NULL,NULL,'4193367813'),('map-remove-ri','keycloak','META-INF/jpa-changelog-11.0.0.xml','2023-09-08 17:25:30',86,'EXECUTED','8:13c419a0eb336e91ee3a3bf8fda6e2a7','dropForeignKeyConstraint baseTableName=REALM, constraintName=FK_TRAF444KK6QRKMS7N56AIWQ5Y; dropForeignKeyConstraint baseTableName=KEYCLOAK_ROLE, constraintName=FK_KJHO5LE2C0RAL09FL8CM9WFW9','',NULL,'4.20.0',NULL,NULL,'4193367813'),('map-remove-ri','keycloak','META-INF/jpa-changelog-12.0.0.xml','2023-09-08 17:25:31',87,'EXECUTED','8:e3fb1e698e0471487f51af1ed80fe3ac','dropForeignKeyConstraint baseTableName=REALM_DEFAULT_GROUPS, constraintName=FK_DEF_GROUPS_GROUP; dropForeignKeyConstraint baseTableName=REALM_DEFAULT_ROLES, constraintName=FK_H4WPD7W4HSOOLNI3H0SW7BTJE; dropForeignKeyConstraint baseTableName=CLIENT...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('12.1.0-add-realm-localization-table','keycloak','META-INF/jpa-changelog-12.0.0.xml','2023-09-08 17:25:33',88,'EXECUTED','8:babadb686aab7b56562817e60bf0abd0','createTable tableName=REALM_LOCALIZATIONS; addPrimaryKey tableName=REALM_LOCALIZATIONS','',NULL,'4.20.0',NULL,NULL,'4193367813'),('default-roles','keycloak','META-INF/jpa-changelog-13.0.0.xml','2023-09-08 17:25:33',89,'EXECUTED','8:72d03345fda8e2f17093d08801947773','addColumn tableName=REALM; customChange','',NULL,'4.20.0',NULL,NULL,'4193367813'),('default-roles-cleanup','keycloak','META-INF/jpa-changelog-13.0.0.xml','2023-09-08 17:25:34',90,'EXECUTED','8:61c9233951bd96ffecd9ba75f7d978a4','dropTable tableName=REALM_DEFAULT_ROLES; dropTable tableName=CLIENT_DEFAULT_ROLES','',NULL,'4.20.0',NULL,NULL,'4193367813'),('13.0.0-KEYCLOAK-16844','keycloak','META-INF/jpa-changelog-13.0.0.xml','2023-09-08 17:25:35',91,'EXECUTED','8:ea82e6ad945cec250af6372767b25525','createIndex indexName=IDX_OFFLINE_USS_PRELOAD, tableName=OFFLINE_USER_SESSION','',NULL,'4.20.0',NULL,NULL,'4193367813'),('map-remove-ri-13.0.0','keycloak','META-INF/jpa-changelog-13.0.0.xml','2023-09-08 17:25:36',92,'EXECUTED','8:d3f4a33f41d960ddacd7e2ef30d126b3','dropForeignKeyConstraint baseTableName=DEFAULT_CLIENT_SCOPE, constraintName=FK_R_DEF_CLI_SCOPE_SCOPE; dropForeignKeyConstraint baseTableName=CLIENT_SCOPE_CLIENT, constraintName=FK_C_CLI_SCOPE_SCOPE; dropForeignKeyConstraint baseTableName=CLIENT_SC...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('13.0.0-KEYCLOAK-17992-drop-constraints','keycloak','META-INF/jpa-changelog-13.0.0.xml','2023-09-08 17:25:36',93,'MARK_RAN','8:1284a27fbd049d65831cb6fc07c8a783','dropPrimaryKey constraintName=C_CLI_SCOPE_BIND, tableName=CLIENT_SCOPE_CLIENT; dropIndex indexName=IDX_CLSCOPE_CL, tableName=CLIENT_SCOPE_CLIENT; dropIndex indexName=IDX_CL_CLSCOPE, tableName=CLIENT_SCOPE_CLIENT','',NULL,'4.20.0',NULL,NULL,'4193367813'),('13.0.0-increase-column-size-federated','keycloak','META-INF/jpa-changelog-13.0.0.xml','2023-09-08 17:25:39',94,'EXECUTED','8:9d11b619db2ae27c25853b8a37cd0dea','modifyDataType columnName=CLIENT_ID, tableName=CLIENT_SCOPE_CLIENT; modifyDataType columnName=SCOPE_ID, tableName=CLIENT_SCOPE_CLIENT','',NULL,'4.20.0',NULL,NULL,'4193367813'),('13.0.0-KEYCLOAK-17992-recreate-constraints','keycloak','META-INF/jpa-changelog-13.0.0.xml','2023-09-08 17:25:39',95,'MARK_RAN','8:3002bb3997451bb9e8bac5c5cd8d6327','addNotNullConstraint columnName=CLIENT_ID, tableName=CLIENT_SCOPE_CLIENT; addNotNullConstraint columnName=SCOPE_ID, tableName=CLIENT_SCOPE_CLIENT; addPrimaryKey constraintName=C_CLI_SCOPE_BIND, tableName=CLIENT_SCOPE_CLIENT; createIndex indexName=...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('json-string-accomodation-fixed','keycloak','META-INF/jpa-changelog-13.0.0.xml','2023-09-08 17:25:41',96,'EXECUTED','8:dfbee0d6237a23ef4ccbb7a4e063c163','addColumn tableName=REALM_ATTRIBUTE; update tableName=REALM_ATTRIBUTE; dropColumn columnName=VALUE, tableName=REALM_ATTRIBUTE; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=REALM_ATTRIBUTE','',NULL,'4.20.0',NULL,NULL,'4193367813'),('14.0.0-KEYCLOAK-11019','keycloak','META-INF/jpa-changelog-14.0.0.xml','2023-09-08 17:25:42',97,'EXECUTED','8:75f3e372df18d38c62734eebb986b960','createIndex indexName=IDX_OFFLINE_CSS_PRELOAD, tableName=OFFLINE_CLIENT_SESSION; createIndex indexName=IDX_OFFLINE_USS_BY_USER, tableName=OFFLINE_USER_SESSION; createIndex indexName=IDX_OFFLINE_USS_BY_USERSESS, tableName=OFFLINE_USER_SESSION','',NULL,'4.20.0',NULL,NULL,'4193367813'),('14.0.0-KEYCLOAK-18286','keycloak','META-INF/jpa-changelog-14.0.0.xml','2023-09-08 17:25:42',98,'MARK_RAN','8:7fee73eddf84a6035691512c85637eef','createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES','',NULL,'4.20.0',NULL,NULL,'4193367813'),('14.0.0-KEYCLOAK-18286-revert','keycloak','META-INF/jpa-changelog-14.0.0.xml','2023-09-08 17:25:42',99,'MARK_RAN','8:7a11134ab12820f999fbf3bb13c3adc8','dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES','',NULL,'4.20.0',NULL,NULL,'4193367813'),('14.0.0-KEYCLOAK-18286-supported-dbs','keycloak','META-INF/jpa-changelog-14.0.0.xml','2023-09-08 17:25:43',100,'EXECUTED','8:f43dfba07ba249d5d932dc489fd2b886','createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES','',NULL,'4.20.0',NULL,NULL,'4193367813'),('14.0.0-KEYCLOAK-18286-unsupported-dbs','keycloak','META-INF/jpa-changelog-14.0.0.xml','2023-09-08 17:25:43',101,'MARK_RAN','8:18186f0008b86e0f0f49b0c4d0e842ac','createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES','',NULL,'4.20.0',NULL,NULL,'4193367813'),('KEYCLOAK-17267-add-index-to-user-attributes','keycloak','META-INF/jpa-changelog-14.0.0.xml','2023-09-08 17:25:43',102,'EXECUTED','8:09c2780bcb23b310a7019d217dc7b433','createIndex indexName=IDX_USER_ATTRIBUTE_NAME, tableName=USER_ATTRIBUTE','',NULL,'4.20.0',NULL,NULL,'4193367813'),('KEYCLOAK-18146-add-saml-art-binding-identifier','keycloak','META-INF/jpa-changelog-14.0.0.xml','2023-09-08 17:25:43',103,'EXECUTED','8:276a44955eab693c970a42880197fff2','customChange','',NULL,'4.20.0',NULL,NULL,'4193367813'),('15.0.0-KEYCLOAK-18467','keycloak','META-INF/jpa-changelog-15.0.0.xml','2023-09-08 17:25:46',104,'EXECUTED','8:ba8ee3b694d043f2bfc1a1079d0760d7','addColumn tableName=REALM_LOCALIZATIONS; update tableName=REALM_LOCALIZATIONS; dropColumn columnName=TEXTS, tableName=REALM_LOCALIZATIONS; renameColumn newColumnName=TEXTS, oldColumnName=TEXTS_NEW, tableName=REALM_LOCALIZATIONS; addNotNullConstrai...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('17.0.0-9562','keycloak','META-INF/jpa-changelog-17.0.0.xml','2023-09-08 17:25:46',105,'EXECUTED','8:5e06b1d75f5d17685485e610c2851b17','createIndex indexName=IDX_USER_SERVICE_ACCOUNT, tableName=USER_ENTITY','',NULL,'4.20.0',NULL,NULL,'4193367813'),('18.0.0-10625-IDX_ADMIN_EVENT_TIME','keycloak','META-INF/jpa-changelog-18.0.0.xml','2023-09-08 17:25:47',106,'EXECUTED','8:4b80546c1dc550ac552ee7b24a4ab7c0','createIndex indexName=IDX_ADMIN_EVENT_TIME, tableName=ADMIN_EVENT_ENTITY','',NULL,'4.20.0',NULL,NULL,'4193367813'),('19.0.0-10135','keycloak','META-INF/jpa-changelog-19.0.0.xml','2023-09-08 17:25:47',107,'EXECUTED','8:af510cd1bb2ab6339c45372f3e491696','customChange','',NULL,'4.20.0',NULL,NULL,'4193367813'),('20.0.0-12964-supported-dbs','keycloak','META-INF/jpa-changelog-20.0.0.xml','2023-09-08 17:25:48',108,'EXECUTED','8:d00f99ed899c4d2ae9117e282badbef5','createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE','',NULL,'4.20.0',NULL,NULL,'4193367813'),('20.0.0-12964-unsupported-dbs','keycloak','META-INF/jpa-changelog-20.0.0.xml','2023-09-08 17:25:48',109,'MARK_RAN','8:314e803baf2f1ec315b3464e398b8247','createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE','',NULL,'4.20.0',NULL,NULL,'4193367813'),('client-attributes-string-accomodation-fixed','keycloak','META-INF/jpa-changelog-20.0.0.xml','2023-09-08 17:25:51',110,'EXECUTED','8:56e4677e7e12556f70b604c573840100','addColumn tableName=CLIENT_ATTRIBUTES; update tableName=CLIENT_ATTRIBUTES; dropColumn columnName=VALUE, tableName=CLIENT_ATTRIBUTES; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=CLIENT_ATTRIBUTES','',NULL,'4.20.0',NULL,NULL,'4193367813'),('21.0.2-17277','keycloak','META-INF/jpa-changelog-21.0.2.xml','2023-09-08 17:25:51',111,'EXECUTED','8:8806cb33d2a546ce770384bf98cf6eac','customChange','',NULL,'4.20.0',NULL,NULL,'4193367813'),('21.1.0-19404','keycloak','META-INF/jpa-changelog-21.1.0.xml','2023-09-08 17:25:56',112,'EXECUTED','8:fdb2924649d30555ab3a1744faba4928','modifyDataType columnName=DECISION_STRATEGY, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=LOGIC, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=POLICY_ENFORCE_MODE, tableName=RESOURCE_SERVER','',NULL,'4.20.0',NULL,NULL,'4193367813'),('21.1.0-19404-2','keycloak','META-INF/jpa-changelog-21.1.0.xml','2023-09-08 17:25:56',113,'MARK_RAN','8:1c96cc2b10903bd07a03670098d67fd6','addColumn tableName=RESOURCE_SERVER_POLICY; update tableName=RESOURCE_SERVER_POLICY; dropColumn columnName=DECISION_STRATEGY, tableName=RESOURCE_SERVER_POLICY; renameColumn newColumnName=DECISION_STRATEGY, oldColumnName=DECISION_STRATEGY_NEW, tabl...','',NULL,'4.20.0',NULL,NULL,'4193367813'),('22.0.0-17484','keycloak','META-INF/jpa-changelog-22.0.0.xml','2023-09-08 17:25:57',114,'EXECUTED','8:4c3d4e8b142a66fcdf21b89a4dd33301','customChange','',NULL,'4.20.0',NULL,NULL,'4193367813');
/*!40000 ALTER TABLE `DATABASECHANGELOG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DATABASECHANGELOGLOCK`
--

DROP TABLE IF EXISTS `DATABASECHANGELOGLOCK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DATABASECHANGELOGLOCK` (
                                         `ID` int NOT NULL,
                                         `LOCKED` bit(1) NOT NULL,
                                         `LOCKGRANTED` datetime DEFAULT NULL,
                                         `LOCKEDBY` varchar(255) DEFAULT NULL,
                                         PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOGLOCK`
--

LOCK TABLES `DATABASECHANGELOGLOCK` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOGLOCK` VALUES (1,'\0',NULL,NULL),(1000,'\0',NULL,NULL),(1001,'\0',NULL,NULL);
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DEFAULT_CLIENT_SCOPE`
--

DROP TABLE IF EXISTS `DEFAULT_CLIENT_SCOPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DEFAULT_CLIENT_SCOPE` (
                                        `REALM_ID` varchar(36) NOT NULL,
                                        `SCOPE_ID` varchar(36) NOT NULL,
                                        `DEFAULT_SCOPE` bit(1) NOT NULL DEFAULT b'0',
                                        PRIMARY KEY (`REALM_ID`,`SCOPE_ID`),
                                        KEY `IDX_DEFCLS_REALM` (`REALM_ID`),
                                        KEY `IDX_DEFCLS_SCOPE` (`SCOPE_ID`),
                                        CONSTRAINT `FK_R_DEF_CLI_SCOPE_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DEFAULT_CLIENT_SCOPE`
--

LOCK TABLES `DEFAULT_CLIENT_SCOPE` WRITE;
/*!40000 ALTER TABLE `DEFAULT_CLIENT_SCOPE` DISABLE KEYS */;
INSERT INTO `DEFAULT_CLIENT_SCOPE` VALUES ('0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','0492c70e-dbb9-44fd-b926-36b2a480e707','\0'),('0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','09575535-6b7f-404f-8400-526497b4792a',''),('0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','0fa8d901-ba5d-45b6-a6ec-62461a8a8d07',''),('0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','3c466413-4163-4125-bf25-e85b9c2d9ad0','\0'),('0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','5a7014a9-93d9-43e0-9bc5-a389338dcf28',''),('0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','a07a0f75-6b5b-47e5-a788-0a7f56e8fada','\0'),('0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','c73b5dfb-50e1-4649-a20b-2cd7ceadeb54',''),('0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','dfd5755c-e278-4e23-ade7-d1516fc94a7d',''),('0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','e980a100-790b-48c9-b77c-413b2e8f8e2c','\0'),('0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','fe396b62-1b79-4557-9e57-6e0522bdf506',''),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','112981ef-8abb-4615-9064-11e6b03d12db',''),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','269709f9-050d-4363-b800-007a66bdc433','\0'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','2b91a3e9-3562-48c9-8464-c4d021b6a48d',''),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','2e4d99ab-3601-45d1-849e-b383678ec646','\0'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','32ffc623-cc8a-495c-8fba-61c36becefd0','\0'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','937da9b0-a93c-427b-b5ff-db162f988583',''),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','9f6f73b8-23d0-4cb4-8d8b-95c033287f0d',''),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','c1514a42-f14c-48a6-a441-b3cf107d7edc',''),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','c564f5be-0d07-4bd2-90b4-32b3452493cb','\0'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','ecfcd03c-f091-4d6f-80a1-0b69716ce804','');
/*!40000 ALTER TABLE `DEFAULT_CLIENT_SCOPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EVENT_ENTITY`
--

DROP TABLE IF EXISTS `EVENT_ENTITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EVENT_ENTITY` (
                                `ID` varchar(36) NOT NULL,
                                `CLIENT_ID` varchar(255) DEFAULT NULL,
                                `DETAILS_JSON` text,
                                `ERROR` varchar(255) DEFAULT NULL,
                                `IP_ADDRESS` varchar(255) DEFAULT NULL,
                                `REALM_ID` varchar(255) DEFAULT NULL,
                                `SESSION_ID` varchar(255) DEFAULT NULL,
                                `EVENT_TIME` bigint DEFAULT NULL,
                                `TYPE` varchar(255) DEFAULT NULL,
                                `USER_ID` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`ID`),
                                KEY `IDX_EVENT_TIME` (`REALM_ID`,`EVENT_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EVENT_ENTITY`
--

LOCK TABLES `EVENT_ENTITY` WRITE;
/*!40000 ALTER TABLE `EVENT_ENTITY` DISABLE KEYS */;
INSERT INTO `EVENT_ENTITY` VALUES ('06ae8f6b-2e7b-4f2d-98fe-5d828cdc8bec','dodgame-api','{\"token_id\":\"7d3424bd-9ee3-4913-baa0-f1c941a158b2\",\"grant_type\":\"authorization_code\",\"refresh_token_type\":\"Refresh\",\"scope\":\"openid email profile\",\"refresh_token_id\":\"82e88d94-725d-452a-90aa-0121f4c6e055\",\"code_id\":\"f421a8e1-06a6-4d01-adc1-97b0f5b03eab\",\"client_auth_method\":\"client-secret\"}',NULL,'192.168.112.1','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','f421a8e1-06a6-4d01-adc1-97b0f5b03eab',1694194146926,'CODE_TO_TOKEN','3f119e2a-a6b8-408d-8885-9f0869fc84ee'),('1445d5e2-c4d0-4a66-8e78-e55ed191a8de','dodgame-api','{\"auth_method\":\"openid-connect\",\"auth_type\":\"code\",\"response_type\":\"code\",\"redirect_uri\":\"http://ui:8081\",\"consent\":\"no_consent_required\",\"code_id\":\"f421a8e1-06a6-4d01-adc1-97b0f5b03eab\",\"response_mode\":\"query\",\"username\":\"msp\"}',NULL,'192.168.112.1','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','f421a8e1-06a6-4d01-adc1-97b0f5b03eab',1694194198707,'LOGIN','3f119e2a-a6b8-408d-8885-9f0869fc84ee'),('38f1d556-ccec-4d4e-bf5a-b7c388a60a48','dodgame-api','{\"token_id\":\"a02d9f2c-8de6-4e9e-aaff-b685e1d7c0a3\",\"grant_type\":\"authorization_code\",\"refresh_token_type\":\"Refresh\",\"scope\":\"openid email profile\",\"refresh_token_id\":\"470e8ec7-eab6-411c-9e36-fec7d813e904\",\"code_id\":\"df7194bd-3189-45b3-b0a0-6e4a593b2f26\",\"client_auth_method\":\"client-secret\"}',NULL,'192.168.112.1','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','df7194bd-3189-45b3-b0a0-6e4a593b2f26',1694194211938,'CODE_TO_TOKEN','3f119e2a-a6b8-408d-8885-9f0869fc84ee'),('4b1be9b5-6d77-4b16-8408-cc9792e0f769','dodgame-api','{\"token_id\":\"c4bb8475-4882-45d3-a91c-ca37ec4fd685\",\"grant_type\":\"authorization_code\",\"refresh_token_type\":\"Refresh\",\"scope\":\"openid email profile\",\"refresh_token_id\":\"43ca5cf8-b186-4601-a7c5-651eb178aab4\",\"code_id\":\"f421a8e1-06a6-4d01-adc1-97b0f5b03eab\",\"client_auth_method\":\"client-secret\"}',NULL,'192.168.112.1','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','f421a8e1-06a6-4d01-adc1-97b0f5b03eab',1694194007572,'CODE_TO_TOKEN','3f119e2a-a6b8-408d-8885-9f0869fc84ee'),('5e1f367d-0c03-44a2-a174-ca61a93190d0','dodgame-api','{\"auth_method\":\"openid-connect\",\"auth_type\":\"code\",\"redirect_uri\":\"http://ui:8081\",\"code_id\":\"f421a8e1-06a6-4d01-adc1-97b0f5b03eab\",\"username\":\"msp\"}','user_not_found','192.168.112.1','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5',NULL,1694193984120,'LOGIN_ERROR',NULL),('68bec1e0-e20b-46fa-9407-60f4dbf80881','dodgame-api','{\"auth_method\":\"openid-connect\",\"auth_type\":\"code\",\"register_method\":\"form\",\"last_name\":\"Pekilidi\",\"redirect_uri\":\"http://ui:8081\",\"first_name\":\"Marc\",\"code_id\":\"f421a8e1-06a6-4d01-adc1-97b0f5b03eab\",\"email\":\"mpekilidi@gmail.com\",\"username\":\"msp\"}',NULL,'192.168.112.1','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5',NULL,1694194006212,'REGISTER','3f119e2a-a6b8-408d-8885-9f0869fc84ee'),('7d5e0741-58f6-44c5-be35-5064c5fbe6c0','dodgame-api','{\"auth_method\":\"openid-connect\",\"auth_type\":\"code\",\"redirect_uri\":\"http://ui:8081\",\"consent\":\"no_consent_required\",\"code_id\":\"df7194bd-3189-45b3-b0a0-6e4a593b2f26\",\"username\":\"msp\"}',NULL,'192.168.112.1','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','df7194bd-3189-45b3-b0a0-6e4a593b2f26',1694194211453,'LOGIN','3f119e2a-a6b8-408d-8885-9f0869fc84ee'),('9a798378-1da3-4973-8a8a-da1f147b3342','dodgame-api','{\"token_id\":\"dc315625-d97f-48f1-b114-9e34f60e264e\",\"grant_type\":\"authorization_code\",\"refresh_token_type\":\"Refresh\",\"scope\":\"openid email profile\",\"refresh_token_id\":\"885aedd4-fa36-47b7-8ab0-f500fae15c6a\",\"code_id\":\"0feec745-53fe-45b1-ae05-0cd89ed9f95b\",\"client_auth_method\":\"client-secret\"}',NULL,'192.168.112.1','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','0feec745-53fe-45b1-ae05-0cd89ed9f95b',1694194371722,'CODE_TO_TOKEN','3f119e2a-a6b8-408d-8885-9f0869fc84ee'),('9f755f66-19b3-42f5-a0be-58bc7a6585ae','dodgame-api','{\"token_id\":\"f2b6bae7-187b-433f-a58c-aa6c1e8f0c61\",\"grant_type\":\"authorization_code\",\"refresh_token_type\":\"Refresh\",\"scope\":\"openid email profile\",\"refresh_token_id\":\"08f79a0f-8151-43b9-a645-cfa6b85a0002\",\"code_id\":\"f421a8e1-06a6-4d01-adc1-97b0f5b03eab\",\"client_auth_method\":\"client-secret\"}',NULL,'192.168.112.1','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','f421a8e1-06a6-4d01-adc1-97b0f5b03eab',1694194199349,'CODE_TO_TOKEN','3f119e2a-a6b8-408d-8885-9f0869fc84ee'),('a9b17c07-0c4c-42ee-ade3-fbad1974f485','dodgame-api','{\"auth_method\":\"openid-connect\",\"auth_type\":\"code\",\"redirect_uri\":\"http://ui:8081\",\"consent\":\"no_consent_required\",\"code_id\":\"f421a8e1-06a6-4d01-adc1-97b0f5b03eab\",\"username\":\"msp\"}',NULL,'192.168.112.1','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','f421a8e1-06a6-4d01-adc1-97b0f5b03eab',1694194006463,'LOGIN','3f119e2a-a6b8-408d-8885-9f0869fc84ee'),('bd23d05c-d688-4d93-b3e2-62a987ea0ed7','dodgame-api','{\"auth_method\":\"openid-connect\",\"auth_type\":\"code\",\"response_type\":\"code\",\"redirect_uri\":\"http://ui:8081\",\"consent\":\"no_consent_required\",\"code_id\":\"f421a8e1-06a6-4d01-adc1-97b0f5b03eab\",\"response_mode\":\"query\",\"username\":\"msp\"}',NULL,'192.168.112.1','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','f421a8e1-06a6-4d01-adc1-97b0f5b03eab',1694194146243,'LOGIN','3f119e2a-a6b8-408d-8885-9f0869fc84ee'),('d7d7eca3-87bd-49ce-80b5-fa1b8962a3ec','dodgame-api','{\"auth_method\":\"openid-connect\",\"auth_type\":\"code\",\"redirect_uri\":\"http://ui:8081\",\"consent\":\"no_consent_required\",\"code_id\":\"0feec745-53fe-45b1-ae05-0cd89ed9f95b\",\"username\":\"msp\"}',NULL,'192.168.112.1','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','0feec745-53fe-45b1-ae05-0cd89ed9f95b',1694194370532,'LOGIN','3f119e2a-a6b8-408d-8885-9f0869fc84ee'),('d9417200-321c-496f-ab51-5b4850c9d8b2',NULL,'{\"redirect_uri\":\"http://ui:8081\"}',NULL,'192.168.112.1','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','f421a8e1-06a6-4d01-adc1-97b0f5b03eab',1694194202982,'LOGOUT','3f119e2a-a6b8-408d-8885-9f0869fc84ee');
/*!40000 ALTER TABLE `EVENT_ENTITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FEDERATED_IDENTITY`
--

DROP TABLE IF EXISTS `FEDERATED_IDENTITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FEDERATED_IDENTITY` (
                                      `IDENTITY_PROVIDER` varchar(255) NOT NULL,
                                      `REALM_ID` varchar(36) DEFAULT NULL,
                                      `FEDERATED_USER_ID` varchar(255) DEFAULT NULL,
                                      `FEDERATED_USERNAME` varchar(255) DEFAULT NULL,
                                      `TOKEN` text,
                                      `USER_ID` varchar(36) NOT NULL,
                                      PRIMARY KEY (`IDENTITY_PROVIDER`,`USER_ID`),
                                      KEY `IDX_FEDIDENTITY_USER` (`USER_ID`),
                                      KEY `IDX_FEDIDENTITY_FEDUSER` (`FEDERATED_USER_ID`),
                                      CONSTRAINT `FK404288B92EF007A6` FOREIGN KEY (`USER_ID`) REFERENCES `USER_ENTITY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FEDERATED_IDENTITY`
--

LOCK TABLES `FEDERATED_IDENTITY` WRITE;
/*!40000 ALTER TABLE `FEDERATED_IDENTITY` DISABLE KEYS */;
/*!40000 ALTER TABLE `FEDERATED_IDENTITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FEDERATED_USER`
--

DROP TABLE IF EXISTS `FEDERATED_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FEDERATED_USER` (
                                  `ID` varchar(255) NOT NULL,
                                  `STORAGE_PROVIDER_ID` varchar(255) DEFAULT NULL,
                                  `REALM_ID` varchar(36) NOT NULL,
                                  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FEDERATED_USER`
--

LOCK TABLES `FEDERATED_USER` WRITE;
/*!40000 ALTER TABLE `FEDERATED_USER` DISABLE KEYS */;
/*!40000 ALTER TABLE `FEDERATED_USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FED_USER_ATTRIBUTE`
--

DROP TABLE IF EXISTS `FED_USER_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FED_USER_ATTRIBUTE` (
                                      `ID` varchar(36) NOT NULL,
                                      `NAME` varchar(255) NOT NULL,
                                      `USER_ID` varchar(255) NOT NULL,
                                      `REALM_ID` varchar(36) NOT NULL,
                                      `STORAGE_PROVIDER_ID` varchar(36) DEFAULT NULL,
                                      `VALUE` text,
                                      PRIMARY KEY (`ID`),
                                      KEY `IDX_FU_ATTRIBUTE` (`USER_ID`,`REALM_ID`,`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FED_USER_ATTRIBUTE`
--

LOCK TABLES `FED_USER_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `FED_USER_ATTRIBUTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `FED_USER_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FED_USER_CONSENT`
--

DROP TABLE IF EXISTS `FED_USER_CONSENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FED_USER_CONSENT` (
                                    `ID` varchar(36) NOT NULL,
                                    `CLIENT_ID` varchar(255) DEFAULT NULL,
                                    `USER_ID` varchar(255) NOT NULL,
                                    `REALM_ID` varchar(36) NOT NULL,
                                    `STORAGE_PROVIDER_ID` varchar(36) DEFAULT NULL,
                                    `CREATED_DATE` bigint DEFAULT NULL,
                                    `LAST_UPDATED_DATE` bigint DEFAULT NULL,
                                    `CLIENT_STORAGE_PROVIDER` varchar(36) DEFAULT NULL,
                                    `EXTERNAL_CLIENT_ID` varchar(255) DEFAULT NULL,
                                    PRIMARY KEY (`ID`),
                                    KEY `IDX_FU_CONSENT` (`USER_ID`,`CLIENT_ID`),
                                    KEY `IDX_FU_CONSENT_RU` (`REALM_ID`,`USER_ID`),
                                    KEY `IDX_FU_CNSNT_EXT` (`USER_ID`,`CLIENT_STORAGE_PROVIDER`,`EXTERNAL_CLIENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FED_USER_CONSENT`
--

LOCK TABLES `FED_USER_CONSENT` WRITE;
/*!40000 ALTER TABLE `FED_USER_CONSENT` DISABLE KEYS */;
/*!40000 ALTER TABLE `FED_USER_CONSENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FED_USER_CONSENT_CL_SCOPE`
--

DROP TABLE IF EXISTS `FED_USER_CONSENT_CL_SCOPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FED_USER_CONSENT_CL_SCOPE` (
                                             `USER_CONSENT_ID` varchar(36) NOT NULL,
                                             `SCOPE_ID` varchar(36) NOT NULL,
                                             PRIMARY KEY (`USER_CONSENT_ID`,`SCOPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FED_USER_CONSENT_CL_SCOPE`
--

LOCK TABLES `FED_USER_CONSENT_CL_SCOPE` WRITE;
/*!40000 ALTER TABLE `FED_USER_CONSENT_CL_SCOPE` DISABLE KEYS */;
/*!40000 ALTER TABLE `FED_USER_CONSENT_CL_SCOPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FED_USER_CREDENTIAL`
--

DROP TABLE IF EXISTS `FED_USER_CREDENTIAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FED_USER_CREDENTIAL` (
                                       `ID` varchar(36) NOT NULL,
                                       `SALT` tinyblob,
                                       `TYPE` varchar(255) DEFAULT NULL,
                                       `CREATED_DATE` bigint DEFAULT NULL,
                                       `USER_ID` varchar(255) NOT NULL,
                                       `REALM_ID` varchar(36) NOT NULL,
                                       `STORAGE_PROVIDER_ID` varchar(36) DEFAULT NULL,
                                       `USER_LABEL` varchar(255) DEFAULT NULL,
                                       `SECRET_DATA` longtext,
                                       `CREDENTIAL_DATA` longtext,
                                       `PRIORITY` int DEFAULT NULL,
                                       PRIMARY KEY (`ID`),
                                       KEY `IDX_FU_CREDENTIAL` (`USER_ID`,`TYPE`),
                                       KEY `IDX_FU_CREDENTIAL_RU` (`REALM_ID`,`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FED_USER_CREDENTIAL`
--

LOCK TABLES `FED_USER_CREDENTIAL` WRITE;
/*!40000 ALTER TABLE `FED_USER_CREDENTIAL` DISABLE KEYS */;
/*!40000 ALTER TABLE `FED_USER_CREDENTIAL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FED_USER_GROUP_MEMBERSHIP`
--

DROP TABLE IF EXISTS `FED_USER_GROUP_MEMBERSHIP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FED_USER_GROUP_MEMBERSHIP` (
                                             `GROUP_ID` varchar(36) NOT NULL,
                                             `USER_ID` varchar(255) NOT NULL,
                                             `REALM_ID` varchar(36) NOT NULL,
                                             `STORAGE_PROVIDER_ID` varchar(36) DEFAULT NULL,
                                             PRIMARY KEY (`GROUP_ID`,`USER_ID`),
                                             KEY `IDX_FU_GROUP_MEMBERSHIP` (`USER_ID`,`GROUP_ID`),
                                             KEY `IDX_FU_GROUP_MEMBERSHIP_RU` (`REALM_ID`,`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FED_USER_GROUP_MEMBERSHIP`
--

LOCK TABLES `FED_USER_GROUP_MEMBERSHIP` WRITE;
/*!40000 ALTER TABLE `FED_USER_GROUP_MEMBERSHIP` DISABLE KEYS */;
/*!40000 ALTER TABLE `FED_USER_GROUP_MEMBERSHIP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FED_USER_REQUIRED_ACTION`
--

DROP TABLE IF EXISTS `FED_USER_REQUIRED_ACTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FED_USER_REQUIRED_ACTION` (
                                            `REQUIRED_ACTION` varchar(255) NOT NULL DEFAULT ' ',
                                            `USER_ID` varchar(255) NOT NULL,
                                            `REALM_ID` varchar(36) NOT NULL,
                                            `STORAGE_PROVIDER_ID` varchar(36) DEFAULT NULL,
                                            PRIMARY KEY (`REQUIRED_ACTION`,`USER_ID`),
                                            KEY `IDX_FU_REQUIRED_ACTION` (`USER_ID`,`REQUIRED_ACTION`),
                                            KEY `IDX_FU_REQUIRED_ACTION_RU` (`REALM_ID`,`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FED_USER_REQUIRED_ACTION`
--

LOCK TABLES `FED_USER_REQUIRED_ACTION` WRITE;
/*!40000 ALTER TABLE `FED_USER_REQUIRED_ACTION` DISABLE KEYS */;
/*!40000 ALTER TABLE `FED_USER_REQUIRED_ACTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FED_USER_ROLE_MAPPING`
--

DROP TABLE IF EXISTS `FED_USER_ROLE_MAPPING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FED_USER_ROLE_MAPPING` (
                                         `ROLE_ID` varchar(36) NOT NULL,
                                         `USER_ID` varchar(255) NOT NULL,
                                         `REALM_ID` varchar(36) NOT NULL,
                                         `STORAGE_PROVIDER_ID` varchar(36) DEFAULT NULL,
                                         PRIMARY KEY (`ROLE_ID`,`USER_ID`),
                                         KEY `IDX_FU_ROLE_MAPPING` (`USER_ID`,`ROLE_ID`),
                                         KEY `IDX_FU_ROLE_MAPPING_RU` (`REALM_ID`,`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FED_USER_ROLE_MAPPING`
--

LOCK TABLES `FED_USER_ROLE_MAPPING` WRITE;
/*!40000 ALTER TABLE `FED_USER_ROLE_MAPPING` DISABLE KEYS */;
/*!40000 ALTER TABLE `FED_USER_ROLE_MAPPING` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GROUP_ATTRIBUTE`
--

DROP TABLE IF EXISTS `GROUP_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GROUP_ATTRIBUTE` (
                                   `ID` varchar(36) NOT NULL DEFAULT 'sybase-needs-something-here',
                                   `NAME` varchar(255) NOT NULL,
                                   `VALUE` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                                   `GROUP_ID` varchar(36) NOT NULL,
                                   PRIMARY KEY (`ID`),
                                   KEY `IDX_GROUP_ATTR_GROUP` (`GROUP_ID`),
                                   KEY `IDX_GROUP_ATT_BY_NAME_VALUE` (`NAME`,`VALUE`),
                                   CONSTRAINT `FK_GROUP_ATTRIBUTE_GROUP` FOREIGN KEY (`GROUP_ID`) REFERENCES `KEYCLOAK_GROUP` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GROUP_ATTRIBUTE`
--

LOCK TABLES `GROUP_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `GROUP_ATTRIBUTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `GROUP_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GROUP_ROLE_MAPPING`
--

DROP TABLE IF EXISTS `GROUP_ROLE_MAPPING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GROUP_ROLE_MAPPING` (
                                      `ROLE_ID` varchar(36) NOT NULL,
                                      `GROUP_ID` varchar(36) NOT NULL,
                                      PRIMARY KEY (`ROLE_ID`,`GROUP_ID`),
                                      KEY `IDX_GROUP_ROLE_MAPP_GROUP` (`GROUP_ID`),
                                      CONSTRAINT `FK_GROUP_ROLE_GROUP` FOREIGN KEY (`GROUP_ID`) REFERENCES `KEYCLOAK_GROUP` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GROUP_ROLE_MAPPING`
--

LOCK TABLES `GROUP_ROLE_MAPPING` WRITE;
/*!40000 ALTER TABLE `GROUP_ROLE_MAPPING` DISABLE KEYS */;
INSERT INTO `GROUP_ROLE_MAPPING` VALUES ('67d35d03-c1cb-414f-935e-b026084ce8cd','d654f365-d5b3-4283-ba0f-627e5685988d');
/*!40000 ALTER TABLE `GROUP_ROLE_MAPPING` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `IDENTITY_PROVIDER`
--

DROP TABLE IF EXISTS `IDENTITY_PROVIDER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `IDENTITY_PROVIDER` (
                                     `INTERNAL_ID` varchar(36) NOT NULL,
                                     `ENABLED` bit(1) NOT NULL DEFAULT b'0',
                                     `PROVIDER_ALIAS` varchar(255) DEFAULT NULL,
                                     `PROVIDER_ID` varchar(255) DEFAULT NULL,
                                     `STORE_TOKEN` bit(1) NOT NULL DEFAULT b'0',
                                     `AUTHENTICATE_BY_DEFAULT` bit(1) NOT NULL DEFAULT b'0',
                                     `REALM_ID` varchar(36) DEFAULT NULL,
                                     `ADD_TOKEN_ROLE` bit(1) NOT NULL DEFAULT b'1',
                                     `TRUST_EMAIL` bit(1) NOT NULL DEFAULT b'0',
                                     `FIRST_BROKER_LOGIN_FLOW_ID` varchar(36) DEFAULT NULL,
                                     `POST_BROKER_LOGIN_FLOW_ID` varchar(36) DEFAULT NULL,
                                     `PROVIDER_DISPLAY_NAME` varchar(255) DEFAULT NULL,
                                     `LINK_ONLY` bit(1) NOT NULL DEFAULT b'0',
                                     PRIMARY KEY (`INTERNAL_ID`),
                                     UNIQUE KEY `UK_2DAELWNIBJI49AVXSRTUF6XJ33` (`PROVIDER_ALIAS`,`REALM_ID`),
                                     KEY `IDX_IDENT_PROV_REALM` (`REALM_ID`),
                                     CONSTRAINT `FK2B4EBC52AE5C3B34` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `IDENTITY_PROVIDER`
--

LOCK TABLES `IDENTITY_PROVIDER` WRITE;
/*!40000 ALTER TABLE `IDENTITY_PROVIDER` DISABLE KEYS */;
/*!40000 ALTER TABLE `IDENTITY_PROVIDER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `IDENTITY_PROVIDER_CONFIG`
--

DROP TABLE IF EXISTS `IDENTITY_PROVIDER_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `IDENTITY_PROVIDER_CONFIG` (
                                            `IDENTITY_PROVIDER_ID` varchar(36) NOT NULL,
                                            `VALUE` longtext,
                                            `NAME` varchar(255) NOT NULL,
                                            PRIMARY KEY (`IDENTITY_PROVIDER_ID`,`NAME`),
                                            CONSTRAINT `FKDC4897CF864C4E43` FOREIGN KEY (`IDENTITY_PROVIDER_ID`) REFERENCES `IDENTITY_PROVIDER` (`INTERNAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `IDENTITY_PROVIDER_CONFIG`
--

LOCK TABLES `IDENTITY_PROVIDER_CONFIG` WRITE;
/*!40000 ALTER TABLE `IDENTITY_PROVIDER_CONFIG` DISABLE KEYS */;
/*!40000 ALTER TABLE `IDENTITY_PROVIDER_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `IDENTITY_PROVIDER_MAPPER`
--

DROP TABLE IF EXISTS `IDENTITY_PROVIDER_MAPPER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `IDENTITY_PROVIDER_MAPPER` (
                                            `ID` varchar(36) NOT NULL,
                                            `NAME` varchar(255) NOT NULL,
                                            `IDP_ALIAS` varchar(255) NOT NULL,
                                            `IDP_MAPPER_NAME` varchar(255) NOT NULL,
                                            `REALM_ID` varchar(36) NOT NULL,
                                            PRIMARY KEY (`ID`),
                                            KEY `IDX_ID_PROV_MAPP_REALM` (`REALM_ID`),
                                            CONSTRAINT `FK_IDPM_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `IDENTITY_PROVIDER_MAPPER`
--

LOCK TABLES `IDENTITY_PROVIDER_MAPPER` WRITE;
/*!40000 ALTER TABLE `IDENTITY_PROVIDER_MAPPER` DISABLE KEYS */;
/*!40000 ALTER TABLE `IDENTITY_PROVIDER_MAPPER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `IDP_MAPPER_CONFIG`
--

DROP TABLE IF EXISTS `IDP_MAPPER_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `IDP_MAPPER_CONFIG` (
                                     `IDP_MAPPER_ID` varchar(36) NOT NULL,
                                     `VALUE` longtext,
                                     `NAME` varchar(255) NOT NULL,
                                     PRIMARY KEY (`IDP_MAPPER_ID`,`NAME`),
                                     CONSTRAINT `FK_IDPMCONFIG` FOREIGN KEY (`IDP_MAPPER_ID`) REFERENCES `IDENTITY_PROVIDER_MAPPER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `IDP_MAPPER_CONFIG`
--

LOCK TABLES `IDP_MAPPER_CONFIG` WRITE;
/*!40000 ALTER TABLE `IDP_MAPPER_CONFIG` DISABLE KEYS */;
/*!40000 ALTER TABLE `IDP_MAPPER_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `KEYCLOAK_GROUP`
--

DROP TABLE IF EXISTS `KEYCLOAK_GROUP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `KEYCLOAK_GROUP` (
                                  `ID` varchar(36) NOT NULL,
                                  `NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                                  `PARENT_GROUP` varchar(36) NOT NULL,
                                  `REALM_ID` varchar(36) DEFAULT NULL,
                                  PRIMARY KEY (`ID`),
                                  UNIQUE KEY `SIBLING_NAMES` (`REALM_ID`,`PARENT_GROUP`,`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `KEYCLOAK_GROUP`
--

LOCK TABLES `KEYCLOAK_GROUP` WRITE;
/*!40000 ALTER TABLE `KEYCLOAK_GROUP` DISABLE KEYS */;
INSERT INTO `KEYCLOAK_GROUP` VALUES ('d654f365-d5b3-4283-ba0f-627e5685988d','PlayerUsers',' ','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5');
/*!40000 ALTER TABLE `KEYCLOAK_GROUP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `KEYCLOAK_ROLE`
--

DROP TABLE IF EXISTS `KEYCLOAK_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `KEYCLOAK_ROLE` (
                                 `ID` varchar(36) NOT NULL,
                                 `CLIENT_REALM_CONSTRAINT` varchar(255) DEFAULT NULL,
                                 `CLIENT_ROLE` bit(1) DEFAULT NULL,
                                 `DESCRIPTION` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                                 `NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                                 `REALM_ID` varchar(255) DEFAULT NULL,
                                 `CLIENT` varchar(36) DEFAULT NULL,
                                 `REALM` varchar(36) DEFAULT NULL,
                                 PRIMARY KEY (`ID`),
                                 UNIQUE KEY `UK_J3RWUVD56ONTGSUHOGM184WW2-2` (`NAME`,`CLIENT_REALM_CONSTRAINT`),
                                 KEY `IDX_KEYCLOAK_ROLE_CLIENT` (`CLIENT`),
                                 KEY `IDX_KEYCLOAK_ROLE_REALM` (`REALM`),
                                 CONSTRAINT `FK_6VYQFE4CN4WLQ8R6KT5VDSJ5C` FOREIGN KEY (`REALM`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `KEYCLOAK_ROLE`
--

LOCK TABLES `KEYCLOAK_ROLE` WRITE;
/*!40000 ALTER TABLE `KEYCLOAK_ROLE` DISABLE KEYS */;
INSERT INTO `KEYCLOAK_ROLE` VALUES ('089cbeda-af28-4c3a-b0e1-e77379f5d82e','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_realm-admin}','realm-admin','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('0fe8c5e1-0f93-49d7-bfb8-3938c89f6d7c','950de219-4129-4d3b-8919-0987a1a13174','','${role_view-users}','view-users','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','950de219-4129-4d3b-8919-0987a1a13174',NULL),('1239c49e-f23c-4b54-b29c-444da3a0e256','950de219-4129-4d3b-8919-0987a1a13174','','${role_manage-identity-providers}','manage-identity-providers','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','950de219-4129-4d3b-8919-0987a1a13174',NULL),('157b6bdc-877f-4e55-8520-dfcfcb23e4a3','950de219-4129-4d3b-8919-0987a1a13174','','${role_impersonation}','impersonation','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','950de219-4129-4d3b-8919-0987a1a13174',NULL),('16abc242-2623-40c1-8553-6fbb0f8746d1','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_view-authorization}','view-authorization','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('189d957e-1809-4d79-b9b1-60e4c1e8bcf8','950de219-4129-4d3b-8919-0987a1a13174','','${role_query-clients}','query-clients','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','950de219-4129-4d3b-8919-0987a1a13174',NULL),('1df22cbe-28c5-4683-9bb9-b0ea589684b1','529e6128-c1bc-4008-ac4d-6d645f6fe9d4','','${role_view-applications}','view-applications','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','529e6128-c1bc-4008-ac4d-6d645f6fe9d4',NULL),('23626911-39f5-44b1-9c71-ce8186c6c4e1','1ff4cae9-f76a-4e4b-a700-c272c99710d6','','${role_create-client}','create-client','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1ff4cae9-f76a-4e4b-a700-c272c99710d6',NULL),('24279816-36d9-4fc0-aa59-e91a19e48a34','34eac0d0-6d27-4bc5-931d-dcd506948309','','${role_delete-account}','delete-account','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','34eac0d0-6d27-4bc5-931d-dcd506948309',NULL),('265b6035-0618-4f76-a050-044986a3b109','529e6128-c1bc-4008-ac4d-6d645f6fe9d4','','${role_manage-consent}','manage-consent','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','529e6128-c1bc-4008-ac4d-6d645f6fe9d4',NULL),('26b9da60-3c69-4bc1-b630-0fbce0079614','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','\0','${role_uma_authorization}','uma_authorization','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5',NULL,NULL),('29c9ac46-af02-41aa-b4b4-d16ca46c913a','529e6128-c1bc-4008-ac4d-6d645f6fe9d4','','${role_delete-account}','delete-account','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','529e6128-c1bc-4008-ac4d-6d645f6fe9d4',NULL),('2a1cb718-6bcd-4514-8606-b1ffb9a241f9','1ff4cae9-f76a-4e4b-a700-c272c99710d6','','${role_manage-users}','manage-users','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1ff4cae9-f76a-4e4b-a700-c272c99710d6',NULL),('3191fcc2-8776-400f-ac9d-b7ca5d9f0b30','1ff4cae9-f76a-4e4b-a700-c272c99710d6','','${role_view-events}','view-events','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1ff4cae9-f76a-4e4b-a700-c272c99710d6',NULL),('330cde7a-8620-4a9e-98d7-5987b69473a7','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','\0','${role_uma_authorization}','uma_authorization','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f',NULL,NULL),('3418dfb6-b6b6-4744-938c-6de5938d12ed','1ff4cae9-f76a-4e4b-a700-c272c99710d6','','${role_manage-realm}','manage-realm','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1ff4cae9-f76a-4e4b-a700-c272c99710d6',NULL),('34de8f55-1faf-4f0d-83a5-6a829a0fdc67','f5155791-97e6-48e4-8049-652547f6e9ff','','${role_read-token}','read-token','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','f5155791-97e6-48e4-8049-652547f6e9ff',NULL),('365b4a11-6ac9-4e62-bc39-4f14760a5a7c','950de219-4129-4d3b-8919-0987a1a13174','','${role_manage-users}','manage-users','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','950de219-4129-4d3b-8919-0987a1a13174',NULL),('41c90a82-51ca-47e2-af7c-c745f2beef0f','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_query-realms}','query-realms','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('425b4273-22a7-4b14-8937-6bc3b46662c7','138c5771-aaf5-495f-8480-05f1504a6c3a','','','npc','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','138c5771-aaf5-495f-8480-05f1504a6c3a',NULL),('46774ffd-dc65-4ec4-a188-c1caf988789c','138c5771-aaf5-495f-8480-05f1504a6c3a','','','master','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','138c5771-aaf5-495f-8480-05f1504a6c3a',NULL),('46eff922-acb9-49c4-82e5-64345fcd264c','950de219-4129-4d3b-8919-0987a1a13174','','${role_view-events}','view-events','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','950de219-4129-4d3b-8919-0987a1a13174',NULL),('4749ecd0-7f22-47e9-b3bc-d5bcfb9f11e0','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_query-users}','query-users','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('4c54591f-c37c-4a8e-ae43-c8573609f6a5','529e6128-c1bc-4008-ac4d-6d645f6fe9d4','','${role_manage-account}','manage-account','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','529e6128-c1bc-4008-ac4d-6d645f6fe9d4',NULL),('4d3fcf9f-d270-4892-b7a6-a30d06f0bc27','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_view-realm}','view-realm','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('4e721d0c-e399-4d03-8704-6cfb2d1421c0','1ff4cae9-f76a-4e4b-a700-c272c99710d6','','${role_view-users}','view-users','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1ff4cae9-f76a-4e4b-a700-c272c99710d6',NULL),('4f88f8c5-8bb0-4698-99dc-48d89d00082d','1ff4cae9-f76a-4e4b-a700-c272c99710d6','','${role_impersonation}','impersonation','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1ff4cae9-f76a-4e4b-a700-c272c99710d6',NULL),('5602e35f-5870-4380-975c-92798e14390e','1ff4cae9-f76a-4e4b-a700-c272c99710d6','','${role_manage-identity-providers}','manage-identity-providers','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1ff4cae9-f76a-4e4b-a700-c272c99710d6',NULL),('5fdaa8ba-b745-463a-8ef0-20a24fe7dba1','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_create-client}','create-client','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('67d35d03-c1cb-414f-935e-b026084ce8cd','138c5771-aaf5-495f-8480-05f1504a6c3a','','','player','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','138c5771-aaf5-495f-8480-05f1504a6c3a',NULL),('69bf757a-2809-4086-8c0d-757c5428db36','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','\0','${role_offline-access}','offline_access','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f',NULL,NULL),('6a0e2da3-f172-4fc0-9ac0-e16c2015a4f4','950de219-4129-4d3b-8919-0987a1a13174','','${role_manage-events}','manage-events','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','950de219-4129-4d3b-8919-0987a1a13174',NULL),('6dc86259-1b7a-4ade-8947-b124889bf08f','1ff4cae9-f76a-4e4b-a700-c272c99710d6','','${role_view-identity-providers}','view-identity-providers','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1ff4cae9-f76a-4e4b-a700-c272c99710d6',NULL),('6dcbd598-4c43-4a47-bace-7cef7ad839ed','950de219-4129-4d3b-8919-0987a1a13174','','${role_query-users}','query-users','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','950de219-4129-4d3b-8919-0987a1a13174',NULL),('6f240ff5-8abf-4e85-b720-f54292d0a9b7','1ff4cae9-f76a-4e4b-a700-c272c99710d6','','${role_query-realms}','query-realms','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1ff4cae9-f76a-4e4b-a700-c272c99710d6',NULL),('72784b1c-31d0-4355-8f2c-cfaca7ea1dea','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_manage-events}','manage-events','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('739d4c36-7d49-4a50-9b3c-5ae23fbfdd94','34eac0d0-6d27-4bc5-931d-dcd506948309','','${role_manage-consent}','manage-consent','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','34eac0d0-6d27-4bc5-931d-dcd506948309',NULL),('755032b4-aba7-4b0b-ba5b-63cbd467091e','1ff4cae9-f76a-4e4b-a700-c272c99710d6','','${role_query-users}','query-users','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1ff4cae9-f76a-4e4b-a700-c272c99710d6',NULL),('757b7ed1-a8bd-471c-99e4-0108ed3d8aa6','529e6128-c1bc-4008-ac4d-6d645f6fe9d4','','${role_manage-account-links}','manage-account-links','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','529e6128-c1bc-4008-ac4d-6d645f6fe9d4',NULL),('80f71a11-daa3-4bde-8275-7f86120a6eec','138c5771-aaf5-495f-8480-05f1504a6c3a','','','system','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','138c5771-aaf5-495f-8480-05f1504a6c3a',NULL),('8415c6ec-f054-4fe4-a5a5-e2ba97c43412','1ff4cae9-f76a-4e4b-a700-c272c99710d6','','${role_view-clients}','view-clients','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1ff4cae9-f76a-4e4b-a700-c272c99710d6',NULL),('8578e2df-331c-4052-8603-579ecb49455a','34eac0d0-6d27-4bc5-931d-dcd506948309','','${role_view-applications}','view-applications','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','34eac0d0-6d27-4bc5-931d-dcd506948309',NULL),('8b3a6803-dfea-4279-b6c3-18d2a012bbc9','1ff4cae9-f76a-4e4b-a700-c272c99710d6','','${role_manage-authorization}','manage-authorization','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1ff4cae9-f76a-4e4b-a700-c272c99710d6',NULL),('8fdbfbe3-f4d5-426f-8db5-79360f6fb14a','950de219-4129-4d3b-8919-0987a1a13174','','${role_view-clients}','view-clients','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','950de219-4129-4d3b-8919-0987a1a13174',NULL),('914e1aac-ce75-4bd7-9a06-dcffa62103db','950de219-4129-4d3b-8919-0987a1a13174','','${role_create-client}','create-client','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','950de219-4129-4d3b-8919-0987a1a13174',NULL),('931dacd8-0f69-447f-b4c4-287e53330216','34eac0d0-6d27-4bc5-931d-dcd506948309','','${role_view-profile}','view-profile','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','34eac0d0-6d27-4bc5-931d-dcd506948309',NULL),('9977f1e2-22bc-4c80-b099-ab3423ac3f1d','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_manage-users}','manage-users','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('9a23483d-b149-48cc-b313-61593ea6d622','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_manage-identity-providers}','manage-identity-providers','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('9bfa0632-f779-47c6-bdf0-edb79173eb0c','950de219-4129-4d3b-8919-0987a1a13174','','${role_manage-clients}','manage-clients','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','950de219-4129-4d3b-8919-0987a1a13174',NULL),('a728a384-0234-4a65-ab06-779c089e3dfd','1ff4cae9-f76a-4e4b-a700-c272c99710d6','','${role_view-authorization}','view-authorization','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1ff4cae9-f76a-4e4b-a700-c272c99710d6',NULL),('aed89732-bf03-4679-8858-90ff50dc9c65','950de219-4129-4d3b-8919-0987a1a13174','','${role_view-realm}','view-realm','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','950de219-4129-4d3b-8919-0987a1a13174',NULL),('afcfa009-5a7e-4a77-8f72-4981e185b04e','1ff4cae9-f76a-4e4b-a700-c272c99710d6','','${role_query-clients}','query-clients','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1ff4cae9-f76a-4e4b-a700-c272c99710d6',NULL),('b0376f21-a1e3-4a60-aae3-aa985f56cf6e','34eac0d0-6d27-4bc5-931d-dcd506948309','','${role_manage-account-links}','manage-account-links','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','34eac0d0-6d27-4bc5-931d-dcd506948309',NULL),('b09cea9f-6a62-4ae0-b12d-7e1514a81794','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','\0','${role_offline-access}','offline_access','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5',NULL,NULL),('b194e1a7-d04c-4478-8d33-31b82304233d','950de219-4129-4d3b-8919-0987a1a13174','','${role_view-identity-providers}','view-identity-providers','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','950de219-4129-4d3b-8919-0987a1a13174',NULL),('b39f38f8-e322-40e9-834e-3d3246dd497d','34eac0d0-6d27-4bc5-931d-dcd506948309','','${role_view-consent}','view-consent','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','34eac0d0-6d27-4bc5-931d-dcd506948309',NULL),('ba0008a6-aba7-4bf1-960b-93ee33b5a03e','950de219-4129-4d3b-8919-0987a1a13174','','${role_manage-realm}','manage-realm','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','950de219-4129-4d3b-8919-0987a1a13174',NULL),('bff87df8-9dd0-42ef-892c-33e8267244a5','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','\0','${role_default-roles}','default-roles-master','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f',NULL,NULL),('c2c9715e-e21b-4bc8-9b01-15abd59f0b61','dc5d94e6-2255-47c0-abbe-03a224d39661','','${role_read-token}','read-token','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','dc5d94e6-2255-47c0-abbe-03a224d39661',NULL),('c398d42d-3391-49b4-82dd-b726df97ead3','529e6128-c1bc-4008-ac4d-6d645f6fe9d4','','${role_view-consent}','view-consent','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','529e6128-c1bc-4008-ac4d-6d645f6fe9d4',NULL),('c56f698f-1e19-48d6-b419-b201d2e9e80d','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_manage-realm}','manage-realm','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('c787b540-29f8-445a-bc0d-d07412be4658','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','\0','${role_admin}','admin','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f',NULL,NULL),('c7f5033f-d308-403a-88ba-8076610ed85a','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_query-groups}','query-groups','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('c8b182dc-ab23-4b7b-a5e6-eb546fadadca','1ff4cae9-f76a-4e4b-a700-c272c99710d6','','${role_query-groups}','query-groups','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1ff4cae9-f76a-4e4b-a700-c272c99710d6',NULL),('cb0b8e0e-64d2-4747-9d3a-5bf0ef265616','34eac0d0-6d27-4bc5-931d-dcd506948309','','${role_view-groups}','view-groups','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','34eac0d0-6d27-4bc5-931d-dcd506948309',NULL),('cb1cffd1-f807-4642-90c5-a2f9481f7a08','34eac0d0-6d27-4bc5-931d-dcd506948309','','${role_manage-account}','manage-account','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','34eac0d0-6d27-4bc5-931d-dcd506948309',NULL),('cd0ea247-8b19-479a-b503-8d862d315960','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_view-identity-providers}','view-identity-providers','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('ce7a36e9-bbcd-492e-bee2-0fb4f75b2648','529e6128-c1bc-4008-ac4d-6d645f6fe9d4','','${role_view-groups}','view-groups','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','529e6128-c1bc-4008-ac4d-6d645f6fe9d4',NULL),('cf863212-b3e9-47c9-9d31-d21a0f6f2e12','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_manage-clients}','manage-clients','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('d0cc53e5-c83d-45d5-bf4e-ef14ca703b77','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_view-clients}','view-clients','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('d16f1f30-ee0f-4d31-9a9e-5896a9a108d9','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_manage-authorization}','manage-authorization','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('db45abcb-6ba2-4f67-ac79-685177b323ae','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_impersonation}','impersonation','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('de4df032-d15c-4e80-b7cb-2916694a551d','529e6128-c1bc-4008-ac4d-6d645f6fe9d4','','${role_view-profile}','view-profile','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','529e6128-c1bc-4008-ac4d-6d645f6fe9d4',NULL),('df248b6d-25dd-4a0d-850f-86aa32203820','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_view-users}','view-users','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('df5cfea1-8355-438a-8570-a009c7817911','1ff4cae9-f76a-4e4b-a700-c272c99710d6','','${role_manage-events}','manage-events','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1ff4cae9-f76a-4e4b-a700-c272c99710d6',NULL),('e5809fd7-a888-4565-b7f6-cc07d5e32381','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_query-clients}','query-clients','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('e612a057-f268-4812-9183-e8fda70df1de','48b200f0-dff2-4bb4-b869-1524705753f9','','${role_view-events}','view-events','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','48b200f0-dff2-4bb4-b869-1524705753f9',NULL),('ed4a78dd-65d2-4bcc-b1b5-0e3e17318f74','1ff4cae9-f76a-4e4b-a700-c272c99710d6','','${role_view-realm}','view-realm','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1ff4cae9-f76a-4e4b-a700-c272c99710d6',NULL),('eebc1457-de46-49ba-abba-a76ae2a6e0ec','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','\0','${role_create-realm}','create-realm','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f',NULL,NULL),('ef43ee72-7603-48e1-9456-cd3e2014f66a','950de219-4129-4d3b-8919-0987a1a13174','','${role_manage-authorization}','manage-authorization','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','950de219-4129-4d3b-8919-0987a1a13174',NULL),('f0d7109e-8757-4f49-96ea-f27599448043','950de219-4129-4d3b-8919-0987a1a13174','','${role_query-realms}','query-realms','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','950de219-4129-4d3b-8919-0987a1a13174',NULL),('f10a55d3-3d28-48f7-b44a-cabcf4e99e5c','950de219-4129-4d3b-8919-0987a1a13174','','${role_query-groups}','query-groups','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','950de219-4129-4d3b-8919-0987a1a13174',NULL),('f3e73123-f39a-4284-a4d8-cee68cd5e898','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','\0','${role_default-roles}','default-roles-dodgame','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5',NULL,NULL),('faa2ba5e-2200-4600-b3ad-f3a84cb0bf24','950de219-4129-4d3b-8919-0987a1a13174','','${role_view-authorization}','view-authorization','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','950de219-4129-4d3b-8919-0987a1a13174',NULL),('fc14b5be-de8a-43ae-a31d-318cefd9e11f','1ff4cae9-f76a-4e4b-a700-c272c99710d6','','${role_manage-clients}','manage-clients','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1ff4cae9-f76a-4e4b-a700-c272c99710d6',NULL);
/*!40000 ALTER TABLE `KEYCLOAK_ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MIGRATION_MODEL`
--

DROP TABLE IF EXISTS `MIGRATION_MODEL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MIGRATION_MODEL` (
                                   `ID` varchar(36) NOT NULL,
                                   `VERSION` varchar(36) DEFAULT NULL,
                                   `UPDATE_TIME` bigint NOT NULL DEFAULT '0',
                                   PRIMARY KEY (`ID`),
                                   KEY `IDX_UPDATE_TIME` (`UPDATE_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MIGRATION_MODEL`
--

LOCK TABLES `MIGRATION_MODEL` WRITE;
/*!40000 ALTER TABLE `MIGRATION_MODEL` DISABLE KEYS */;
INSERT INTO `MIGRATION_MODEL` VALUES ('55nnz','22.0.4',1701518959),('6a9db','22.0.1',1694193957);
/*!40000 ALTER TABLE `MIGRATION_MODEL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OFFLINE_CLIENT_SESSION`
--

DROP TABLE IF EXISTS `OFFLINE_CLIENT_SESSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OFFLINE_CLIENT_SESSION` (
                                          `USER_SESSION_ID` varchar(36) NOT NULL,
                                          `CLIENT_ID` varchar(255) NOT NULL,
                                          `OFFLINE_FLAG` varchar(4) NOT NULL,
                                          `TIMESTAMP` int DEFAULT NULL,
                                          `DATA` longtext,
                                          `CLIENT_STORAGE_PROVIDER` varchar(36) NOT NULL DEFAULT 'local',
                                          `EXTERNAL_CLIENT_ID` varchar(255) NOT NULL DEFAULT 'local',
                                          PRIMARY KEY (`USER_SESSION_ID`,`CLIENT_ID`,`CLIENT_STORAGE_PROVIDER`,`EXTERNAL_CLIENT_ID`,`OFFLINE_FLAG`),
                                          KEY `IDX_US_SESS_ID_ON_CL_SESS` (`USER_SESSION_ID`),
                                          KEY `IDX_OFFLINE_CSS_PRELOAD` (`CLIENT_ID`,`OFFLINE_FLAG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OFFLINE_CLIENT_SESSION`
--

LOCK TABLES `OFFLINE_CLIENT_SESSION` WRITE;
/*!40000 ALTER TABLE `OFFLINE_CLIENT_SESSION` DISABLE KEYS */;
/*!40000 ALTER TABLE `OFFLINE_CLIENT_SESSION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OFFLINE_USER_SESSION`
--

DROP TABLE IF EXISTS `OFFLINE_USER_SESSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OFFLINE_USER_SESSION` (
                                        `USER_SESSION_ID` varchar(36) NOT NULL,
                                        `USER_ID` varchar(255) DEFAULT NULL,
                                        `REALM_ID` varchar(36) NOT NULL,
                                        `CREATED_ON` int NOT NULL,
                                        `OFFLINE_FLAG` varchar(4) NOT NULL,
                                        `DATA` longtext,
                                        `LAST_SESSION_REFRESH` int NOT NULL DEFAULT '0',
                                        PRIMARY KEY (`USER_SESSION_ID`,`OFFLINE_FLAG`),
                                        KEY `IDX_OFFLINE_USS_CREATEDON` (`CREATED_ON`),
                                        KEY `IDX_OFFLINE_USS_PRELOAD` (`OFFLINE_FLAG`,`CREATED_ON`,`USER_SESSION_ID`),
                                        KEY `IDX_OFFLINE_USS_BY_USER` (`USER_ID`,`REALM_ID`,`OFFLINE_FLAG`),
                                        KEY `IDX_OFFLINE_USS_BY_USERSESS` (`REALM_ID`,`OFFLINE_FLAG`,`USER_SESSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OFFLINE_USER_SESSION`
--

LOCK TABLES `OFFLINE_USER_SESSION` WRITE;
/*!40000 ALTER TABLE `OFFLINE_USER_SESSION` DISABLE KEYS */;
/*!40000 ALTER TABLE `OFFLINE_USER_SESSION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `POLICY_CONFIG`
--

DROP TABLE IF EXISTS `POLICY_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `POLICY_CONFIG` (
                                 `POLICY_ID` varchar(36) NOT NULL,
                                 `NAME` varchar(255) NOT NULL,
                                 `VALUE` longtext,
                                 PRIMARY KEY (`POLICY_ID`,`NAME`),
                                 CONSTRAINT `FKDC34197CF864C4E43` FOREIGN KEY (`POLICY_ID`) REFERENCES `RESOURCE_SERVER_POLICY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `POLICY_CONFIG`
--

LOCK TABLES `POLICY_CONFIG` WRITE;
/*!40000 ALTER TABLE `POLICY_CONFIG` DISABLE KEYS */;
/*!40000 ALTER TABLE `POLICY_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PROTOCOL_MAPPER`
--

DROP TABLE IF EXISTS `PROTOCOL_MAPPER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PROTOCOL_MAPPER` (
                                   `ID` varchar(36) NOT NULL,
                                   `NAME` varchar(255) NOT NULL,
                                   `PROTOCOL` varchar(255) NOT NULL,
                                   `PROTOCOL_MAPPER_NAME` varchar(255) NOT NULL,
                                   `CLIENT_ID` varchar(36) DEFAULT NULL,
                                   `CLIENT_SCOPE_ID` varchar(36) DEFAULT NULL,
                                   PRIMARY KEY (`ID`),
                                   KEY `IDX_PROTOCOL_MAPPER_CLIENT` (`CLIENT_ID`),
                                   KEY `IDX_CLSCOPE_PROTMAP` (`CLIENT_SCOPE_ID`),
                                   CONSTRAINT `FK_CLI_SCOPE_MAPPER` FOREIGN KEY (`CLIENT_SCOPE_ID`) REFERENCES `CLIENT_SCOPE` (`ID`),
                                   CONSTRAINT `FK_PCM_REALM` FOREIGN KEY (`CLIENT_ID`) REFERENCES `CLIENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PROTOCOL_MAPPER`
--

LOCK TABLES `PROTOCOL_MAPPER` WRITE;
/*!40000 ALTER TABLE `PROTOCOL_MAPPER` DISABLE KEYS */;
INSERT INTO `PROTOCOL_MAPPER` VALUES ('0341adc7-5d0a-4dd6-8eb4-0dd3a55a07c8','gender','openid-connect','oidc-usermodel-attribute-mapper',NULL,'0fa8d901-ba5d-45b6-a6ec-62461a8a8d07'),('07ba8b41-c10e-4479-949e-a472f3f3bdbc','audience resolve','openid-connect','oidc-audience-resolve-mapper','ced0633a-f3d9-4c75-b85d-1c62fb955164',NULL),('07bf90dc-efff-4a9f-88c3-ce3926eb3f39','role list','saml','saml-role-list-mapper',NULL,'937da9b0-a93c-427b-b5ff-db162f988583'),('0a17655b-0638-4db6-aa2c-ecdc431f3a1b','profile','openid-connect','oidc-usermodel-attribute-mapper',NULL,'0fa8d901-ba5d-45b6-a6ec-62461a8a8d07'),('0b220cc9-6c7e-4e2d-b28b-c6d34866fabe','given name','openid-connect','oidc-usermodel-attribute-mapper',NULL,'0fa8d901-ba5d-45b6-a6ec-62461a8a8d07'),('18ee3f98-41e4-4e8c-8c8a-93a13d394297','upn','openid-connect','oidc-usermodel-attribute-mapper',NULL,'32ffc623-cc8a-495c-8fba-61c36becefd0'),('22358647-5344-48d2-82a0-f82010b74ada','realm roles','openid-connect','oidc-usermodel-realm-role-mapper',NULL,'c73b5dfb-50e1-4649-a20b-2cd7ceadeb54'),('23a9f132-4f91-4876-a27d-17f0542938d1','client roles','openid-connect','oidc-usermodel-client-role-mapper',NULL,'c73b5dfb-50e1-4649-a20b-2cd7ceadeb54'),('2915541b-eadb-4818-b925-be0bf8c0c96e','realm roles','openid-connect','oidc-usermodel-realm-role-mapper',NULL,'9f6f73b8-23d0-4cb4-8d8b-95c033287f0d'),('2c4a9b15-0fb8-4987-9875-8f9d6966f857','phone number','openid-connect','oidc-usermodel-attribute-mapper',NULL,'0492c70e-dbb9-44fd-b926-36b2a480e707'),('2e041753-949e-4861-8622-812919448f40','full name','openid-connect','oidc-full-name-mapper',NULL,'0fa8d901-ba5d-45b6-a6ec-62461a8a8d07'),('35cd8e62-9f79-4825-8a9c-d73d893e27d3','website','openid-connect','oidc-usermodel-attribute-mapper',NULL,'2b91a3e9-3562-48c9-8464-c4d021b6a48d'),('3d7ed671-7ae7-47ee-8107-ea310df03137','profile','openid-connect','oidc-usermodel-attribute-mapper',NULL,'2b91a3e9-3562-48c9-8464-c4d021b6a48d'),('3f7573ed-6645-4e62-a7eb-4598aa10ba2d','email','openid-connect','oidc-usermodel-attribute-mapper',NULL,'fe396b62-1b79-4557-9e57-6e0522bdf506'),('480bbe39-f02f-4dd1-b0b6-41eac41680a7','phone number verified','openid-connect','oidc-usermodel-attribute-mapper',NULL,'2e4d99ab-3601-45d1-849e-b383678ec646'),('4a6e15a2-2159-4034-8580-f160517b5b4e','upn','openid-connect','oidc-usermodel-attribute-mapper',NULL,'3c466413-4163-4125-bf25-e85b9c2d9ad0'),('4d8d7c47-c906-4cb0-ab84-548cdcb394f7','role list','saml','saml-role-list-mapper',NULL,'5a7014a9-93d9-43e0-9bc5-a389338dcf28'),('4e817151-f54b-4c3c-89e1-00146f891a66','audience resolve','openid-connect','oidc-audience-resolve-mapper','d43f3531-9d53-401b-a17e-59e6e6664b6a',NULL),('512142fd-8f9c-4fec-abb3-7a5da5f289a3','allowed web origins','openid-connect','oidc-allowed-origins-mapper',NULL,'09575535-6b7f-404f-8400-526497b4792a'),('52eedb58-503a-442e-a49a-b6a0ff1df20b','client roles','openid-connect','oidc-usermodel-client-role-mapper',NULL,'9f6f73b8-23d0-4cb4-8d8b-95c033287f0d'),('53a06cfa-4d51-4c3a-bdd5-dce4590dd1d1','family name','openid-connect','oidc-usermodel-attribute-mapper',NULL,'0fa8d901-ba5d-45b6-a6ec-62461a8a8d07'),('53f0c0fc-924d-4e21-aae6-15dd62e2588e','family name','openid-connect','oidc-usermodel-attribute-mapper',NULL,'2b91a3e9-3562-48c9-8464-c4d021b6a48d'),('58a5c727-394b-4ca0-8346-3c9aa971df43','nickname','openid-connect','oidc-usermodel-attribute-mapper',NULL,'2b91a3e9-3562-48c9-8464-c4d021b6a48d'),('5bd6d7e2-5f6a-44de-9885-04f3241b52f9','email verified','openid-connect','oidc-usermodel-property-mapper',NULL,'c1514a42-f14c-48a6-a441-b3cf107d7edc'),('5e31bf5f-74cc-4e33-ac1c-93d9190d0b99','acr loa level','openid-connect','oidc-acr-mapper',NULL,'ecfcd03c-f091-4d6f-80a1-0b69716ce804'),('63f36368-9b7d-4162-ba88-ae0082561d0c','locale','openid-connect','oidc-usermodel-attribute-mapper','de191c36-54f9-4ce6-a3a6-695d1d3b7583',NULL),('65eb362c-d8e7-4cd4-ad1d-fdf5e7d89602','allowed web origins','openid-connect','oidc-allowed-origins-mapper',NULL,'112981ef-8abb-4615-9064-11e6b03d12db'),('700ff9a5-b2d0-4d47-a207-1829b16d0842','address','openid-connect','oidc-address-mapper',NULL,'e980a100-790b-48c9-b77c-413b2e8f8e2c'),('750f8e52-1491-4b67-b04c-39348f8b08e4','locale','openid-connect','oidc-usermodel-attribute-mapper',NULL,'0fa8d901-ba5d-45b6-a6ec-62461a8a8d07'),('77be5163-c452-467a-93d1-b6e010c6efeb','groups','openid-connect','oidc-usermodel-realm-role-mapper',NULL,'32ffc623-cc8a-495c-8fba-61c36becefd0'),('80072aa9-4db1-4e7d-9326-02fee98571f6','middle name','openid-connect','oidc-usermodel-attribute-mapper',NULL,'0fa8d901-ba5d-45b6-a6ec-62461a8a8d07'),('80e53625-336f-46bf-867b-4aea48b4f5da','acr loa level','openid-connect','oidc-acr-mapper',NULL,'dfd5755c-e278-4e23-ade7-d1516fc94a7d'),('85ca1203-83f6-4b8e-a7ab-5aace9574ae6','email verified','openid-connect','oidc-usermodel-property-mapper',NULL,'fe396b62-1b79-4557-9e57-6e0522bdf506'),('8696c4ad-ab31-41a5-bf34-6de6244db674','picture','openid-connect','oidc-usermodel-attribute-mapper',NULL,'0fa8d901-ba5d-45b6-a6ec-62461a8a8d07'),('90223460-6e43-451c-9cb4-27ff3ce50961','username','openid-connect','oidc-usermodel-attribute-mapper',NULL,'2b91a3e9-3562-48c9-8464-c4d021b6a48d'),('9765fde8-e5e0-483b-b340-d8ce03d91f60','locale','openid-connect','oidc-usermodel-attribute-mapper',NULL,'2b91a3e9-3562-48c9-8464-c4d021b6a48d'),('980b2178-4526-44cc-b170-de82c4f25eae','updated at','openid-connect','oidc-usermodel-attribute-mapper',NULL,'0fa8d901-ba5d-45b6-a6ec-62461a8a8d07'),('a081689d-8e9a-4065-a9c2-757e9c6ca7f2','website','openid-connect','oidc-usermodel-attribute-mapper',NULL,'0fa8d901-ba5d-45b6-a6ec-62461a8a8d07'),('a5497a67-9bd1-471e-8c54-5f561bb94a98','Client Roles','openid-connect','oidc-usermodel-client-role-mapper','138c5771-aaf5-495f-8480-05f1504a6c3a',NULL),('a81748c8-fbbe-4719-a168-cd5131c09ef7','nickname','openid-connect','oidc-usermodel-attribute-mapper',NULL,'0fa8d901-ba5d-45b6-a6ec-62461a8a8d07'),('ab1d319b-e7b3-44a6-b3af-81941868b5a4','audience resolve','openid-connect','oidc-audience-resolve-mapper',NULL,'c73b5dfb-50e1-4649-a20b-2cd7ceadeb54'),('b376fd72-4d81-452d-b0fe-9c1e6ab9361f','updated at','openid-connect','oidc-usermodel-attribute-mapper',NULL,'2b91a3e9-3562-48c9-8464-c4d021b6a48d'),('bd1032b4-a5d2-417f-98da-b480f015f0a8','groups','openid-connect','oidc-usermodel-realm-role-mapper',NULL,'3c466413-4163-4125-bf25-e85b9c2d9ad0'),('bf70700d-8664-4889-a9f8-5e3a8ccdc05c','birthdate','openid-connect','oidc-usermodel-attribute-mapper',NULL,'2b91a3e9-3562-48c9-8464-c4d021b6a48d'),('c7a1b289-8272-4521-9d2f-ae6c09509aaf','given name','openid-connect','oidc-usermodel-attribute-mapper',NULL,'2b91a3e9-3562-48c9-8464-c4d021b6a48d'),('c99d10ae-1cc2-4749-a80e-f3d942d13e68','phone number','openid-connect','oidc-usermodel-attribute-mapper',NULL,'2e4d99ab-3601-45d1-849e-b383678ec646'),('ca82b4fb-52bb-4a59-b6a8-cc24b67d0207','picture','openid-connect','oidc-usermodel-attribute-mapper',NULL,'2b91a3e9-3562-48c9-8464-c4d021b6a48d'),('cbdb1307-60a5-4f07-9167-ee6729014e1d','middle name','openid-connect','oidc-usermodel-attribute-mapper',NULL,'2b91a3e9-3562-48c9-8464-c4d021b6a48d'),('cf06e061-88a9-40a6-967d-0728819010aa','gender','openid-connect','oidc-usermodel-attribute-mapper',NULL,'2b91a3e9-3562-48c9-8464-c4d021b6a48d'),('cf1c5e28-c39c-448b-8427-7f459f4b8f43','audience resolve','openid-connect','oidc-audience-resolve-mapper',NULL,'9f6f73b8-23d0-4cb4-8d8b-95c033287f0d'),('daf2b850-1031-42e4-875e-388a89234351','username','openid-connect','oidc-usermodel-attribute-mapper',NULL,'0fa8d901-ba5d-45b6-a6ec-62461a8a8d07'),('db774fe8-f14f-4022-86bf-d5fd48b30245','email','openid-connect','oidc-usermodel-attribute-mapper',NULL,'c1514a42-f14c-48a6-a441-b3cf107d7edc'),('e7c733fa-d987-4337-8bbf-8e0380a3b2c7','locale','openid-connect','oidc-usermodel-attribute-mapper','28f1393f-9c6f-4b5e-b45b-e7708d58f86a',NULL),('ea9fa8f3-4e53-4ee8-be9a-640a60ca014a','phone number verified','openid-connect','oidc-usermodel-attribute-mapper',NULL,'0492c70e-dbb9-44fd-b926-36b2a480e707'),('ed82dc77-b06a-42cf-907f-f21f85f4d063','full name','openid-connect','oidc-full-name-mapper',NULL,'2b91a3e9-3562-48c9-8464-c4d021b6a48d'),('ee985a8c-6f53-4858-b6d2-23bafe3ba001','birthdate','openid-connect','oidc-usermodel-attribute-mapper',NULL,'0fa8d901-ba5d-45b6-a6ec-62461a8a8d07'),('f185ef04-5926-4132-974b-0e1c931d311a','address','openid-connect','oidc-address-mapper',NULL,'269709f9-050d-4363-b800-007a66bdc433'),('f6117639-7919-4417-a5bd-6c1e060e8b0c','zoneinfo','openid-connect','oidc-usermodel-attribute-mapper',NULL,'2b91a3e9-3562-48c9-8464-c4d021b6a48d'),('fbfe1e41-d446-4ac6-aacd-08bd421d62cb','zoneinfo','openid-connect','oidc-usermodel-attribute-mapper',NULL,'0fa8d901-ba5d-45b6-a6ec-62461a8a8d07');
/*!40000 ALTER TABLE `PROTOCOL_MAPPER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PROTOCOL_MAPPER_CONFIG`
--

DROP TABLE IF EXISTS `PROTOCOL_MAPPER_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PROTOCOL_MAPPER_CONFIG` (
                                          `PROTOCOL_MAPPER_ID` varchar(36) NOT NULL,
                                          `VALUE` longtext,
                                          `NAME` varchar(255) NOT NULL,
                                          PRIMARY KEY (`PROTOCOL_MAPPER_ID`,`NAME`),
                                          CONSTRAINT `FK_PMCONFIG` FOREIGN KEY (`PROTOCOL_MAPPER_ID`) REFERENCES `PROTOCOL_MAPPER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PROTOCOL_MAPPER_CONFIG`
--

LOCK TABLES `PROTOCOL_MAPPER_CONFIG` WRITE;
/*!40000 ALTER TABLE `PROTOCOL_MAPPER_CONFIG` DISABLE KEYS */;
INSERT INTO `PROTOCOL_MAPPER_CONFIG` VALUES ('0341adc7-5d0a-4dd6-8eb4-0dd3a55a07c8','true','access.token.claim'),('0341adc7-5d0a-4dd6-8eb4-0dd3a55a07c8','gender','claim.name'),('0341adc7-5d0a-4dd6-8eb4-0dd3a55a07c8','true','id.token.claim'),('0341adc7-5d0a-4dd6-8eb4-0dd3a55a07c8','String','jsonType.label'),('0341adc7-5d0a-4dd6-8eb4-0dd3a55a07c8','gender','user.attribute'),('0341adc7-5d0a-4dd6-8eb4-0dd3a55a07c8','true','userinfo.token.claim'),('07bf90dc-efff-4a9f-88c3-ce3926eb3f39','Role','attribute.name'),('07bf90dc-efff-4a9f-88c3-ce3926eb3f39','Basic','attribute.nameformat'),('07bf90dc-efff-4a9f-88c3-ce3926eb3f39','false','single'),('0a17655b-0638-4db6-aa2c-ecdc431f3a1b','true','access.token.claim'),('0a17655b-0638-4db6-aa2c-ecdc431f3a1b','profile','claim.name'),('0a17655b-0638-4db6-aa2c-ecdc431f3a1b','true','id.token.claim'),('0a17655b-0638-4db6-aa2c-ecdc431f3a1b','String','jsonType.label'),('0a17655b-0638-4db6-aa2c-ecdc431f3a1b','profile','user.attribute'),('0a17655b-0638-4db6-aa2c-ecdc431f3a1b','true','userinfo.token.claim'),('0b220cc9-6c7e-4e2d-b28b-c6d34866fabe','true','access.token.claim'),('0b220cc9-6c7e-4e2d-b28b-c6d34866fabe','given_name','claim.name'),('0b220cc9-6c7e-4e2d-b28b-c6d34866fabe','true','id.token.claim'),('0b220cc9-6c7e-4e2d-b28b-c6d34866fabe','String','jsonType.label'),('0b220cc9-6c7e-4e2d-b28b-c6d34866fabe','firstName','user.attribute'),('0b220cc9-6c7e-4e2d-b28b-c6d34866fabe','true','userinfo.token.claim'),('18ee3f98-41e4-4e8c-8c8a-93a13d394297','true','access.token.claim'),('18ee3f98-41e4-4e8c-8c8a-93a13d394297','upn','claim.name'),('18ee3f98-41e4-4e8c-8c8a-93a13d394297','true','id.token.claim'),('18ee3f98-41e4-4e8c-8c8a-93a13d394297','String','jsonType.label'),('18ee3f98-41e4-4e8c-8c8a-93a13d394297','username','user.attribute'),('18ee3f98-41e4-4e8c-8c8a-93a13d394297','true','userinfo.token.claim'),('22358647-5344-48d2-82a0-f82010b74ada','true','access.token.claim'),('22358647-5344-48d2-82a0-f82010b74ada','realm_access.roles','claim.name'),('22358647-5344-48d2-82a0-f82010b74ada','String','jsonType.label'),('22358647-5344-48d2-82a0-f82010b74ada','true','multivalued'),('22358647-5344-48d2-82a0-f82010b74ada','foo','user.attribute'),('23a9f132-4f91-4876-a27d-17f0542938d1','true','access.token.claim'),('23a9f132-4f91-4876-a27d-17f0542938d1','resource_access.${client_id}.roles','claim.name'),('23a9f132-4f91-4876-a27d-17f0542938d1','String','jsonType.label'),('23a9f132-4f91-4876-a27d-17f0542938d1','true','multivalued'),('23a9f132-4f91-4876-a27d-17f0542938d1','foo','user.attribute'),('2915541b-eadb-4818-b925-be0bf8c0c96e','true','access.token.claim'),('2915541b-eadb-4818-b925-be0bf8c0c96e','realm_access.roles','claim.name'),('2915541b-eadb-4818-b925-be0bf8c0c96e','String','jsonType.label'),('2915541b-eadb-4818-b925-be0bf8c0c96e','true','multivalued'),('2915541b-eadb-4818-b925-be0bf8c0c96e','foo','user.attribute'),('2c4a9b15-0fb8-4987-9875-8f9d6966f857','true','access.token.claim'),('2c4a9b15-0fb8-4987-9875-8f9d6966f857','phone_number','claim.name'),('2c4a9b15-0fb8-4987-9875-8f9d6966f857','true','id.token.claim'),('2c4a9b15-0fb8-4987-9875-8f9d6966f857','String','jsonType.label'),('2c4a9b15-0fb8-4987-9875-8f9d6966f857','phoneNumber','user.attribute'),('2c4a9b15-0fb8-4987-9875-8f9d6966f857','true','userinfo.token.claim'),('2e041753-949e-4861-8622-812919448f40','true','access.token.claim'),('2e041753-949e-4861-8622-812919448f40','true','id.token.claim'),('2e041753-949e-4861-8622-812919448f40','true','userinfo.token.claim'),('35cd8e62-9f79-4825-8a9c-d73d893e27d3','true','access.token.claim'),('35cd8e62-9f79-4825-8a9c-d73d893e27d3','website','claim.name'),('35cd8e62-9f79-4825-8a9c-d73d893e27d3','true','id.token.claim'),('35cd8e62-9f79-4825-8a9c-d73d893e27d3','String','jsonType.label'),('35cd8e62-9f79-4825-8a9c-d73d893e27d3','website','user.attribute'),('35cd8e62-9f79-4825-8a9c-d73d893e27d3','true','userinfo.token.claim'),('3d7ed671-7ae7-47ee-8107-ea310df03137','true','access.token.claim'),('3d7ed671-7ae7-47ee-8107-ea310df03137','profile','claim.name'),('3d7ed671-7ae7-47ee-8107-ea310df03137','true','id.token.claim'),('3d7ed671-7ae7-47ee-8107-ea310df03137','String','jsonType.label'),('3d7ed671-7ae7-47ee-8107-ea310df03137','profile','user.attribute'),('3d7ed671-7ae7-47ee-8107-ea310df03137','true','userinfo.token.claim'),('3f7573ed-6645-4e62-a7eb-4598aa10ba2d','true','access.token.claim'),('3f7573ed-6645-4e62-a7eb-4598aa10ba2d','email','claim.name'),('3f7573ed-6645-4e62-a7eb-4598aa10ba2d','true','id.token.claim'),('3f7573ed-6645-4e62-a7eb-4598aa10ba2d','String','jsonType.label'),('3f7573ed-6645-4e62-a7eb-4598aa10ba2d','email','user.attribute'),('3f7573ed-6645-4e62-a7eb-4598aa10ba2d','true','userinfo.token.claim'),('480bbe39-f02f-4dd1-b0b6-41eac41680a7','true','access.token.claim'),('480bbe39-f02f-4dd1-b0b6-41eac41680a7','phone_number_verified','claim.name'),('480bbe39-f02f-4dd1-b0b6-41eac41680a7','true','id.token.claim'),('480bbe39-f02f-4dd1-b0b6-41eac41680a7','boolean','jsonType.label'),('480bbe39-f02f-4dd1-b0b6-41eac41680a7','phoneNumberVerified','user.attribute'),('480bbe39-f02f-4dd1-b0b6-41eac41680a7','true','userinfo.token.claim'),('4a6e15a2-2159-4034-8580-f160517b5b4e','true','access.token.claim'),('4a6e15a2-2159-4034-8580-f160517b5b4e','upn','claim.name'),('4a6e15a2-2159-4034-8580-f160517b5b4e','true','id.token.claim'),('4a6e15a2-2159-4034-8580-f160517b5b4e','String','jsonType.label'),('4a6e15a2-2159-4034-8580-f160517b5b4e','username','user.attribute'),('4a6e15a2-2159-4034-8580-f160517b5b4e','true','userinfo.token.claim'),('4d8d7c47-c906-4cb0-ab84-548cdcb394f7','Role','attribute.name'),('4d8d7c47-c906-4cb0-ab84-548cdcb394f7','Basic','attribute.nameformat'),('4d8d7c47-c906-4cb0-ab84-548cdcb394f7','false','single'),('52eedb58-503a-442e-a49a-b6a0ff1df20b','true','access.token.claim'),('52eedb58-503a-442e-a49a-b6a0ff1df20b','resource_access.${client_id}.roles','claim.name'),('52eedb58-503a-442e-a49a-b6a0ff1df20b','String','jsonType.label'),('52eedb58-503a-442e-a49a-b6a0ff1df20b','true','multivalued'),('52eedb58-503a-442e-a49a-b6a0ff1df20b','foo','user.attribute'),('53a06cfa-4d51-4c3a-bdd5-dce4590dd1d1','true','access.token.claim'),('53a06cfa-4d51-4c3a-bdd5-dce4590dd1d1','family_name','claim.name'),('53a06cfa-4d51-4c3a-bdd5-dce4590dd1d1','true','id.token.claim'),('53a06cfa-4d51-4c3a-bdd5-dce4590dd1d1','String','jsonType.label'),('53a06cfa-4d51-4c3a-bdd5-dce4590dd1d1','lastName','user.attribute'),('53a06cfa-4d51-4c3a-bdd5-dce4590dd1d1','true','userinfo.token.claim'),('53f0c0fc-924d-4e21-aae6-15dd62e2588e','true','access.token.claim'),('53f0c0fc-924d-4e21-aae6-15dd62e2588e','family_name','claim.name'),('53f0c0fc-924d-4e21-aae6-15dd62e2588e','true','id.token.claim'),('53f0c0fc-924d-4e21-aae6-15dd62e2588e','String','jsonType.label'),('53f0c0fc-924d-4e21-aae6-15dd62e2588e','lastName','user.attribute'),('53f0c0fc-924d-4e21-aae6-15dd62e2588e','true','userinfo.token.claim'),('58a5c727-394b-4ca0-8346-3c9aa971df43','true','access.token.claim'),('58a5c727-394b-4ca0-8346-3c9aa971df43','nickname','claim.name'),('58a5c727-394b-4ca0-8346-3c9aa971df43','true','id.token.claim'),('58a5c727-394b-4ca0-8346-3c9aa971df43','String','jsonType.label'),('58a5c727-394b-4ca0-8346-3c9aa971df43','nickname','user.attribute'),('58a5c727-394b-4ca0-8346-3c9aa971df43','true','userinfo.token.claim'),('5bd6d7e2-5f6a-44de-9885-04f3241b52f9','true','access.token.claim'),('5bd6d7e2-5f6a-44de-9885-04f3241b52f9','email_verified','claim.name'),('5bd6d7e2-5f6a-44de-9885-04f3241b52f9','true','id.token.claim'),('5bd6d7e2-5f6a-44de-9885-04f3241b52f9','boolean','jsonType.label'),('5bd6d7e2-5f6a-44de-9885-04f3241b52f9','emailVerified','user.attribute'),('5bd6d7e2-5f6a-44de-9885-04f3241b52f9','true','userinfo.token.claim'),('5e31bf5f-74cc-4e33-ac1c-93d9190d0b99','true','access.token.claim'),('5e31bf5f-74cc-4e33-ac1c-93d9190d0b99','true','id.token.claim'),('5e31bf5f-74cc-4e33-ac1c-93d9190d0b99','true','userinfo.token.claim'),('63f36368-9b7d-4162-ba88-ae0082561d0c','true','access.token.claim'),('63f36368-9b7d-4162-ba88-ae0082561d0c','locale','claim.name'),('63f36368-9b7d-4162-ba88-ae0082561d0c','true','id.token.claim'),('63f36368-9b7d-4162-ba88-ae0082561d0c','String','jsonType.label'),('63f36368-9b7d-4162-ba88-ae0082561d0c','locale','user.attribute'),('63f36368-9b7d-4162-ba88-ae0082561d0c','true','userinfo.token.claim'),('700ff9a5-b2d0-4d47-a207-1829b16d0842','true','access.token.claim'),('700ff9a5-b2d0-4d47-a207-1829b16d0842','true','id.token.claim'),('700ff9a5-b2d0-4d47-a207-1829b16d0842','country','user.attribute.country'),('700ff9a5-b2d0-4d47-a207-1829b16d0842','formatted','user.attribute.formatted'),('700ff9a5-b2d0-4d47-a207-1829b16d0842','locality','user.attribute.locality'),('700ff9a5-b2d0-4d47-a207-1829b16d0842','postal_code','user.attribute.postal_code'),('700ff9a5-b2d0-4d47-a207-1829b16d0842','region','user.attribute.region'),('700ff9a5-b2d0-4d47-a207-1829b16d0842','street','user.attribute.street'),('700ff9a5-b2d0-4d47-a207-1829b16d0842','true','userinfo.token.claim'),('750f8e52-1491-4b67-b04c-39348f8b08e4','true','access.token.claim'),('750f8e52-1491-4b67-b04c-39348f8b08e4','locale','claim.name'),('750f8e52-1491-4b67-b04c-39348f8b08e4','true','id.token.claim'),('750f8e52-1491-4b67-b04c-39348f8b08e4','String','jsonType.label'),('750f8e52-1491-4b67-b04c-39348f8b08e4','locale','user.attribute'),('750f8e52-1491-4b67-b04c-39348f8b08e4','true','userinfo.token.claim'),('77be5163-c452-467a-93d1-b6e010c6efeb','true','access.token.claim'),('77be5163-c452-467a-93d1-b6e010c6efeb','groups','claim.name'),('77be5163-c452-467a-93d1-b6e010c6efeb','true','id.token.claim'),('77be5163-c452-467a-93d1-b6e010c6efeb','String','jsonType.label'),('77be5163-c452-467a-93d1-b6e010c6efeb','true','multivalued'),('77be5163-c452-467a-93d1-b6e010c6efeb','foo','user.attribute'),('77be5163-c452-467a-93d1-b6e010c6efeb','true','userinfo.token.claim'),('80072aa9-4db1-4e7d-9326-02fee98571f6','true','access.token.claim'),('80072aa9-4db1-4e7d-9326-02fee98571f6','middle_name','claim.name'),('80072aa9-4db1-4e7d-9326-02fee98571f6','true','id.token.claim'),('80072aa9-4db1-4e7d-9326-02fee98571f6','String','jsonType.label'),('80072aa9-4db1-4e7d-9326-02fee98571f6','middleName','user.attribute'),('80072aa9-4db1-4e7d-9326-02fee98571f6','true','userinfo.token.claim'),('80e53625-336f-46bf-867b-4aea48b4f5da','true','access.token.claim'),('80e53625-336f-46bf-867b-4aea48b4f5da','true','id.token.claim'),('85ca1203-83f6-4b8e-a7ab-5aace9574ae6','true','access.token.claim'),('85ca1203-83f6-4b8e-a7ab-5aace9574ae6','email_verified','claim.name'),('85ca1203-83f6-4b8e-a7ab-5aace9574ae6','true','id.token.claim'),('85ca1203-83f6-4b8e-a7ab-5aace9574ae6','boolean','jsonType.label'),('85ca1203-83f6-4b8e-a7ab-5aace9574ae6','emailVerified','user.attribute'),('85ca1203-83f6-4b8e-a7ab-5aace9574ae6','true','userinfo.token.claim'),('8696c4ad-ab31-41a5-bf34-6de6244db674','true','access.token.claim'),('8696c4ad-ab31-41a5-bf34-6de6244db674','picture','claim.name'),('8696c4ad-ab31-41a5-bf34-6de6244db674','true','id.token.claim'),('8696c4ad-ab31-41a5-bf34-6de6244db674','String','jsonType.label'),('8696c4ad-ab31-41a5-bf34-6de6244db674','picture','user.attribute'),('8696c4ad-ab31-41a5-bf34-6de6244db674','true','userinfo.token.claim'),('90223460-6e43-451c-9cb4-27ff3ce50961','true','access.token.claim'),('90223460-6e43-451c-9cb4-27ff3ce50961','preferred_username','claim.name'),('90223460-6e43-451c-9cb4-27ff3ce50961','true','id.token.claim'),('90223460-6e43-451c-9cb4-27ff3ce50961','String','jsonType.label'),('90223460-6e43-451c-9cb4-27ff3ce50961','username','user.attribute'),('90223460-6e43-451c-9cb4-27ff3ce50961','true','userinfo.token.claim'),('9765fde8-e5e0-483b-b340-d8ce03d91f60','true','access.token.claim'),('9765fde8-e5e0-483b-b340-d8ce03d91f60','locale','claim.name'),('9765fde8-e5e0-483b-b340-d8ce03d91f60','true','id.token.claim'),('9765fde8-e5e0-483b-b340-d8ce03d91f60','String','jsonType.label'),('9765fde8-e5e0-483b-b340-d8ce03d91f60','locale','user.attribute'),('9765fde8-e5e0-483b-b340-d8ce03d91f60','true','userinfo.token.claim'),('980b2178-4526-44cc-b170-de82c4f25eae','true','access.token.claim'),('980b2178-4526-44cc-b170-de82c4f25eae','updated_at','claim.name'),('980b2178-4526-44cc-b170-de82c4f25eae','true','id.token.claim'),('980b2178-4526-44cc-b170-de82c4f25eae','long','jsonType.label'),('980b2178-4526-44cc-b170-de82c4f25eae','updatedAt','user.attribute'),('980b2178-4526-44cc-b170-de82c4f25eae','true','userinfo.token.claim'),('a081689d-8e9a-4065-a9c2-757e9c6ca7f2','true','access.token.claim'),('a081689d-8e9a-4065-a9c2-757e9c6ca7f2','website','claim.name'),('a081689d-8e9a-4065-a9c2-757e9c6ca7f2','true','id.token.claim'),('a081689d-8e9a-4065-a9c2-757e9c6ca7f2','String','jsonType.label'),('a081689d-8e9a-4065-a9c2-757e9c6ca7f2','website','user.attribute'),('a081689d-8e9a-4065-a9c2-757e9c6ca7f2','true','userinfo.token.claim'),('a5497a67-9bd1-471e-8c54-5f561bb94a98','false','access.token.claim'),('a5497a67-9bd1-471e-8c54-5f561bb94a98','client_roles','claim.name'),('a5497a67-9bd1-471e-8c54-5f561bb94a98','true','id.token.claim'),('a5497a67-9bd1-471e-8c54-5f561bb94a98','String','jsonType.label'),('a5497a67-9bd1-471e-8c54-5f561bb94a98','true','multivalued'),('a5497a67-9bd1-471e-8c54-5f561bb94a98','true','userinfo.token.claim'),('a5497a67-9bd1-471e-8c54-5f561bb94a98','dodgame-api','usermodel.clientRoleMapping.clientId'),('a81748c8-fbbe-4719-a168-cd5131c09ef7','true','access.token.claim'),('a81748c8-fbbe-4719-a168-cd5131c09ef7','nickname','claim.name'),('a81748c8-fbbe-4719-a168-cd5131c09ef7','true','id.token.claim'),('a81748c8-fbbe-4719-a168-cd5131c09ef7','String','jsonType.label'),('a81748c8-fbbe-4719-a168-cd5131c09ef7','nickname','user.attribute'),('a81748c8-fbbe-4719-a168-cd5131c09ef7','true','userinfo.token.claim'),('b376fd72-4d81-452d-b0fe-9c1e6ab9361f','true','access.token.claim'),('b376fd72-4d81-452d-b0fe-9c1e6ab9361f','updated_at','claim.name'),('b376fd72-4d81-452d-b0fe-9c1e6ab9361f','true','id.token.claim'),('b376fd72-4d81-452d-b0fe-9c1e6ab9361f','long','jsonType.label'),('b376fd72-4d81-452d-b0fe-9c1e6ab9361f','updatedAt','user.attribute'),('b376fd72-4d81-452d-b0fe-9c1e6ab9361f','true','userinfo.token.claim'),('bd1032b4-a5d2-417f-98da-b480f015f0a8','true','access.token.claim'),('bd1032b4-a5d2-417f-98da-b480f015f0a8','groups','claim.name'),('bd1032b4-a5d2-417f-98da-b480f015f0a8','true','id.token.claim'),('bd1032b4-a5d2-417f-98da-b480f015f0a8','String','jsonType.label'),('bd1032b4-a5d2-417f-98da-b480f015f0a8','true','multivalued'),('bd1032b4-a5d2-417f-98da-b480f015f0a8','foo','user.attribute'),('bf70700d-8664-4889-a9f8-5e3a8ccdc05c','true','access.token.claim'),('bf70700d-8664-4889-a9f8-5e3a8ccdc05c','birthdate','claim.name'),('bf70700d-8664-4889-a9f8-5e3a8ccdc05c','true','id.token.claim'),('bf70700d-8664-4889-a9f8-5e3a8ccdc05c','String','jsonType.label'),('bf70700d-8664-4889-a9f8-5e3a8ccdc05c','birthdate','user.attribute'),('bf70700d-8664-4889-a9f8-5e3a8ccdc05c','true','userinfo.token.claim'),('c7a1b289-8272-4521-9d2f-ae6c09509aaf','true','access.token.claim'),('c7a1b289-8272-4521-9d2f-ae6c09509aaf','given_name','claim.name'),('c7a1b289-8272-4521-9d2f-ae6c09509aaf','true','id.token.claim'),('c7a1b289-8272-4521-9d2f-ae6c09509aaf','String','jsonType.label'),('c7a1b289-8272-4521-9d2f-ae6c09509aaf','firstName','user.attribute'),('c7a1b289-8272-4521-9d2f-ae6c09509aaf','true','userinfo.token.claim'),('c99d10ae-1cc2-4749-a80e-f3d942d13e68','true','access.token.claim'),('c99d10ae-1cc2-4749-a80e-f3d942d13e68','phone_number','claim.name'),('c99d10ae-1cc2-4749-a80e-f3d942d13e68','true','id.token.claim'),('c99d10ae-1cc2-4749-a80e-f3d942d13e68','String','jsonType.label'),('c99d10ae-1cc2-4749-a80e-f3d942d13e68','phoneNumber','user.attribute'),('c99d10ae-1cc2-4749-a80e-f3d942d13e68','true','userinfo.token.claim'),('ca82b4fb-52bb-4a59-b6a8-cc24b67d0207','true','access.token.claim'),('ca82b4fb-52bb-4a59-b6a8-cc24b67d0207','picture','claim.name'),('ca82b4fb-52bb-4a59-b6a8-cc24b67d0207','true','id.token.claim'),('ca82b4fb-52bb-4a59-b6a8-cc24b67d0207','String','jsonType.label'),('ca82b4fb-52bb-4a59-b6a8-cc24b67d0207','picture','user.attribute'),('ca82b4fb-52bb-4a59-b6a8-cc24b67d0207','true','userinfo.token.claim'),('cbdb1307-60a5-4f07-9167-ee6729014e1d','true','access.token.claim'),('cbdb1307-60a5-4f07-9167-ee6729014e1d','middle_name','claim.name'),('cbdb1307-60a5-4f07-9167-ee6729014e1d','true','id.token.claim'),('cbdb1307-60a5-4f07-9167-ee6729014e1d','String','jsonType.label'),('cbdb1307-60a5-4f07-9167-ee6729014e1d','middleName','user.attribute'),('cbdb1307-60a5-4f07-9167-ee6729014e1d','true','userinfo.token.claim'),('cf06e061-88a9-40a6-967d-0728819010aa','true','access.token.claim'),('cf06e061-88a9-40a6-967d-0728819010aa','gender','claim.name'),('cf06e061-88a9-40a6-967d-0728819010aa','true','id.token.claim'),('cf06e061-88a9-40a6-967d-0728819010aa','String','jsonType.label'),('cf06e061-88a9-40a6-967d-0728819010aa','gender','user.attribute'),('cf06e061-88a9-40a6-967d-0728819010aa','true','userinfo.token.claim'),('daf2b850-1031-42e4-875e-388a89234351','true','access.token.claim'),('daf2b850-1031-42e4-875e-388a89234351','preferred_username','claim.name'),('daf2b850-1031-42e4-875e-388a89234351','true','id.token.claim'),('daf2b850-1031-42e4-875e-388a89234351','String','jsonType.label'),('daf2b850-1031-42e4-875e-388a89234351','username','user.attribute'),('daf2b850-1031-42e4-875e-388a89234351','true','userinfo.token.claim'),('db774fe8-f14f-4022-86bf-d5fd48b30245','true','access.token.claim'),('db774fe8-f14f-4022-86bf-d5fd48b30245','email','claim.name'),('db774fe8-f14f-4022-86bf-d5fd48b30245','true','id.token.claim'),('db774fe8-f14f-4022-86bf-d5fd48b30245','String','jsonType.label'),('db774fe8-f14f-4022-86bf-d5fd48b30245','email','user.attribute'),('db774fe8-f14f-4022-86bf-d5fd48b30245','true','userinfo.token.claim'),('e7c733fa-d987-4337-8bbf-8e0380a3b2c7','true','access.token.claim'),('e7c733fa-d987-4337-8bbf-8e0380a3b2c7','locale','claim.name'),('e7c733fa-d987-4337-8bbf-8e0380a3b2c7','true','id.token.claim'),('e7c733fa-d987-4337-8bbf-8e0380a3b2c7','String','jsonType.label'),('e7c733fa-d987-4337-8bbf-8e0380a3b2c7','locale','user.attribute'),('e7c733fa-d987-4337-8bbf-8e0380a3b2c7','true','userinfo.token.claim'),('ea9fa8f3-4e53-4ee8-be9a-640a60ca014a','true','access.token.claim'),('ea9fa8f3-4e53-4ee8-be9a-640a60ca014a','phone_number_verified','claim.name'),('ea9fa8f3-4e53-4ee8-be9a-640a60ca014a','true','id.token.claim'),('ea9fa8f3-4e53-4ee8-be9a-640a60ca014a','boolean','jsonType.label'),('ea9fa8f3-4e53-4ee8-be9a-640a60ca014a','phoneNumberVerified','user.attribute'),('ea9fa8f3-4e53-4ee8-be9a-640a60ca014a','true','userinfo.token.claim'),('ed82dc77-b06a-42cf-907f-f21f85f4d063','true','access.token.claim'),('ed82dc77-b06a-42cf-907f-f21f85f4d063','true','id.token.claim'),('ed82dc77-b06a-42cf-907f-f21f85f4d063','true','userinfo.token.claim'),('ee985a8c-6f53-4858-b6d2-23bafe3ba001','true','access.token.claim'),('ee985a8c-6f53-4858-b6d2-23bafe3ba001','birthdate','claim.name'),('ee985a8c-6f53-4858-b6d2-23bafe3ba001','true','id.token.claim'),('ee985a8c-6f53-4858-b6d2-23bafe3ba001','String','jsonType.label'),('ee985a8c-6f53-4858-b6d2-23bafe3ba001','birthdate','user.attribute'),('ee985a8c-6f53-4858-b6d2-23bafe3ba001','true','userinfo.token.claim'),('f185ef04-5926-4132-974b-0e1c931d311a','true','access.token.claim'),('f185ef04-5926-4132-974b-0e1c931d311a','true','id.token.claim'),('f185ef04-5926-4132-974b-0e1c931d311a','country','user.attribute.country'),('f185ef04-5926-4132-974b-0e1c931d311a','formatted','user.attribute.formatted'),('f185ef04-5926-4132-974b-0e1c931d311a','locality','user.attribute.locality'),('f185ef04-5926-4132-974b-0e1c931d311a','postal_code','user.attribute.postal_code'),('f185ef04-5926-4132-974b-0e1c931d311a','region','user.attribute.region'),('f185ef04-5926-4132-974b-0e1c931d311a','street','user.attribute.street'),('f185ef04-5926-4132-974b-0e1c931d311a','true','userinfo.token.claim'),('f6117639-7919-4417-a5bd-6c1e060e8b0c','true','access.token.claim'),('f6117639-7919-4417-a5bd-6c1e060e8b0c','zoneinfo','claim.name'),('f6117639-7919-4417-a5bd-6c1e060e8b0c','true','id.token.claim'),('f6117639-7919-4417-a5bd-6c1e060e8b0c','String','jsonType.label'),('f6117639-7919-4417-a5bd-6c1e060e8b0c','zoneinfo','user.attribute'),('f6117639-7919-4417-a5bd-6c1e060e8b0c','true','userinfo.token.claim'),('fbfe1e41-d446-4ac6-aacd-08bd421d62cb','true','access.token.claim'),('fbfe1e41-d446-4ac6-aacd-08bd421d62cb','zoneinfo','claim.name'),('fbfe1e41-d446-4ac6-aacd-08bd421d62cb','true','id.token.claim'),('fbfe1e41-d446-4ac6-aacd-08bd421d62cb','String','jsonType.label'),('fbfe1e41-d446-4ac6-aacd-08bd421d62cb','zoneinfo','user.attribute'),('fbfe1e41-d446-4ac6-aacd-08bd421d62cb','true','userinfo.token.claim');
/*!40000 ALTER TABLE `PROTOCOL_MAPPER_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REALM`
--

DROP TABLE IF EXISTS `REALM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REALM` (
                         `ID` varchar(36) NOT NULL,
                         `ACCESS_CODE_LIFESPAN` int DEFAULT NULL,
                         `USER_ACTION_LIFESPAN` int DEFAULT NULL,
                         `ACCESS_TOKEN_LIFESPAN` int DEFAULT NULL,
                         `ACCOUNT_THEME` varchar(255) DEFAULT NULL,
                         `ADMIN_THEME` varchar(255) DEFAULT NULL,
                         `EMAIL_THEME` varchar(255) DEFAULT NULL,
                         `ENABLED` bit(1) NOT NULL DEFAULT b'0',
                         `EVENTS_ENABLED` bit(1) NOT NULL DEFAULT b'0',
                         `EVENTS_EXPIRATION` bigint DEFAULT NULL,
                         `LOGIN_THEME` varchar(255) DEFAULT NULL,
                         `NAME` varchar(255) DEFAULT NULL,
                         `NOT_BEFORE` int DEFAULT NULL,
                         `PASSWORD_POLICY` text,
                         `REGISTRATION_ALLOWED` bit(1) NOT NULL DEFAULT b'0',
                         `REMEMBER_ME` bit(1) NOT NULL DEFAULT b'0',
                         `RESET_PASSWORD_ALLOWED` bit(1) NOT NULL DEFAULT b'0',
                         `SOCIAL` bit(1) NOT NULL DEFAULT b'0',
                         `SSL_REQUIRED` varchar(255) DEFAULT NULL,
                         `SSO_IDLE_TIMEOUT` int DEFAULT NULL,
                         `SSO_MAX_LIFESPAN` int DEFAULT NULL,
                         `UPDATE_PROFILE_ON_SOC_LOGIN` bit(1) NOT NULL DEFAULT b'0',
                         `VERIFY_EMAIL` bit(1) NOT NULL DEFAULT b'0',
                         `MASTER_ADMIN_CLIENT` varchar(36) DEFAULT NULL,
                         `LOGIN_LIFESPAN` int DEFAULT NULL,
                         `INTERNATIONALIZATION_ENABLED` bit(1) NOT NULL DEFAULT b'0',
                         `DEFAULT_LOCALE` varchar(255) DEFAULT NULL,
                         `REG_EMAIL_AS_USERNAME` bit(1) NOT NULL DEFAULT b'0',
                         `ADMIN_EVENTS_ENABLED` bit(1) NOT NULL DEFAULT b'0',
                         `ADMIN_EVENTS_DETAILS_ENABLED` bit(1) NOT NULL DEFAULT b'0',
                         `EDIT_USERNAME_ALLOWED` bit(1) NOT NULL DEFAULT b'0',
                         `OTP_POLICY_COUNTER` int DEFAULT '0',
                         `OTP_POLICY_WINDOW` int DEFAULT '1',
                         `OTP_POLICY_PERIOD` int DEFAULT '30',
                         `OTP_POLICY_DIGITS` int DEFAULT '6',
                         `OTP_POLICY_ALG` varchar(36) DEFAULT 'HmacSHA1',
                         `OTP_POLICY_TYPE` varchar(36) DEFAULT 'totp',
                         `BROWSER_FLOW` varchar(36) DEFAULT NULL,
                         `REGISTRATION_FLOW` varchar(36) DEFAULT NULL,
                         `DIRECT_GRANT_FLOW` varchar(36) DEFAULT NULL,
                         `RESET_CREDENTIALS_FLOW` varchar(36) DEFAULT NULL,
                         `CLIENT_AUTH_FLOW` varchar(36) DEFAULT NULL,
                         `OFFLINE_SESSION_IDLE_TIMEOUT` int DEFAULT '0',
                         `REVOKE_REFRESH_TOKEN` bit(1) NOT NULL DEFAULT b'0',
                         `ACCESS_TOKEN_LIFE_IMPLICIT` int DEFAULT '0',
                         `LOGIN_WITH_EMAIL_ALLOWED` bit(1) NOT NULL DEFAULT b'1',
                         `DUPLICATE_EMAILS_ALLOWED` bit(1) NOT NULL DEFAULT b'0',
                         `DOCKER_AUTH_FLOW` varchar(36) DEFAULT NULL,
                         `REFRESH_TOKEN_MAX_REUSE` int DEFAULT '0',
                         `ALLOW_USER_MANAGED_ACCESS` bit(1) NOT NULL DEFAULT b'0',
                         `SSO_MAX_LIFESPAN_REMEMBER_ME` int NOT NULL,
                         `SSO_IDLE_TIMEOUT_REMEMBER_ME` int NOT NULL,
                         `DEFAULT_ROLE` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`ID`),
                         UNIQUE KEY `UK_ORVSDMLA56612EAEFIQ6WL5OI` (`NAME`),
                         KEY `IDX_REALM_MASTER_ADM_CLI` (`MASTER_ADMIN_CLIENT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REALM`
--

LOCK TABLES `REALM` WRITE;
/*!40000 ALTER TABLE `REALM` DISABLE KEYS */;
INSERT INTO `REALM` VALUES ('0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f',60,300,60,NULL,NULL,NULL,'','\0',0,NULL,'master',0,NULL,'\0','\0','\0','\0','EXTERNAL',1800,36000,'\0','\0','950de219-4129-4d3b-8919-0987a1a13174',1800,'\0',NULL,'\0','\0','\0','\0',0,1,30,6,'HmacSHA1','totp','e32d377b-79a7-411b-bb2a-b6437f19b770','35b2c286-e6ac-460a-965a-ed59ceda5bcc','c0498828-7e44-4b44-b560-9d4866bb9aac','7eaf199c-da4c-41fb-96e8-ac5f8f6e8ac8','a0f0f47f-9fb3-4ccd-9a26-03bca5eeedc3',2592000,'\0',900,'','\0','e241bd16-f27e-4f1b-9afa-b876226f2905',0,'\0',0,0,'bff87df8-9dd0-42ef-892c-33e8267244a5'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5',60,300,300,NULL,NULL,NULL,'','',3600,NULL,'dodgame',1692812956,NULL,'','\0','','\0','NONE',1800,36000,'\0','\0','1ff4cae9-f76a-4e4b-a700-c272c99710d6',1800,'\0',NULL,'\0','\0','\0','\0',0,1,30,6,'HmacSHA1','totp','be9390f9-79bb-45d3-a64a-83363361426c','54999abc-689c-4cef-87d3-288767117ed4','c6481670-4293-4d25-b7b1-e4cf14bc29ea','fb0c2ad3-fde6-4fab-94a6-b8716c2063a7','1bdeee37-80a5-49af-9f5d-e23f547fe2f0',2592000,'\0',900,'','\0','10d0ec81-6450-4859-9ada-7a549bb929f9',0,'',0,0,'f3e73123-f39a-4284-a4d8-cee68cd5e898');
/*!40000 ALTER TABLE `REALM` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REALM_ATTRIBUTE`
--

DROP TABLE IF EXISTS `REALM_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REALM_ATTRIBUTE` (
                                   `NAME` varchar(255) NOT NULL,
                                   `REALM_ID` varchar(36) NOT NULL,
                                   `VALUE` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
                                   PRIMARY KEY (`NAME`,`REALM_ID`),
                                   KEY `IDX_REALM_ATTR_REALM` (`REALM_ID`),
                                   CONSTRAINT `FK_8SHXD6L3E9ATQUKACXGPFFPTW` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REALM_ATTRIBUTE`
--

LOCK TABLES `REALM_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `REALM_ATTRIBUTE` DISABLE KEYS */;
INSERT INTO `REALM_ATTRIBUTE` VALUES ('acr.loa.map','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','{}'),('actionTokenGeneratedByAdminLifespan','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','43200'),('actionTokenGeneratedByUserLifespan','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','300'),('bruteForceProtected','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','false'),('bruteForceProtected','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','false'),('cibaAuthRequestedUserHint','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','login_hint'),('cibaBackchannelTokenDeliveryMode','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','poll'),('cibaExpiresIn','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','120'),('cibaInterval','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','5'),('client-policies.policies','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','{\"policies\":[]}'),('client-policies.profiles','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','{\"profiles\":[]}'),('clientOfflineSessionIdleTimeout','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','0'),('clientOfflineSessionMaxLifespan','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','0'),('clientSessionIdleTimeout','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','0'),('clientSessionMaxLifespan','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','0'),('defaultSignatureAlgorithm','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','RS256'),('defaultSignatureAlgorithm','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','RS256'),('displayName','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','Keycloak'),('displayName','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','Drager & Dæmoner'),('displayNameHtml','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','<div class=\"kc-logo-text\"><span>Keycloak</span></div>'),('displayNameHtml','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','DoD'),('failureFactor','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','30'),('failureFactor','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','30'),('frontendUrl','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','http://security:8181/'),('maxDeltaTimeSeconds','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','43200'),('maxDeltaTimeSeconds','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','43200'),('maxFailureWaitSeconds','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','900'),('maxFailureWaitSeconds','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','900'),('minimumQuickLoginWaitSeconds','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','60'),('minimumQuickLoginWaitSeconds','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','60'),('oauth2DeviceCodeLifespan','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','600'),('oauth2DevicePollingInterval','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','5'),('offlineSessionMaxLifespan','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','5184000'),('offlineSessionMaxLifespan','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','5184000'),('offlineSessionMaxLifespanEnabled','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','false'),('offlineSessionMaxLifespanEnabled','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','false'),('parRequestUriLifespan','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','60'),('permanentLockout','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','false'),('permanentLockout','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','false'),('quickLoginCheckMilliSeconds','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1000'),('quickLoginCheckMilliSeconds','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','1000'),('realmReusableOtpCode','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','false'),('realmReusableOtpCode','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','false'),('waitIncrementSeconds','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','60'),('waitIncrementSeconds','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','60'),('webAuthnPolicyAttestationConveyancePreference','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','not specified'),('webAuthnPolicyAttestationConveyancePreferencePasswordless','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','not specified'),('webAuthnPolicyAuthenticatorAttachment','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','not specified'),('webAuthnPolicyAuthenticatorAttachmentPasswordless','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','not specified'),('webAuthnPolicyAvoidSameAuthenticatorRegister','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','false'),('webAuthnPolicyAvoidSameAuthenticatorRegisterPasswordless','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','false'),('webAuthnPolicyCreateTimeout','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','0'),('webAuthnPolicyCreateTimeoutPasswordless','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','0'),('webAuthnPolicyRequireResidentKey','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','not specified'),('webAuthnPolicyRequireResidentKeyPasswordless','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','not specified'),('webAuthnPolicyRpEntityName','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','keycloak'),('webAuthnPolicyRpEntityNamePasswordless','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','keycloak'),('webAuthnPolicyRpId','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5',''),('webAuthnPolicyRpIdPasswordless','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5',''),('webAuthnPolicySignatureAlgorithms','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','ES256'),('webAuthnPolicySignatureAlgorithmsPasswordless','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','ES256'),('webAuthnPolicyUserVerificationRequirement','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','not specified'),('webAuthnPolicyUserVerificationRequirementPasswordless','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','not specified'),('_browser_header.contentSecurityPolicy','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','frame-src \'self\'; frame-ancestors \'self\'; object-src \'none\';'),('_browser_header.contentSecurityPolicy','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','frame-src \'self\'; frame-ancestors \'self\'; object-src \'none\';'),('_browser_header.contentSecurityPolicyReportOnly','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f',''),('_browser_header.contentSecurityPolicyReportOnly','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5',''),('_browser_header.referrerPolicy','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','no-referrer'),('_browser_header.referrerPolicy','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','no-referrer'),('_browser_header.strictTransportSecurity','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','max-age=31536000; includeSubDomains'),('_browser_header.strictTransportSecurity','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','max-age=31536000; includeSubDomains'),('_browser_header.xContentTypeOptions','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','nosniff'),('_browser_header.xContentTypeOptions','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','nosniff'),('_browser_header.xFrameOptions','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','SAMEORIGIN'),('_browser_header.xFrameOptions','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','SAMEORIGIN'),('_browser_header.xRobotsTag','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','none'),('_browser_header.xRobotsTag','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','none'),('_browser_header.xXSSProtection','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','1; mode=block'),('_browser_header.xXSSProtection','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','1; mode=block');
/*!40000 ALTER TABLE `REALM_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REALM_DEFAULT_GROUPS`
--

DROP TABLE IF EXISTS `REALM_DEFAULT_GROUPS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REALM_DEFAULT_GROUPS` (
                                        `REALM_ID` varchar(36) NOT NULL,
                                        `GROUP_ID` varchar(36) NOT NULL,
                                        PRIMARY KEY (`REALM_ID`,`GROUP_ID`),
                                        UNIQUE KEY `CON_GROUP_ID_DEF_GROUPS` (`GROUP_ID`),
                                        KEY `IDX_REALM_DEF_GRP_REALM` (`REALM_ID`),
                                        CONSTRAINT `FK_DEF_GROUPS_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REALM_DEFAULT_GROUPS`
--

LOCK TABLES `REALM_DEFAULT_GROUPS` WRITE;
/*!40000 ALTER TABLE `REALM_DEFAULT_GROUPS` DISABLE KEYS */;
/*!40000 ALTER TABLE `REALM_DEFAULT_GROUPS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REALM_ENABLED_EVENT_TYPES`
--

DROP TABLE IF EXISTS `REALM_ENABLED_EVENT_TYPES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REALM_ENABLED_EVENT_TYPES` (
                                             `REALM_ID` varchar(36) NOT NULL,
                                             `VALUE` varchar(255) NOT NULL,
                                             PRIMARY KEY (`REALM_ID`,`VALUE`),
                                             KEY `IDX_REALM_EVT_TYPES_REALM` (`REALM_ID`),
                                             CONSTRAINT `FK_H846O4H0W8EPX5NWEDRF5Y69J` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REALM_ENABLED_EVENT_TYPES`
--

LOCK TABLES `REALM_ENABLED_EVENT_TYPES` WRITE;
/*!40000 ALTER TABLE `REALM_ENABLED_EVENT_TYPES` DISABLE KEYS */;
INSERT INTO `REALM_ENABLED_EVENT_TYPES` VALUES ('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','AUTHREQID_TO_TOKEN'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','AUTHREQID_TO_TOKEN_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','CLIENT_DELETE'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','CLIENT_DELETE_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','CLIENT_INITIATED_ACCOUNT_LINKING'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','CLIENT_INITIATED_ACCOUNT_LINKING_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','CLIENT_LOGIN'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','CLIENT_LOGIN_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','CLIENT_REGISTER'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','CLIENT_REGISTER_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','CLIENT_UPDATE'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','CLIENT_UPDATE_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','CODE_TO_TOKEN'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','CODE_TO_TOKEN_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','CUSTOM_REQUIRED_ACTION'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','CUSTOM_REQUIRED_ACTION_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','DELETE_ACCOUNT'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','DELETE_ACCOUNT_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','EXECUTE_ACTIONS'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','EXECUTE_ACTIONS_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','EXECUTE_ACTION_TOKEN'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','EXECUTE_ACTION_TOKEN_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','FEDERATED_IDENTITY_LINK'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','FEDERATED_IDENTITY_LINK_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','GRANT_CONSENT'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','GRANT_CONSENT_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','IDENTITY_PROVIDER_FIRST_LOGIN'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','IDENTITY_PROVIDER_FIRST_LOGIN_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','IDENTITY_PROVIDER_LINK_ACCOUNT'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','IDENTITY_PROVIDER_LINK_ACCOUNT_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','IDENTITY_PROVIDER_POST_LOGIN'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','IDENTITY_PROVIDER_POST_LOGIN_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','IMPERSONATE'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','IMPERSONATE_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','LOGIN'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','LOGIN_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','LOGOUT'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','LOGOUT_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','OAUTH2_DEVICE_AUTH'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','OAUTH2_DEVICE_AUTH_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','OAUTH2_DEVICE_CODE_TO_TOKEN'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','OAUTH2_DEVICE_CODE_TO_TOKEN_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','OAUTH2_DEVICE_VERIFY_USER_CODE'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','OAUTH2_DEVICE_VERIFY_USER_CODE_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','PERMISSION_TOKEN'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','REGISTER'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','REGISTER_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','REMOVE_FEDERATED_IDENTITY'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','REMOVE_FEDERATED_IDENTITY_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','REMOVE_TOTP'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','REMOVE_TOTP_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','RESET_PASSWORD'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','RESET_PASSWORD_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','RESTART_AUTHENTICATION'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','RESTART_AUTHENTICATION_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','REVOKE_GRANT'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','REVOKE_GRANT_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','SEND_IDENTITY_PROVIDER_LINK'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','SEND_IDENTITY_PROVIDER_LINK_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','SEND_RESET_PASSWORD'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','SEND_RESET_PASSWORD_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','SEND_VERIFY_EMAIL'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','SEND_VERIFY_EMAIL_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','TOKEN_EXCHANGE'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','TOKEN_EXCHANGE_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','UPDATE_CONSENT'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','UPDATE_CONSENT_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','UPDATE_EMAIL'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','UPDATE_EMAIL_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','UPDATE_PASSWORD'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','UPDATE_PASSWORD_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','UPDATE_PROFILE'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','UPDATE_PROFILE_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','UPDATE_TOTP'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','UPDATE_TOTP_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','VERIFY_EMAIL'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','VERIFY_EMAIL_ERROR'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','VERIFY_PROFILE'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','VERIFY_PROFILE_ERROR');
/*!40000 ALTER TABLE `REALM_ENABLED_EVENT_TYPES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REALM_EVENTS_LISTENERS`
--

DROP TABLE IF EXISTS `REALM_EVENTS_LISTENERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REALM_EVENTS_LISTENERS` (
                                          `REALM_ID` varchar(36) NOT NULL,
                                          `VALUE` varchar(255) NOT NULL,
                                          PRIMARY KEY (`REALM_ID`,`VALUE`),
                                          KEY `IDX_REALM_EVT_LIST_REALM` (`REALM_ID`),
                                          CONSTRAINT `FK_H846O4H0W8EPX5NXEV9F5Y69J` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REALM_EVENTS_LISTENERS`
--

LOCK TABLES `REALM_EVENTS_LISTENERS` WRITE;
/*!40000 ALTER TABLE `REALM_EVENTS_LISTENERS` DISABLE KEYS */;
INSERT INTO `REALM_EVENTS_LISTENERS` VALUES ('0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','jboss-logging'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','jboss-logging');
/*!40000 ALTER TABLE `REALM_EVENTS_LISTENERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REALM_LOCALIZATIONS`
--

DROP TABLE IF EXISTS `REALM_LOCALIZATIONS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REALM_LOCALIZATIONS` (
                                       `REALM_ID` varchar(255) NOT NULL,
                                       `LOCALE` varchar(255) NOT NULL,
                                       `TEXTS` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                       PRIMARY KEY (`REALM_ID`,`LOCALE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REALM_LOCALIZATIONS`
--

LOCK TABLES `REALM_LOCALIZATIONS` WRITE;
/*!40000 ALTER TABLE `REALM_LOCALIZATIONS` DISABLE KEYS */;
/*!40000 ALTER TABLE `REALM_LOCALIZATIONS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REALM_REQUIRED_CREDENTIAL`
--

DROP TABLE IF EXISTS `REALM_REQUIRED_CREDENTIAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REALM_REQUIRED_CREDENTIAL` (
                                             `TYPE` varchar(255) NOT NULL,
                                             `FORM_LABEL` varchar(255) DEFAULT NULL,
                                             `INPUT` bit(1) NOT NULL DEFAULT b'0',
                                             `SECRET` bit(1) NOT NULL DEFAULT b'0',
                                             `REALM_ID` varchar(36) NOT NULL,
                                             PRIMARY KEY (`REALM_ID`,`TYPE`),
                                             CONSTRAINT `FK_5HG65LYBEVAVKQFKI3KPONH9V` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REALM_REQUIRED_CREDENTIAL`
--

LOCK TABLES `REALM_REQUIRED_CREDENTIAL` WRITE;
/*!40000 ALTER TABLE `REALM_REQUIRED_CREDENTIAL` DISABLE KEYS */;
INSERT INTO `REALM_REQUIRED_CREDENTIAL` VALUES ('password','password','','','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f'),('password','password','','','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5');
/*!40000 ALTER TABLE `REALM_REQUIRED_CREDENTIAL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REALM_SMTP_CONFIG`
--

DROP TABLE IF EXISTS `REALM_SMTP_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REALM_SMTP_CONFIG` (
                                     `REALM_ID` varchar(36) NOT NULL,
                                     `VALUE` varchar(255) DEFAULT NULL,
                                     `NAME` varchar(255) NOT NULL,
                                     PRIMARY KEY (`REALM_ID`,`NAME`),
                                     CONSTRAINT `FK_70EJ8XDXGXD0B9HH6180IRR0O` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REALM_SMTP_CONFIG`
--

LOCK TABLES `REALM_SMTP_CONFIG` WRITE;
/*!40000 ALTER TABLE `REALM_SMTP_CONFIG` DISABLE KEYS */;
INSERT INTO `REALM_SMTP_CONFIG` VALUES ('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','true','auth'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','','envelopeFrom'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','dragerdaemoner@gmail.com','from'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','DoD Game','fromDisplayName'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','smtp.gmail.com','host'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','**********','password'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','465','port'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','dragerdaemoner@gmail.com','replyTo'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','DoD Game','replyToDisplayName'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','true','ssl'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','true','starttls'),('31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','dragerdaemoner@gmail.com','user');
/*!40000 ALTER TABLE `REALM_SMTP_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REALM_SUPPORTED_LOCALES`
--

DROP TABLE IF EXISTS `REALM_SUPPORTED_LOCALES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REALM_SUPPORTED_LOCALES` (
                                           `REALM_ID` varchar(36) NOT NULL,
                                           `VALUE` varchar(255) NOT NULL,
                                           PRIMARY KEY (`REALM_ID`,`VALUE`),
                                           KEY `IDX_REALM_SUPP_LOCAL_REALM` (`REALM_ID`),
                                           CONSTRAINT `FK_SUPPORTED_LOCALES_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REALM_SUPPORTED_LOCALES`
--

LOCK TABLES `REALM_SUPPORTED_LOCALES` WRITE;
/*!40000 ALTER TABLE `REALM_SUPPORTED_LOCALES` DISABLE KEYS */;
/*!40000 ALTER TABLE `REALM_SUPPORTED_LOCALES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REDIRECT_URIS`
--

DROP TABLE IF EXISTS `REDIRECT_URIS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REDIRECT_URIS` (
                                 `CLIENT_ID` varchar(36) NOT NULL,
                                 `VALUE` varchar(255) NOT NULL,
                                 PRIMARY KEY (`CLIENT_ID`,`VALUE`),
                                 KEY `IDX_REDIR_URI_CLIENT` (`CLIENT_ID`),
                                 CONSTRAINT `FK_1BURS8PB4OUJ97H5WUPPAHV9F` FOREIGN KEY (`CLIENT_ID`) REFERENCES `CLIENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REDIRECT_URIS`
--

LOCK TABLES `REDIRECT_URIS` WRITE;
/*!40000 ALTER TABLE `REDIRECT_URIS` DISABLE KEYS */;
INSERT INTO `REDIRECT_URIS` VALUES ('138c5771-aaf5-495f-8480-05f1504a6c3a','http://localhost:3000/*'),('138c5771-aaf5-495f-8480-05f1504a6c3a','http://ui:3000/*'),('138c5771-aaf5-495f-8480-05f1504a6c3a','http://ui:80/*'),('138c5771-aaf5-495f-8480-05f1504a6c3a','http://ui:8081/*'),('28f1393f-9c6f-4b5e-b45b-e7708d58f86a','/admin/master/console/*'),('34eac0d0-6d27-4bc5-931d-dcd506948309','/realms/dodgame/account/*'),('529e6128-c1bc-4008-ac4d-6d645f6fe9d4','/realms/master/account/*'),('ced0633a-f3d9-4c75-b85d-1c62fb955164','/realms/dodgame/account/*'),('d43f3531-9d53-401b-a17e-59e6e6664b6a','/realms/master/account/*'),('de191c36-54f9-4ce6-a3a6-695d1d3b7583','/admin/dodgame/console/*');
/*!40000 ALTER TABLE `REDIRECT_URIS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REQUIRED_ACTION_CONFIG`
--

DROP TABLE IF EXISTS `REQUIRED_ACTION_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REQUIRED_ACTION_CONFIG` (
                                          `REQUIRED_ACTION_ID` varchar(36) NOT NULL,
                                          `VALUE` longtext,
                                          `NAME` varchar(255) NOT NULL,
                                          PRIMARY KEY (`REQUIRED_ACTION_ID`,`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REQUIRED_ACTION_CONFIG`
--

LOCK TABLES `REQUIRED_ACTION_CONFIG` WRITE;
/*!40000 ALTER TABLE `REQUIRED_ACTION_CONFIG` DISABLE KEYS */;
/*!40000 ALTER TABLE `REQUIRED_ACTION_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REQUIRED_ACTION_PROVIDER`
--

DROP TABLE IF EXISTS `REQUIRED_ACTION_PROVIDER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REQUIRED_ACTION_PROVIDER` (
                                            `ID` varchar(36) NOT NULL,
                                            `ALIAS` varchar(255) DEFAULT NULL,
                                            `NAME` varchar(255) DEFAULT NULL,
                                            `REALM_ID` varchar(36) DEFAULT NULL,
                                            `ENABLED` bit(1) NOT NULL DEFAULT b'0',
                                            `DEFAULT_ACTION` bit(1) NOT NULL DEFAULT b'0',
                                            `PROVIDER_ID` varchar(255) DEFAULT NULL,
                                            `PRIORITY` int DEFAULT NULL,
                                            PRIMARY KEY (`ID`),
                                            KEY `IDX_REQ_ACT_PROV_REALM` (`REALM_ID`),
                                            CONSTRAINT `FK_REQ_ACT_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REQUIRED_ACTION_PROVIDER`
--

LOCK TABLES `REQUIRED_ACTION_PROVIDER` WRITE;
/*!40000 ALTER TABLE `REQUIRED_ACTION_PROVIDER` DISABLE KEYS */;
INSERT INTO `REQUIRED_ACTION_PROVIDER` VALUES ('11e9fd01-fa3d-452b-b0bb-748b417d3c26','update_user_locale','Update User Locale','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','','\0','update_user_locale',1000),('1843b3e7-6046-43f4-b71b-e039bce01d57','webauthn-register-passwordless','Webauthn Register Passwordless','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','','\0','webauthn-register-passwordless',80),('26f931ad-09f0-4c0e-937e-05fca26cc502','delete_account','Delete Account','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','\0','\0','delete_account',60),('29a39444-21ad-4c56-8c64-832f86713f2c','delete_account','Delete Account','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','\0','\0','delete_account',60),('2bd7784b-fdd0-4fdd-a13c-92e0ffcbcb77','VERIFY_EMAIL','Verify Email','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','','\0','VERIFY_EMAIL',50),('2eb84737-8795-4214-b92f-087f9946241f','UPDATE_PROFILE','Update Profile','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','','\0','UPDATE_PROFILE',40),('33656097-5897-4dc6-8545-fd1956b7e23d','TERMS_AND_CONDITIONS','Terms and Conditions','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','\0','\0','TERMS_AND_CONDITIONS',20),('35683938-bfd8-49c2-937f-725e668e3887','UPDATE_PASSWORD','Update Password','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','','\0','UPDATE_PASSWORD',30),('4919ef68-1892-43be-8e44-ed0dbc079fb8','UPDATE_PROFILE','Update Profile','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','','\0','UPDATE_PROFILE',40),('50a5c1e1-ddb6-49fb-b7b7-b8f189e7139d','UPDATE_PASSWORD','Update Password','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','','\0','UPDATE_PASSWORD',30),('6acf1a04-0f33-44bc-9fff-7e1e5a688b08','update_user_locale','Update User Locale','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','','\0','update_user_locale',1000),('923ca72f-6741-4bc9-bc70-aa01233573b3','VERIFY_EMAIL','Verify Email','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','','\0','VERIFY_EMAIL',50),('a8dbf83e-e0f0-42ad-a022-1620934c22ee','webauthn-register','Webauthn Register','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','','\0','webauthn-register',70),('abc6c3cd-10c6-475e-8cf1-4f57a4fe9fa9','CONFIGURE_TOTP','Configure OTP','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','','\0','CONFIGURE_TOTP',10),('bc98924f-3c9a-4351-8872-7c86ef4c0043','CONFIGURE_TOTP','Configure OTP','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','','\0','CONFIGURE_TOTP',10),('c5d69f97-9144-4aef-b820-074aafd717a1','webauthn-register','Webauthn Register','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','','\0','webauthn-register',70),('dfac1f19-6d96-478c-aec3-ec370668b5d1','TERMS_AND_CONDITIONS','Terms and Conditions','0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','\0','\0','TERMS_AND_CONDITIONS',20),('fb33a6a5-e606-42e4-95c6-ac6772849f05','webauthn-register-passwordless','Webauthn Register Passwordless','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','','\0','webauthn-register-passwordless',80);
/*!40000 ALTER TABLE `REQUIRED_ACTION_PROVIDER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE_ATTRIBUTE`
--

DROP TABLE IF EXISTS `RESOURCE_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE_ATTRIBUTE` (
                                      `ID` varchar(36) NOT NULL DEFAULT 'sybase-needs-something-here',
                                      `NAME` varchar(255) NOT NULL,
                                      `VALUE` varchar(255) DEFAULT NULL,
                                      `RESOURCE_ID` varchar(36) NOT NULL,
                                      PRIMARY KEY (`ID`),
                                      KEY `FK_5HRM2VLF9QL5FU022KQEPOVBR` (`RESOURCE_ID`),
                                      CONSTRAINT `FK_5HRM2VLF9QL5FU022KQEPOVBR` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `RESOURCE_SERVER_RESOURCE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE_ATTRIBUTE`
--

LOCK TABLES `RESOURCE_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `RESOURCE_ATTRIBUTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESOURCE_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE_POLICY`
--

DROP TABLE IF EXISTS `RESOURCE_POLICY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE_POLICY` (
                                   `RESOURCE_ID` varchar(36) NOT NULL,
                                   `POLICY_ID` varchar(36) NOT NULL,
                                   PRIMARY KEY (`RESOURCE_ID`,`POLICY_ID`),
                                   KEY `IDX_RES_POLICY_POLICY` (`POLICY_ID`),
                                   CONSTRAINT `FK_FRSRPOS53XCX4WNKOG82SSRFY` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `RESOURCE_SERVER_RESOURCE` (`ID`),
                                   CONSTRAINT `FK_FRSRPP213XCX4WNKOG82SSRFY` FOREIGN KEY (`POLICY_ID`) REFERENCES `RESOURCE_SERVER_POLICY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE_POLICY`
--

LOCK TABLES `RESOURCE_POLICY` WRITE;
/*!40000 ALTER TABLE `RESOURCE_POLICY` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESOURCE_POLICY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE_SCOPE`
--

DROP TABLE IF EXISTS `RESOURCE_SCOPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE_SCOPE` (
                                  `RESOURCE_ID` varchar(36) NOT NULL,
                                  `SCOPE_ID` varchar(36) NOT NULL,
                                  PRIMARY KEY (`RESOURCE_ID`,`SCOPE_ID`),
                                  KEY `IDX_RES_SCOPE_SCOPE` (`SCOPE_ID`),
                                  CONSTRAINT `FK_FRSRPOS13XCX4WNKOG82SSRFY` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `RESOURCE_SERVER_RESOURCE` (`ID`),
                                  CONSTRAINT `FK_FRSRPS213XCX4WNKOG82SSRFY` FOREIGN KEY (`SCOPE_ID`) REFERENCES `RESOURCE_SERVER_SCOPE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE_SCOPE`
--

LOCK TABLES `RESOURCE_SCOPE` WRITE;
/*!40000 ALTER TABLE `RESOURCE_SCOPE` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESOURCE_SCOPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE_SERVER`
--

DROP TABLE IF EXISTS `RESOURCE_SERVER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE_SERVER` (
                                   `ID` varchar(36) NOT NULL,
                                   `ALLOW_RS_REMOTE_MGMT` bit(1) NOT NULL DEFAULT b'0',
                                   `POLICY_ENFORCE_MODE` tinyint DEFAULT NULL,
                                   `DECISION_STRATEGY` tinyint NOT NULL DEFAULT '1',
                                   PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE_SERVER`
--

LOCK TABLES `RESOURCE_SERVER` WRITE;
/*!40000 ALTER TABLE `RESOURCE_SERVER` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESOURCE_SERVER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE_SERVER_PERM_TICKET`
--

DROP TABLE IF EXISTS `RESOURCE_SERVER_PERM_TICKET`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE_SERVER_PERM_TICKET` (
                                               `ID` varchar(36) NOT NULL,
                                               `OWNER` varchar(255) DEFAULT NULL,
                                               `REQUESTER` varchar(255) DEFAULT NULL,
                                               `CREATED_TIMESTAMP` bigint NOT NULL,
                                               `GRANTED_TIMESTAMP` bigint DEFAULT NULL,
                                               `RESOURCE_ID` varchar(36) NOT NULL,
                                               `SCOPE_ID` varchar(36) DEFAULT NULL,
                                               `RESOURCE_SERVER_ID` varchar(36) NOT NULL,
                                               `POLICY_ID` varchar(36) DEFAULT NULL,
                                               PRIMARY KEY (`ID`),
                                               UNIQUE KEY `UK_FRSR6T700S9V50BU18WS5PMT` (`OWNER`,`REQUESTER`,`RESOURCE_SERVER_ID`,`RESOURCE_ID`,`SCOPE_ID`),
                                               KEY `FK_FRSRHO213XCX4WNKOG82SSPMT` (`RESOURCE_SERVER_ID`),
                                               KEY `FK_FRSRHO213XCX4WNKOG83SSPMT` (`RESOURCE_ID`),
                                               KEY `FK_FRSRHO213XCX4WNKOG84SSPMT` (`SCOPE_ID`),
                                               KEY `FK_FRSRPO2128CX4WNKOG82SSRFY` (`POLICY_ID`),
                                               CONSTRAINT `FK_FRSRHO213XCX4WNKOG82SSPMT` FOREIGN KEY (`RESOURCE_SERVER_ID`) REFERENCES `RESOURCE_SERVER` (`ID`),
                                               CONSTRAINT `FK_FRSRHO213XCX4WNKOG83SSPMT` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `RESOURCE_SERVER_RESOURCE` (`ID`),
                                               CONSTRAINT `FK_FRSRHO213XCX4WNKOG84SSPMT` FOREIGN KEY (`SCOPE_ID`) REFERENCES `RESOURCE_SERVER_SCOPE` (`ID`),
                                               CONSTRAINT `FK_FRSRPO2128CX4WNKOG82SSRFY` FOREIGN KEY (`POLICY_ID`) REFERENCES `RESOURCE_SERVER_POLICY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE_SERVER_PERM_TICKET`
--

LOCK TABLES `RESOURCE_SERVER_PERM_TICKET` WRITE;
/*!40000 ALTER TABLE `RESOURCE_SERVER_PERM_TICKET` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESOURCE_SERVER_PERM_TICKET` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE_SERVER_POLICY`
--

DROP TABLE IF EXISTS `RESOURCE_SERVER_POLICY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE_SERVER_POLICY` (
                                          `ID` varchar(36) NOT NULL,
                                          `NAME` varchar(255) NOT NULL,
                                          `DESCRIPTION` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                                          `TYPE` varchar(255) NOT NULL,
                                          `DECISION_STRATEGY` tinyint DEFAULT NULL,
                                          `LOGIC` tinyint DEFAULT NULL,
                                          `RESOURCE_SERVER_ID` varchar(36) DEFAULT NULL,
                                          `OWNER` varchar(255) DEFAULT NULL,
                                          PRIMARY KEY (`ID`),
                                          UNIQUE KEY `UK_FRSRPT700S9V50BU18WS5HA6` (`NAME`,`RESOURCE_SERVER_ID`),
                                          KEY `IDX_RES_SERV_POL_RES_SERV` (`RESOURCE_SERVER_ID`),
                                          CONSTRAINT `FK_FRSRPO213XCX4WNKOG82SSRFY` FOREIGN KEY (`RESOURCE_SERVER_ID`) REFERENCES `RESOURCE_SERVER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE_SERVER_POLICY`
--

LOCK TABLES `RESOURCE_SERVER_POLICY` WRITE;
/*!40000 ALTER TABLE `RESOURCE_SERVER_POLICY` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESOURCE_SERVER_POLICY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE_SERVER_RESOURCE`
--

DROP TABLE IF EXISTS `RESOURCE_SERVER_RESOURCE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE_SERVER_RESOURCE` (
                                            `ID` varchar(36) NOT NULL,
                                            `NAME` varchar(255) NOT NULL,
                                            `TYPE` varchar(255) DEFAULT NULL,
                                            `ICON_URI` varchar(255) DEFAULT NULL,
                                            `OWNER` varchar(255) DEFAULT NULL,
                                            `RESOURCE_SERVER_ID` varchar(36) DEFAULT NULL,
                                            `OWNER_MANAGED_ACCESS` bit(1) NOT NULL DEFAULT b'0',
                                            `DISPLAY_NAME` varchar(255) DEFAULT NULL,
                                            PRIMARY KEY (`ID`),
                                            UNIQUE KEY `UK_FRSR6T700S9V50BU18WS5HA6` (`NAME`,`OWNER`,`RESOURCE_SERVER_ID`),
                                            KEY `IDX_RES_SRV_RES_RES_SRV` (`RESOURCE_SERVER_ID`),
                                            CONSTRAINT `FK_FRSRHO213XCX4WNKOG82SSRFY` FOREIGN KEY (`RESOURCE_SERVER_ID`) REFERENCES `RESOURCE_SERVER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE_SERVER_RESOURCE`
--

LOCK TABLES `RESOURCE_SERVER_RESOURCE` WRITE;
/*!40000 ALTER TABLE `RESOURCE_SERVER_RESOURCE` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESOURCE_SERVER_RESOURCE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE_SERVER_SCOPE`
--

DROP TABLE IF EXISTS `RESOURCE_SERVER_SCOPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE_SERVER_SCOPE` (
                                         `ID` varchar(36) NOT NULL,
                                         `NAME` varchar(255) NOT NULL,
                                         `ICON_URI` varchar(255) DEFAULT NULL,
                                         `RESOURCE_SERVER_ID` varchar(36) DEFAULT NULL,
                                         `DISPLAY_NAME` varchar(255) DEFAULT NULL,
                                         PRIMARY KEY (`ID`),
                                         UNIQUE KEY `UK_FRSRST700S9V50BU18WS5HA6` (`NAME`,`RESOURCE_SERVER_ID`),
                                         KEY `IDX_RES_SRV_SCOPE_RES_SRV` (`RESOURCE_SERVER_ID`),
                                         CONSTRAINT `FK_FRSRSO213XCX4WNKOG82SSRFY` FOREIGN KEY (`RESOURCE_SERVER_ID`) REFERENCES `RESOURCE_SERVER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE_SERVER_SCOPE`
--

LOCK TABLES `RESOURCE_SERVER_SCOPE` WRITE;
/*!40000 ALTER TABLE `RESOURCE_SERVER_SCOPE` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESOURCE_SERVER_SCOPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE_URIS`
--

DROP TABLE IF EXISTS `RESOURCE_URIS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE_URIS` (
                                 `RESOURCE_ID` varchar(36) NOT NULL,
                                 `VALUE` varchar(255) NOT NULL,
                                 PRIMARY KEY (`RESOURCE_ID`,`VALUE`),
                                 CONSTRAINT `FK_RESOURCE_SERVER_URIS` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `RESOURCE_SERVER_RESOURCE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE_URIS`
--

LOCK TABLES `RESOURCE_URIS` WRITE;
/*!40000 ALTER TABLE `RESOURCE_URIS` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESOURCE_URIS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ROLE_ATTRIBUTE`
--

DROP TABLE IF EXISTS `ROLE_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ROLE_ATTRIBUTE` (
                                  `ID` varchar(36) NOT NULL,
                                  `ROLE_ID` varchar(36) NOT NULL,
                                  `NAME` varchar(255) NOT NULL,
                                  `VALUE` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                                  PRIMARY KEY (`ID`),
                                  KEY `IDX_ROLE_ATTRIBUTE` (`ROLE_ID`),
                                  CONSTRAINT `FK_ROLE_ATTRIBUTE_ID` FOREIGN KEY (`ROLE_ID`) REFERENCES `KEYCLOAK_ROLE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ROLE_ATTRIBUTE`
--

LOCK TABLES `ROLE_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `ROLE_ATTRIBUTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `ROLE_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SCOPE_MAPPING`
--

DROP TABLE IF EXISTS `SCOPE_MAPPING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SCOPE_MAPPING` (
                                 `CLIENT_ID` varchar(36) NOT NULL,
                                 `ROLE_ID` varchar(36) NOT NULL,
                                 PRIMARY KEY (`CLIENT_ID`,`ROLE_ID`),
                                 KEY `IDX_SCOPE_MAPPING_ROLE` (`ROLE_ID`),
                                 CONSTRAINT `FK_OUSE064PLMLR732LXJCN1Q5F1` FOREIGN KEY (`CLIENT_ID`) REFERENCES `CLIENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SCOPE_MAPPING`
--

LOCK TABLES `SCOPE_MAPPING` WRITE;
/*!40000 ALTER TABLE `SCOPE_MAPPING` DISABLE KEYS */;
INSERT INTO `SCOPE_MAPPING` VALUES ('d43f3531-9d53-401b-a17e-59e6e6664b6a','4c54591f-c37c-4a8e-ae43-c8573609f6a5'),('ced0633a-f3d9-4c75-b85d-1c62fb955164','cb0b8e0e-64d2-4747-9d3a-5bf0ef265616'),('ced0633a-f3d9-4c75-b85d-1c62fb955164','cb1cffd1-f807-4642-90c5-a2f9481f7a08'),('d43f3531-9d53-401b-a17e-59e6e6664b6a','ce7a36e9-bbcd-492e-bee2-0fb4f75b2648');
/*!40000 ALTER TABLE `SCOPE_MAPPING` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SCOPE_POLICY`
--

DROP TABLE IF EXISTS `SCOPE_POLICY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SCOPE_POLICY` (
                                `SCOPE_ID` varchar(36) NOT NULL,
                                `POLICY_ID` varchar(36) NOT NULL,
                                PRIMARY KEY (`SCOPE_ID`,`POLICY_ID`),
                                KEY `IDX_SCOPE_POLICY_POLICY` (`POLICY_ID`),
                                CONSTRAINT `FK_FRSRASP13XCX4WNKOG82SSRFY` FOREIGN KEY (`POLICY_ID`) REFERENCES `RESOURCE_SERVER_POLICY` (`ID`),
                                CONSTRAINT `FK_FRSRPASS3XCX4WNKOG82SSRFY` FOREIGN KEY (`SCOPE_ID`) REFERENCES `RESOURCE_SERVER_SCOPE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SCOPE_POLICY`
--

LOCK TABLES `SCOPE_POLICY` WRITE;
/*!40000 ALTER TABLE `SCOPE_POLICY` DISABLE KEYS */;
/*!40000 ALTER TABLE `SCOPE_POLICY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USERNAME_LOGIN_FAILURE`
--

DROP TABLE IF EXISTS `USERNAME_LOGIN_FAILURE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USERNAME_LOGIN_FAILURE` (
                                          `REALM_ID` varchar(36) NOT NULL,
                                          `USERNAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                          `FAILED_LOGIN_NOT_BEFORE` int DEFAULT NULL,
                                          `LAST_FAILURE` bigint DEFAULT NULL,
                                          `LAST_IP_FAILURE` varchar(255) DEFAULT NULL,
                                          `NUM_FAILURES` int DEFAULT NULL,
                                          PRIMARY KEY (`REALM_ID`,`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USERNAME_LOGIN_FAILURE`
--

LOCK TABLES `USERNAME_LOGIN_FAILURE` WRITE;
/*!40000 ALTER TABLE `USERNAME_LOGIN_FAILURE` DISABLE KEYS */;
/*!40000 ALTER TABLE `USERNAME_LOGIN_FAILURE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_ATTRIBUTE`
--

DROP TABLE IF EXISTS `USER_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_ATTRIBUTE` (
                                  `NAME` varchar(255) NOT NULL,
                                  `VALUE` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                                  `USER_ID` varchar(36) NOT NULL,
                                  `ID` varchar(36) NOT NULL DEFAULT 'sybase-needs-something-here',
                                  PRIMARY KEY (`ID`),
                                  KEY `IDX_USER_ATTRIBUTE` (`USER_ID`),
                                  KEY `IDX_USER_ATTRIBUTE_NAME` (`NAME`,`VALUE`),
                                  CONSTRAINT `FK_5HRM2VLF9QL5FU043KQEPOVBR` FOREIGN KEY (`USER_ID`) REFERENCES `USER_ENTITY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_ATTRIBUTE`
--

LOCK TABLES `USER_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `USER_ATTRIBUTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_CONSENT`
--

DROP TABLE IF EXISTS `USER_CONSENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_CONSENT` (
                                `ID` varchar(36) NOT NULL,
                                `CLIENT_ID` varchar(255) DEFAULT NULL,
                                `USER_ID` varchar(36) NOT NULL,
                                `CREATED_DATE` bigint DEFAULT NULL,
                                `LAST_UPDATED_DATE` bigint DEFAULT NULL,
                                `CLIENT_STORAGE_PROVIDER` varchar(36) DEFAULT NULL,
                                `EXTERNAL_CLIENT_ID` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`ID`),
                                UNIQUE KEY `UK_JKUWUVD56ONTGSUHOGM8UEWRT` (`CLIENT_ID`,`CLIENT_STORAGE_PROVIDER`,`EXTERNAL_CLIENT_ID`,`USER_ID`),
                                KEY `IDX_USER_CONSENT` (`USER_ID`),
                                CONSTRAINT `FK_GRNTCSNT_USER` FOREIGN KEY (`USER_ID`) REFERENCES `USER_ENTITY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_CONSENT`
--

LOCK TABLES `USER_CONSENT` WRITE;
/*!40000 ALTER TABLE `USER_CONSENT` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_CONSENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_CONSENT_CLIENT_SCOPE`
--

DROP TABLE IF EXISTS `USER_CONSENT_CLIENT_SCOPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_CONSENT_CLIENT_SCOPE` (
                                             `USER_CONSENT_ID` varchar(36) NOT NULL,
                                             `SCOPE_ID` varchar(36) NOT NULL,
                                             PRIMARY KEY (`USER_CONSENT_ID`,`SCOPE_ID`),
                                             KEY `IDX_USCONSENT_CLSCOPE` (`USER_CONSENT_ID`),
                                             CONSTRAINT `FK_GRNTCSNT_CLSC_USC` FOREIGN KEY (`USER_CONSENT_ID`) REFERENCES `USER_CONSENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_CONSENT_CLIENT_SCOPE`
--

LOCK TABLES `USER_CONSENT_CLIENT_SCOPE` WRITE;
/*!40000 ALTER TABLE `USER_CONSENT_CLIENT_SCOPE` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_CONSENT_CLIENT_SCOPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_ENTITY`
--

DROP TABLE IF EXISTS `USER_ENTITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_ENTITY` (
                               `ID` varchar(36) NOT NULL,
                               `EMAIL` varchar(255) DEFAULT NULL,
                               `EMAIL_CONSTRAINT` varchar(255) DEFAULT NULL,
                               `EMAIL_VERIFIED` bit(1) NOT NULL DEFAULT b'0',
                               `ENABLED` bit(1) NOT NULL DEFAULT b'0',
                               `FEDERATION_LINK` varchar(255) DEFAULT NULL,
                               `FIRST_NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                               `LAST_NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                               `REALM_ID` varchar(255) DEFAULT NULL,
                               `USERNAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                               `CREATED_TIMESTAMP` bigint DEFAULT NULL,
                               `SERVICE_ACCOUNT_CLIENT_LINK` varchar(255) DEFAULT NULL,
                               `NOT_BEFORE` int NOT NULL DEFAULT '0',
                               PRIMARY KEY (`ID`),
                               UNIQUE KEY `UK_DYKN684SL8UP1CRFEI6ECKHD7` (`REALM_ID`,`EMAIL_CONSTRAINT`),
                               UNIQUE KEY `UK_RU8TT6T700S9V50BU18WS5HA6` (`REALM_ID`,`USERNAME`),
                               KEY `IDX_USER_EMAIL` (`EMAIL`),
                               KEY `IDX_USER_SERVICE_ACCOUNT` (`REALM_ID`,`SERVICE_ACCOUNT_CLIENT_LINK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_ENTITY`
--

LOCK TABLES `USER_ENTITY` WRITE;
/*!40000 ALTER TABLE `USER_ENTITY` DISABLE KEYS */;
INSERT INTO `USER_ENTITY` VALUES ('3f119e2a-a6b8-408d-8885-9f0869fc84ee','mpekilidi@gmail.com','mpekilidi@gmail.com','','',NULL,'Marc','Pekilidi','31b421bb-3aa9-4da8-be1f-cb3e7c35c8a5','msp',1694194006205,NULL,0),('9c11662d-04ce-458f-97dd-659de65808d7',NULL,'88c6303f-ad02-42c1-8bf0-0e38dec623d3','\0','',NULL,NULL,NULL,'0e7a440a-1f29-4ef2-ac0c-cf9f67da0d9f','admin',1694193968645,NULL,0);
/*!40000 ALTER TABLE `USER_ENTITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_FEDERATION_CONFIG`
--

DROP TABLE IF EXISTS `USER_FEDERATION_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_FEDERATION_CONFIG` (
                                          `USER_FEDERATION_PROVIDER_ID` varchar(36) NOT NULL,
                                          `VALUE` varchar(255) DEFAULT NULL,
                                          `NAME` varchar(255) NOT NULL,
                                          PRIMARY KEY (`USER_FEDERATION_PROVIDER_ID`,`NAME`),
                                          CONSTRAINT `FK_T13HPU1J94R2EBPEKR39X5EU5` FOREIGN KEY (`USER_FEDERATION_PROVIDER_ID`) REFERENCES `USER_FEDERATION_PROVIDER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_FEDERATION_CONFIG`
--

LOCK TABLES `USER_FEDERATION_CONFIG` WRITE;
/*!40000 ALTER TABLE `USER_FEDERATION_CONFIG` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_FEDERATION_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_FEDERATION_MAPPER`
--

DROP TABLE IF EXISTS `USER_FEDERATION_MAPPER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_FEDERATION_MAPPER` (
                                          `ID` varchar(36) NOT NULL,
                                          `NAME` varchar(255) NOT NULL,
                                          `FEDERATION_PROVIDER_ID` varchar(36) NOT NULL,
                                          `FEDERATION_MAPPER_TYPE` varchar(255) NOT NULL,
                                          `REALM_ID` varchar(36) NOT NULL,
                                          PRIMARY KEY (`ID`),
                                          KEY `IDX_USR_FED_MAP_FED_PRV` (`FEDERATION_PROVIDER_ID`),
                                          KEY `IDX_USR_FED_MAP_REALM` (`REALM_ID`),
                                          CONSTRAINT `FK_FEDMAPPERPM_FEDPRV` FOREIGN KEY (`FEDERATION_PROVIDER_ID`) REFERENCES `USER_FEDERATION_PROVIDER` (`ID`),
                                          CONSTRAINT `FK_FEDMAPPERPM_REALM` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_FEDERATION_MAPPER`
--

LOCK TABLES `USER_FEDERATION_MAPPER` WRITE;
/*!40000 ALTER TABLE `USER_FEDERATION_MAPPER` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_FEDERATION_MAPPER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_FEDERATION_MAPPER_CONFIG`
--

DROP TABLE IF EXISTS `USER_FEDERATION_MAPPER_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_FEDERATION_MAPPER_CONFIG` (
                                                 `USER_FEDERATION_MAPPER_ID` varchar(36) NOT NULL,
                                                 `VALUE` varchar(255) DEFAULT NULL,
                                                 `NAME` varchar(255) NOT NULL,
                                                 PRIMARY KEY (`USER_FEDERATION_MAPPER_ID`,`NAME`),
                                                 CONSTRAINT `FK_FEDMAPPER_CFG` FOREIGN KEY (`USER_FEDERATION_MAPPER_ID`) REFERENCES `USER_FEDERATION_MAPPER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_FEDERATION_MAPPER_CONFIG`
--

LOCK TABLES `USER_FEDERATION_MAPPER_CONFIG` WRITE;
/*!40000 ALTER TABLE `USER_FEDERATION_MAPPER_CONFIG` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_FEDERATION_MAPPER_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_FEDERATION_PROVIDER`
--

DROP TABLE IF EXISTS `USER_FEDERATION_PROVIDER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_FEDERATION_PROVIDER` (
                                            `ID` varchar(36) NOT NULL,
                                            `CHANGED_SYNC_PERIOD` int DEFAULT NULL,
                                            `DISPLAY_NAME` varchar(255) DEFAULT NULL,
                                            `FULL_SYNC_PERIOD` int DEFAULT NULL,
                                            `LAST_SYNC` int DEFAULT NULL,
                                            `PRIORITY` int DEFAULT NULL,
                                            `PROVIDER_NAME` varchar(255) DEFAULT NULL,
                                            `REALM_ID` varchar(36) DEFAULT NULL,
                                            PRIMARY KEY (`ID`),
                                            KEY `IDX_USR_FED_PRV_REALM` (`REALM_ID`),
                                            CONSTRAINT `FK_1FJ32F6PTOLW2QY60CD8N01E8` FOREIGN KEY (`REALM_ID`) REFERENCES `REALM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_FEDERATION_PROVIDER`
--

LOCK TABLES `USER_FEDERATION_PROVIDER` WRITE;
/*!40000 ALTER TABLE `USER_FEDERATION_PROVIDER` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_FEDERATION_PROVIDER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_GROUP_MEMBERSHIP`
--

DROP TABLE IF EXISTS `USER_GROUP_MEMBERSHIP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_GROUP_MEMBERSHIP` (
                                         `GROUP_ID` varchar(36) NOT NULL,
                                         `USER_ID` varchar(36) NOT NULL,
                                         PRIMARY KEY (`GROUP_ID`,`USER_ID`),
                                         KEY `IDX_USER_GROUP_MAPPING` (`USER_ID`),
                                         CONSTRAINT `FK_USER_GROUP_USER` FOREIGN KEY (`USER_ID`) REFERENCES `USER_ENTITY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_GROUP_MEMBERSHIP`
--

LOCK TABLES `USER_GROUP_MEMBERSHIP` WRITE;
/*!40000 ALTER TABLE `USER_GROUP_MEMBERSHIP` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_GROUP_MEMBERSHIP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_REQUIRED_ACTION`
--

DROP TABLE IF EXISTS `USER_REQUIRED_ACTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_REQUIRED_ACTION` (
                                        `USER_ID` varchar(36) NOT NULL,
                                        `REQUIRED_ACTION` varchar(255) NOT NULL DEFAULT ' ',
                                        PRIMARY KEY (`REQUIRED_ACTION`,`USER_ID`),
                                        KEY `IDX_USER_REQACTIONS` (`USER_ID`),
                                        CONSTRAINT `FK_6QJ3W1JW9CVAFHE19BWSIUVMD` FOREIGN KEY (`USER_ID`) REFERENCES `USER_ENTITY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_REQUIRED_ACTION`
--

LOCK TABLES `USER_REQUIRED_ACTION` WRITE;
/*!40000 ALTER TABLE `USER_REQUIRED_ACTION` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_REQUIRED_ACTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_ROLE_MAPPING`
--

DROP TABLE IF EXISTS `USER_ROLE_MAPPING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_ROLE_MAPPING` (
                                     `ROLE_ID` varchar(255) NOT NULL,
                                     `USER_ID` varchar(36) NOT NULL,
                                     PRIMARY KEY (`ROLE_ID`,`USER_ID`),
                                     KEY `IDX_USER_ROLE_MAPPING` (`USER_ID`),
                                     CONSTRAINT `FK_C4FQV34P1MBYLLOXANG7B1Q3L` FOREIGN KEY (`USER_ID`) REFERENCES `USER_ENTITY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_ROLE_MAPPING`
--

LOCK TABLES `USER_ROLE_MAPPING` WRITE;
/*!40000 ALTER TABLE `USER_ROLE_MAPPING` DISABLE KEYS */;
INSERT INTO `USER_ROLE_MAPPING` VALUES ('46774ffd-dc65-4ec4-a188-c1caf988789c','3f119e2a-a6b8-408d-8885-9f0869fc84ee'),('67d35d03-c1cb-414f-935e-b026084ce8cd','3f119e2a-a6b8-408d-8885-9f0869fc84ee'),('80f71a11-daa3-4bde-8275-7f86120a6eec','3f119e2a-a6b8-408d-8885-9f0869fc84ee'),('f3e73123-f39a-4284-a4d8-cee68cd5e898','3f119e2a-a6b8-408d-8885-9f0869fc84ee'),('bff87df8-9dd0-42ef-892c-33e8267244a5','9c11662d-04ce-458f-97dd-659de65808d7'),('c787b540-29f8-445a-bc0d-d07412be4658','9c11662d-04ce-458f-97dd-659de65808d7');
/*!40000 ALTER TABLE `USER_ROLE_MAPPING` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_SESSION`
--

DROP TABLE IF EXISTS `USER_SESSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_SESSION` (
                                `ID` varchar(36) NOT NULL,
                                `AUTH_METHOD` varchar(255) DEFAULT NULL,
                                `IP_ADDRESS` varchar(255) DEFAULT NULL,
                                `LAST_SESSION_REFRESH` int DEFAULT NULL,
                                `LOGIN_USERNAME` varchar(255) DEFAULT NULL,
                                `REALM_ID` varchar(255) DEFAULT NULL,
                                `REMEMBER_ME` bit(1) NOT NULL DEFAULT b'0',
                                `STARTED` int DEFAULT NULL,
                                `USER_ID` varchar(255) DEFAULT NULL,
                                `USER_SESSION_STATE` int DEFAULT NULL,
                                `BROKER_SESSION_ID` varchar(255) DEFAULT NULL,
                                `BROKER_USER_ID` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_SESSION`
--

LOCK TABLES `USER_SESSION` WRITE;
/*!40000 ALTER TABLE `USER_SESSION` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_SESSION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_SESSION_NOTE`
--

DROP TABLE IF EXISTS `USER_SESSION_NOTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_SESSION_NOTE` (
                                     `USER_SESSION` varchar(36) NOT NULL,
                                     `NAME` varchar(255) NOT NULL,
                                     `VALUE` text,
                                     PRIMARY KEY (`USER_SESSION`,`NAME`),
                                     CONSTRAINT `FK5EDFB00FF51D3472` FOREIGN KEY (`USER_SESSION`) REFERENCES `USER_SESSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_SESSION_NOTE`
--

LOCK TABLES `USER_SESSION_NOTE` WRITE;
/*!40000 ALTER TABLE `USER_SESSION_NOTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_SESSION_NOTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WEB_ORIGINS`
--

DROP TABLE IF EXISTS `WEB_ORIGINS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WEB_ORIGINS` (
                               `CLIENT_ID` varchar(36) NOT NULL,
                               `VALUE` varchar(255) NOT NULL,
                               PRIMARY KEY (`CLIENT_ID`,`VALUE`),
                               KEY `IDX_WEB_ORIG_CLIENT` (`CLIENT_ID`),
                               CONSTRAINT `FK_LOJPHO213XCX4WNKOG82SSRFY` FOREIGN KEY (`CLIENT_ID`) REFERENCES `CLIENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WEB_ORIGINS`
--

LOCK TABLES `WEB_ORIGINS` WRITE;
/*!40000 ALTER TABLE `WEB_ORIGINS` DISABLE KEYS */;
INSERT INTO `WEB_ORIGINS` VALUES ('138c5771-aaf5-495f-8480-05f1504a6c3a','*'),('28f1393f-9c6f-4b5e-b45b-e7708d58f86a','+'),('ced0633a-f3d9-4c75-b85d-1c62fb955164','*'),('de191c36-54f9-4ce6-a3a6-695d1d3b7583','+');
/*!40000 ALTER TABLE `WEB_ORIGINS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-02 13:14:47
