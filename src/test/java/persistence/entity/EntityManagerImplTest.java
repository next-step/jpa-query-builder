package persistence.entity;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class EntityManagerImplTest {

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() throws SQLException {
        final DatabaseServer server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
        jdbcTemplate.execute(new CreateQueryBuilder(Person.class).build());
        jdbcTemplate.execute(new InsertQueryBuilder(new Person("jamie", 34, "jaesungahn91@gmail.com")).build());
    }

    @Test
    @DisplayName("데이터베이스에서 Person 조회 테스트")
    void entityManagerFindTest() {
        // given
        EntityManager entityManager = new EntityManagerImpl(jdbcTemplate);

        // when
        Person person = entityManager.find(Person.class, 1L);

        // then
        assertAll(
                () -> assertThat(person.getId()).isEqualTo(1L),
                () -> assertThat(person.getName()).isEqualTo("jamie"),
                () -> assertThat(person.getAge()).isEqualTo(34),
                () -> assertThat(person.getEmail()).isEqualTo("jaesungahn91@gmail.com")
        );
    }

}
