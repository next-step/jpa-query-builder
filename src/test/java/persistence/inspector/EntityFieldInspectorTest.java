package persistence.inspector;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EntityFieldInspectorTest {

    Class<Person> personClass = Person.class;

    @Test
    @DisplayName("entity 컬럼 필드는 isPersistable이 true여야 함.")
    void isPersistable_true() {
        Field id = getField("id");
        Field name = getField("name");
        Field email = getField("email");

        assertAll(
                () -> assertTrue(EntityFieldInspector.isPersistable(id)),
                () -> assertTrue(EntityFieldInspector.isPersistable(name)),
                () -> assertTrue(EntityFieldInspector.isPersistable(email))
        );
    }

    @Test
    @DisplayName("Transient 필드는 isPersistable이 false여야 함.")
    void isPersistable_false() {
        Field index = getField("index");

        assertAll(
                () -> assertFalse(EntityFieldInspector.isPersistable(index))
        );
    }

    @Test
    @DisplayName("컬럼 필드의 이름을 가져옴.")
    void getColumnName() {
        Field name = getField("name");
        Field age = getField("age");

        assertAll(
                () -> assertEquals("nick_name", EntityFieldInspector.getColumnName(name)),
                () -> assertEquals("old", EntityFieldInspector.getColumnName(age))
        );
    }

    @Test
    @DisplayName("컬럼 필드의 타입을 가져옴.")
    void getColumnType() {
        EntityColumnType IdCcolumnType = EntityFieldInspector.getColumnType(getField("id"));
        EntityColumnType nameColumnType = EntityFieldInspector.getColumnType(getField("name"));
        EntityColumnType ageColumnType = EntityFieldInspector.getColumnType(getField("age"));

        assertAll(
                () -> assertEquals(EntityColumnType.LONG, IdCcolumnType),
                () -> assertEquals(EntityColumnType.STRING, nameColumnType),
                () -> assertEquals(EntityColumnType.INTEGER, ageColumnType)
        );
    }

    @Test
    @DisplayName("컬럼 필드의 nullable_true.")
    void isNullable_true() {
        Arrays.stream(personClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .filter(field -> field.getAnnotation(Column.class).nullable())
                .collect(Collectors.toList())
                .forEach(field -> assertThat(EntityFieldInspector.isNullable(field)).isTrue());
    }

    @Test
    @DisplayName("컬럼 필드의 nullable_false.")
    void isNullable_false() {

        Arrays.stream(personClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .filter(field -> !field.getAnnotation(Column.class).nullable())
                .collect(Collectors.toList())
                .forEach(field -> assertThat(EntityFieldInspector.isNullable(field)).isFalse());
    }

    @Test
    @DisplayName("@Id 필드는 isPrimaryKey가 true여야 함.")
    void isPrimaryKey_true() {

        Arrays.stream(personClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .collect(Collectors.toList())
                .forEach(field -> assertThat(EntityFieldInspector.isPrimaryKey(field)).isTrue());
    }

    @Test
    @DisplayName("@Id 필드가 아닌 필드는 isPrimaryKey가 false여야 함.")
    void isPrimaryKey_false() {

        Arrays.stream(personClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .collect(Collectors.toList())
                .forEach(field -> assertThat(EntityFieldInspector.isPrimaryKey(field)).isFalse());
    }

    @Test
    void isAutoIncrement() {

        Arrays.stream(personClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(GeneratedValue.class))
                .collect(Collectors.toList())
                .forEach(field -> assertThat(EntityFieldInspector.isAutoIncrement(field)).isTrue());
    }

    @Test
    @DisplayName("Column 어노테이션을 가진 필드는 hasAnnotation이 true여야 함.")
    void hasAnnotation() {

        Arrays.stream(personClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .collect(Collectors.toList())
                .forEach(field -> assertThat(EntityFieldInspector.hasAnnotation(field, Column.class)).isTrue());
    }

    private Field getField(String fieldName) {
        try {
            return personClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
