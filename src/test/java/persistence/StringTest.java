package persistence;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class StringTest {

    @Test
    void convert() {
        int number = 123;
        String value = String.valueOf(number);
        assertThat(value).isEqualTo("123");
    }

}