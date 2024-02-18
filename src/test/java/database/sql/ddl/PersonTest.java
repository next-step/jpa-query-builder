package database.sql.ddl;

import org.junit.jupiter.api.Test;

import static database.sql.ddl.QueryBuilder.convertFieldToDdl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PersonTest {

    @Test
    void creatingCreateQuery() {
        String query = buildQuery(Person.class);

        String expected = "CREATE TABLE users (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(100) NULL, old INT NULL, email VARCHAR(100) NOT NULL)";
        assertThat(query).isEqualTo(expected);
    }

    @Test
    void testConvertFieldToDdl() {
        Class<Person> clazz = Person.class;

        assertAll(
                () -> assertThat(convertFieldToDdl(clazz.getDeclaredField("id"))).isEqualTo("id BIGINT AUTO_INCREMENT PRIMARY KEY"),
                () -> assertThat(convertFieldToDdl(clazz.getDeclaredField("name"))).isEqualTo("nick_name VARCHAR(100) NULL"),
                () -> assertThat(convertFieldToDdl(clazz.getDeclaredField("age"))).isEqualTo("old INT NULL"),
                () -> assertThat(convertFieldToDdl(clazz.getDeclaredField("email"))).isEqualTo("email VARCHAR(100) NOT NULL")
        );
    }

    private String buildQuery(Class<?> entityClass) {
        return new QueryBuilder().buildCreateQuery(entityClass);
    }
}

