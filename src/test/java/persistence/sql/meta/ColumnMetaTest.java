package persistence.sql.meta;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnMetaTest {

    @Test
    @DisplayName("ID 컬럼")
    void isId() throws Exception {
        Field field = Person.class.getDeclaredField("id");
        ColumnMeta columnMeta = ColumnMeta.of(field);
        boolean isId = columnMeta.isId();
        assertThat(isId).isTrue();
    }

    @Test
    @DisplayName("ID 컬럼이 아님")
    void isNotId() throws Exception {
        Field field = Person.class.getDeclaredField("email");
        ColumnMeta columnMeta = ColumnMeta.of(field);
        boolean isId = columnMeta.isId();
        assertThat(isId).isFalse();
    }

    @Test
    @DisplayName("AutoGen Type Identity 여부")
    void isGenerationTypeIdentity() throws Exception {
        Field field = Person.class.getDeclaredField("id");
        ColumnMeta columnMeta = ColumnMeta.of(field);
        boolean isGenerationTypeIdentity = columnMeta.isGenerationTypeIdentity();
        assertThat(isGenerationTypeIdentity).isTrue();
    }

    @Test
    @DisplayName("Nullable 컬럼")
    void isNullable() throws Exception {
        Field field = Person.class.getDeclaredField("age");
        ColumnMeta columnMeta = ColumnMeta.of(field);
        boolean isNullable = columnMeta.isNullable();
        assertThat(isNullable).isTrue();
    }

    @Test
    @DisplayName("Not Null 컬럼")
    void isNotNull() throws Exception {
        Field field = Person.class.getDeclaredField("email");
        ColumnMeta columnMeta = ColumnMeta.of(field);
        boolean isNullable = columnMeta.isNullable();
        assertThat(isNullable).isFalse();
    }

    @Test
    @DisplayName("Transient 컬럼")
    void isTransient() throws Exception {
        Field field = Person.class.getDeclaredField("index");
        ColumnMeta columnMeta = ColumnMeta.of(field);
        boolean isTransient = columnMeta.isTransient();
        assertThat(isTransient).isTrue();
    }

    @Test
    @DisplayName("Transient 컬럼이 아님")
    void isNotTransient() throws Exception {
        Field field = Person.class.getDeclaredField("name");
        ColumnMeta columnMeta = ColumnMeta.of(field);
        boolean isTransient = columnMeta.isTransient();
        assertThat(isTransient).isFalse();
    }

    @Test
    @DisplayName("name 속성이 존재할 때의 필드명")
    void hasNameAttribute() throws Exception {
        Field field = Person.class.getDeclaredField("age");
        ColumnMeta columnMeta = ColumnMeta.of(field);
        String columnName = columnMeta.getColumnName();
        assertThat(columnName).isEqualTo("old");
    }

    @Test
    @DisplayName("name 속성이 존재하지 않을 때의 필드명")
    void hasNotNameAttribute() throws Exception {
        Field field = Person.class.getDeclaredField("email");
        ColumnMeta columnMeta = ColumnMeta.of(field);
        String columnName = columnMeta.getColumnName();
        assertThat(columnName).isEqualTo("email");
    }

}
