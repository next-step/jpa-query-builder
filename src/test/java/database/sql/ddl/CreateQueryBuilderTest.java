package database.sql.ddl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CreateQueryBuilderTest {
    @ParameterizedTest
    @CsvSource(value = {
            "database.sql.ddl.OldPerson1:CREATE TABLE OldPerson1 (id BIGINT PRIMARY KEY, name VARCHAR(255) NULL, age INT NULL)",
            "database.sql.ddl.OldPerson2:CREATE TABLE OldPerson2 (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(255) NULL, old INT NULL, email VARCHAR(255) NOT NULL)",
            "database.sql.ddl.OldPerson3:CREATE TABLE users (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(255) NULL, old INT NULL, email VARCHAR(255) NOT NULL)"
    }, delimiter = ':')
    void buildCreateQuery(Class<?> entityClass, String expected) {
        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(entityClass);
        String actual = createQueryBuilder.buildQuery();

        assertThat(actual).isEqualTo(expected);
    }
}
