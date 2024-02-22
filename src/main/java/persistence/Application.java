package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.dialect.Database;
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

            String ddl = CreateQueryBuilder.generate(Person.class, Database.MYSQL).build();
            jdbcTemplate.execute(ddl);

            Person person1 = new Person("username", 30, "test@test.com", 1);
            Person person2 = new Person("username2", "email2@test.com", 12);

            InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();
            jdbcTemplate.execute(insertQueryBuilder.generate(person1, Database.MYSQL));
            jdbcTemplate.execute(insertQueryBuilder.generate(person2, Database.MYSQL));

            SelectQueryBuilder selectAllQueryBuilder = SelectQueryBuilder.generate(Person.class, Database.MYSQL).build();
            String findAll = selectAllQueryBuilder.findAll();
            RowMapper<Person> rowMapper = resultSet -> {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("nick_name");
                Integer age = resultSet.getInt("old");
                String email = resultSet.getString("email");
                return new Person(id, name, age, email, null);
            };
            List<Person> persons = jdbcTemplate.query(findAll, rowMapper);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
