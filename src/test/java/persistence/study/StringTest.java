package persistence.study;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringTest {

    @Test
    void convert() {
        String result = String.valueOf(1234);
        assertThat(result).isEqualTo("1234");
    }
}
