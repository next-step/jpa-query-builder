package database.sql.ddl;

import org.junit.jupiter.api.Test;

import static database.sql.ddl.QueryBuilder.convertFieldToDdl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class QueryBuilderTest {

    @Test
    void buildCreateQuery() {
        QueryBuilder queryBuilder = new QueryBuilder();

        String actual = queryBuilder.buildCreateQuery(Person.class);

        assertThat(actual).isEqualTo("CREATE TABLE users (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(100) NULL, old INT NULL, email VARCHAR(100) NOT NULL)");
    }

    @Test
    void buildDeleteQuery() {
        QueryBuilder queryBuilder = new QueryBuilder();

        String actual = queryBuilder.buildDeleteQuery(Person.class);

        assertThat(actual).isEqualTo("DROP TABLE users");
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
}

