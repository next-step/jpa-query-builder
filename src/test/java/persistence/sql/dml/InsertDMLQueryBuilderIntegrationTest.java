package persistence.sql.dml;

import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dbms.DbmsStrategy;
import persistence.sql.ddl.CreateDDLQueryBuilder;
import persistence.testutils.ReflectionTestSupport;
import persistence.testutils.TestQueryExecuteSupport;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class InsertDMLQueryBuilderIntegrationTest extends TestQueryExecuteSupport {

    @Test
    void executeInsertDmlQuery() {
        // given
        CreateDDLQueryBuilder<Person> createDDLQueryBuilder = new CreateDDLQueryBuilder<>(DbmsStrategy.H2, Person.class);
        String createQuery = createDDLQueryBuilder.build();
        jdbcTemplate.execute(createQuery.replace("CREATE TABLE USERS", "CREATE TABLE PUBLIC.USERS"));
        List<Person> persons = Arrays.asList(
                createPerson(1L, "name1", 20, "email1"),
                createPerson(2L, "name2", 20, "email2"),
                createPerson(3L, "name3", 20, "email3")
        );

        // when
        for (Person person : persons) {
            InsertDMLQueryBuilder<Person> insertDMLQueryBuilder = new InsertDMLQueryBuilder<>(DbmsStrategy.H2, person);
            jdbcTemplate.execute(insertDMLQueryBuilder.build());
        }

        // then
        List<Person> selectQueryResultPersons = jdbcTemplate.query("SELECT * FROM USERS;", resultSet -> {
            int id = resultSet.getInt("ID");
            String name = resultSet.getString("NICK_NAME");
            int age = resultSet.getInt("OLD");
            String email = resultSet.getString("EMAIL");

            return createPerson(id, name, age, email);
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

    private Person createPerson(long id, String name, int age, String email) {
        Person person = new Person();
        ReflectionTestSupport.setFieldValue(person, "id", id);
        ReflectionTestSupport.setFieldValue(person, "name", name);
        ReflectionTestSupport.setFieldValue(person, "age", age);
        ReflectionTestSupport.setFieldValue(person, "email", email);
        return person;
    }
}
