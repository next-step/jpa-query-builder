package persistence.sql.dml;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import persistence.sql.exception.JdbcTemplateException;

public class JdbcTemplate implements Database {

    private final Connection connection;

    public JdbcTemplate(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ResultSet executeQuery(String sql) {
        try {
            return connection.prepareStatement(sql).executeQuery();
        } catch (SQLException e) {
            throw new JdbcTemplateException(e);
        }
    }

    @Override
    public boolean execute(String sql) {
        try {
            return connection.prepareStatement(sql).execute();
        } catch (SQLException e) {
            throw new JdbcTemplateException(e);
        }
    }
}
