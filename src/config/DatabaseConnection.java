package config;

import java.sql.*;

public class DatabaseConnection {
    private static Connection connection;

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            String url = "jdbc:mysql://localhost:3306/pao_project";
            String username = "root";
            String password = "manager";
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }
}
