package persistence.study;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringTest {

    @Test
    void convert() {
        //given
        int numbers = 123;

        //when
        String result = String.valueOf(numbers);

        //then
        assertThat(result).isEqualTo("123");
    }
}
