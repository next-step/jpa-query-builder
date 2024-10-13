package persistence.sql.meta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.example.Person;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.*;

class ColumnTest {
    @Test
    @DisplayName("컬럼명을 반환한다.")
    void getColumnName() {
        // given
        final Column column = new Column(getField("name"));

        // when
        final String result = column.getColumnName();

        // then
        assertThat(result).isEqualTo("nick_name");
    }

    @Test
    @DisplayName("db type을 반환한다.")
    void getDbType() {
        // given
        final Column column = new Column(getField("name"));

        // when
        final String result = column.getDbType();

        // then
        assertThat(result).isEqualTo("VARCHAR(255)");
    }

    @Test
    @DisplayName("generation 필드인 경우 true를 반환한다.")
    void isGeneration_true() {
        // given
        final Column column = new Column(getField("id"));

        // when
        final boolean result = column.isGeneration();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("generation 필드가 아닌 경우 false를 반환한다.")
    void isGeneration_false() {
        // given
        final Column column = new Column(getField("age"));

        // when
        final boolean result = column.isGeneration();

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("not null 필드인 경우 true를 반환한다.")
    void isNotNull_true() {
        // given
        final Column column = new Column(getField("email"));

        // when
        final boolean result = column.isNotNull();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("not null 필드가 아닌 경우 false를 반환한다.")
    void isNotNull_false() {
        // given
        final Column column = new Column(getField("age"));

        // when
        final boolean result = column.isNotNull();

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("primary key 필드인 경우 true를 반환한다.")
    void isId_true() {
        // given
        final Column column = new Column(getField("id"));

        // when
        final boolean result = column.isId();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("primary key 필드가 아닌 경우 false를 반환한다.")
    void isId_false() {
        // given
        final Column column = new Column(getField("age"));

        // when
        final boolean result = column.isId();

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("transient 필드인 경우 true를 반환한다.")
    void isTransient_true() {
        // given
        final Column column = new Column(getField("index"));

        // when
        final boolean result = column.isTransient();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("transient 필드가 아닌 경우 false를 반환한다.")
    void isTransient_false() {
        // given
        final Column column = new Column(getField("age"));

        // when
        final boolean result = column.isTransient();

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
