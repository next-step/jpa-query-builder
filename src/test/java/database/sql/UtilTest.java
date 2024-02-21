package database.sql;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class UtilTest {
    private static Stream<Arguments> quoteTestSources() {
        return Stream.of(
                arguments("0", "0"),
                arguments("1", "1"),
                arguments("abc", "'abc'"),
                arguments("1f", "'1f'")
        );
    }

    @ParameterizedTest
    @MethodSource("quoteTestSources")
    void testQuote(String value, String expected) {
        assertThat(Util.quote(value)).isEqualTo(expected);
    }
}
