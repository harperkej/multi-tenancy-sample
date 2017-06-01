package com.abidat.example.schema;

import java.io.*;

/**
 * Created by a.kuci on 5/18/2017.
 */
public class SchemaGenerator {

    public static void createSchema(String schemaName) throws Exception {

        schemaName = schemaName.replace( " ", "" );

        String currentProject = System.getProperty( "user.dir" );

        String command = "cmd /c " + currentProject + "\\src\\main\\resources\\db\\client_schema\\create_schema.bat " + schemaName;

        String toolWorkingDir = currentProject + "\\src\\main\\resources\\db\\client_schema\\";

        File file = new File( toolWorkingDir );

        Process p = Runtime.getRuntime().exec( command, null, file );

        int res = p.waitFor();

        InputStream inputStream = p.getErrorStream();

        InputStreamReader inputStreamReader = new InputStreamReader( inputStream );


        BufferedReader bufferedInputStream = new BufferedReader( inputStreamReader );

        String line = null;

        while ((line = bufferedInputStream.readLine()) != null) {

            System.out.print( line );

        }
    }


    public static void createDefaultSchemaAtStartup() throws IOException, InterruptedException {

        String currentProject = System.getProperty( "user.dir" );

        String command = "cmd /c " + currentProject + "\\src\\main\\resources\\db\\default_db\\create_default_schema.bat ";

        String toolWorkingDir = currentProject + "\\src\\main\\resources\\db\\default_db\\";

        File file = new File( toolWorkingDir );

        Process p = Runtime.getRuntime().exec( command, null, file );

        int res = p.waitFor();

        InputStream inputStream = p.getErrorStream();

        InputStreamReader inputStreamReader = new InputStreamReader( inputStream );

        BufferedReader bufferedInputStream = new BufferedReader( inputStreamReader );

        String line = null;

        while ((line = bufferedInputStream.readLine()) != null) {

            System.out.print( line );

        }

        int exitCod = p.exitValue();

    }

}
