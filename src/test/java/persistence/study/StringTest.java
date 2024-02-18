package persistence.study;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringTest {

    @Test
    public void convert() throws Exception {
        int num = 123;

        final String numString = String.valueOf(num);

        assertThat(numString).isEqualTo("123");
    }

}
