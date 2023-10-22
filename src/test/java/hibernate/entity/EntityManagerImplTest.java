package hibernate.entity;

import database.DatabaseServer;
import database.H2;
import hibernate.ddl.CreateQueryBuilder;
import jakarta.persistence.*;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class EntityManagerImplTest {

    private DatabaseServer server;
    private JdbcTemplate jdbcTemplate;
    private final CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder();
    private EntityManagerImpl entityManager;

    @BeforeEach
    void beforeEach() throws SQLException {
        server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
        entityManager = new EntityManagerImpl(jdbcTemplate);

        jdbcTemplate.execute(createQueryBuilder.generateQuery(new EntityClass<>(TestEntity.class)));
    }

    @AfterEach
    void afterEach() {
        server.stop();
    }

    @Test
    void 저장된_객채를_찾는다() {
        // given
        jdbcTemplate.execute("insert into test_entity (id, nick_name, age) values (1, '최진영', 19)");

        // when
        TestEntity actual = entityManager.find(TestEntity.class, 1L);

        // then
        assertAll(
                () -> assertThat(actual.id).isEqualTo(1L),
                () -> assertThat(actual.name).isEqualTo("최진영"),
                () -> assertThat(actual.age).isEqualTo(19)
        );
    }

    @Entity
    @Table(name= "test_entity")
    static class TestEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nick_name", nullable = false)
        private String name;

        private Integer age;

        @Transient
        private String email;
    }
}
