package persistence.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import database.DatabaseServer;
import database.H2;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.generator.CreateDDLQueryGenerator;
import persistence.sql.ddl.generator.DropDDLQueryGenerator;
import persistence.sql.ddl.generator.fixture.PersonV3;
import persistence.sql.dialect.H2ColumnType;
import persistence.sql.dml.Database;
import persistence.sql.dml.JdbcTemplate;
import persistence.sql.dml.statement.InsertStatementBuilder;

@DisplayName("EntityManager 통합 테스트")
class EntityManagerImplIntegrationTest {

    private DatabaseServer server;

    private Database jdbcTemplate;

    private EntityManager<PersonV3> entityManager;

    @BeforeEach
    void setUp() throws SQLException {
        server = new H2();
        server.start();

        entityManager = new EntityManagerImpl<>(PersonV3.class, server.getConnection(), new H2ColumnType());

        jdbcTemplate = new JdbcTemplate(server.getConnection());
        CreateDDLQueryGenerator createDDLQueryGenerator = new CreateDDLQueryGenerator(new H2ColumnType());
        jdbcTemplate.execute(createDDLQueryGenerator.create(PersonV3.class));
    }

    @AfterEach
    void tearDown() {
        DropDDLQueryGenerator dropDDLQueryGenerator = new DropDDLQueryGenerator(new H2ColumnType());
        jdbcTemplate.execute(dropDDLQueryGenerator.drop(PersonV3.class));
        server.stop();
    }

    @Test
    @DisplayName("EntityManager를 통해 ID로 원하는 Entity를 갖고 올 수 있다.")
    void selectFindById() {
        //given
        InsertStatementBuilder insertStatementBuilder = new InsertStatementBuilder(new H2ColumnType());
        PersonV3 person1 = new PersonV3("유저1", 20, "user1@jpa.com", 1);
        PersonV3 person2 = new PersonV3("유저2", 21, "user2@jpa.com", 2);
        PersonV3 person3 = new PersonV3("유저3", 25, "user3@jpa.com", 3);
        PersonV3 person4 = new PersonV3("유저4", 29, "user4@jpa.com", 4);

        final String person1Insert = insertStatementBuilder.insert(person1);
        final String person2Insert = insertStatementBuilder.insert(person2);
        final String person3Insert = insertStatementBuilder.insert(person3);
        final String person4Insert = insertStatementBuilder.insert(person4);

        jdbcTemplate.execute(person1Insert);
        jdbcTemplate.execute(person2Insert);
        jdbcTemplate.execute(person3Insert);
        jdbcTemplate.execute(person4Insert);

        final PersonV3 expectedPerson = entityManager.find(PersonV3.class, 1L);
        assertAll(
            () -> assertThat(expectedPerson.getId()).isEqualTo(1L),
            () -> assertThat(expectedPerson.getAge()).isEqualTo(20),
            () -> assertThat(expectedPerson.getEmail()).isEqualTo("user1@jpa.com"),
            () -> assertThat(expectedPerson.getIndex()).isEqualTo(null)
        );
    }

    @Test
    @DisplayName("EntityManager를 통해 Entity를 저장할 수 있다.")
    void persistEntity() {
        //given
        PersonV3 person1 = new PersonV3("유저1", 20, "user1@jpa.com", 1);

        //when
        entityManager.persist(person1);

        //then
        final PersonV3 expectedPerson = entityManager.find(PersonV3.class, 1L);
        assertAll(
            () -> assertThat(expectedPerson.getId()).isEqualTo(1L),
            () -> assertThat(expectedPerson.getAge()).isEqualTo(20),
            () -> assertThat(expectedPerson.getEmail()).isEqualTo("user1@jpa.com"),
            () -> assertThat(expectedPerson.getIndex()).isEqualTo(null)
        );
    }

    @Test
    @DisplayName("EntityManager를 통해 Entity를 삭제할 수 있다.")
    void deleteEntity() {
        //given
        PersonV3 person1 = new PersonV3("유저1", 20, "user1@jpa.com", 1);
        entityManager.persist(person1);
        final PersonV3 expectedPerson = entityManager.find(PersonV3.class, 1L);

        //expect
        assertThatCode(() -> entityManager.remove(expectedPerson))
            .doesNotThrowAnyException();
    }
}