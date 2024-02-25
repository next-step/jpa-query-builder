package database.sql;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class UtilTest {
    @ParameterizedTest
    @MethodSource("quoteTestSources")
    void testQuote(String value, String expected) {
        assertThat(Util.quote(value)).isEqualTo(expected);
    }

    private static List<Arguments> quoteTestSources() {
        return List.of(
                arguments("0", "0"),
                arguments("1", "1"),
                arguments("abc", "'abc'"),
                arguments("1f", "'1f'"),
                arguments(null, "NULL")
        );
    }
}
