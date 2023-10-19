package persistence;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import fixture.PersonFixtureFactory;
import jdbc.EntityRowMapper;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.dialect.DbmsDdlQueryBuilder;
import persistence.sql.dialect.DbmsDmlQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;

import java.util.List;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            DbmsDdlQueryBuilder ddlbuilder = DbmsDdlQueryBuilder.findByDbmsType(H2.class.getSimpleName());
            DbmsDmlQueryBuilder dmlbuilder = DbmsDmlQueryBuilder.findByDbmsType(H2.class.getSimpleName());

            CreateQueryBuilder createQueryBuilder = ddlbuilder.getCreateQueryBuilder();
            jdbcTemplate.execute(createQueryBuilder.getQuery(Person.class));

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
            Person person = jdbcTemplate.queryForObject(selectByPkQuery, new EntityRowMapper<>(Person.class));

            DropQueryBuilder dropQueryBuilder = ddlbuilder.getDropQueryBuilder();
            jdbcTemplate.execute(dropQueryBuilder.getQuery(Person.class));

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
