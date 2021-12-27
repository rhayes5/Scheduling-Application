package model;

public class User {

    private static String username;
    private static int id;

    /**
     * Sets the logged in username.
     * @param un the username
     */
    public static void setUsername(String un)
    {
        username = un;
    }

    /**
     * Sets the logged in user id
     * @param userId the user's id
     */
    public static void setId(int userId)
    {
        id = userId;
    }

    /**
     * Gets the logged in username.
     * @return The username
     */
    public static String getUsername()
    {
        return username;
    }

    /**
     * Gets the logged in user id
     * @return The user id
     */
    public static int getId()
    {
        return id;
    }

}
