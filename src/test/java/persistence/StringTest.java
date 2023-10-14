package persistence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StringTest {
    @Test
    void convert() {
        assertThat(String.valueOf(123)).isEqualTo("123");
    }
}
