package persistence.study;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringTest {

    @Test
    void convert() {
        // given
        int number = 123;

        // when
        String result = String.valueOf(number);

        // then
        assertEquals(result, "123");
    }
}
