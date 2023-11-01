package persistence.entity;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.*;
import persistence.sql.ddl.EntityDefinitionBuilder;
import persistence.sql.ddl.EntityMetadata;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.H2Dialect;
import persistence.sql.dml.EntityManipulationBuilder;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleEntityManagerTest {

    private static DatabaseServer server;
    private static JdbcTemplate jdbcTemplate;
    private EntityDefinitionBuilder entityDefinitionBuilder;
    private EntityManipulationBuilder entityManipulationBuilder;

    private EntityManager entityManager;

    @BeforeAll
    static void beforeAll() throws SQLException {
        server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
    }

    @AfterAll
    static void afterAll() {
        server.stop();
    }

    @BeforeEach
    void beforeEach() {
        EntityMetadata entityMetadata = EntityMetadata.of(Person.class, new H2Dialect());
        entityDefinitionBuilder = new EntityDefinitionBuilder(entityMetadata);
        entityManipulationBuilder = new EntityManipulationBuilder(entityMetadata);
        Dialect dialect = new H2Dialect();
        entityManager = new SimpleEntityManager(jdbcTemplate, dialect);

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
        Person person = entityManager.find(Person.class, 1L);

        assertThat(person.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("persist() 메서드 테스트")
    void persist() {
        Person person = new Person("test2", 30, "test1@gmail.com");

        Object key = entityManager.persist(person);

        assertThat(key).isEqualTo(2L);
    }

    @Test
    @DisplayName("remove() 메서드 테스트")
    void remove() {
        Person person = entityManager.find(Person.class, 1L);
        entityManager.remove(person);

        Person removedPerson = entityManager.find(Person.class, 1L);
        assertThat(removedPerson).isNull();
    }

}
