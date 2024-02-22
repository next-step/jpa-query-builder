package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.PersonRowMapper;
import jdbc.RowMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.ddl.DDLQueryBuilder;
import persistence.sql.dml.DMLQueryBuilder;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcTemplateTest {

    private DatabaseServer server;
    private JdbcTemplate jdbcTemplate;
    private DDLQueryBuilder ddlQueryBuilder;
    private DMLQueryBuilder dmlQueryBuilder;


    @BeforeEach
    public void setUp() throws SQLException {
        server = new H2();
        server.start();

        jdbcTemplate = new JdbcTemplate(server.getConnection());

        ddlQueryBuilder = DDLQueryBuilder.getInstance();
        dmlQueryBuilder = DMLQueryBuilder.getInstance();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        dropTable();

        server.stop();
    }

    private Person createPerson(String name, int age, String email) {
        final Person person = new Person();
        person.setName(name);
        person.setAge(age);
        person.setEmail(email);

        return person;
    }

    @Test
    @DisplayName("createTable 실행")
    public void createTableTest() throws SQLException {
        createTable();

    }

    @Test
    @DisplayName("insert 실행")
    public void insertTest() throws SQLException {
        List<Person> persons = List.of(
                createPerson("kassy", 30, "kassy@gmail.com"),
                createPerson("jinny", 24, "jinny@gmail.com"));
        createTable();

        insert(persons);
    }

    @Test
    @DisplayName("select 실행")
    public void selectTest() throws SQLException {
        createTable();
        List<Person> persons = List.of(
                createPerson("kassy", 30, "kassy@gmail.com"),
                createPerson("jinny", 24, "jinny@gmail.com"));
        insert(persons);

        List<Person> result = jdbcTemplate.query(dmlQueryBuilder.selectAllSql(Person.class), new PersonRowMapper());

        Assertions.assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("select by id 실행")
    public void selectByIdTest() throws SQLException {
        createTable();
        List<Person> persons = List.of(
                createPerson("kassy", 30, "kassy@gmail.com"),
                createPerson("jinny", 24, "jinny@gmail.com"));
        insert(persons);

        Person person = jdbcTemplate.queryForObject(dmlQueryBuilder.selectByIdQuery(Person.class, 1L), new PersonRowMapper());

        Assertions.assertThat(person.getId()).isNotNull();
        Assertions.assertThat(person.getName()).isEqualTo("kassy");

    }

    @Test
    @DisplayName("delete 실행")
    public void deleteTest() throws SQLException {
        createTable();
        List<Person> persons = List.of(
                createPerson("kassy", 30, "kassy@gmail.com"),
                createPerson("jinny", 24, "jinny@gmail.com"));
        insert(persons);

        final Person person = new Person();
        person.setId(1L);

        delete(person);
    }

    private void createTable() throws SQLException {
        jdbcTemplate.execute(ddlQueryBuilder.createTableQuery(Person.class));
    }

    private void insert(List<Person> list) throws SQLException {
        for (Person entity : list) {
            jdbcTemplate.execute(dmlQueryBuilder.insertSql(entity));
        }
    }

    private <T> T selectById(Class<T> tClass, Long id, RowMapper<T> rowMapper) {
        return jdbcTemplate.queryForObject(dmlQueryBuilder.selectByIdQuery(tClass, id), rowMapper);
    }

    private void delete(Person entity) throws SQLException {
        jdbcTemplate.execute(dmlQueryBuilder.deleteSql(entity));
    }

    private void dropTable() throws SQLException {
        jdbcTemplate.execute(ddlQueryBuilder.dropTableQuery(Person.class));
    }

}
