package persistence;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entity.EntityManager;
import persistence.entity.impl.FakeEntityManager;
import persistence.sql.ddl.CreateTableQueryBuilder;
import persistence.sql.ddl.DropTableQueryBuilder;
import persistence.sql.ddl.QueryBuilder;
import persistence.sql.dml.DMLQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            QueryBuilder ddlQueryBuilder = new CreateTableQueryBuilder(Person.class);

            String createTableQuery = ddlQueryBuilder.executeQuery();
            jdbcTemplate.execute(createTableQuery); // Create table
            EntityManager entityManager = new FakeEntityManager(Person.class, jdbcTemplate);

            entityManager.persist(Person.of(null,"John", 25,"demian@gmail.com",1));

            entityManager.find(Person.class, 1L);

            entityManager.remove(Person.class, 1L);

            entityManager.update(Person.of(1L,"John", 25,null,null));


            ddlQueryBuilder = new DropTableQueryBuilder(Person.class);
            ddlQueryBuilder.executeQuery();

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
