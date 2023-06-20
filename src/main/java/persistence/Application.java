package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import jdbc.RowMapperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.model.Person;
import persistence.sql.ddl.builder.DdlQueryBuilder;
import persistence.sql.dml.builder.DeleteQueryBuilder;
import persistence.sql.dml.builder.InsertQueryBuilder;
import persistence.sql.dml.builder.SelectQueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            queryTest(server);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

    private static void queryTest(DatabaseServer server) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
        RowMapper<Person> rowMapper = new RowMapperImpl<>(Person.class);

        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder(Person.class);
        String createQuery = ddlQueryBuilder.create();
        jdbcTemplate.execute(createQuery);

        Person person = new Person("yohan", 31, "yohan@test.com", 0);
        InsertQueryBuilder insertQueryBuilder = InsertQueryBuilder.INSTANCE;
        String insertQuery = insertQueryBuilder.insert(person);
        jdbcTemplate.execute(insertQuery);

        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(person.getClass());
        String findAllQuery = selectQueryBuilder.findAll();
        List<Person> persons = jdbcTemplate.query(findAllQuery, rowMapper);
        logger.info("조회 데이터 수: " + persons.size());

        String findByIdQuery = selectQueryBuilder.findById(persons.get(0).getId());
        Person savedPerson = jdbcTemplate.queryForObject(findByIdQuery, rowMapper);
        logger.info("저장된 Person.id: " + savedPerson.getId());

        DeleteQueryBuilder deleteQueryBuilder = DeleteQueryBuilder.INSTANCE;
        String deleteQuery = deleteQueryBuilder.delete(savedPerson);
        jdbcTemplate.execute(deleteQuery);

        Person deletedPerson = jdbcTemplate.queryForObject(findByIdQuery, rowMapper);
        logger.info("삭제(null) 여부: " + deletedPerson);

        String dropQuery = ddlQueryBuilder.drop();
        jdbcTemplate.execute(dropQuery);
    }
}
