package persistence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringTest {

    @Test
    void convert() {
        int numbers = 123;
        String result = String.valueOf(numbers);
        assertThat(result).isEqualTo("123");
    }
}