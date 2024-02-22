package persistence.sql.ddl;

import java.sql.*;

public class CreateTable {

    private static final String createTableSQL = "CREATE TABLE IF NOT EXISTS person (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "name VARCHAR(30), " +
            "age INT";

    private final Connection connection;

    public CreateTable(Connection connection) {
        this.connection = connection;
    }

    public ResultSet createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(createTableSQL);
        ResultSet resultSet = statement.getResultSet();
        statement.close();
        return resultSet;
    }

}
