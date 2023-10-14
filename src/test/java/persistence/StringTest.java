package persistence;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.lang.String;

class StringTest {

    @Test
    void convert() {
        int numbers = 123;
        String value = String.valueOf(numbers);

        Assertions.assertThat(value).isEqualTo("123");
    }
}