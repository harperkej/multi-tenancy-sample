[![Build Status](https://travis-ci.org/harperkej/multi-tenancy-sample.svg?branch=master)](https://travis-ci.org/harperkej/multi-tenancy-sample)

This is a sample application, that shows how multi-tenancy architecture can be implemented.

Requirements: 
	You should have maven installed and configured
	You should have MySQL installed and setup environment variables setup for MySQL(you can execute command 'mysql' in you machine no matter the directory of you command line / terminal).

In this application, each 'tenant' has it's own separate schema/database -> in MySQL there's no difference between schema & database in contrast with other databases.

Go to the file create_schema.bat/create_schema.sh && create_default_schema.bat/create_default_schema.sh and file application.properties and adjust the username and password according to the you needs.

- Build the project -> perform: mvn clean package in the root of the project(there where pom.xml file is located)

- Execute the jar file in the target folder.(java -jar ...)

- Create a new TENANT -> using URI http://localhost:8080/companies POST HTTP method. (The value of field 'companyNumber' that should be part of HTTP body must unique -> in this way we are sure that
    the name of each schema is unique!)
 - Use other API for users(http://localhost:8080/companies POST or GET method), and create or retrieve all users of one tenant -> **Don't forget to specify the tenant in the
    HTTP header -> "TENANT-ID" -> the "TENANT-ID" is the 'companyNumber' without spaces.

Stack used: Spring Boot, Maven, MySQL database.

