package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.H2Dialect;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {

    private Dialect dialect;

    @BeforeEach
    void setUp() {
        dialect = H2Dialect.getInstance();
    }

    @Test
    @DisplayName("요구사항 3 - 추가된 정보를 통해 create 쿼리 만들어보기2")
    void generateCreateTableQuery_WithTableAndTransientAnnotation() {
        QueryBuilder queryBuilder = new QueryBuilder(dialect);
        String createTableQuery = queryBuilder.createTableSql(Person.class);

        assertThat(createTableQuery).isEqualTo("CREATE TABLE users (id BIGINT NOT NULL AUTO_INCREMENT, nick_name VARCHAR, old INTEGER, email VARCHAR NOT NULL, PRIMARY KEY (id))");
    }

    @Test
    @DisplayName("요구사항 4 - 정보를 바탕으로 drop 쿼리 만들어보기")
    void generateDropTableQuery() {
        QueryBuilder queryBuilder = new QueryBuilder(dialect);
        String dropTableQuery = queryBuilder.dropTableSql(Person.class);

        assertThat(dropTableQuery).isEqualTo("DROP TABLE users");
    }
}
