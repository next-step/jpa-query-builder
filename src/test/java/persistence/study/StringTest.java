package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringTest {

    private static class MyStringUtil {

        public static String convert(int number) {
            return String.valueOf(number);
        }
    }

    @DisplayName("숫자를 문자열로 변환 가능해야 한다.")
    @Test
    void convert() {
        assertThat(MyStringUtil.convert(123)).isEqualTo("123");
    }

}
