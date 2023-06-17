package persistence;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import domain.PersonFixture;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import jdbc.RowMapperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.CreateQuery;
import persistence.sql.ddl.DropQuery;
import persistence.sql.dml.DeleteByIdQuery;
import persistence.sql.dml.FindAllQuery;
import persistence.sql.dml.FindByIdQuery;
import persistence.sql.dml.InsertQuery;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final RowMapper<Person> rowMapper = new RowMapperImpl(Person.class);
            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            jdbcTemplate.execute(
                    new CreateQuery<>(Person.class).build()
            );

            jdbcTemplate.execute(
                    new InsertQuery<>(
                            PersonFixture.createPerson()
                    ).build()
            );
            Long id = jdbcTemplate.query(
                    new FindAllQuery<>(
                            Person.class
                    ).build(),
                    rowMapper
            ).get(0).getId();
            jdbcTemplate.query(
                    new FindByIdQuery<>(
                            Person.class
                    ).build(id),
                    rowMapper
            ).get(0);

            jdbcTemplate.execute(
                    new DeleteByIdQuery<>(
                            Person.class
                    ).build(id)
            );

            jdbcTemplate.execute(
                    new DropQuery<>(Person.class).build()
            );
            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
