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
    void deleteQueryWithPrimaryKeyField() {
        HashMap<String, Object> where = new HashMap<>() {{
            put("id", 3L);
        }};
        assertDeleteQuery(where, "DELETE FROM users WHERE id = 3");
    }

    @Test
    void deleteQueryWithNotPrimaryKeyField() {
        HashMap<String, Object> where = new HashMap<>() {{
            put("nick_name", "foo");
        }};
        assertDeleteQuery(where, "DELETE FROM users WHERE nick_name = 'foo'");
    }

    @Test
    void deleteQueryWithMultipleCond() {
        HashMap<String, Object> where = new HashMap<>() {{
            put("old", 18);
            put("email", "example@email.com");
        }};
        assertDeleteQuery(where, "DELETE FROM users WHERE old = 18 AND email = 'example@email.com'");
    }

    void assertDeleteQuery(Map<String, Object> where, String expected) {
        QueryBuilder queryBuilder = new QueryBuilder();
        String actual = queryBuilder.buildDeleteQuery(Person4.class, where);

        assertThat(actual).isEqualTo(expected);
    }
}
