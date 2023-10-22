package persistence;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import fixture.PersonFixtureFactory;
import jdbc.EntityRowMapper;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.DdlQueryGenerator;
import persistence.sql.dialect.h2.H2Dialect;
import persistence.sql.dml.DmlQueryGenerator;
import persistence.sql.meta.EntityMeta;
import persistence.sql.meta.MetaFactory;

import java.util.List;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            DdlQueryGenerator ddlGenerator = DdlQueryGenerator.of(H2Dialect.getInstance());
            DmlQueryGenerator dmlGenerator = DmlQueryGenerator.of(H2Dialect.getInstance());

            EntityMeta personMeta = MetaFactory.get(Person.class);
            jdbcTemplate.execute(ddlGenerator.generateCreateQuery(personMeta));

            for (Person personFixture : PersonFixtureFactory.getFixtures()) {
                String insertPersonQuery = dmlGenerator.generateInsertQuery(personFixture);
                jdbcTemplate.execute(insertPersonQuery);
            }

            String selectAllQuery = dmlGenerator.generateSelectAllQuery(Person.class);
            List<Person> personList = jdbcTemplate.query(selectAllQuery, new EntityRowMapper<>(Person.class));
            logger.info("personList size = {}", personList.size());

            String selectByPkQuery = dmlGenerator.generateSelectByPkQuery(Person.class, 1L);
            jdbcTemplate.queryForObject(selectByPkQuery, new EntityRowMapper<>(Person.class));

            String deleteByPkQuery = dmlGenerator.generateDeleteByPkQuery(Person.class, 1L);
            jdbcTemplate.execute(deleteByPkQuery);

            personList = jdbcTemplate.query(selectAllQuery, new EntityRowMapper<>(Person.class));
            logger.info("personList size = {}", personList.size());

            String deleteAllQuery = dmlGenerator.generateDeleteAllQuery(Person.class);
            jdbcTemplate.execute(deleteAllQuery);

            personList = jdbcTemplate.query(selectAllQuery, new EntityRowMapper<>(Person.class));
            logger.info("personList size = {}", personList.size());

            jdbcTemplate.execute(ddlGenerator.generateDropQuery(personMeta));

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
