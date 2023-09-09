CREATE SCHEMA IF NOT EXISTS `keycloakDB`;
CREATE SCHEMA IF NOT EXISTS `dodcoreDB`;

-- create root user and grant rights
GRANT ALL ON *.* TO 'sa'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
#CREATE USER 'root'@'localhost' IDENTIFIED BY 'dev';
