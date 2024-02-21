package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.Person;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectAllQueryBuilder;
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
            CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(Person.class);
            jdbcTemplate.execute(createQueryBuilder.build());

            Person person = new Person(1L, "John", 25, "email", 1);
            Person person2 = new Person(1L, "James", 45, "james@asdf.com", 10);
            InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();
            jdbcTemplate.execute(insertQueryBuilder.build(person));
            jdbcTemplate.execute(insertQueryBuilder.build(person2));

            SelectAllQueryBuilder selectAllQueryBuilder = new SelectAllQueryBuilder(Person.class);
            String selectAllQuery = selectAllQueryBuilder.build();
            RowMapper<Person> rowMapper = resultSet -> {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("nick_name");
                Integer age = resultSet.getInt("old");
                String email = resultSet.getString("email");
                return new Person(id, name, age, email, null);
            };
            List<Person> persons = jdbcTemplate.query(selectAllQuery, rowMapper);

            SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(Person.class);
            String selectQuery = selectQueryBuilder.build(1L);
            jdbcTemplate.queryForObject(selectQuery, rowMapper);

            DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder();
            String deleteQuery = deleteQueryBuilder.build(person);
            jdbcTemplate.execute(deleteQuery);

            DropQueryBuilder dropQuery = new DropQueryBuilder(Person.class);
            jdbcTemplate.execute(dropQuery.build());

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
