package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class H2DatabaseConnection implements DatabaseConnection {
    private final String url;
    private final String user;
    private final String password;
    public H2DatabaseConnection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к H2", e);
        }
    }
}
