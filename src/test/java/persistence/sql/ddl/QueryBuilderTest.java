package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {
    @Test
    @DisplayName("요구사항 1 - 클래스 정보 출력")
    void generateCreateTableQuery() {
        QueryBuilder queryBuilder = new QueryBuilder();
        String createTableQuery = queryBuilder.createTableSql(Person.class);

        assertThat(createTableQuery).isEqualTo("CREATE TABLE person (id BIGINT, name VARCHAR, age INTEGER, PRIMARY KEY (id))");
    }
}