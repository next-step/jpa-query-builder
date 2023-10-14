package persistence.study;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class StringTest {

    @Test
    void convert() {
        int original = 123;
        String expected = String.valueOf(original);

        assertThat(expected).isEqualTo("123");
    }
}
