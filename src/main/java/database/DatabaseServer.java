package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseServer {
    ResultSet executeQuery(String sql);

    void start() throws SQLException;

    void stop();

    Connection getConnection() throws SQLException;
}
