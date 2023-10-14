import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringTest {

    @Test
    void convert() {
        int numbers = 123;
        String value = String.valueOf(numbers);

        assertThat(value).isEqualTo("123");
    }

}