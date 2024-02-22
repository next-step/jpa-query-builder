package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.DatabaseDialect;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.ddl.DdlQueryBuilderFactory;
import persistence.sql.ddl.Person;
import persistence.sql.dml.DmlQueryBuilder;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

            DdlQueryBuilderFactory factory = new DdlQueryBuilderFactory();

            DdlQueryBuilder ddlQueryBuilder = factory.getInstance(DatabaseDialect.MYSQL);

            jdbcTemplate.execute(ddlQueryBuilder.createQuery(Person.class));

            DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();
            Person person = new Person("cs",29,"katd216@gmail.com",0);
            jdbcTemplate.execute(dmlQueryBuilder.insert(person));
            jdbcTemplate.execute(dmlQueryBuilder.insert(person));
            jdbcTemplate.execute(dmlQueryBuilder.insert(person));

            PersonRowMapper mapper = new PersonRowMapper();
            List<Person> foundPerson = jdbcTemplate.query(dmlQueryBuilder.findById(Person.class, 1l), mapper);
            System.out.println("foundPerson = " + foundPerson);
            System.out.println("dmlQueryBuilder.delete(foundPerson.get(0)) = " + dmlQueryBuilder.delete(foundPerson.get(0)));
            jdbcTemplate.execute(dmlQueryBuilder.delete(foundPerson.get(0)));
            List<Person> deletePerson = jdbcTemplate.query(dmlQueryBuilder.findById(Person.class,1l), mapper);
            System.out.println("foundPerson = " + deletePerson);


            jdbcTemplate.execute(ddlQueryBuilder.dropQuery(Person.class));

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
                    resultSet.getString("email"),
                    0
            );
        }
    }
}
