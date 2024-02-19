package persistence.sql.ddl.h2.meta;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import persistence.sql.ddl.h2.meta.Nullable;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class NullableTest {

    private static final Class<Person> personClass = Person.class;

    @ParameterizedTest()
    @CsvSource({
            "id,              NOT NULL",
            "notNullCol,      NOT NULL",
            "noAnnotationCol, NULL",
            "nullableCol,     NULL",
    })
    void nullableSQL(String fieldName, String sql) throws NoSuchFieldException {
        Field field = personClass.getDeclaredField(fieldName);

        assertThat(Nullable.getSQL(field)).isEqualTo(sql);
    }

    private static class Person {
        @Id
        private Long id;

        @Column(nullable = false)
        private String notNullCol;

        @Column(nullable = true)
        private String nullableCol;

        private String noAnnotationCol;
    }
}