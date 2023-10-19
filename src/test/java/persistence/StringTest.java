package persistence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringTest {

    @Test
    void convert() {
        final int number = 123;
        String value = String.valueOf(number);
        assertThat(value).isEqualTo("123");
    }

}
