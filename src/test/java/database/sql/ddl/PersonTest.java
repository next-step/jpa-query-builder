package database.sql.ddl;

import org.junit.jupiter.api.Test;

import static database.sql.ddl.QueryBuilder.convertFieldToDdl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PersonTest {

    @Test
    void creatingCreateQuery() {
        String query = buildQuery(Person.class);

        String expected = "CREATE TABLE Person (id bigint unsigned auto_increment, name varchar(100), age int)";
        assertThat(query).isEqualTo(expected);
    }

    @Test
    void testConvertFieldToDdl() {
        Class<Person> clazz = Person.class;

        assertAll(
                () -> assertThat(convertFieldToDdl(clazz.getDeclaredField("id"))).isEqualTo("id bigint unsigned auto_increment"),
                () -> assertThat(convertFieldToDdl(clazz.getDeclaredField("name"))).isEqualTo("name varchar(100)"),
                () -> assertThat(convertFieldToDdl(clazz.getDeclaredField("age"))).isEqualTo("age int")
        );
    }

    private String buildQuery(Class<?> entityClass) {
        return new QueryBuilder().buildCreateQuery(entityClass);
    }
}

