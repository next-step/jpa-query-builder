package persistence.sql.meta.column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ColumnTest {
    private static final Class<Person> PERSON_CLAZZ = Person.class;

    @Test
    @DisplayName("생성/컬럼 메타정보/성공")
    void createTable() throws NoSuchFieldException {
        Field field = PERSON_CLAZZ.getDeclaredField("name");
        assertThat(new Column(field)).isInstanceOf(Column.class);
    }

    @Test
    @DisplayName("생성/Transient 컬럼 메타정보/IllegalArgumentException")
    void createTableFail() throws NoSuchFieldException {
        Field field = PERSON_CLAZZ.getDeclaredField("index");
        assertThatThrownBy(() -> new Column(field))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Entity
    private static class Person {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @jakarta.persistence.Column(name = "nick_name")
        private String name;

        @jakarta.persistence.Column(nullable = false)
        private String email;
        @Transient
        private String index;
    }
}
