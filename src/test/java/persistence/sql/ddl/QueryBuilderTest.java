package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryBuilderTest {

    SqlTypeMapper sqlTypeMapper;
    @Test
    @DisplayName("파싱한 쿼리 내용 테스트")
    void getPersonMetadata() {
        QueryBuilder queryBuilder = new QueryBuilder(Person.class, sqlTypeMapper);

        String query = queryBuilder.getPersonMetadata();
        assertThat(query).isEqualTo("id BIGINT PRIMARY KEY AUTO_INCREMENT, nick_name VARCHAR(255), old Integer, email VARCHAR(255) NOT NULL");
    }
}
