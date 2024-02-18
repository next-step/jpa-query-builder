package persistence.study;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringTest {

    @Test
    void convert() {
        String result = Converter.numberToString(123);
        assertThat(result).isEqualTo("123");

        String result2 = Converter.numberToString(456);
        assertThat(result2).isEqualTo("456");
    }
}
