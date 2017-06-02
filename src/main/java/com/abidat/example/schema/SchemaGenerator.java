package com.abidat.example.schema;

import com.abidat.example.configuration.common.Constant;

import java.io.*;

/**
 * Created by a.kuci on 5/18/2017.
 */
public class SchemaGenerator {

    public static void createSchema(String schemaName) throws Exception {


        schemaName = schemaName.replace( " ", "" );

        String currentProject = System.getProperty( "user.dir" );

        String os = System.getProperty("os.name");

        String command = "";

        String toolWorkingDir = "";

        File file = null;

        Process p;

        if(os.startsWith(Constant.WINDOWS_OS)) {

            command = "cmd /c " + currentProject + "\\src\\main\\resources\\db\\client_schema\\create_schema.bat " + schemaName;

            toolWorkingDir = currentProject + "\\src\\main\\resources\\db\\client_schema\\";

            file = new File( toolWorkingDir );

            p = Runtime.getRuntime().exec( command, null, file );
        }
        else
        {

            command = currentProject + "/src/main/resources/db/client_schema/create_schema.sh " + schemaName;

            toolWorkingDir = currentProject + "/src/main/resources/db/client_schema/";

            file = new File( toolWorkingDir );

            p = Runtime.getRuntime().exec( command, null, file );

        }

        int res = p.waitFor();

        InputStream inputStream = p.getErrorStream();

        InputStreamReader inputStreamReader = new InputStreamReader( inputStream );


        BufferedReader bufferedInputStream = new BufferedReader( inputStreamReader );

        String line = null;

        while ((line = bufferedInputStream.readLine()) != null) {

            System.out.println( line );

        }

        System.out.println(p.exitValue());


    }


    public static void createDefaultSchemaAtStartup() throws IOException, InterruptedException {

        String currentProject = System.getProperty( "user.dir" );

        String os = System.getProperty("os.name");

        String command;

        String toolWorkingDir;

        File file;

        Process p;

        if(os.startsWith(Constant.WINDOWS_OS)){

            command = "cmd /c " + currentProject + "\\src\\main\\resources\\db\\default_db\\create_default_schema.bat ";

            toolWorkingDir = currentProject + "\\src\\main\\resources\\db\\default_db\\";

            file = new File( toolWorkingDir );

            p = Runtime.getRuntime().exec( command, null, file );
        }
        else {

            command = "bash -c " + currentProject + "/src/main/resources/db/default_db/create_default_schema.sh ";

            toolWorkingDir = currentProject + "/src/main/resources/db/default_db/";

            file = new File( toolWorkingDir );

            p = Runtime.getRuntime().exec( command, null, file );
        }


        int res = p.waitFor();

        InputStream inputStream = p.getErrorStream();

        InputStreamReader inputStreamReader = new InputStreamReader( inputStream );

        BufferedReader bufferedInputStream = new BufferedReader( inputStreamReader );

        String line = null;

        while ((line = bufferedInputStream.readLine()) != null) {

            System.out.println( line );

        }

        int exitCod = p.exitValue();

        System.out.println(exitCod);

    }

}
