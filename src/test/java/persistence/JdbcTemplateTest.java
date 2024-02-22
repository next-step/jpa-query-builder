package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.RowMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.ddl.DDLQueryBuilder;
import persistence.sql.ddl.DMLQueryBuilder;

import java.sql.SQLException;

public class JdbcTemplateTest {
    private Person person;

    private DDLQueryBuilder ddlQueryBuilder;
    private DMLQueryBuilder dmlQueryBuilder;

    final String name = "kassy";
    final int age = 30;
    final String email = "test@gmail.com";

    @BeforeEach
    public void setUp() {
        person = new Person();
        person.setName(name);
        person.setAge(age);
        person.setEmail(email);

        ddlQueryBuilder = DDLQueryBuilder.getInstance();
        dmlQueryBuilder = new DMLQueryBuilder();
    }
    @Test
    public void insertTest() throws SQLException {
        final DatabaseServer server = new H2();
        server.start();

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

        jdbcTemplate.execute(ddlQueryBuilder.createTableQuery(Person.class));
        jdbcTemplate.execute(dmlQueryBuilder.insertSql(person));
        jdbcTemplate.query(dmlQueryBuilder.selectSql(Person.class), new RowMapperImpl());
        System.out.println(dmlQueryBuilder.selectByKeySql(Person.class, 1L));
        jdbcTemplate.query(dmlQueryBuilder.selectByKeySql(Person.class, 1L), new RowMapperImpl());

        server.stop();
    }

}
