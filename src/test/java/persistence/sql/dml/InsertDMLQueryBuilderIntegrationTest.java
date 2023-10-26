package persistence.sql.dml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dbms.Dialect;
import persistence.sql.ddl.CreateDDLQueryBuilder;
import persistence.testutils.ReflectionTestSupport;
import persistence.testutils.TestQueryExecuteSupport;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static persistence.entity.PersonFixtures.fixture;

class InsertDMLQueryBuilderIntegrationTest extends TestQueryExecuteSupport {
    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS PUBLIC.USERS;");
    }

    @Test
    void executeInsertDmlQuery() {
        // given
        CreateDDLQueryBuilder<Person> createDDLQueryBuilder = new CreateDDLQueryBuilder<>(Dialect.H2, Person.class);
        String createQuery = createDDLQueryBuilder.build();
        jdbcTemplate.execute(createQuery.replace("CREATE TABLE USERS", "CREATE TABLE IF NOT EXISTS PUBLIC.USERS"));
        List<Person> persons = Arrays.asList(
                fixture(1L, "name1", 20, "email1"),
                fixture(2L, "name2", 20, "email2"),
                fixture(3L, "name3", 20, "email3")
        );

        // when
        for (Person person : persons) {
            InsertDMLQueryBuilder<Person> insertDMLQueryBuilder = new InsertDMLQueryBuilder<>(Dialect.H2, person);
            jdbcTemplate.execute(insertDMLQueryBuilder.build());
        }

        // then
        List<Person> selectQueryResultPersons = jdbcTemplate.query("SELECT * FROM USERS;", resultSet -> {
            int id = resultSet.getInt("ID");
            String name = resultSet.getString("NICK_NAME");
            int age = resultSet.getInt("OLD");
            String email = resultSet.getString("EMAIL");

            return fixture(id, name, age, email);
        });

        assertAll(
                () -> assertThat(selectQueryResultPersons).hasSize(3),
                () -> assertThat(selectQueryResultPersons.stream()
                        .map(it -> ReflectionTestSupport.getFieldValue(it, "id"))
                ).containsExactly(1L, 2L, 3L),
                () -> assertThat(selectQueryResultPersons.stream()
                        .map(it -> ReflectionTestSupport.getFieldValue(it, "name"))
                ).containsExactly("name1", "name2", "name3"),
                () -> assertThat(selectQueryResultPersons.stream()
                        .map(it -> ReflectionTestSupport.getFieldValue(it, "email"))
                ).containsExactly("email1", "email2", "email3")
        );
    }
}
