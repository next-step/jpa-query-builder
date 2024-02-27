package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SimpleDatabase implements Database {

    private final Connection connection;

    public SimpleDatabase(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ResultSet executeQuery(String sql) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new IllegalArgumentException("This sql is invalid: " + sql);
        }
    }
}
