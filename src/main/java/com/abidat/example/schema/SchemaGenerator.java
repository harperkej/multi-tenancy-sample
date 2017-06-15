package com.abidat.example.schema;

import com.abidat.example.configuration.common.Constant;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by a.kuci on 5/18/2017.
 */
public class SchemaGenerator {

    public static void createSchema(String schemaName) throws Exception {

        schemaName = schemaName.replace(" ", "");

        String os = System.getProperty("os.name");

        String command = "";

        File file = null;

        Process p;

        if (os.startsWith(Constant.WINDOWS_OS)) {

            //Get batch file from
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("create_schema.bat");
            //
            Files.deleteIfExists(Paths.get(System.getProperty("user.home") + "\\create_schema.bat"));

            File relocatedSchema = new File(System.getProperty("user.home") + "\\create_schema.bat");

            Files.copy(inputStream, relocatedSchema.toPath());

            command = "cmd /c " + System.getProperty("user.home") + "\\create_schema.bat " + schemaName;

            file = new File(System.getProperty("user.home"));

            p = Runtime.getRuntime().exec(command, null, file);
        } else {

            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("create_schema.sh");

            Files.deleteIfExists(Paths.get(System.getProperty("user.home") + "/create_schema.sh"));

            File fileToCopyTo = new File(System.getProperty("user.home") + "/create_schema.sh");

            Files.copy(inputStream, fileToCopyTo.toPath());

            command = "bash " + System.getProperty("user.home") + "/create_schema.sh " + schemaName;

            file = new File(System.getProperty("user.home"));

            //give permission to execute the created shell file!
            p = Runtime.getRuntime().exec("chmod u+x create_schema.sh", null, file);

            p = Runtime.getRuntime().exec(command, null, file);


        }

        int res = p.waitFor();

        InputStream inputStream = p.getErrorStream();

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);


        BufferedReader bufferedInputStream = new BufferedReader(inputStreamReader);

        String line = null;

        while ((line = bufferedInputStream.readLine()) != null) {

            System.out.println(line);

        }
        System.out.println(p.exitValue());

    }


    public static void createDefaultSchemaAtStartup() throws IOException, InterruptedException {

        String os = System.getProperty("os.name");

        String command;

        String toolWorkingDir;

        File file;

        Process p;

        if (os.startsWith(Constant.WINDOWS_OS)) {

            //Get batch file from jar that creates the default db.
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("create_default_schema.bat");
            //Delete if there's any file in user.home directory with the name 'create_default_schema.bat'
            Files.deleteIfExists(Paths.get(System.getProperty("user.home") + "\\create_default_schema.bat"));
            //Create batch file in user.directory that will create default db
            File fileToCopyTo = new File(System.getProperty("user.home") + "\\create_default_schema.bat");
            //copy the batch file for creating default db to user.home directory from jar -> user.home directory
            Files.copy(inputStream, fileToCopyTo.toPath());
            //the command that will execute the batch in user.home directory for creating default db
            command = "cmd /c " + System.getProperty("user.home") + "\\create_default_schema.bat";

            toolWorkingDir = System.getProperty("user.home");

            file = new File(toolWorkingDir);

            p = Runtime.getRuntime().exec(command, null, file);
        } else {

            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("create_default_schema.sh");

            Files.deleteIfExists(Paths.get(System.getProperty("user.home") + "/create_default_schema.sh"));

            File fileToCopyTo = new File(System.getProperty("user.home") + "/create_default_schema.sh");

            Files.copy(inputStream, fileToCopyTo.toPath());

            command = "bash -c " + System.getProperty("user.home") + "/create_default_schema.sh ";

            toolWorkingDir = System.getProperty("user.home");

            file = new File(toolWorkingDir);

            //give permission to execute the created shell file!
            p = Runtime.getRuntime().exec("chmod u+x create_default_schema.sh", null, file);

            p = Runtime.getRuntime().exec(command, null, file);
        }


        int res = p.waitFor();

        InputStream inputStream = p.getErrorStream();

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        BufferedReader bufferedInputStream = new BufferedReader(inputStreamReader);

        String line = null;

        while ((line = bufferedInputStream.readLine()) != null) {

            System.out.println(line);

        }

        int exitCod = p.exitValue();

        System.out.println(exitCod);

    }

}
