package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringTest {

    @DisplayName("123 이라는 숫자를 문자열로 반환한다.")
    @Test
    void convert() {
        final int actual = 123;
        final String expected = "123";

        assertThat(String.valueOf(actual)).isEqualTo(expected);
    }

}
