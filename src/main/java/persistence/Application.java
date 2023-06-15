package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.ColumnMapper;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.TableQueryMapper;
import persistence.sql.ddl.h2.H2Dialect;
import persistence.sql.ddl.h2.H2GeneratedValueStrategy;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            final TableQueryMapper tableQueryMapper =
                    new TableQueryMapper(new ColumnMapper(new H2Dialect()), new H2GeneratedValueStrategy());
            String sql = tableQueryMapper.create(Person.class);
            jdbcTemplate.execute(sql);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
