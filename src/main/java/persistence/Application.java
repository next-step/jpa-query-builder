package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.query.CreateQueryBuilder;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.query.DropQueryBuilder;

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
            CreateQueryBuilder createQuery = new CreateQueryBuilder();
            jdbcTemplate.execute(createQuery.build(Person.class));

            // test insert and select
            String insertSQL = "INSERT INTO users (nick_name, old, email) VALUES ('John Doe', 30, 'chanho0912@gmail.com')";
            jdbcTemplate.execute(insertSQL);
            logger.info("Data inserted successfully!");

            String selectSQL = "SELECT * FROM users where id = 1";
            Map<String, Object> result = jdbcTemplate.queryForObject(selectSQL, new RowMapper<Map<String, Object>>() {
                @Override
                public Map<String, Object> mapRow(ResultSet resultSet) throws SQLException {
                    return Map.of("nick_name", resultSet.getString("nick_name"), "old", resultSet.getInt("old"));
                }
            });

            for (Map.Entry<String, Object> row : result.entrySet()) {
                logger.info("Row: column={}, value={}", row.getKey(), row.getValue());
            }

            // drop table
            DropQueryBuilder dropQuery = new DropQueryBuilder();
            String build = dropQuery.build(Person.class);
            logger.info("Drop query: {}", build);
            jdbcTemplate.execute(build);

//            jdbcTemplate.execute("select * from users");

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
