package persistence.sql.dml.builder;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import jdbc.RowMapperImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.domain.Person;
import persistence.sql.ddl.builder.CreateQueryBuilder;
import persistence.sql.ddl.builder.DropQueryBuilder;
import persistence.sql.ddl.builder.QueryBuilder;
import persistence.sql.ddl.dialect.H2Dialect;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SelectQueryBuilderTest {
    private JdbcTemplate jdbcTemplate;
    private DatabaseServer server;
    private final SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
    private final RowMapper<Person> rowMapper = new RowMapperImpl<>(Person.class);


    @BeforeEach
    void setup() throws SQLException {
        server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());

        QueryBuilder createQueryBuilder = new CreateQueryBuilder(new H2Dialect());
        jdbcTemplate.execute(createQueryBuilder.generateSQL(Person.class));
    }

    @AfterEach
    void clean() throws SQLException {
        String sql = new DropQueryBuilder(new H2Dialect()).generateSQL(Person.class);
        jdbcTemplate.execute(sql);
        server.stop();
    }

    @Test
    @DisplayName("findAll/데이터 insert 2회/2개 조회 성공")
    void findAll() {
        Person person = new Person("hoon25", 20, "hoon25@gmail.com");
        jdbcTemplate.execute(new InsertQueryBuilder().generateSQL(person));
        jdbcTemplate.execute(new InsertQueryBuilder().generateSQL(person));

        List<Person> persons = jdbcTemplate.query(selectQueryBuilder.findAll(Person.class), rowMapper);

        assertThat(persons).hasSize(2);
    }

    @Test
    @DisplayName("findById/데이터 insert 1회/1L로 조회 성공")
    void findById() {
        Person person = new Person("hoon25", 20, "hoon25@gmail.com");
        jdbcTemplate.execute(new InsertQueryBuilder().generateSQL(person));

        Person findPerson = jdbcTemplate.queryForObject(selectQueryBuilder.findById(Person.class, 1L), rowMapper);

        assertThat(person.getName()).isEqualTo(findPerson.getName());
    }

    @Test
    @DisplayName("findById/데이터 insert 1회/2L로 조회 RunTimeException")
    void findByIdFail() {
        Person person = new Person("hoon25", 20, "hoon25@gmail.com");
        jdbcTemplate.execute(new InsertQueryBuilder().generateSQL(person));

        assertThatThrownBy(() -> jdbcTemplate.queryForObject(selectQueryBuilder.findById(Person.class, 2L), rowMapper))
                .isInstanceOf(RuntimeException.class);
    }
}
