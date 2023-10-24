package persistence.entity;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.CreateQueryBuilder;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SimpleEntityManagerTest {
    private JdbcTemplate jdbcTemplate;

    private EntityManager entityManager;

    @BeforeEach
    void setJdbcTemplate() throws SQLException {
        DatabaseServer server = new H2();
        server.start();

        jdbcTemplate = new JdbcTemplate(server.getConnection());
        jdbcTemplate.execute(new CreateQueryBuilder(Person.class).buildQuery());
        jdbcTemplate.execute("INSERT INTO users (nick_name, old, email) VALUES ('hhhhhwi',1,'aab555586@gmail.com');");

        entityManager = new SimpleEntityManager(jdbcTemplate);
    }

    @DisplayName("EnityManager로 PK 값이 일치하는 Person 객체를 DB에서 찾는다.")
    @Test
    void test_find() {
        Person resultPerson = entityManager.find(Person.class, 1L);
        Assertions.assertAll(
                () -> assertTrue(resultPerson.getId().equals(1L)),
                () -> assertTrue(resultPerson.getName().equals("hhhhhwi"))
                );
    }

    @DisplayName("EntityManager로 Person 객체를 DB에 저장한다.")
    @Test
    void test_persist() {
        entityManager.persist(new Person("name", 1, "test@email.com", 1));
        Person resultPerson = entityManager.find(Person.class, 2L);
        Assertions.assertAll(
                () -> assertTrue(resultPerson.getName().equals("name")),
                () -> assertTrue(resultPerson.getId().equals(2L))
        );
    }
}
