package persistence.sql.dml.impl;

import database.DatabaseServer;
import jdbc.RowMapper;
import persistence.sql.dml.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DefaultDatabase implements Database {
    private final DatabaseServer server;

    public DefaultDatabase(DatabaseServer dataSource) {
        this.server = dataSource;
    }

    @Override
    public void executeUpdate(String query) {
        try (Connection connection = server.getConnection()) {
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to execute update: " + query, e);
        }
    }

    @Override
    public <T> T executeQuery(String query, RowMapper<T> rowMapper) {
        try (Connection connection = server.getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            return rowMapper.mapRow(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to execute query: " + query, e);
        }
    }
}
