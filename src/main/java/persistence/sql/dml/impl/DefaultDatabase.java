package persistence.sql.dml.impl;

import database.DatabaseServer;
import persistence.sql.dml.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.function.Function;

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
    public ResultSet executeQuery(String query) {
        try {
            Connection connection = server.getConnection();
            return connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to execute query: " + query, e);
        }
    }

    @Override
    public <T> T executeQuery(String query, Function<ResultSet, T> mapRowMapperFunction) {
        try (Connection connection = server.getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            if(!resultSet.next()) {
                return null;
            }

            return mapRowMapperFunction.apply(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to execute query: " + query, e);
        }
    }
}
