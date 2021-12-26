package Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connects to the database.
 * Citation: Wabara, Malcolm (9/21/2021) "QSG_Establishing_A_Client-Side_Database_Part_4" webinar.
 *     https://wgu.webex.com/webappng/sites/wgu/recording/9b8a01f9fcbe1039a7d900505681e613/playback
 */
public class DBConnection {

    //JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String ipAddress = protocol + vendorName + location + databaseName + "?connectionTimeZone = SERVER";

    //driver and connection interface reference
    private static final String mysqlJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection connection = null;

    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";

    /**
     * Starts the connection to the database and returns the connection.
     * @return The database connection
     */
    public static Connection startConnection()
    {
        try {
            Class.forName(mysqlJDBCDriver);
            connection = DriverManager.getConnection(ipAddress, username, password);
            //System.out.println("Connection Successful");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * Returns the active database connection.
     * @return The database connection
     */
    public static Connection getConnection()
    {
        return connection;
    }

    /**
     * Closes the database connection
     */
    public static void closeConnection() {
        try
        {
            connection.close();
            //System.out.println("Connection Closed");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
