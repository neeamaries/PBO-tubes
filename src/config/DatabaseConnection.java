package src.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String HOSTNAME = "fintrack-pbo.database.windows.net";
    private static final String DATABASE = "fintrack_db";
    private static final String USERNAME = "fintrackadmin";
    private static final String PASSWORD = "adminFintrack26@keren";

    private static final String URL =
            "jdbc:sqlserver://" + HOSTNAME + ":1433;"
            + "database=" + DATABASE + ";"
            + "user=" + USERNAME + ";"
            + "password=" + PASSWORD + ";"
            + "encrypt=true;"
            + "trustServerCertificate=false;"
            + "hostNameInCertificate=*.database.windows.net;"
            + "loginTimeout=30;";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("SQL Server JDBC Driver berhasil dimuat.");
        } catch (ClassNotFoundException e) {
            System.out.println("SQL Server JDBC Driver tidak ditemukan.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}