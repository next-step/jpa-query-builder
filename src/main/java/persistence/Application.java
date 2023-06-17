package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dialect.Dialect;
import persistence.dialect.collection.Dialects;
import persistence.entity.Person;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.ddl.JavaToSqlColumnParser;
import persistence.sql.dml.DmlQueryBuilder;

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
            final String createSql = new DdlQueryBuilder(columnParser, Person.class).createTable();
            jdbcTemplate.execute(createSql);

            final DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder(Person.class);
            final String insertSql = dmlQueryBuilder.insert(new Person(null, "정원", 20, "a@a.com", 10));
            jdbcTemplate.execute(insertSql);

            final String findAllSql = dmlQueryBuilder.findAll();
            jdbcTemplate.execute(findAllSql);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
