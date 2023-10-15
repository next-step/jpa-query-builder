package persistence.study;

import static org.junit.jupiter.api.Assertions.fail;

public class TestUtils {
    public static void assertDoesNotThrowException(Executable executable) {
        try {
            executable.execute();
        } catch (Exception e) {
            fail(e);
        }
    }

    @FunctionalInterface
    public interface Executable {
        void execute() throws Exception;
    }
}
