package database.sql.ddl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {
    @ParameterizedTest
    @CsvSource(value = {
            "database.sql.ddl.OldPerson1:CREATE TABLE OldPerson1 (id BIGINT PRIMARY KEY, name VARCHAR(255) NULL, age INT NULL)",
            "database.sql.ddl.OldPerson2:CREATE TABLE OldPerson2 (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(255) NULL, old INT NULL, email VARCHAR(255) NOT NULL)",
            "database.sql.ddl.Person:CREATE TABLE users (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(255) NULL, old INT NULL, email VARCHAR(255) NOT NULL)"
    }, delimiter = ':')
    void buildCreateQuery(String entityClassCanonicalName, String expected) throws ClassNotFoundException {
        Class<?> entityClass = Class.forName(entityClassCanonicalName);
        QueryBuilder queryBuilder = new QueryBuilder();
        String actual = queryBuilder.buildCreateQuery(entityClass);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "database.sql.ddl.OldPerson1:DROP TABLE OldPerson1",
            "database.sql.ddl.OldPerson2:DROP TABLE OldPerson2",
            "database.sql.ddl.Person:DROP TABLE users"
    }, delimiter = ':')
    void buildDeleteQuery(Class<?> entityClass, String expected) {
        QueryBuilder queryBuilder = new QueryBuilder();
        String actual = queryBuilder.buildDeleteQuery(entityClass);
        assertThat(actual).isEqualTo(expected);
    }
}
