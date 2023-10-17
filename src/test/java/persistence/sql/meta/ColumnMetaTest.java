package persistence.sql.meta;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnMetaTest {

    @Test
    @DisplayName("Transient 컬럼")
    void isTransient() throws Exception {
        Field field = Person.class.getDeclaredField("index");
        boolean isTransient = ColumnMeta.isTransient(field);
        assertThat(isTransient).isTrue();
    }

    @Test
    @DisplayName("Transient 컬럼이 아님")
    void isNotTransient() throws Exception {
        Field field = Person.class.getDeclaredField("name");
        boolean isTransient = ColumnMeta.isTransient(field);
        assertThat(isTransient).isFalse();
    }

    @Test
    @DisplayName("name 속성이 존재할 때의 필드명")
    void hasNameAttribute() throws Exception {
        Field field = Person.class.getDeclaredField("age");
        String columnName = ColumnMeta.getColumnName(field);
        assertThat(columnName).isEqualTo("old");
    }

    @Test
    @DisplayName("name 속성이 존재하지 않을 때의 필드명")
    void hasNotNameAttribute() throws Exception {
        Field field = Person.class.getDeclaredField("email");
        String columnName = ColumnMeta.getColumnName(field);
        assertThat(columnName).isEqualTo("email");
    }

}
