package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.column.TableColumn;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.dialect.Database;
import persistence.sql.dml.DeleteQueryBuilder;
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

            TableColumn tableColumn = TableColumn.from(Person.class, Database.MYSQL);

            createPersonDdl(jdbcTemplate, tableColumn);
            Person person1 = new Person("username", 30, "test@test.com", 1);
            insertPerson(tableColumn, person1, jdbcTemplate);

            RowMapper<Person> rowMapper = getPersonRowMapper();

            selectAll(tableColumn, person1, jdbcTemplate, rowMapper);

            selectOne(tableColumn, person1, jdbcTemplate, rowMapper);

            deletePerson(tableColumn, person1, jdbcTemplate);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

    private static void createPersonDdl(JdbcTemplate jdbcTemplate, TableColumn tableColumn) {
        String ddl = new CreateQueryBuilder(tableColumn).build();
        jdbcTemplate.execute(ddl);
    }

    private static void insertPerson(TableColumn tableColumn, Person person1, JdbcTemplate jdbcTemplate) {

        Person person2 = new Person("username2", "email2@test.com", 12);

        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(tableColumn);
        jdbcTemplate.execute(insertQueryBuilder.build(person1));
        jdbcTemplate.execute(insertQueryBuilder.build(person2));
    }

    private static RowMapper<Person> getPersonRowMapper() {
        return resultSet -> {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("nick_name");
            Integer age = resultSet.getInt("old");
            String email = resultSet.getString("email");
            return new Person(id, name, age, email, null);
        };
    }

    private static void selectAll(TableColumn tableColumn, Person person, JdbcTemplate jdbcTemplate, RowMapper<Person> rowMapper) {
        SelectQueryBuilder queryBuilder = new SelectQueryBuilder(tableColumn);
        String findAll = queryBuilder.build(person).findAll();
        List<Person> persons = jdbcTemplate.query(findAll, rowMapper);
    }

    private static void selectOne(TableColumn tableColumn, Person person, JdbcTemplate jdbcTemplate, RowMapper<Person> rowMapper) {
        SelectQueryBuilder queryBuilder = new SelectQueryBuilder(tableColumn);

        String selectOneQuery = queryBuilder.build(person).findById(1L);
        jdbcTemplate.queryForObject(selectOneQuery, rowMapper);
    }

    private static void deletePerson(TableColumn tableColumn, Person person, JdbcTemplate jdbcTemplate) {

        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(tableColumn);
        String deleteQuery = deleteQueryBuilder.build(person).deleteById(1L);
        jdbcTemplate.execute(deleteQuery);
    }

		RowMapper<Person> rowMapper = new PersonMapper();
}
