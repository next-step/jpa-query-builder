package persistence.entity;

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
import persistence.sql.dml.builder.InsertQueryBuilder;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class EntityManagerImplTest {

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

    @DisplayName("find/id로 조회/성공")
    @Test
    void find() {
        Person person = new Person("hoon25", 20, "hoon25@gmail.com");
        jdbcTemplate.execute(new InsertQueryBuilder().generateSQL(person));

        EntityManager<Person> entityManager = new EntityManagerImpl<>();
        Person findPerson = entityManager.find(Person.class, 1L);

        assertThat(person.getName()).isEqualTo(findPerson.getName());
    }

}
