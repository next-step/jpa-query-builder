package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringTest {

    @DisplayName("숫자를 문자열로 반환")
    @Test
    void convertTest() {
        int number = 123;
        assertThat(String.valueOf(number)).isInstanceOf(String.class);
    }
}
