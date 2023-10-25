package persistence.entity;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.*;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.metadata.EntityMetadata;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SimpleEntityManagerTest {
    private JdbcTemplate jdbcTemplate;

    private EntityManager entityManager;

    private EntityMetadata entityMetadata = new EntityMetadata(Person.class);

    @BeforeEach
    void setJdbcTemplate() throws SQLException {
        DatabaseServer server = new H2();
        server.start();

        jdbcTemplate = new JdbcTemplate(server.getConnection());
        jdbcTemplate.execute(new DropQueryBuilder().buildQuery(entityMetadata));
        jdbcTemplate.execute(new CreateQueryBuilder().buildQuery(entityMetadata));
        jdbcTemplate.execute("INSERT INTO users (nick_name, old, email) VALUES ('hhhhhwi',1,'aab555586@gmail.com');");

        entityManager = new SimpleEntityManager(jdbcTemplate);
    }

    @DisplayName("EnityManager를 통해 PK 값이 일치하는 Entity를 찾는다.")
    @Test
    void test_find() {
        Person resultPerson = entityManager.find(Person.class, 1L);
        Assertions.assertAll(
                () -> assertTrue(resultPerson.getId().equals(1L)),
                () -> assertTrue(resultPerson.getName().equals("hhhhhwi"))
                );
    }

    @DisplayName("EntityManager를 통해 Entity를 저장한다.")
    @Test
    void test_persist() {
        entityManager.persist(new Person("name", 1, "test@email.com", 1));
        Person resultPerson = entityManager.find(Person.class, 2L);
        Assertions.assertAll(
                () -> assertTrue(resultPerson.getName().equals("name")),
                () -> assertTrue(resultPerson.getId().equals(2L))
        );
    }

    @DisplayName("EntityManager를 통해 Entity를 삭제한다.")
    @Test
    void test_remove() {
        entityManager.remove(new Person(1L, "hhhhhwi", 1, "aab555586@gmail.com", 0));
        Person resultPerson = entityManager.find(Person.class, 1L);

        assertThat(resultPerson).isNull();
    }
}
