package persistence.study;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StringTest {

    @Test
    void convert() {
        // given
        int a = 123;

        // when
        String result = String.valueOf(a);

        // then
        assertThat(result).isEqualTo("123");
    }
}
