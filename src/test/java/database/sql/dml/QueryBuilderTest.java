package database.sql.dml;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {
    QueryBuilder builder = QueryBuilder.getInstance();

    @Test
    void singletonTest() {
        QueryBuilder anotherBuilder = QueryBuilder.getInstance();

        assertThat(builder).isEqualTo(anotherBuilder);
    }

    @Test
    void buildInsertQuery() {
        QueryBuilder builder = QueryBuilder.getInstance();
        Class<?> entityClass = Person4.class;
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("nick_name", "abc");
        valueMap.put("old", "14");
        valueMap.put("email", "a@b.com");
        String actual = builder.buildInsertQuery(entityClass, valueMap);

        assertThat(actual).isEqualTo("INSERT INTO users (nick_name, old, email) VALUES ('abc', 14, 'a@b.com')");
    }

    @Test
    void buildSelectQuery() {
        String actual = builder.buildSelectQuery(Person4.class);
        assertThat(actual).isEqualTo("SELECT id, nick_name, old, email FROM users");
    }

    @Test
    void buildSelectOneQuery() {
        String actual = builder.buildSelectOneQuery(Person4.class, 1L);
        assertThat(actual).isEqualTo("SELECT id, nick_name, old, email FROM users WHERE id = 1");
    }

    @Test
    void deleteQueryWithPrimaryKeyField() {
        HashMap<String, Object> where = new HashMap<>() {{
            put("id", 3L);
        }};
        String actual = builder.buildDeleteQuery(Person4.class, where);

        assertThat(actual).isEqualTo("DELETE FROM users WHERE id = 3");
    }


}
