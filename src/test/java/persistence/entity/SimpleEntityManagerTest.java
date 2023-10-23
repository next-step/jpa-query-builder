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

import static org.junit.jupiter.api.Assertions.*;

class SimpleEntityManagerTest {
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setJdbcTemplate() throws SQLException {
        DatabaseServer server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
        jdbcTemplate.execute(new CreateQueryBuilder(Person.class).buildQuery());
        jdbcTemplate.execute("INSERT INTO users (nick_name, old, email) VALUES ('hhhhhwi',1,'aab555586@gmail.com');");
    }
    @DisplayName("EnityManager로 PK 값이 일치하는 Person 객체를 DB에서 찾는다.")
    @Test
    void test_find() {
        SimpleEntityManager entityManager = new SimpleEntityManager(jdbcTemplate);
        Person person = entityManager.find(Person.class, 1L);
        Assertions.assertAll(
                () -> assertTrue(person.getId().equals(1L)),
                () -> assertTrue(person.getName().equals("hhhhhwi"))
                );
    }
}
