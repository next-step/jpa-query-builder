package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            // create table
            Person person = new Person();
            CreateQueryBuilder createQuery = new CreateQueryBuilder();
            jdbcTemplate.execute(createQuery.build(person));

            String insertSQL = "INSERT INTO Person (id, name, age) VALUES (1, 'John Doe', 30)";
            jdbcTemplate.execute(insertSQL);
            logger.info("Data inserted successfully!");

            String selectSQL = "SELECT * FROM Person where id = 1";
            Map<String, Object> result = jdbcTemplate.queryForObject(selectSQL, new RowMapper<Map<String, Object>>() {
                @Override
                public Map<String, Object> mapRow(ResultSet resultSet) throws SQLException {
                    return Map.of("name", resultSet.getString("name"), "age", resultSet.getInt("age"));
                }
            });

            for (Map.Entry<String, Object> row : result.entrySet()) {
                logger.info("Row: column={}, value={}", row.getKey(), row.getValue());
            }

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
