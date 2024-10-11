package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateQueryBuilderTest {

    @Test
    @DisplayName("Should create a CREATE TABLE query")
    public void shouldCreateCreateTableQuery() {
        Person person = new Person();
        CreateQueryBuilder queryBuilder = new CreateQueryBuilder();

        // When
        String query = queryBuilder.build(person);

        // Then
        String expectedQuery = "CREATE TABLE person (id BIGINT, name VARCHAR(255), age INT, PRIMARY KEY (id));";
        assert query.equals(expectedQuery);
    }
}
