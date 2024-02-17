package persistence.study;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringTest {
    @Test
    void convert() {
        int input = 123;
        String expectedResult = "123";

        String result = Integer.toString(input);

        Assertions.assertEquals(expectedResult, result);
    }
}
