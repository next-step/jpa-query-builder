package persistence.sql.ddl;

import jdbc.JdbcTemplate;

import java.sql.*;

public class CreatePersonCommand {

    private static final String createTableSQL = "CREATE TABLE IF NOT EXISTS person (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "name VARCHAR(30), " +
            "age INT)";

    private final JdbcTemplate template;

    public CreatePersonCommand(JdbcTemplate template) {
        this.template = template;
    }

    public void execute() throws SQLException {
        template.execute(createTableSQL);
    }

}
