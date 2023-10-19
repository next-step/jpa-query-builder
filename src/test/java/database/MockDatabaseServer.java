package database;

import java.sql.Connection;
import java.sql.SQLException;


public class MockDatabaseServer implements DatabaseServer {

    @Override
    public void start() throws SQLException {

    }

    @Override
    public void stop() {

    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }
}
