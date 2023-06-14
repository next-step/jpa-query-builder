package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.ddl.JavaToSqlColumnParser;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.collection.Dialects;
import persistence.sql.ddl.dialect.Dialect;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            final Dialects dialects = new Dialects();
            final Dialect dialect = dialects.get("h2");
            final JavaToSqlColumnParser columnParser = new JavaToSqlColumnParser(dialect);
            final String sql = new DdlQueryBuilder(columnParser).build(Person.class);
            jdbcTemplate.execute(sql);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
