package database;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseServer {
    void start() throws SQLException;

    void stop();

    Connection getConnection() throws SQLException;
}
