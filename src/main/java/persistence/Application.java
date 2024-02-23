package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.AbstractQueryBuilderFactory;
import persistence.sql.DatabaseDialect;
import persistence.sql.ddl.DdlQueryBuild;
import persistence.sql.ddl.DdlQueryBuilderFactory;
import persistence.sql.entity.Person;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            AbstractQueryBuilderFactory<DdlQueryBuild> factory = new DdlQueryBuilderFactory();

            DdlQueryBuild ddlQueryBuilder = factory.getInstance(DatabaseDialect.MYSQL);

            jdbcTemplate.execute(ddlQueryBuilder.createQuery(Person.class));
            jdbcTemplate.execute(ddlQueryBuilder.dropQuery(Person.class));

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

}
