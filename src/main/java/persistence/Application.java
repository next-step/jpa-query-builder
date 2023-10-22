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
import persistence.sql.dml.RowFindAllQueryGenerator;
import persistence.sql.dml.RowFindByIdQueryGenerator;

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

            String sql = new TableCreateQueryBuilder(h2Dialect).generateSQLQuery(person);
            logger.info(sql);
            jdbcTemplate.execute(sql);

            person.setEmail("a@email.com");
            sql = new ColumnInsertQueryGenerator(h2Dialect).generateSQLQuery(person);
            logger.info(sql);
            jdbcTemplate.execute(sql);

            person.setEmail("b@email.com");
            sql = new ColumnInsertQueryGenerator(h2Dialect).generateSQLQuery(person);
            logger.info(sql);
            jdbcTemplate.execute(sql);

            sql = new RowFindAllQueryGenerator(h2Dialect).generateSQLQuery(person);
            logger.info(sql);
            jdbcTemplate.query(sql, resultSet -> new Person(
                resultSet.getLong("id"),
                resultSet.getString("nick_name"),
                resultSet.getInt("old"),
                resultSet.getString("email")
            )).forEach(p -> logger.info("{}", p));

            sql = new RowFindByIdQueryGenerator(h2Dialect).findBy(1L, 3L).generateSQLQuery(person);
            logger.info(sql);
            jdbcTemplate.query(sql, resultSet -> new Person(
                resultSet.getLong("id"),
                resultSet.getString("nick_name"),
                resultSet.getInt("old"),
                resultSet.getString("email")
            )).forEach(p -> logger.info("{}", p));

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
