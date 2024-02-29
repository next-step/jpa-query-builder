package persistence;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import jdbc.PersonRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.DDLGenerator;
import persistence.sql.dml.DMLGenerator;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            DDLGenerator ddlGenerator = new DDLGenerator();
            DMLGenerator dmlGenerator = new DMLGenerator();

            jdbcTemplate.execute(ddlGenerator.generateCreate(Person.class));
            jdbcTemplate.execute(dmlGenerator.generateInsert(new Person("name", 26, "email", 1)));
            jdbcTemplate.query(dmlGenerator.generateFindAll(Person.class), new PersonRowMapper());

            jdbcTemplate.execute(ddlGenerator.generateDrop(Person.class));

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
