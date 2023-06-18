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
import persistence.sql.ddl.DdlBuilder;
import persistence.sql.dialect.Dialect;
import persistence.sql.dml.DmlBuilder;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final RowMapper<Person> rowMapper = new RowMapperImpl(Person.class);
            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            final Dialect dialect = Dialect.H2;
            final DdlBuilder ddl = dialect.getDdl();
            final DmlBuilder dml = dialect.getDml();

            jdbcTemplate.execute(
                    ddl.getCreateQuery(Person.class)
            );

            jdbcTemplate.execute(
                    dml.getInsertQuery(PersonFixture.createPerson())
            );
            Long id = jdbcTemplate.query(
                    dml.getFindAllQuery(Person.class),
                    rowMapper
            ).get(0).getId();
            jdbcTemplate.query(
                    dml.getFindByIdQuery(Person.class, id),
                    rowMapper
            ).get(0);

            jdbcTemplate.execute(
                    dml.getDeleteByIdQuery(Person.class, id)
            );

            jdbcTemplate.execute(
                    ddl.getDropQuery(Person.class)
            );
            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
