package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.DefaultEntityManager;
import persistence.sql.ddl.EntityManager;
import persistence.sql.ddl.EntityTable;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.H2Dialect;
import persistence.sql.ddl.generator.CreateDDLGenerator;
import persistence.sql.ddl.generator.DefaultCreateDDLGenerator;
import persistence.sql.ddl.generator.DefaultDropDDLGenerator;
import persistence.sql.ddl.generator.DropDDLGenerator;

import java.sql.Connection;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        Dialect h2Dialect = new H2Dialect();

        CreateDDLGenerator createDDLGenerator = new DefaultCreateDDLGenerator(h2Dialect);
        DropDDLGenerator dropDDLGenerator = new DefaultDropDDLGenerator();

        EntityTable entityTable = EntityTable.from(Person.class);

        Person personForSave = new Person(null, "soora", 10, "soora@naver.com", 5);

        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            Connection connection = server.getConnection();
            final JdbcTemplate jdbcTemplate = new JdbcTemplate(connection);
            final EntityManager entityManager = new DefaultEntityManager(connection);


            String createDDL = createDDLGenerator.generate(entityTable);

            jdbcTemplate.execute(createDDL);

            Person result = (Person) entityManager.persist(personForSave);

            Person found = entityManager.find(Person.class, result.getId());

            System.out.println(result == found);

            entityManager.remove(found);

            Person found2 = entityManager.find(Person.class, result.getId());

            System.out.println(found2);

            String dropDDL = dropDDLGenerator.generate(entityTable);

            jdbcTemplate.execute(dropDDL);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}

