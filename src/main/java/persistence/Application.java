package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;
import persistence.sql.domain.Person;
import persistence.sql.entity.EntityManagerImpl;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            CreateQueryBuilder queryBuilder = new CreateQueryBuilder(Person.class);
            String tableQuery = queryBuilder.createTableQuery(Person.class);
            InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(Person.class);

            Person person = new Person("yang", 23, "rhfp@naver.com", 3);
            String insertQuery = insertQueryBuilder.getInsertQuery(person);

            jdbcTemplate.execute(tableQuery);
            jdbcTemplate.execute(insertQuery);
            SelectQueryBuilder sb = new SelectQueryBuilder(Person.class);

            EntityManagerImpl entityManager = new EntityManagerImpl(server.getConnection());
            Person person1 = entityManager.find(Person.class, 1L);
            System.out.println(person1);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
