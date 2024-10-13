package persistence.sql.meta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.fixture.EntityWithId;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.*;

class EntityFieldTest {
    @Test
    @DisplayName("컬럼명을 반환한다.")
    void getColumnName() {
        // given
        final EntityField entityField = new EntityField(getField("name"));

        // when
        final String result = entityField.getColumnName();

        // then
        assertThat(result).isEqualTo("nick_name");
    }

    @Test
    @DisplayName("db type을 반환한다.")
    void getDbType() {
        // given
        final EntityField entityField = new EntityField(getField("name"));

        // when
        final String result = entityField.getDbType();

        // then
        assertThat(result).isEqualTo("VARCHAR(255)");
    }

    @Test
    @DisplayName("generation 필드인 경우 true를 반환한다.")
    void isGeneration_true() {
        // given
        final EntityField entityField = new EntityField(getField("id"));

        // when
        final boolean result = entityField.isGeneration();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("generation 필드가 아닌 경우 false를 반환한다.")
    void isGeneration_false() {
        // given
        final EntityField entityField = new EntityField(getField("age"));

        // when
        final boolean result = entityField.isGeneration();

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("not null 필드인 경우 true를 반환한다.")
    void isNotNull_true() {
        // given
        final EntityField entityField = new EntityField(getField("email"));

        // when
        final boolean result = entityField.isNotNull();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("not null 필드가 아닌 경우 false를 반환한다.")
    void isNotNull_false() {
        // given
        final EntityField entityField = new EntityField(getField("age"));

        // when
        final boolean result = entityField.isNotNull();

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("primary key 필드인 경우 true를 반환한다.")
    void isId_true() {
        // given
        final EntityField entityField = new EntityField(getField("id"));

        // when
        final boolean result = entityField.isId();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("primary key 필드가 아닌 경우 false를 반환한다.")
    void isId_false() {
        // given
        final EntityField entityField = new EntityField(getField("age"));

        // when
        final boolean result = entityField.isId();

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("transient 필드인 경우 true를 반환한다.")
    void isTransient_true() {
        // given
        final EntityField entityField = new EntityField(getField("index"));

        // when
        final boolean result = entityField.isTransient();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("transient 필드가 아닌 경우 false를 반환한다.")
    void isTransient_false() {
        // given
        final EntityField entityField = new EntityField(getField("age"));

        // when
        final boolean result = entityField.isTransient();

        // then
        assertThat(result).isFalse();
    }

    private static Field getField(String fieldName) {
        Class<EntityWithId> clazz = EntityWithId.class;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }
}
