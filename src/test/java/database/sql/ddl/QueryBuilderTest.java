package database.sql.ddl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class QueryBuilderTest {

    @Test
    void buildCreateQuery() {
        assertAll(
                () -> assertCreateQuery(OldPerson1.class, "CREATE TABLE OldPerson1 (id BIGINT PRIMARY KEY, name VARCHAR(255) NULL, age INT NULL)"),
                () -> assertCreateQuery(OldPerson2.class, "CREATE TABLE OldPerson2 (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(255) NULL, old INT NULL, email VARCHAR(255) NOT NULL)"),
                () -> assertCreateQuery(Person.class, "CREATE TABLE users (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(255) NULL, old INT NULL, email VARCHAR(255) NOT NULL)")
        );
    }

    private void assertCreateQuery(Class<?> entityClass, String expected) {
        QueryBuilder queryBuilder = new QueryBuilder();
        String actual = queryBuilder.buildCreateQuery(entityClass);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void buildDeleteQuery() {
        assertAll(
                () -> assertDropQuery(OldPerson1.class, "DROP TABLE OldPerson1"),
                () -> assertDropQuery(OldPerson2.class, "DROP TABLE OldPerson2"),
                () -> assertDropQuery(Person.class, "DROP TABLE users")
        );
    }

    private void assertDropQuery(Class<?> entityClass, String expected) {
        QueryBuilder queryBuilder = new QueryBuilder();
        String actual = queryBuilder.buildDeleteQuery(entityClass);
        assertThat(actual).isEqualTo(expected);
    }
}
