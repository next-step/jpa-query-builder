package persistence.study;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringTest {

    // 123 이라는 숫자를 문자열로 반환
    @Test
    void convert() {
        final var number = 123;
        final var expected = "123";

        final var actual = Integer.toString(number);

        assertThat(actual).isEqualTo(expected);
    }

}
