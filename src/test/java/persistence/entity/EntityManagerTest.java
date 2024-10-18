package persistence.entity;

import database.DatabaseServer;
import database.H2;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.*;
import persistence.sql.H2Dialect;
import persistence.sql.ddl.query.CreateQueryBuilder;
import persistence.sql.ddl.query.DropQueryBuilder;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class EntityManagerTest {

    @Entity
    private static class EntityManagerUpdateTestEntity {
        @Id
        private Long id;

        private String name;

        private Integer age;

        public EntityManagerUpdateTestEntity() {
        }

        public EntityManagerUpdateTestEntity(Long id, Integer age) {
            this.id = id;
            this.age = age;
        }

        public EntityManagerUpdateTestEntity(Long id, String name, Integer age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }

    private static DatabaseServer server;

    @BeforeEach
    void setUp() throws SQLException {
        server = new H2();
        server.start();

        String query = new CreateQueryBuilder(new H2Dialect()).build(EntityManagerUpdateTestEntity.class);
        new JdbcTemplate(server.getConnection()).execute(query);
    }

    @AfterEach
    void tearDown() throws SQLException {
        String query = new DropQueryBuilder().build(EntityManagerUpdateTestEntity.class);
        new JdbcTemplate(server.getConnection()).execute(query);
        server.stop();
    }

    @Test
    @DisplayName("EntityManager.persist()를 통해 엔티티를 저장한다.")
    void testPersist() throws Exception {
        EntityManager entityManager = new EntityManagerImpl(new JdbcTemplate(server.getConnection()));
        EntityManagerUpdateTestEntity entity = new EntityManagerUpdateTestEntity(1L, "john_doe", 30);
        entityManager.persist(entity);

        EntityManagerUpdateTestEntity persistedEntity = entityManager.find(EntityManagerUpdateTestEntity.class, 1L);
        assertAll(
                () -> assertThat(persistedEntity.id).isEqualTo(1L),
                () -> assertThat(persistedEntity.name).isEqualTo("john_doe"),
                () -> assertThat(persistedEntity.age).isEqualTo(30)
        );
    }

    @Test
    @DisplayName("EntityManager.update()를 통해 엔티티를 수정한다.")
    void testMerge() throws Exception {
        EntityManager entityManager = new EntityManagerImpl(new JdbcTemplate(server.getConnection()));
        EntityManagerUpdateTestEntity entity = new EntityManagerUpdateTestEntity(1L, "john_doe", 30);
        entityManager.persist(entity);

        entity.name = "jane_doe";
        entity.age = 40;

        entityManager.update(entity);

        EntityManagerUpdateTestEntity updated = entityManager.find(EntityManagerUpdateTestEntity.class, 1L);

        assertAll(
                () -> assertThat(updated.id).isEqualTo(1L),
                () -> assertThat(updated.name).isEqualTo("jane_doe"),
                () -> assertThat(updated.age).isEqualTo(40)
        );
    }

}
