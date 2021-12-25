package SchedulingApplication;

import java.io.*;
import java.util.Scanner;

public class FileIO {

    private static String filename = "login_activity.txt";

    public static void write(String s)
    {
        try {
            FileWriter fileWriter = new FileWriter(filename, true);
            PrintWriter outputFile = new PrintWriter(fileWriter);
            outputFile.println(s);
            outputFile.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String read() {
        File file = new File(filename);
        String s = "";
        try {
            Scanner inputFile = new Scanner(file);

            while (inputFile.hasNext())
            {
                s = s + inputFile.nextLine() + "\n";
            }

            return s;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return s;
        }
    }



}
