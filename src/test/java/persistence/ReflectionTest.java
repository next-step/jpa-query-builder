package persistence;

import domain.Person;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import jakarta.persistence.Column;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReflectionTest {

    @Test
    void annotation_value() throws NoSuchFieldException {
        Class<Person> personClass = Person.class;

        Field field = personClass.getDeclaredField("age");
        Column columnAnnotation = field.getAnnotation(Column.class);

        assertAll(
                () -> assertThat(columnAnnotation.name()).isEqualTo("old"),
                () -> assertThat(columnAnnotation.length()).isEqualTo(3)
        );
    }
}
