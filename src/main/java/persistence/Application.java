package persistence;

import database.DatabaseServer;
import database.H2;
import java.util.List;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.QueryTranslator;
import persistence.sql.ddl.entity.Person;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private static RowMapper<Person> rowMapper = resultSet -> new Person(
        resultSet.getLong(1),
        resultSet.getString(2),
        resultSet.getInt(3),
        resultSet.getString(4)
    );

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            QueryTranslator queryTranslator = new QueryTranslator();

            jdbcTemplate.execute(queryTranslator.getCreateTableQuery(Person.class));

            executeInitializedQuery(jdbcTemplate, queryTranslator);

            List<Person> persons = jdbcTemplate.query(
                queryTranslator.getSelectAllQuery(Person.class),
                rowMapper
            );

            persons.forEach(person -> logger.info("Person: {}", person));

            Person person = jdbcTemplate.queryForObject(
                queryTranslator.getSelectByIdQuery(Person.class, 2L),
                rowMapper
            );

            logger.info("Person: {}", person);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

    private static void executeInitializedQuery(JdbcTemplate jdbcTemplate, QueryTranslator queryTranslator) {
        jdbcTemplate.execute(queryTranslator.getInsertQuery(new Person("John", 23, "john@gmail.com")));
        jdbcTemplate.execute(
            queryTranslator.getInsertQuery(new Person("Smith", 33, "smith@gmail.com")));
        jdbcTemplate.execute(queryTranslator.getInsertQuery(new Person("Tom", 45, "tom@gmail.com")));
        jdbcTemplate.execute(
            queryTranslator.getInsertQuery(new Person("rolroralra", 37, "rolroralra@gmail.com")));
    }


}
