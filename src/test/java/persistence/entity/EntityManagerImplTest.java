package persistence.entity;

import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.example.Person;
import persistence.fixture.EntityWithId;
import persistence.fixture.EntityWithoutDefaultConstructor;
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
    @DisplayName("기본 생성자가 없는 엔티티를 조회하면 예외를 발생한다.")
    void find_exception() {
        // given
        final EntityManager<EntityWithoutDefaultConstructor> entityManager = new EntityManagerImpl<>();

        // when & then
        assertThatThrownBy(() -> entityManager.find(EntityWithoutDefaultConstructor.class, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(CustomRowMapper.NO_DEFAULT_CONSTRUCTOR_FAILED_MESSAGE);
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
