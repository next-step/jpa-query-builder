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
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.DmlQueryGenerator;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;
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
            DdlQueryGenerator ddlGenerator = DdlQueryGenerator.of(new H2Dialect());
            DmlQueryGenerator dmlbuilder = DmlQueryGenerator.findByDbmsType(H2.class.getSimpleName());

            EntityMeta personMeta = MetaFactory.get(Person.class);
            jdbcTemplate.execute(ddlGenerator.generateCreateQuery(personMeta));

            InsertQueryBuilder insertQueryBuilder = dmlbuilder.getInsertQueryBuilder();
            for (Person personFixture : PersonFixtureFactory.getFixtures()) {
                String insertPersonQuery = insertQueryBuilder.getQuery(personFixture);
                jdbcTemplate.execute(insertPersonQuery);
            }

            SelectQueryBuilder selectQueryBuilder = dmlbuilder.getSelectQueryBuilder();
            String selectAllQuery = selectQueryBuilder.getSelectAllQuery(Person.class);
            List<Person> personList = jdbcTemplate.query(selectAllQuery, new EntityRowMapper<>(Person.class));
            logger.info("personList size = {}", personList.size());

            String selectByPkQuery = selectQueryBuilder.getSelectByPkQuery(Person.class, 1L);
            jdbcTemplate.queryForObject(selectByPkQuery, new EntityRowMapper<>(Person.class));

            DeleteQueryBuilder deleteQueryBuilder = dmlbuilder.getDeleteQueryBuilder();
            String deleteByPkQuery = deleteQueryBuilder.getDeleteByPkQuery(Person.class, 1L);
            jdbcTemplate.execute(deleteByPkQuery);

            personList = jdbcTemplate.query(selectAllQuery, new EntityRowMapper<>(Person.class));
            logger.info("personList size = {}", personList.size());

            String deleteAllQuery = deleteQueryBuilder.getDeleteAllQuery(Person.class);
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
