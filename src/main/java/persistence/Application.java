package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.*;
import persistence.sql.dml.H2InsertQueryBuilder;
import persistence.sql.dml.H2SelectQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            AbstractCreateQueryBuilder createQueryBuilder = new H2CreateQueryBuilder(Person.class);
            DropQueryBuilder dropQueryBuilder = new H2DropQueryBuilder(Person.class);

            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            jdbcTemplate.execute(createQueryBuilder.makeQuery());

            Person person = new Person("name", 10, "test@email.com", 1);
            InsertQueryBuilder insertQueryBuilder = new H2InsertQueryBuilder(person);
            jdbcTemplate.execute(insertQueryBuilder.makeQuery());

            SelectQueryBuilder selectQueryBuilder = new H2SelectQueryBuilder(Person.class);
            List<Person> persons = jdbcTemplate.query(selectQueryBuilder.findAllQuery(), resultSet -> {
                String email = resultSet.getString("email");
                int age = resultSet.getInt("old");
                String nickname = resultSet.getString("nick_name");
                return new Person(nickname, age, email, 0);
            });


            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
