Stack used: Spring Boot, Maven, MySQL database.

This is a sample application developed to support multi-tenancy architecture.

In this application, each 'tenant' has it's own separate schema/database -> in MySQL there's no difference between schema & database in contrast with other databases.
For each 'tenant' a configuration(*.properties file) file must be added in resources folder, with some appropriate configurations regarding datasource should be added.

In order to start and run this app, you should setup the database first:
1. Create the default database :
 - Create a database with name: default_db
 (This is the 'default' database, the tables of this database will be create on the start of the application)
2. Create the data for other tenants:

  a. Create database: 'client_1' and execute the following ddl:

        CREATE TABLE `user_entity` (

          `id` bigint(20) NOT NULL AUTO_INCREMENT,

          `age` int(11) NOT NULL,

          `full_name` varchar(255) DEFAULT NULL,

          `user_name` varchar(255) DEFAULT NULL,

          PRIMARY KEY (`id`)

        ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

  b. Create database: 'client_2' and execute the following ddl:

        CREATE TABLE `user_entity` (

          `id` bigint(20) NOT NULL AUTO_INCREMENT,

          `age` int(11) NOT NULL,

          `full_name` varchar(255) DEFAULT NULL,

          `user_name` varchar(255) DEFAULT NULL,

          PRIMARY KEY (`id`)

        ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


3. Build the project -> perform: mvn clean package in the root of the project(there where pom.xml file is located)
4. Execute the jar file in the target folder.(java -jar ...)
5. Perform some HTTP requests in the following URI: http://localhost:8080/users (POST to create a new user, GET to get all the users).
    Don't forget to specify the tenant-it in the following header -> "TENANT-ID".
    Currently, the configurations exist for the following three 'tenants' -> 'default_db', "client_1", "client_1"


 - TO BE DONE & IMPROVED:
1. The identifications of each tenant must not be hard coded!(Maybe store in database and in the startup load in some cache or any other better solution!)


