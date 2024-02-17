package persistence.study;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StringTest {
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 999, 1000})
    void _123_이라는_숫자를_문자열로_반환(int value) {
        // Expected
        assertThat(convertToString(value)).isEqualTo(String.valueOf(value));
    }

    private String convertToString(int value) {
        return Integer.valueOf(value).toString();
    }
}
