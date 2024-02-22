package database.sql.dml;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: 테스트 세밀하게
class InsertQueryBuilderTest {
    private final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(Person4.class);

    @Test
    void buildInsertQuery() {
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("nick_name", "abc");
        valueMap.put("old", "14");
        valueMap.put("email", "a@b.com");
        String actual = insertQueryBuilder.buildQuery(valueMap);

        assertThat(actual).isEqualTo("INSERT INTO users (nick_name, old, email) VALUES ('abc', 14, 'a@b.com')");
    }
}
