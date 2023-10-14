package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringTest {
    @Test
    @DisplayName("숫자값이 문자열 형식으로 바뀌어 반환된다.")
    void convert() {
        //given
        int input = 123;

        //when
        String result = Integer.toString(input);

        //then
        assertThat(result).isEqualTo("123");
    }
}
