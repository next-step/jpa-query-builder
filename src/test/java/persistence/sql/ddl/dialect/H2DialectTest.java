package persistence.sql.ddl.dialect;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import persistence.sql.meta.column.Column;
import persistence.sql.meta.column.ColumnType;
import persistence.sql.meta.column.Nullable;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class H2DialectTest {

    private static final Class<Person> personClass = Person.class;


    @ParameterizedTest()
    @CsvSource({
            "id,    NOT NULL",
            "email, NOT NULL",
            "name,  NULL",
            "age,   NULL",
    })
    void Nullable_SQL_변환(String fieldName, String sql) throws NoSuchFieldException {
        Field field = personClass.getDeclaredField(fieldName);
        Nullable nullable = new Column(field).getNullable();
        assertThat(new H2Dialect().getColumnNullableType(nullable)).isEqualTo(sql);
    }

    @ParameterizedTest()
    @CsvSource({
            "id,    bigint",
            "email, varchar(255)",
            "age,   int",
    })
    void ColumnType_SQL_변환(String fieldName, String sql) throws NoSuchFieldException {
        Field field = personClass.getDeclaredField(fieldName);
        ColumnType columnType = new Column(field).getColumnType();
        assertThat(new H2Dialect().getColumnDataType(columnType)).isEqualTo(sql);
    }

    @Test
    void GenerationType_SQL_변환() throws NoSuchFieldException {
        Field field = personClass.getDeclaredField("id");
        assertThat(new H2Dialect().getPKGenerationType(field)).isEqualTo("generated by default as identity");
    }

    private static class Person {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @jakarta.persistence.Column(nullable = false)
        private String email;

        @jakarta.persistence.Column(nullable = true)
        private String name;

        private Integer age;
    }
}