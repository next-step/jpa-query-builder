package persistence.study;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringTest {

    @Test
    void convert() {
        int integer = 123;
        String string = String.valueOf(integer);

        assertThat(string).isEqualTo("123");
    }
}
