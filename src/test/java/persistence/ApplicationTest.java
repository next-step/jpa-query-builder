package persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class ApplicationTest {
    @DisplayName("Application.main 메서드는 예외를 던지지 않아야 한다")
    @Test
    void mainMethodDoesNotThrowAnyException() {
        assertThatCode(() -> Application.main(new String[]{}))
                .doesNotThrowAnyException();
    }
}
