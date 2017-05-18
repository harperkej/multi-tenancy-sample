package com.abidat.example.schema;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by a.kuci on 5/18/2017.
 */
public class SchemaGenerator {

    public static void createSchema(String schemaName) throws Exception {

        schemaName = schemaName.replace(" ", "");

        String currentProject = System.getProperty("user.dir");

        String command = "cmd /c " + currentProject + "\\src\\main\\resources\\db\\create_schema.bat " + schemaName;

        String toolWorkingDir = currentProject + "\\src\\main\\resources\\db\\";

        File file = new File(toolWorkingDir);

        Process p = Runtime.getRuntime().exec(command, null, file);

        int res = p.waitFor();

        InputStream inputStream = p.getErrorStream();

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);


        BufferedReader bufferedInputStream = new BufferedReader(inputStreamReader);

        String line = null;

        while ((line = bufferedInputStream.readLine()) != null) {

            System.out.print(line);

        }


    }

}
