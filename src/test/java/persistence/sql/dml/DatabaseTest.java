package persistence.sql.dml;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.SchemaGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class DatabaseTest {

    private final SchemaGenerator schemaGenerator = new SchemaGenerator(Person.class);
    private final DmlGenerator dmlGenerator = new DmlGenerator(Person.class);
    private DatabaseServer server;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() throws SQLException {
        server = new H2();
        server.start();

        jdbcTemplate = new JdbcTemplate(server.getConnection());
        jdbcTemplate.execute(schemaGenerator.generateDropTableDdlString());
        jdbcTemplate.execute(schemaGenerator.generateCreateTableDdlString());
    }

    @AfterEach
    void teardown() {
        server.stop();
    }

    @DisplayName("INSERT 쿼리 실행 시 엔티티가 DB에 저장된다.")
    @Test
    void insert() {
        jdbcTemplate.execute(dmlGenerator.generateInsertQuery(new Person("jack", 20, "jack@abc.com")));
        Person person = jdbcTemplate.queryForObject(dmlGenerator.generateFindByIdQuery(1L), new PersonRowMapper());

        assertAll("저장된 Person 조회", () -> {
            assertThat(person.getId()).isEqualTo(1L);
            assertThat(person.getName()).isEqualTo("jack");
            assertThat(person.getAge()).isEqualTo(20);
            assertThat(person.getEmail()).isEqualTo("jack@abc.com");
            assertThat(person.getIndex()).isNull();
        });
    }

    @DisplayName("DB에 저장된 모든 엔티티를 조회한다.")
    @Test
    void findAll() {
        jdbcTemplate.execute(dmlGenerator.generateInsertQuery(new Person("jack", 20, "jack@abc.com")));
        jdbcTemplate.execute(dmlGenerator.generateInsertQuery(new Person("kevin", 30, "kevin@abc.com")));
        List<Person> persons = jdbcTemplate.query(dmlGenerator.generateFindAllQuery(), new PersonRowMapper());

        assertThat(persons).hasSize(2);
    }

    @DisplayName("식별자로 하나의 엔티티를 조회한다.")
    @Test
    void findById() {
        jdbcTemplate.execute(dmlGenerator.generateInsertQuery(new Person("jack", 20, "jack@abc.com")));
        jdbcTemplate.execute(dmlGenerator.generateInsertQuery(new Person("kevin", 30, "kevin@abc.com")));
        Person person = jdbcTemplate.queryForObject(dmlGenerator.generateFindByIdQuery(2L), new PersonRowMapper());

        assertAll("저장된 Person 조회", () -> {
            assertThat(person.getId()).isEqualTo(2L);
            assertThat(person.getName()).isEqualTo("kevin");
            assertThat(person.getAge()).isEqualTo(30);
            assertThat(person.getEmail()).isEqualTo("kevin@abc.com");
            assertThat(person.getIndex()).isNull();
        });
    }

    @DisplayName("DB에 저장된 모든 엔티티를 삭제한다.")
    @Test
    void deleteAll() {
        jdbcTemplate.execute(dmlGenerator.generateInsertQuery(new Person("jack", 20, "jack@abc.com")));
        jdbcTemplate.execute(dmlGenerator.generateInsertQuery(new Person("kevin", 30, "kevin@abc.com")));
        List<Person> persons = jdbcTemplate.query(dmlGenerator.generateFindAllQuery(), new PersonRowMapper());
        assertThat(persons).hasSize(2);

        jdbcTemplate.execute(dmlGenerator.generateDeleteAllQuery());
        persons = jdbcTemplate.query(dmlGenerator.generateFindAllQuery(), new PersonRowMapper());
        assertThat(persons).isEmpty();
    }

    @DisplayName("식별자로 하나의 엔티티를 삭제한다.")
    @Test
    void deleteById() {
        jdbcTemplate.execute(dmlGenerator.generateInsertQuery(new Person("jack", 20, "jack@abc.com")));
        jdbcTemplate.execute(dmlGenerator.generateInsertQuery(new Person("kevin", 30, "kevin@abc.com")));
        List<Person> persons = jdbcTemplate.query(dmlGenerator.generateFindAllQuery(), new PersonRowMapper());
        assertThat(persons).hasSize(2);

        jdbcTemplate.execute(dmlGenerator.generateDeleteByIdQuery(1L));
        persons = jdbcTemplate.query(dmlGenerator.generateFindAllQuery(), new PersonRowMapper());
        assertThat(persons).hasSize(1);
    }

    private static class PersonRowMapper implements RowMapper<Person> {
        @Override
        public Person mapRow(ResultSet resultSet) throws SQLException {
            Person person = new Person();
            person.setId(resultSet.getLong("id"));
            person.setName(resultSet.getString("nick_name"));
            person.setAge(resultSet.getInt("old"));
            person.setEmail(resultSet.getString("email"));
            return person;
        }
    }
}
