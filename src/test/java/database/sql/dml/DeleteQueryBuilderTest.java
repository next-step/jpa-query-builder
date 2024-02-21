package database.sql.dml;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteQueryBuilderTest {
    private final DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(Person4.class);

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
        String actual = deleteQueryBuilder.buildQuery(where);

        assertThat(actual).isEqualTo(expected);
    }
}
