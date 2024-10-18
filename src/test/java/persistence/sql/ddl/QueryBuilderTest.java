package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryBuilderTest {

    QueryBuilder queryBuilder = new QueryBuilder(Person.class, new H2SqlTypeMapper());
    @Test
    @DisplayName("파싱한 쿼리 내용 테스트")
    void getPersonMetadata() {
        String query = queryBuilder.getPersonMetadata();
        assertThat(query).isEqualTo("id BIGINT PRIMARY KEY AUTO_INCREMENT, nick_name VARCHAR(255), old INT, email VARCHAR(255) NOT NULL");
    }

    @Test
    @DisplayName("create 테스트")
    void create() {
        String createQuery = queryBuilder.create();
        assertThat(createQuery).isEqualTo("CREATE TABLE users(id BIGINT PRIMARY KEY AUTO_INCREMENT, nick_name VARCHAR(255), old INT, email VARCHAR(255) NOT NULL)");
    }

    @Test
    @DisplayName("drop 테스트")
    void drop() {
        String dropQuery = queryBuilder.drop();
        assertThat(dropQuery).isEqualTo("DROP TABLE users IF EXISTS");
    }
}
