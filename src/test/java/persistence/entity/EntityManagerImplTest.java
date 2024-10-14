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
                () -> assertThat(entityWithId.getId()).isNotNull(),
                () -> assertThat(entityWithId.getName()).isNotNull(),
                () -> assertThat(entityWithId.getAge()).isNotNull(),
                () -> assertThat(entityWithId.getEmail()).isNotNull(),
                () -> assertThat(entityWithId.getIndex()).isNull()
        );
    }

    @Test
    @DisplayName("엔티티를 저장한다.")
    void persist() {
        // given
        final EntityWithId entityWithId = new EntityWithId("Jaden", 30, "test@email.com", 1);
        final EntityManager<EntityWithId> entityManager = new EntityManagerImpl<>();

        // when
        entityManager.persist(entityWithId);

        // then
        final EntityWithId savedEntity = entityManager.find(EntityWithId.class, 1L);
        assertAll(
                () -> assertThat(savedEntity).isNotNull(),
                () -> assertThat(savedEntity.getId()).isNotNull(),
                () -> assertThat(savedEntity.getName()).isNotNull(),
                () -> assertThat(savedEntity.getAge()).isNotNull(),
                () -> assertThat(savedEntity.getEmail()).isNotNull(),
                () -> assertThat(savedEntity.getIndex()).isNull()
        );
    }

    @Test
    @DisplayName("엔티티를 삭제한다.")
    void remove() {
        // given
        final EntityWithId entityWithId = new EntityWithId(1L, "Jaden", 30, "test@email.com", 1);
        final EntityManager<EntityWithId> entityManager = new EntityManagerImpl<>();

        // when
        entityManager.remove(entityWithId);

        // then
        assertThatThrownBy(() -> entityManager.find(EntityWithId.class, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Expected 1 result, got");
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
