import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class StringTest {
    @Test
    void convert() {
        int input = 123;
        String result = String.valueOf(input);
        String expected = "123";
        assertThat(result).isEqualTo(expected);
    }
}
