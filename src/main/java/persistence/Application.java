package persistence;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.model.Table;
import persistence.sql.ddl.converter.H2TypeConverter;
import persistence.sql.ddl.converter.TypeConverter;
import persistence.sql.ddl.mapping.DDLQueryBuilder;
import persistence.sql.ddl.mapping.H2PrimaryKeyGenerationType;
import persistence.sql.ddl.mapping.PrimaryKeyGenerationType;
import persistence.sql.ddl.mapping.QueryBuilder;
import persistence.sql.ddl.model.DDLColumn;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final TypeConverter typeConverter = new H2TypeConverter();
            final PrimaryKeyGenerationType generationType = new H2PrimaryKeyGenerationType();
            final QueryBuilder queryBuilder = new DDLQueryBuilder(
                    new Table(),
                    new DDLColumn(typeConverter, generationType)
            );
            final String createQuery = queryBuilder.create(Person.class);
            final String dropQuery = queryBuilder.drop(Person.class);

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            jdbcTemplate.execute(createQuery);
            jdbcTemplate.execute(dropQuery);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
