@echo off

setlocal EnableDelayedExpansion

set SCHEMA_NAME=%1

set SQL_SCRIPT="USE %SCHEMA_NAME%; CREATE TABLE `user_entity` ( `id` bigint(20) NOT NULL AUTO_INCREMENT,`age` int(11) NOT NULL,`full_name` varchar(255) DEFAULT NULL,`user_name` varchar(255) DEFAULT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8";


mysql -uroot -proot -e "CREATE DATABASE %SCHEMA_NAME%"
mysql -uroot -proot -e "USE %SCHEMA_NAME%"
mysql -uroot -proot -e %SQL_SCRIPT%
