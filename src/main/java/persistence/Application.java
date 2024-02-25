package persistence;

import database.DatabaseServer;
import database.H2;
import database.sql.Person;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entity.EntityManager;
import persistence.entity.EntityManagerImpl;

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

            EntityManager entityManager = new EntityManagerImpl(jdbcTemplate);
            entityManager.persist(new Person("abc123", 14, "c123@d.com"));
            entityManager.persist(new Person("abc234", 15, "c234@d.com"));
            entityManager.persist(new Person("abc345", 16, "c456@d.com"));

            Person p3 = entityManager.find(Person.class, 3L);

            entityManager.remove(p3);

            Person p11 = entityManager.find(Person.class, 1L);
            Person p12 = entityManager.find(Person.class, 2L);

            // 여기서 NullPointerException (의도된 예외 발생, JdbcTemplate.java:27)
            Person p13 = entityManager.find(Person.class, 3L);

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
}
