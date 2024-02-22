package persistence;

import database.DatabaseServer;
import database.H2;
import database.sql.Person;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static final RowMapper<Person> personRowMapper = resultSet ->
            new Person(resultSet.getLong("id"),
                    resultSet.getString("nick_name"),
                    resultSet.getInt("old"),
                    resultSet.getString("email"));

    private static final database.sql.dml.QueryBuilder dmlQueryBuilder = database.sql.dml.QueryBuilder.getInstance();
    private static final database.sql.ddl.QueryBuilder ddlQueryBuilder = database.sql.ddl.QueryBuilder.getInstance();

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            createTable(jdbcTemplate);
            insertPerson(jdbcTemplate, new Person("abc123", 14, "c123@d.com"));
            insertPerson(jdbcTemplate, new Person("abc234", 15, "c234@d.com"));
            insertPerson(jdbcTemplate, new Person("abc345", 16, "c456@d.com"));

            List<Person> people = selectPeople(jdbcTemplate);
            System.out.println(people);

            Person person = selectOnePerson(jdbcTemplate, people.get(1).getId());
            System.out.println(person);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

    private static void createTable(JdbcTemplate jdbcTemplate) {
        String query = ddlQueryBuilder.buildCreateQuery(Person.class);
        jdbcTemplate.execute(query);
    }

    private static void insertPerson(JdbcTemplate jdbcTemplate, Person person) {
        Class<? extends Person> entityClass = person.getClass();
        Map<String, Object> valueMap = person.toMap();

        insertRow(jdbcTemplate, entityClass, valueMap);
    }

    private static List<Person> selectPeople(JdbcTemplate jdbcTemplate) {
        String selectQuery = dmlQueryBuilder.buildSelectQuery(Person.class);
        return jdbcTemplate.query(selectQuery, personRowMapper);
    }

    private static Person selectOnePerson(JdbcTemplate jdbcTemplate, Long id) {
        String selectQuery = dmlQueryBuilder.buildSelectOneQuery(Person.class, id);
        return jdbcTemplate.queryForObject(selectQuery, personRowMapper);
    }

    private static void insertRow(JdbcTemplate jdbcTemplate, Class<?> entityClass, Map<String, Object> valueMap) {
        String query = dmlQueryBuilder.buildInsertQuery(entityClass, valueMap);
        jdbcTemplate.execute(query);
    }
}
