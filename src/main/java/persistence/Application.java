package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.H2Dialect;
import persistence.sql.ddl.TableCreateQueryBuilder;
import persistence.sql.ddl.TableDropQueryBuilder;
import persistence.sql.dml.ColumnInsertQueryGenerator;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        DatabaseServer server = null;
        try {
            server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            final H2Dialect h2Dialect = new H2Dialect();
            final Person person = new Person();
            person.setEmail("a");

            String sql = new TableCreateQueryBuilder(h2Dialect).generateSQLQuery(person);
            logger.info(sql);
            jdbcTemplate.execute(sql);

            sql = new ColumnInsertQueryGenerator(h2Dialect).generateSQLQuery(person);
            logger.info(sql);
            jdbcTemplate.execute(sql);

            sql = new TableDropQueryBuilder(h2Dialect).generateSQLQuery(person);
            logger.info(sql);
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
            if (server != null) {
                server.stop();
            }
        }
    }
}
