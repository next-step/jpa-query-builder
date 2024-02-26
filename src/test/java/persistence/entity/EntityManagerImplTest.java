package persistence.entity;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class EntityManagerImplTest {

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() throws SQLException {
        DatabaseServer server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
        jdbcTemplate.execute(new CreateQueryBuilder(Person.class).build());
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute(new DropQueryBuilder(Person.class).build());
    }

    @Test
    @DisplayName("데이터베이스에서 Person 조회 테스트")
    void entityManagerFindTest() {
        // given
        EntityManager entityManager = new EntityManagerImpl(jdbcTemplate);
        jdbcTemplate.execute(new InsertQueryBuilder(new Person("jay", 30, "jay@gmail.com")).build());

        // when
        Person person = entityManager.find(Person.class, 1L);

        // then
        assertAll(
                () -> assertThat(person.getId()).isEqualTo(1L),
                () -> assertThat(person.getName()).isEqualTo("jay"),
                () -> assertThat(person.getAge()).isEqualTo(30),
                () -> assertThat(person.getEmail()).isEqualTo("jay@gmail.com")
        );
    }

    @Test
    @DisplayName("데이터베이스에 Person 저장 테스트")
    void entityManagerPersistTest() {
        // given
        EntityManager entityManager = new EntityManagerImpl(jdbcTemplate);
        Person person = new Person("jamie", 34, "jaime@gmail.com");

        // when
        entityManager.persist(person);
        Person findedPerson = entityManager.find(Person.class, 1L);

        // then
        assertAll(
                () -> assertThat(findedPerson.getId()).isEqualTo(1L),
                () -> assertThat(findedPerson.getName()).isEqualTo("jamie"),
                () -> assertThat(findedPerson.getAge()).isEqualTo(34),
                () -> assertThat(findedPerson.getEmail()).isEqualTo("jaime@gmail.com")
        );
    }
}
