package persistence.study;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringTest {

    @Test
    void convert() {

        int num = 123;

        String result = String.valueOf(num);

        assertThat(result).isEqualTo("123");
    }

}
