package database.sql.dml;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderDmlTest {
    @Test
    void buildInsertQuery() {
        QueryBuilder queryBuilder = new QueryBuilder();
        Class<?> entityClass = Person4.class;
        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("nick_name", "abc");
        valueMap.put("old", "14");
        valueMap.put("email", "a@b.com");
        String actual = queryBuilder.buildInsertQuery(entityClass, valueMap);

        assertThat(actual).isEqualTo("INSERT INTO users (nick_name, old, email) VALUES ('abc', 14, 'a@b.com')");
    }
}
