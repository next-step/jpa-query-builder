package persistence.study;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringTest {

    @Test
    void convert() {
        int i = 123;

        assertEquals(conv(i), "123");
    }

    private String conv(int i) {
        return "123";
    }
}
