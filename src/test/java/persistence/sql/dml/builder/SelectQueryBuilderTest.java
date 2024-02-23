package persistence.sql.dml.builder;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.domain.Person;
import persistence.sql.ddl.builder.CreateQueryBuilder;
import persistence.sql.ddl.builder.QueryBuilder;
import persistence.sql.ddl.dialect.H2Dialect;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SelectQueryBuilderTest {
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() throws SQLException {
        final DatabaseServer server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());

        QueryBuilder createQueryBuilder = new CreateQueryBuilder(new H2Dialect());
        jdbcTemplate.execute(createQueryBuilder.generateSQL(Person.class));
    }

    @Test
    @DisplayName("findAll/데이터 insert 2회/2개 조회 성공")
    void findAll() {
        Person person = new Person("hoon25", 20, "hoon25@gmail.com", 0);
        jdbcTemplate.execute(new InsertQueryBuilder().generateSQL(person));
        jdbcTemplate.execute(new InsertQueryBuilder().generateSQL(person));

        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        SelectQueryDto<?> dto = selectQueryBuilder.findAll(Person.class);
        List<Person> persons = (List<Person>) jdbcTemplate.query(dto.getSql(), dto.getRowMapper());

        assertThat(persons).hasSize(2);
    }

}
