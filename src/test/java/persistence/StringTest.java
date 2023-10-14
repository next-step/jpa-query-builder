package persistence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringTest {


    @Test
    void convertToString() {
        int numbers = 123;
        String value = new String();

        assertThat(value.convert(numbers)).isEqualTo("123");
    }

}
