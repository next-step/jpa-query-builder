package persistance.study;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringTest {

    @Test
    void convert() {
        int numbers = 123;
        String actual = String.valueOf(numbers);
        assertThat(actual).isEqualTo("123");
    }
}
