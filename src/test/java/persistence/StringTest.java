package persistence;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class StringTest {

    @Test
    void convert() {
        int num = 10;
        final String s = String.valueOf(num);

        assertThat(s).isEqualTo("10");
    }
}