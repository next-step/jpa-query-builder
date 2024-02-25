package persistence.sql.dml.builder;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
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
import static org.junit.jupiter.api.Assertions.assertAll;

class SelectQueryBuilderTest {
    private JdbcTemplate jdbcTemplate;
    private DatabaseServer server;

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

        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        SelectQueryDto<?> dto = selectQueryBuilder.findAll(Person.class);
        List<Person> persons = (List<Person>) jdbcTemplate.query(dto.getSql(), dto.getRowMapper());

        assertThat(persons).hasSize(2);
    }

    @Test
    @DisplayName("findById/데이터 insert 1회/1L로 조회 성공")
    void findById() {
        Person person = new Person("hoon25", 20, "hoon25@gmail.com");
        jdbcTemplate.execute(new InsertQueryBuilder().generateSQL(person));

        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        SelectQueryDto<?> dto = selectQueryBuilder.findById(Person.class, 1L);
        Person findPerson = (Person) jdbcTemplate.queryForObject(dto.getSql(), dto.getRowMapper());

        assertAll(
                () -> assertThat(findPerson.getId()).isEqualTo(findPerson.getId()),
                () -> assertThat(findPerson.getName()).isEqualTo(findPerson.getName())
        );
    }

    @Test
    @DisplayName("findById/데이터 insert 1회/2L로 조회 RunTimeException")
    void findByIdFail() {
        Person person = new Person("hoon25", 20, "hoon25@gmail.com");
        jdbcTemplate.execute(new InsertQueryBuilder().generateSQL(person));

        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        SelectQueryDto<?> dto = selectQueryBuilder.findById(Person.class, 2L);
        assertThatThrownBy(() -> jdbcTemplate.queryForObject(dto.getSql(), dto.getRowMapper()))
                .isInstanceOf(RuntimeException.class);
    }
}
