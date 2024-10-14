package persistence.entity;

import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.example.Person;
import persistence.fixture.EntityWithId;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class EntityManagerImplTest {
    private Connection connection;

    @BeforeEach
    void setUp() {
        connection= H2ConnectionFactory.newConnection();
        createTable();
        insertData();
    }

    @AfterEach
    void tearDown() {
        dropTable();
    }

    @Test
    @DisplayName("엔티티를 조회한다.")
    void find() {
        // given
        final EntityManager<EntityWithId> entityManager = new EntityManagerImpl<>();

        // when
        final EntityWithId entityWithId = entityManager.find(EntityWithId.class, 1L);

        // then
        assertAll(
                () -> assertThat(entityWithId).isNotNull(),
                () -> assertThat(entityWithId.getId()).isEqualTo(1),
                () -> assertThat(entityWithId.getName()).isEqualTo("Jaden"),
                () -> assertThat(entityWithId.getAge()).isEqualTo(30),
                () -> assertThat(entityWithId.getEmail()).isEqualTo("test@email.com"),
                () -> assertThat(entityWithId.getIndex()).isNull()
        );
    }

    @Test
    @DisplayName("엔티티를 저장한다.")
    void persist() {
        // given
        final EntityManager<EntityWithId> entityManager = new EntityManagerImpl<>();
        final EntityWithId entityWithId = new EntityWithId("Jaden", 30, "test@email.com", 1);

        // when
        entityManager.persist(entityWithId);

        // then
        final EntityWithId savedEntity = entityManager.find(EntityWithId.class, 1L);
        assertAll(
                () -> assertThat(savedEntity).isNotNull(),
                () -> assertThat(savedEntity.getId()).isNotNull(),
                () -> assertThat(savedEntity.getName()).isEqualTo(entityWithId.getName()),
                () -> assertThat(savedEntity.getAge()).isEqualTo(entityWithId.getAge()),
                () -> assertThat(savedEntity.getEmail()).isEqualTo(entityWithId.getEmail()),
                () -> assertThat(savedEntity.getIndex()).isNull()
        );
    }

    @Test
    @DisplayName("엔티티를 삭제한다.")
    void remove() {
        // given
        final EntityManager<EntityWithId> entityManager = new EntityManagerImpl<>();
        final EntityWithId entityWithId = new EntityWithId(1L, "Jaden", 30, "test@email.com");

        // when
        entityManager.remove(entityWithId);

        // then
        assertThatThrownBy(() -> entityManager.find(EntityWithId.class, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Expected 1 result, got");
    }

    @Test
    @DisplayName("엔티티를 수정한다.")
    void update() {
        // given
        final EntityManager<EntityWithId> entityManager = new EntityManagerImpl<>();
        final EntityWithId entityWithId = new EntityWithId(1L, "Jackson", 20, "test2@email.com");

        // when
        entityManager.update(entityWithId);

        // then
        final EntityWithId savedEntity = entityManager.find(EntityWithId.class, 1L);
        assertAll(
                () -> assertThat(savedEntity).isNotNull(),
                () -> assertThat(savedEntity.getId()).isEqualTo(entityWithId.getId()),
                () -> assertThat(savedEntity.getName()).isEqualTo(entityWithId.getName()),
                () -> assertThat(savedEntity.getAge()).isEqualTo(entityWithId.getAge()),
                () -> assertThat(savedEntity.getEmail()).isEqualTo(entityWithId.getEmail()),
                () -> assertThat(savedEntity.getIndex()).isNull()
        );
    }

    private void createTable() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(connection);
        final CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(Person.class);
        jdbcTemplate.execute(createQueryBuilder.create());
    }

    private void insertData() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(connection);
        final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(
                new EntityWithId("Jaden", 30, "test@email.com", 1)
        );
        jdbcTemplate.execute(insertQueryBuilder.insert());
    }

    private void dropTable() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(connection);
        final DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(Person.class);
        jdbcTemplate.execute(dropQueryBuilder.drop());
    }
}
