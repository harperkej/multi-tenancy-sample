#!/usr/bin/env bash

MYSQL="create database if not exists default_db;
		use default_db;
		create table if not exists \`company_entity\`
			(\`id\` bigint(20) NOT NULL AUTO_INCREMENT,
			\`address\` varchar(255) DEFAULT NULL,
			\`company_name\` varchar(255) DEFAULT NULL,
			\`company_number\` varchar(32) NOT NULL,
            		PRIMARY KEY (\`id\`),
			UNIQUE KEY \`UK_38t1tv869q7kvri9l4nvskhv9\` (\`company_number\`))
			ENGINE=InnoDB DEFAULT CHARSET=utf8;
		create table if not exists \`user_entity\` (
			\`id\` bigint(20) NOT NULL AUTO_INCREMENT,
			\`age\` int(11) NOT NULL,
			\`full_name\` varchar(255) DEFAULT NULL,
			\`password\` varchar(255) DEFAULT NULL,
			\`user_name\` varchar(255) DEFAULT NULL,
			PRIMARY KEY (\`id\`))
			ENGINE=InnoDB DEFAULT CHARSET=utf8;"

mysql -uroot -proot -e "$MYSQL"