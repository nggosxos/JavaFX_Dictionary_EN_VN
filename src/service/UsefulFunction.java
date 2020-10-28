package service;

import java.io.*;

public class UsefulFunction {

    public static String projectPath = new File("").getAbsolutePath();

    public static void cls() {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }

    public static String readLine(InputStream stream) throws Exception{
//        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.readLine();
    }
}
