package SchedulingApplication;

import java.io.*;
import java.util.Scanner;

public class FileIO {

    private static String filename = "login_activity.txt";

    /**
     * Writes a string to the login_activity file
     * @param s the string to write
     */
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

    /**
     * Reads from the login_activity file line by line and returns the file contents as a string.
     * @return A string of the file contents
     */
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
