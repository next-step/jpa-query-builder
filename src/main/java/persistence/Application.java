package persistence;

import database.DatabaseServer;
import database.H2;
import database.sql.Person;
import database.sql.ddl.DdlQueryBuilder;
import database.sql.dml.DmlQueryBuilder;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            createTable(jdbcTemplate, Person.class);
            insertPerson(jdbcTemplate, new Person("abc123", 14, "c123@d.com"));
            insertPerson(jdbcTemplate, new Person("abc234", 15, "c234@d.com"));
            insertPerson(jdbcTemplate, new Person("abc345", 16, "c456@d.com"));

            jdbcTemplate.query("SELECT * from users", resultSet -> {
                System.out.println(resultSet);
                System.out.println(resultSet.getString("email"));
                return new Person();
            });

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

    private static void insertPerson(JdbcTemplate jdbcTemplate, Person person) {
        Class<? extends Person> entityClass = person.getClass();
        Map<String, Object> valueMap = person.toMap();

        insertRow(jdbcTemplate, entityClass, valueMap);
    }

    private static void createTable(JdbcTemplate jdbcTemplate, Class<?> entityClass) {
        DdlQueryBuilder builder = new DdlQueryBuilder();
        String query = builder.buildCreateQuery(entityClass);
        jdbcTemplate.execute(query);
    }

    private static void insertRow(JdbcTemplate jdbcTemplate, Class<?> entityClass, Map<String, Object> valueMap) {
        DmlQueryBuilder builder = new DmlQueryBuilder();
        String query = builder.buildInsertQuery(entityClass, valueMap);
        jdbcTemplate.execute(query);
    }
}
