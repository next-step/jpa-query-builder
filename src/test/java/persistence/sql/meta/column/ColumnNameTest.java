package persistence.sql.meta.column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;

public class ColumnNameTest {

    @ParameterizedTest
    @CsvSource({
            "name,          nick_name",
            "email,         email",
            "noAnnotation,  noAnnotation"
    })
    void columName(String fieldName, String columnName) throws NoSuchFieldException {
        Field field = Person.class.getDeclaredField(fieldName);

        Assertions.assertThat(new Column(field).getColumnName()).isEqualTo(columnName);
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
        private String noAnnotation;
    }
}
