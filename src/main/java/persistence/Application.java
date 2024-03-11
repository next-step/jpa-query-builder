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

    private static final RowMapper<Person> rowMapper = resultSet -> new Person(
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

            QueryTranslator queryBuilder = new QueryTranslator();

            jdbcTemplate.execute(queryBuilder.getCreateTableQuery(Person.class));

            executeInitializedQuery(jdbcTemplate, queryBuilder);

            querySelectAll(jdbcTemplate, queryBuilder);

            querySelectById(jdbcTemplate, queryBuilder);

            jdbcTemplate.execute(queryBuilder.getDeleteByIdQuery(Person.class, 2L));

            querySelectAll(jdbcTemplate, queryBuilder);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

    private static void querySelectById(JdbcTemplate jdbcTemplate, QueryTranslator queryBuilder) {
        Person person = jdbcTemplate.queryForObject(
            queryBuilder.getSelectByIdQuery(Person.class, 2L),
            rowMapper
        );

        logger.info("Person: {}", person);
    }

    private static void querySelectAll(JdbcTemplate jdbcTemplate, QueryTranslator queryBuilder) {
        List<Person> persons = jdbcTemplate.query(
            queryBuilder.getSelectAllQuery(Person.class),
            rowMapper
        );

        persons.forEach(person -> logger.info("Person: {}", person));
    }

    private static void executeInitializedQuery(JdbcTemplate jdbcTemplate, QueryTranslator queryBuilder) {
        List<Person> persons = List.of(
            new Person("John", 23, "john@gmail.com"),
            new Person("Smith", 33, "smith@gmail.com"),
            new Person("rolroralra", 37, "rolroralra@gmail.com")
        );

        persons.stream()
            .map(queryBuilder::getInsertQuery)
            .forEach(jdbcTemplate::execute);
    }


}
