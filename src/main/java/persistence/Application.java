package persistence;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import domain.PersonFixture;
import domain.PersonMapper;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.dml.FindAllQueryBuilder;
import persistence.sql.dml.FindByIdQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            jdbcTemplate.execute(
                    new CreateQueryBuilder<>(Person.class).build()
            );

            jdbcTemplate.execute(
                    new InsertQueryBuilder<>(
                            PersonFixture.createPerson()
                    ).build()
            );
            Long id = jdbcTemplate.query(
                    new FindAllQueryBuilder<>(
                            Person.class
                    ).build(),
                    new PersonMapper()
            ).get(0).getId();
            jdbcTemplate.query(
                    new FindByIdQueryBuilder<>(
                            Person.class
                    ).build(id),
                    new PersonMapper()
            );

            jdbcTemplate.execute(
                    new DropQueryBuilder<>(Person.class).build()
            );
            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
