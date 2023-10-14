package persistence.study;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class StringTest {

    @Test
    @DisplayName("123 이라는 숫자를 문자열로 반환")
    void convert() {
        //given
        int numbers = 123;

        //when
        String result = String.valueOf(numbers);

        //then
        assertThat(result).isEqualTo("123");
    }
}
