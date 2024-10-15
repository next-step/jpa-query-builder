package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryBuilderTest {
    @Test
    @DisplayName("Person 엔티티 create 쿼리를 생성한다")
    void testCreatePersonQuery() {
        QueryBuilder queryBuilder = new QueryBuilder();
        String expectedQuery = "CREATE TABLE Person (id BIGINT PRIMARY KEY, name VARCHAR(255), age INTEGER);";
        assertThat(queryBuilder.create(Person.class)).isEqualTo(expectedQuery);
    }
}
