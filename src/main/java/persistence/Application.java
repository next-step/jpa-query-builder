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

            DDLGenerator ddlGenerator = new DDLGenerator(Person.class);
            DMLGenerator dmlGenerator = new DMLGenerator(Person.class);

            jdbcTemplate.execute(ddlGenerator.generateCreate());
            jdbcTemplate.execute(dmlGenerator.generateInsert(new Person("name", 26, "email", 1)));

            PersonRowMapper rowMapper = new PersonRowMapper();
            jdbcTemplate.query(dmlGenerator.generateFindAll(), rowMapper);
            Person person = jdbcTemplate.queryForObject(dmlGenerator.generateFindById(1L), rowMapper);
            jdbcTemplate.execute(dmlGenerator.generateDelete(person));

            jdbcTemplate.execute(ddlGenerator.generateDrop());

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
