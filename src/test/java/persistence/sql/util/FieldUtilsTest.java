package persistence.sql.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.Person;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.*;

class FieldUtilsTest {
    @Test
    @DisplayName("컬럼명을 반환한다.")
    void getColumnName() {
        // given
        final Field field = getField("name");

        // when
        final String result = FieldUtils.getColumnName(field);

        // then
        assertThat(result).isEqualTo("nick_name");
    }

    @Test
    @DisplayName("db type을 반환한다.")
    void getDbType() {
        // given
        final Field field = getField("name");

        // when
        final String result = FieldUtils.getDbType(field);

        // then
        assertThat(result).isEqualTo("VARCHAR(255)");
    }

    @Test
    @DisplayName("generation 필드인 경우 true를 반환한다.")
    void isGeneration_true() {
        // given
        final Field field = getField("id");

        // when
        final boolean result = FieldUtils.isGeneration(field);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("generation 필드가 아닌 경우 false를 반환한다.")
    void isGeneration_false() {
        // given
        final Field field = getField("age");

        // when
        final boolean result = FieldUtils.isGeneration(field);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("not null 필드인 경우 true를 반환한다.")
    void isNotNull_true() {
        // given
        final Field field = getField("email");

        // when
        final boolean result = FieldUtils.isNotNull(field);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("not null 필드가 아닌 경우 false를 반환한다.")
    void isNotNull_false() {
        // given
        final Field field = getField("age");

        // when
        final boolean result = FieldUtils.isNotNull(field);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("primary key 필드인 경우 true를 반환한다.")
    void isPrimaryKey_true() {
        // given
        final Field field = getField("id");

        // when
        final boolean result = FieldUtils.isPrimaryKey(field);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("primary key 필드가 아닌 경우 false를 반환한다.")
    void isPrimaryKey_false() {
        // given
        final Field field = getField("age");

        // when
        final boolean result = FieldUtils.isPrimaryKey(field);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("transient 필드인 경우 true를 반환한다.")
    void isTransient_true() {
        // given
        final Field field = getField("index");

        // when
        final boolean result = FieldUtils.isTransient(field);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("transient 필드가 아닌 경우 false를 반환한다.")
    void isTransient_false() {
        // given
        final Field field = getField("age");

        // when
        final boolean result = FieldUtils.isTransient(field);

        // then
        assertThat(result).isFalse();
    }

    private static Field getField(String fieldName) {
        Class<Person> clazz = Person.class;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }
}
