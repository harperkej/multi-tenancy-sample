Stack used: Spring Boot, Maven, MySQL database.

This is a sample application developed to support multi-tenancy architecture.

In this application, each 'tenant' has it's own separate schema/database -> in MySQL there's no difference between schema & database in contrast with other databases.

In order to start and run this app, you should setup the database first:
1. Create the default database :
 - Create a database with name: default_db
 (This is the 'default' database, which has information like which how many tenants are in our application, the tables of this database will be create on the start of the application)

Go to the file create_schema.bat and to file application.properties and adjust the username and password.

- Build the project -> perform: mvn clean package in the root of the project(there where pom.xml file is located)
- Execute the jar file in the target folder.(java -jar ...)

- Create a new TENANT -> using URI http://localhost:8080/companies POST HTTP method. (The value of field 'companyNumber' that should be part of HTTP body must unique -> in this way we are sure that
    the name of each schema is unique!)
 - Use other API for users(http://localhost:8080/companies POST or GET method), and create or retrieve all users of one tenant -> **Don't forget to specify the tenant in the
    HTTP header -> "TENANT-ID".

