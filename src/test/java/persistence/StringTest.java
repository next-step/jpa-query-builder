package persistence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringTest {

    @Test
    void convert() {
        int num = 10;
        final String s = String.valueOf(num);

        assertThat(s).isEqualTo("10");
    }

}