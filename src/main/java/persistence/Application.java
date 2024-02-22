package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.PersonMapper;
import jdbc.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.QueryBuilder;
import persistence.sql.QueryBuilderFactory;
import persistence.sql.ddl.DatabaseDialect;
import persistence.sql.ddl.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            QueryBuilderFactory factory = new QueryBuilderFactory();

            QueryBuilder queryBuilder = factory.getInstance(DatabaseDialect.MYSQL);

            jdbcTemplate.execute(queryBuilder.createQuery(Person.class));
            jdbcTemplate.execute(queryBuilder.dropQuery(Person.class));

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

}
