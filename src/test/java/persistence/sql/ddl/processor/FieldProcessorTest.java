package persistence.sql.ddl.processor;

import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("FieldProcessor class의")
class FieldProcessorTest {

    private FieldProcessor fieldProcessor;

    @BeforeEach
    public void setup() {
        fieldProcessor = FieldProcessor.getInstance();
    }

    @DisplayName("process 메서드는")
    @Nested
    class Process {
        @DisplayName("String 타입의 필드일 경우 필드명 VARCHAR가 리턴된다")
        @Test
        public void testProcess_WhenFieldTypeIsString() throws NoSuchFieldException {
            Field field = DummyClass.class.getDeclaredField("stringField");

            String result = fieldProcessor.process(field);

            assertEquals("stringField VARCHAR", result);
        }

        @DisplayName("Integer 타입의 필드일 경우 '필드명 INTEGER'가 리턴된다")
        @Test
        public void testProcess_WhenFieldTypeIsInteger() throws NoSuchFieldException {
            Field field = DummyClass.class.getDeclaredField("integerField");

            String result = fieldProcessor.process(field);

            assertEquals("integerField INTEGER", result);
        }

        @DisplayName("Long 타입의 필드일 경우 '필드명 BIGINT'가 리턴된다")
        @Test
        public void testProcess_WhenFieldTypeIsLong() throws NoSuchFieldException {
            Field field = DummyClass.class.getDeclaredField("longField");

            String result = fieldProcessor.process(field);

            assertEquals("longField BIGINT", result);
        }

        @DisplayName("지원하지 않은 타입의 필드일 경우 예외가 발생한다.")
        @Test
        public void testProcess_WhenFieldTypeIsNotSupported() throws NoSuchFieldException {
            Field field = DummyClass.class.getDeclaredField("unsupportedField");

            assertThrows(IllegalArgumentException.class, () -> fieldProcessor.process(field));
        }
    }

    private static class DummyClass {
        String stringField;
        Integer integerField;
        Long longField;
        Double unsupportedField;
    }
}
