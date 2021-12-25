package model;

public class User {

    private static String username;
    private static int id;


    public static void setUsername(String un)
    {
        username = un;
    }

    public static void setId(int userId)
    {
        id = userId;
    }

    public static String getUsername()
    {
        return username;
    }

    public static int getId()
    {
        return id;
    }

}
