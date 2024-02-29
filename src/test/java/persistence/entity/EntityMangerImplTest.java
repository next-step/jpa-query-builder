package persistence.entity;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.*;
import persistence.Person;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.dml.InsertQueryBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EntityMangerImplTest {
    private static JdbcTemplate jdbcTemplate;
    private static DatabaseServer server;
    EntityManger entityManger = new EntityMangerImpl(jdbcTemplate, new H2GetGeneratedIdStrategy());
    private CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(new H2Dialect(), Person.class);;
    private DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(Person.class);;
    private InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(Person.class);;

    @BeforeAll
    public static void tearUp() throws Exception {
        server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
    }

    @AfterAll
    public static void tearDown() {
        server.stop();
    }

    @BeforeEach
    public void setUp() {
        String sql = createQueryBuilder.toQuery();
        jdbcTemplate.execute(sql);

        Person person1 = new Person(null, "nick_name", 10, "test@test.com", null);
        jdbcTemplate.execute(insertQueryBuilder.toQuery(person1));
    }

    @AfterEach
    public void cleanUp() {
        jdbcTemplate.execute(dropQueryBuilder.toQuery());
    }

    @Test
    @DisplayName("요구사항1: find")
    void testFind() {
        Long id = 1L;
        Person person = entityManger.find(Person.class, id);

        assertThat(person).isNotNull();
    }

    @Test
    @DisplayName("요구사항2: persist")
    void testPersist() {
        Person person = new Person(null, "nick_name", 10, "df", null);

        Object saved = entityManger.persist(person);
        Person savedPerson = (Person) saved;

        assertThat(savedPerson.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("요구사항3: delete")
    void testDelete() {
        Person person = new Person(1L, "nick_name", 10, "df", null);

        entityManger.remove(person);

        assertThrows(Exception.class, () -> {
            jdbcTemplate.queryForObject("select * from users where id = 1", rs -> new Person());
        });
    }
}
