package com.abidat.example.schema;

import com.abidat.example.configuration.common.Constant;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by a.kuci on 5/18/2017.
 * Utility class that execute batch files to create database schema
 */
final public class DatabaseSchemaUtilities {

    private DatabaseSchemaUtilities() {
    }

    /**
     * This method executes the batch file create_schema.bat or create_schema.sh depending on the operating system where the app is deployed.
     * The file create_schema.bat/create_schema.sh creates a new schema/databases. This method is called after the tenant and it's information are stored
     * in the table CompanyEntity of default database of app 'default_db'.
     *
     * @param schemaName - name of the schema/database which in reality is the companyNumber property of entity CompanyEntity that is unique.
     * @throws Exception
     */
    public static void createSchema(String schemaName) throws Exception {

        schemaName = schemaName.replace( " ", "" );

        //Get the operating system in which the app is deployed!
        String operatingSystem = System.getProperty( "os.name" );

        String batchOrShellCommand;

        //The directory where batch/shell file will be located(will be copied) -> user.home directory.
        File directoryWhereBatchOrShellFileIs;

        Process process;

        if (operatingSystem.startsWith( Constant.WINDOWS_OS )) {

            //Get batch file from create_schema.bat
            InputStream batchFileInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream( "create_schema.bat" );
            //If it is not the first time that app is being deployed -> delete the batch file from user.home directory!
            Files.deleteIfExists( Paths.get( System.getProperty( "user.home" ) + "\\create_schema.bat" ) );

            //Create the file create_schema.bat to user.home directory.
            File copiedBatchFileFromJar = new File( System.getProperty( "user.home" ) + "\\create_schema.bat" );

            //Copy batch file from jar to user.home directory so that it can be executed.
            Files.copy( batchFileInputStream, copiedBatchFileFromJar.toPath() );

            //build the command.
            batchOrShellCommand = "cmd /c " + System.getProperty( "user.home" ) + "\\create_schema.bat " + schemaName;

            //The directory where batch file is copeied
            directoryWhereBatchOrShellFileIs = new File( System.getProperty( "user.home" ) );
            //Execute the batch file -> create schema for the tenant with companyNumber -> 'schemaName' -> without spaces!
            process = Runtime.getRuntime().exec( batchOrShellCommand, null, directoryWhereBatchOrShellFileIs );
        } else {

            //Get shell file create_schema.
            InputStream shellFileputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream( "create_schema.sh" );
            //If it is ot the first time that app if being deployed, then shell file will be deleted from user.home directory
            Files.deleteIfExists( Paths.get( System.getProperty( "user.home" ) + "/create_schema.sh" ) );
            //
            File copiedShellFileFromJar = new File( System.getProperty( "user.home" ) + "/create_schema.sh" );

            Files.copy( shellFileputStream, copiedShellFileFromJar.toPath() );

            batchOrShellCommand = "bash " + System.getProperty( "user.home" ) + "/create_schema.sh " + schemaName;

            directoryWhereBatchOrShellFileIs = new File( System.getProperty( "user.home" ) );

            //give permission to execute the created shell file!
            process = Runtime.getRuntime().exec( "chmod u+x create_schema.sh", null, directoryWhereBatchOrShellFileIs );

            //execute shell file.
            process = Runtime.getRuntime().exec( batchOrShellCommand, null, directoryWhereBatchOrShellFileIs );
        }
        
        int result = process.waitFor();
    }


    /**
     * This method creates the default schema/db at the startup of app - if it does not exist.
     * This method in order to create the default schema/db(named 'default_db') it executes the file create_default_schema.bat/create_default_schema.sh depending on the
     * operating system.
     *
     * @throws Exception
     */
    public static void createDefaultSchemaAtStartup() throws Exception {

        //Detect the operating system!
        String operatingSystem = System.getProperty( "os.name" );

        String batchOrShellCommand;

        File directoryWhereCopiedBatchOrShellFileIs;

        Process p;

        if (operatingSystem.startsWith( Constant.WINDOWS_OS )) {

            //Get batch file from jar that creates the default db.
            InputStream batchFileInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream( "create_default_schema.bat" );
            //Delete if there's any file in user.home directory with the name 'create_default_schema.bat'
            Files.deleteIfExists( Paths.get( System.getProperty( "user.home" ) + "\\create_default_schema.bat" ) );
            //Create batch file in user.home directory that will create default db
            File fileToCopyTo = new File( System.getProperty( "user.home" ) + "\\create_default_schema.bat" );
            //copy the batch file for creating default db to user.home directory from jar -> user.home directory
            Files.copy( batchFileInputStream, fileToCopyTo.toPath() );
            //the command that will execute the batch in user.home directory for creating default db
            batchOrShellCommand = "cmd /c " + System.getProperty( "user.home" ) + "\\create_default_schema.bat";

            directoryWhereCopiedBatchOrShellFileIs = new File( System.getProperty( "user.home" ) );

            p = Runtime.getRuntime().exec( batchOrShellCommand, null, directoryWhereCopiedBatchOrShellFileIs );
        } else {

            InputStream shellFileInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream( "create_default_schema.sh" );

            Files.deleteIfExists( Paths.get( System.getProperty( "user.home" ) + "/create_default_schema.sh" ) );

            File fileToCopyTo = new File( System.getProperty( "user.home" ) + "/create_default_schema.sh" );

            Files.copy( shellFileInputStream, fileToCopyTo.toPath() );

            batchOrShellCommand = "bash -c " + System.getProperty( "user.home" ) + "/create_default_schema.sh ";

            directoryWhereCopiedBatchOrShellFileIs = new File( System.getProperty( "user.home" ) );

            //give permission to execute the created shell file!
            p = Runtime.getRuntime().exec( "chmod u+x create_default_schema.sh", null, directoryWhereCopiedBatchOrShellFileIs );

            p = Runtime.getRuntime().exec( batchOrShellCommand, null, directoryWhereCopiedBatchOrShellFileIs );
        }
        int res = p.waitFor();
    }

}
