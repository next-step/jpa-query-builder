package database.sql.dml;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {
    @Test
    void buildInsertQuery() {
        QueryBuilder queryBuilder = new QueryBuilder();
        Class<?> entityClass = Person4.class;
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("nick_name", "abc");
        valueMap.put("old", "14");
        valueMap.put("email", "a@b.com");
        String actual = queryBuilder.buildInsertQuery(entityClass, valueMap);

        assertThat(actual).isEqualTo("INSERT INTO users (nick_name, old, email) VALUES ('abc', 14, 'a@b.com')");
    }

    @Test
    void buildSelectQuery() {
        QueryBuilder queryBuilder = new QueryBuilder();
        String actual = queryBuilder.buildSelectQuery(Person4.class);
        assertThat(actual).isEqualTo("SELECT id, nick_name, old, email FROM users");
    }

    @Test
    void buildSelectOneQuery() {
        QueryBuilder queryBuilder = new QueryBuilder();
        String actual = queryBuilder.buildSelectOneQuery(Person4.class, 1L);
        assertThat(actual).isEqualTo("SELECT id, nick_name, old, email FROM users WHERE id = 1");
    }

    // TODO: 테스트간략화
    @Test
    void buildDeleteQuery() {
        QueryBuilder queryBuilder = new QueryBuilder();

        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("id", 3L);
        String actual = queryBuilder.buildDeleteQuery(Person4.class, conditionMap);
        assertThat(actual).isEqualTo("DELETE FROM users WHERE id = 3");

        Map<String, Object> conditionMap2 = new HashMap<>();
        conditionMap2.put("nick_name", "foo");
        String actual2 = queryBuilder.buildDeleteQuery(Person4.class, conditionMap2);
        assertThat(actual2).isEqualTo("DELETE FROM users WHERE nick_name = 'foo'");

        Map<String, Object> conditionMap3 = new HashMap<>();
        conditionMap3.put("old", 18);
        conditionMap3.put("email", "example@email.com");
        String actual3 = queryBuilder.buildDeleteQuery(Person4.class, conditionMap3);
        assertThat(actual3).isEqualTo("DELETE FROM users WHERE old = 18 AND email = 'example@email.com'");

    }
}
