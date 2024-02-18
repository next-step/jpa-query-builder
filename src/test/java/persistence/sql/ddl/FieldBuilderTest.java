package persistence.sql.ddl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.wrapper.Column;

@DisplayName("FieldBuilderor class의")
class FieldBuilderTest {

    private FieldBuilder fieldBuilder;

    @BeforeEach
    public void setup() {
        fieldBuilder = FieldBuilder.getInstance();
    }

    @DisplayName("builder 메서드는")
    @Nested
    class Builder {
        @DisplayName("String 타입의 필드일 경우 필드명 VARCHAR가 리턴된다")
        @Test
        void testBuilder_WhenFieldTypeIsString() throws NoSuchFieldException {
            Column field = Column.from(DummyClass.class.getDeclaredField("stringField"));

            String result = fieldBuilder.builder(field);

            assertEquals("string_field VARCHAR", result);
        }

        @DisplayName("Integer 타입의 필드일 경우 '필드명 INTEGER'가 리턴된다")
        @Test
        void testBuilder_WhenFieldTypeIsInteger() throws NoSuchFieldException {
            Column field = Column.from(DummyClass.class.getDeclaredField("integerField"));

            String result = fieldBuilder.builder(field);

            assertEquals("integer_field INTEGER", result);
        }

        @DisplayName("Long 타입의 필드일 경우 '필드명 BIGINT'가 리턴된다")
        @Test
        void testBuilder_WhenFieldTypeIsLong() throws NoSuchFieldException {
            Column field = Column.from(DummyClass.class.getDeclaredField("longField"));

            String result = fieldBuilder.builder(field);

            assertEquals("long_field BIGINT", result);
        }

        @DisplayName("지원하지 않은 타입의 필드일 경우 예외가 발생한다.")
        @Test
        void testBuilder_WhenFieldTypeIsNotSupported() throws NoSuchFieldException {
            Column field = Column.from(DummyClass.class.getDeclaredField("unsupportedField"));

            assertThrows(IllegalArgumentException.class, () -> fieldBuilder.builder(field));
        }
    }

    private static class DummyClass {
        String stringField;
        Integer integerField;
        Long longField;
        Double unsupportedField;
    }
}
