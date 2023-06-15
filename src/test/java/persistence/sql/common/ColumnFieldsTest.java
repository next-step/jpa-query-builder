package persistence.sql.common;

import domain.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

class ColumnFieldsTest {


    @Test
    @DisplayName("Transient 어노테이션이 붙은 field 는 제거해야 한다.")
    void filterTransient() {
        Assertions.assertThat(
                ColumnFields.from(Person.class)
                        .stream()
                        .map(Field::getName)
                        .collect(Collectors.toList())
        ).containsExactlyInAnyOrder(
                "id", "name", "age", "email"
        );
    }

    @Test
    @DisplayName("Insert Query 에서는 Id 어노테이션이 붙은 field 를 제거한다.")
    void filterId() {
        Assertions.assertThat(
                ColumnFields.forInsert(Person.class)
                        .stream()
                        .map(Field::getName)
                        .collect(Collectors.toList())
        ).containsExactlyInAnyOrder(
                "name", "age", "email"
        );
    }
}
