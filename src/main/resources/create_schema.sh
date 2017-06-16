#!/usr/bin/env bash

DATABASE_NAME=$1

SQL_SCRIPT="create database if not exists $DATABASE_NAME;
            use $DATABASE_NAME;
            create table if not exists \`user_entity\`
                (\`id\` bigint(20) NOT NULL AUTO_INCREMENT,
                \`age\` int(11) NOT NULL,
                \`full_name\` varchar(255) DEFAULT NULL,
                \`user_name\` varchar(255) DEFAULT NULL,
                \`password\` varchar(255) DEFAULT NULL,
                PRIMARY KEY (\`id\`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;"

mysql -uroot -e "$SQL_SCRIPT"