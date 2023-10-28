package persistence.entity;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.*;
import persistence.sql.ddl.EntityDefinitionBuilder;
import persistence.sql.ddl.dialect.H2Dialect;
import persistence.sql.dml.EntityManipulationBuilder;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleEntityManagerTest {

    private static DatabaseServer server;
    private static JdbcTemplate jdbcTemplate;
    private static EntityDefinitionBuilder entityDefinitionBuilder;
    private static EntityManipulationBuilder entityManipulationBuilder;

    @BeforeAll
    static void beforeAll() throws SQLException {
        server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
        entityDefinitionBuilder = new EntityDefinitionBuilder(Person.class, new H2Dialect());
        entityManipulationBuilder = new EntityManipulationBuilder(Person.class, new H2Dialect());
    }

    @AfterAll
    static void afterAll() {
        server.stop();
    }

    @BeforeEach
    void beforeEach() {
        jdbcTemplate.execute(entityDefinitionBuilder.create());
        jdbcTemplate.execute(entityManipulationBuilder
                .insert(new Person("test1", 30, "test1@gmail.com"))
        );
    }

    @AfterEach
    void afterEach() {
        jdbcTemplate.execute(entityDefinitionBuilder.drop());
    }

    @Test
    @DisplayName("find() 메서드 테스트")
    void find() {
//        final EntityManager entityManager = new SimpleEntityManager(entityManipulationBuilder);
//
//        Person person = entityManager.find(Person.class, 1L);
//
//        assertThat(person.getId()).isEqualTo(1L);
    }

}
