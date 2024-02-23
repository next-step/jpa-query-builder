package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.converter.EntityConverter;
import persistence.sql.converter.TypeMapper;
import persistence.sql.ddl.QueryBuilder;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.entity.Person;
import persistence.sql.model.Table;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            QueryBuilder queryBuilder = new QueryBuilder(new H2Dialect());
            EntityConverter entityConverter = new EntityConverter(new TypeMapper());

            Table personTable = entityConverter.convertEntityToTable(Person.class);

            String createQuery = queryBuilder.buildCreateQuery(personTable);
            jdbcTemplate.execute(createQuery);

            String dropQuery = queryBuilder.buildDropQuery(personTable);
            jdbcTemplate.execute(dropQuery);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
