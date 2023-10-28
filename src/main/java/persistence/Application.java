package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entity.EntityManager;
import persistence.entity.JdbcEntityManager;
import persistence.sql.H2Dialect;
import persistence.sql.ddl.TableCreateQueryBuilder;
import persistence.sql.ddl.TableDropQueryBuilder;
import persistence.sql.dml.RowDeleteQueryBuilder;
import persistence.sql.dml.RowFindAllQueryBuilder;
import persistence.sql.dml.RowFindByIdQueryBuilder;
import persistence.sql.dml.RowInsertQueryBuilder;

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

            String sql = new TableCreateQueryBuilder(h2Dialect).generateSQLQuery(person.getClass());
            logger.info(sql);
            jdbcTemplate.execute(sql);

            person.setEmail("a@email.com").setAge(11);
            sql = new RowInsertQueryBuilder(h2Dialect).generateSQLQuery(person);
            logger.info(sql);
            jdbcTemplate.execute(sql);

            person.setEmail("b@email.com").setAge(21).setId(2L);
            sql = new RowInsertQueryBuilder(h2Dialect).generateSQLQuery(person);
            logger.info(sql);
            jdbcTemplate.execute(sql);

            sql = new RowFindAllQueryBuilder(h2Dialect).generateSQLQuery(person.getClass());
            logger.info(sql);
            jdbcTemplate.query(sql, resultSet -> new Person(
                resultSet.getLong("id"),
                resultSet.getString("nick_name"),
                resultSet.getInt("old"),
                resultSet.getString("email")
            )).forEach(p -> logger.info("{}", p));

            EntityManager jdbcEntityManager = new JdbcEntityManager(h2Dialect, jdbcTemplate);
            person.setEmail("c@email.com").setAge(31).setId(3L);
            Person person3 = (Person) jdbcEntityManager.persist(person);
            logger.info("entity after persist: {}", person3);
            person3 = jdbcEntityManager.find(Person.class, 3L);
            logger.info("entity after find: {}", person3);
            jdbcEntityManager.remove(person3);
            person3 = jdbcEntityManager.find(Person.class, 3L);
            logger.info("entity after remove: {}", person3);

            person.setId(1L);
            sql = new RowDeleteQueryBuilder(h2Dialect).generateSQLQuery(person);
            logger.info(sql);
            jdbcTemplate.execute(sql);

            sql = new RowFindByIdQueryBuilder(h2Dialect).findBy(1L, 2L).generateSQLQuery(person.getClass());
            logger.info(sql);
            jdbcTemplate.query(sql, resultSet -> new Person(
                resultSet.getLong("id"),
                resultSet.getString("nick_name"),
                resultSet.getInt("old"),
                resultSet.getString("email")
            )).forEach(p -> logger.info("{}", p));

            sql = new TableDropQueryBuilder(h2Dialect).generateSQLQuery(person.getClass());
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
