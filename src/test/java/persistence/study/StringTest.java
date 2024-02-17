package persistence.study;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringTest {

    @Test
    void convert() {
        assertEquals("123", conv(123));
    }

    private String conv(int i) {
        return String.format("%d", i);
    }
}
