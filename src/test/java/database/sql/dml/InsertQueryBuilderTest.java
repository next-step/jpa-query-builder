package database.sql.dml;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class InsertQueryBuilderTest {
    private final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(Person4.class);

    static List<Arguments> testCases() {
        return List.of(
                arguments(Map.of("nick_name", "abc"), "INSERT INTO users (nick_name) VALUES ('abc')"),
                arguments(Map.of("nick_name", "abc", "old", 14, "email", "a@b.com"),
                          "INSERT INTO users (nick_name, old, email) VALUES ('abc', 14, 'a@b.com')"),
                arguments(Map.of("nick_name", "abc", "old", 14),
                          "INSERT INTO users (nick_name, old) VALUES ('abc', 14)"),
                arguments(new HashMap<String, Object>() {
                    {
                        put("nick_name", null);
                        put("old", 14);
                    }
                }, "INSERT INTO users (nick_name, old) VALUES (NULL, 14)")
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void buildInsertQuery(Map<String, Object> valueMap, String expected) {
        String actual = insertQueryBuilder.buildQuery(valueMap);
        assertThat(actual).isEqualTo(expected);
    }
}
