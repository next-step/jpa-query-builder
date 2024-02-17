package persistence.sql.ddl;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {

    @Disabled
    @Test
    @DisplayName("요구사항 1 - 아래 정보를 바탕으로 create 쿼리 만들어보기")
    void generateCreateTableQuery() {
        QueryBuilder queryBuilder = new QueryBuilder();
        String createTableQuery = queryBuilder.createTableSql(Person.class);

        assertThat(createTableQuery).isEqualTo("CREATE TABLE person (id BIGINT, name VARCHAR, age INTEGER, PRIMARY KEY (id))");
    }

    @Disabled
    @Test
    @DisplayName("요구사항 2 - 추가된 정보를 통해 create 쿼리 만들어보기")
    void generateCreateTableQuery_WithOptionalColumnAnnotation() {
        QueryBuilder queryBuilder = new QueryBuilder();
        String createTableQuery = queryBuilder.createTableSql(Person.class);

        assertThat(createTableQuery).isEqualTo("CREATE TABLE person (id BIGINT NOT NULL AUTO_INCREMENT, nick_name VARCHAR, old INTEGER, email VARCHAR NOT NULL, PRIMARY KEY (id))");
    }

    @Test
    @DisplayName("요구사항 3 - 추가된 정보를 통해 create 쿼리 만들어보기2")
    void generateCreateTableQuery_WithTableAndTransientAnnotation() {
        QueryBuilder queryBuilder = new QueryBuilder();
        String createTableQuery = queryBuilder.createTableSql(Person.class);

        assertThat(createTableQuery).isEqualTo("CREATE TABLE users (id BIGINT NOT NULL AUTO_INCREMENT, nick_name VARCHAR, old INTEGER, email VARCHAR NOT NULL, PRIMARY KEY (id))");
    }

    @Test
    @DisplayName("요구사항 4 - 정보를 바탕으로 drop 쿼리 만들어보기")
    void generateDropTableQuery() {
        QueryBuilder queryBuilder = new QueryBuilder();
        String dropTableQuery = queryBuilder.dropTableSql(Person.class);

        assertThat(dropTableQuery).isEqualTo("DROP TABLE users");
    }
}