package database;

import java.sql.*;

public class SimpleDatabase implements Database {

    private final Connection connection;

    public SimpleDatabase(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void execute(String sql) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (Exception e) {
            throw new IllegalArgumentException("This sql is invalid: " + sql);
        }
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

    @Override
    public void executeUpdate(String sql) {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new IllegalArgumentException("This sql is invalid: " + sql);
        }

        try {
            int rows = preparedStatement.executeUpdate();
            checkAffectedRows(rows);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Object executeUpdate(String sql, String pkColumnName) {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(sql, new String[]{pkColumnName});
        } catch (SQLException e) {
            throw new IllegalArgumentException("This sql is invalid: " + sql);
        }

        try {
            int rows = preparedStatement.executeUpdate();
            checkAffectedRows(rows);

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            hasNext(generatedKeys);

            return generatedKeys.getObject(pkColumnName);
        } catch (SQLException e) {
            // 어떤 exception을 throw해야 적절할지 잘 모르곘습니다!
            throw new RuntimeException(e.getMessage());
        }
    }

    private void checkAffectedRows(int row) throws SQLException {
        if (row == 0) {
            throw new SQLException("No rows were inserted or updated or deleted.");
        }
    }

    private void hasNext(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new SQLException("No PK values were generated.");
        }
    }
}
