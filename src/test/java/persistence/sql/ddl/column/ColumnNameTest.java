package persistence.sql.ddl.column;

import jakarta.persistence.Column;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ColumnNameTest {

    @Test
    @DisplayName("필드에 @Column 이 없는 경우 컬럼명이 name 이 된다.")
    void from_1() throws NoSuchFieldException {
        // given
        Class<NotHaveColumnAnnotation> aClass = NotHaveColumnAnnotation.class;
        Field name = aClass.getDeclaredField("name");

        // when
        ColumnName result = ColumnName.from(name);

        //then
        assertThat(result.getName()).isEqualTo("name");
    }

    @Test
    @DisplayName("필드에 @Column 가 있고 name 속성이 없는 경우 컬럼명이 name 이 된다.")
    void from_2() throws NoSuchFieldException {
        // given
        Class<UnspecifiedName> aClass = UnspecifiedName.class;
        Field name = aClass.getDeclaredField("name");

        // when
        ColumnName result = ColumnName.from(name);

        //then
        assertThat(result.getName()).isEqualTo("name");
    }

    @Test
    @DisplayName("필드에 @Column 가 있고 name 속성이 있는 경우 컬럼명이 user 가 된다.")
    void from_3() throws NoSuchFieldException {
        // given
        Class<SpecifiedName> aClass = SpecifiedName.class;
        Field name = aClass.getDeclaredField("name");

        // when
        ColumnName result = ColumnName.from(name);

        //then
        assertThat(result.getName()).isEqualTo("user");
    }

    static class NotHaveColumnAnnotation {
        private String name;
    }

    static class UnspecifiedName {

        @Column
        private String name;
    }

    static class SpecifiedName {

        @Column(name = "user")
        private String name;
    }
}
