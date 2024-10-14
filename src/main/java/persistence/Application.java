package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.example.Person;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            final CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(Person.class);
            jdbcTemplate.execute(createQueryBuilder.create());

            final Person entity = new Person("Jaden", 30, "test@email.com", 1);
            final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(entity);
            jdbcTemplate.execute(insertQueryBuilder.insert());

            final SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(Person.class);
            final List<Person> people = jdbcTemplate.query(selectQueryBuilder.findAll(), new PersonRowMapper());
            logger.debug(people.toString());

            final Person person = jdbcTemplate.queryForObject(selectQueryBuilder.findById(1), new PersonRowMapper());
            logger.debug(person.toString());

            final DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(Person.class);
            jdbcTemplate.execute(deleteQueryBuilder.delete(entity));

            final DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(Person.class);
            jdbcTemplate.execute(dropQueryBuilder.drop());

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

    private static class PersonRowMapper implements RowMapper<Person> {
        @Override
        public Person mapRow(ResultSet resultSet) throws SQLException {
            return new Person(
                    resultSet.getLong("id"),
                    resultSet.getString("nick_name"),
                    resultSet.getInt("old"),
                    resultSet.getString("email")
            );
        }
    }
}
