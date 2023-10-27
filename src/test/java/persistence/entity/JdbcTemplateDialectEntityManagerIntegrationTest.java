package persistence.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.sql.dbms.Dialect;
import persistence.sql.ddl.CreateDDLQueryBuilder;
import persistence.sql.dml.InsertDMLQueryBuilder;
import persistence.testutils.ReflectionTestSupport;
import persistence.testutils.TestQueryExecuteSupport;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static persistence.entity.PersonFixtures.fixture;
import static persistence.entity.PersonFixtures.fixtureById;

class JdbcTemplateDialectEntityManagerIntegrationTest extends TestQueryExecuteSupport {
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        initializePersons(Arrays.asList(
                fixture(1L, "name1", 20, "email1"),
                fixture(2L, "name2", 20, "email2"),
                fixture(3L, "name3", 20, "email3")
        ));

        entityManager = new JdbcTemplateDialectEntityManager(jdbcTemplate, Dialect.H2);
    }

    @Test
    void find() {
        // when
        Person person = entityManager.find(Person.class, 1L);

        // then
        assertAll(
                () -> assertThat(ReflectionTestSupport.getFieldValue(person, "id")).isEqualTo(1L),
                () -> assertThat(ReflectionTestSupport.getFieldValue(person, "name")).isEqualTo("name1"),
                () -> assertThat(ReflectionTestSupport.getFieldValue(person, "age")).isEqualTo(20),
                () -> assertThat(ReflectionTestSupport.getFieldValue(person, "email")).isEqualTo("email1")
        );
    }

    @Test
    void persist() {
        // given
        Person beforePersistId4person = entityManager.find(Person.class, 4L);
        assertThat(beforePersistId4person).isNull();

        // when
        entityManager.persist(fixture(4L, "name4", 20, "email4"));

        // then
        Person afterPersistId4Person = entityManager.find(Person.class, 4L);

        // then
        assertAll(
                () -> assertThat(afterPersistId4Person).isNotNull(),
                () -> assertThat(ReflectionTestSupport.getFieldValue(afterPersistId4Person, "id")).isEqualTo(4L),
                () -> assertThat(ReflectionTestSupport.getFieldValue(afterPersistId4Person, "name")).isEqualTo("name4"),
                () -> assertThat(ReflectionTestSupport.getFieldValue(afterPersistId4Person, "age")).isEqualTo(20),
                () -> assertThat(ReflectionTestSupport.getFieldValue(afterPersistId4Person, "email")).isEqualTo("email4")
        );
    }

    @Test
    void remove() {
        // given
        Person beforeRemoveId1person = entityManager.find(Person.class, 1L);
        assertThat(beforeRemoveId1person).isNotNull();

        // when
        entityManager.remove(fixtureById(1L));

        // then
        Person afterRemoveId1Person = entityManager.find(Person.class, 1L);

        // then
        assertThat(afterRemoveId1Person).isNull();
    }

    private void initializePersons(List<Person> persons) {
        CreateDDLQueryBuilder<Person> createDDLQueryBuilder = new CreateDDLQueryBuilder<>(Dialect.H2, Person.class);
        String createQuery = createDDLQueryBuilder.build();
        jdbcTemplate.execute("DROP TABLE IF EXISTS USERS");
        jdbcTemplate.execute(createQuery.replace("CREATE TABLE USERS", "CREATE TABLE IF NOT EXISTS PUBLIC.USERS"));

        for (Person person : persons) {
            InsertDMLQueryBuilder<Person> insertDMLQueryBuilder = new InsertDMLQueryBuilder<>(Dialect.H2, person);
            jdbcTemplate.execute(insertDMLQueryBuilder.build());
        }
    }
}
