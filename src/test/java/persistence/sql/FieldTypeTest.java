package persistence.sql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.*;

class FieldTypeTest {
    @Test
    @DisplayName("존재하는 필드 타입으로 인스턴스를 생성하다.")
    void valueOf() {
        // given
        Class<ExistingField> clazz = ExistingField.class;
        Field field = clazz.getDeclaredFields()[0];

        // when
        FieldType fieldType = FieldType.valueOf(field);

        // then
        assertThat(fieldType).isEqualTo(FieldType.STRING);
    }

    @Test
    @DisplayName("존재하지 않는 필드 타입으로 인스턴스를 생성하면 예외를 발생한다.")
    void valueOf_exception() {
        // given
        Class<NonExistingField> clazz = NonExistingField.class;
        Field field = clazz.getDeclaredFields()[0];

        // when & then
        assertThatThrownBy(() -> FieldType.valueOf(field))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static class ExistingField {
        private String field;
    }

    private static class NonExistingField {
        private Object field;
    }
}
