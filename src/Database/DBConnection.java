package Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public static Connection startConnection()
    {
        try {
            Class.forName(mysqlJDBCDriver);
            connection = DriverManager.getConnection(ipAddress, username, password);
            System.out.println("Connection Successful");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }

        return connection;
    }

    public static Connection getConnection()
    {
        return connection;
    }

    public static void closeConnection() {
        try
        {
            connection.close();
            //System.out.println("Connection Closed");
        }
        catch (NullPointerException e)
        {
            System.out.println("No connection open");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
}
