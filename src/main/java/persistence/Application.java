package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.Dialect;
import persistence.sql.H2Dialect;
import persistence.sql.ddl.*;
import persistence.sql.ddl.QueryBuilder;
import persistence.sql.dml.*;

import java.util.List;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            Dialect dialect = new H2Dialect();
            QueryBuilder createQueryBuilder = new CreateQueryBuilder(Person.class, dialect);

            QueryBuilder dropQueryBuilder = new DropQueryBuilder(Person.class);
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            jdbcTemplate.execute(createQueryBuilder.build());

            Person person = new Person("name", 10, "test@email.com", 1);
            InsertQueryBuilder insertQueryBuilder = new H2InsertQueryBuilder(person);
            jdbcTemplate.execute(insertQueryBuilder.makeQuery());

            SelectQueryBuilder selectQueryBuilder = new H2SelectQueryBuilder(Person.class);
            List<Person> persons = jdbcTemplate.query(selectQueryBuilder.findAll(), resultSet -> {
                String email = resultSet.getString("email");
                int age = resultSet.getInt("old");
                String nickname = resultSet.getString("nick_name");
                return new Person(nickname, age, email, 0);
            });


            Person getByIdPerson = jdbcTemplate.queryForObject(selectQueryBuilder.findById(1L), resultSet -> {
                Long id = resultSet.getLong("id");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("old");
                String nickname = resultSet.getString("nick_name");
                return new Person(id, nickname, age, email);
            });

            DeleteQueryBuilder deleteQueryBuilder = new H2DeleteQueryBuilder(getByIdPerson);
            jdbcTemplate.execute(deleteQueryBuilder.delete());
            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
