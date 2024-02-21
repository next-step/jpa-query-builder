package persistence.sql.ddl.h2.meta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import persistence.sql.meta.ColumnName;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

public class ColumnNameTest {

    private static final Class<Person> personClass = Person.class;

    @ParameterizedTest
    @CsvSource({
            "name,          nick_name",
            "email,         email",
            "noAnnotation,  noAnnotation"
    })
    void columName(String fieldName, String columnName) throws NoSuchFieldException {
        Field field = personClass.getDeclaredField(fieldName);

        assertThat(new ColumnName(field).getColumnName()).isEqualTo(columnName);
    }

    @Entity
    private static class Person {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nick_name")
        private String name;

        @Column(nullable = false)
        private String email;
        private String noAnnotation;
    }
}
