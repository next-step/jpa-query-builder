package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringTest {
    @Test
    @DisplayName("Init commit test")
    void convert() {
        // Arrange
        final int value = 123;

        // Act
        final String result = convert(value);

        // Assert
        assertThat(result).isEqualTo(String.valueOf(value));
    }

    private static String convert(int value) {
        return String.valueOf(value);
    }
}
