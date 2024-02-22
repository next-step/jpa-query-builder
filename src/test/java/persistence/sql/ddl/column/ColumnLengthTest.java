package persistence.sql.ddl.column;

import jakarta.persistence.Column;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static persistence.sql.ddl.column.ColumnLength.DEFAULT_LENGTH;

class ColumnLengthTest {

    @Test
    @DisplayName("필드에 @Column 가 없는 경우 컬럼길이가 255가 된다.")
    void from_1() throws NoSuchFieldException {
        // given
        Class<NotHaveColumnAnnotation> aClass = NotHaveColumnAnnotation.class;
        Field name = aClass.getDeclaredField("name");

        // when
        ColumnLength result = ColumnLength.from(name);

        //then
        assertThat(result.getLength()).isEqualTo(DEFAULT_LENGTH);
    }

    @Test
    @DisplayName("필드에 @Column 가 있고 length 속성이 3인 경우 컬럼길이가 3이 된다.")
    void from_2() throws NoSuchFieldException {
        // given
        Class<UnspecifiedLength> aClass = UnspecifiedLength.class;
        Field name = aClass.getDeclaredField("name");

        // when
        ColumnLength result = ColumnLength.from(name);

        //then
        assertThat(result.getLength()).isEqualTo(3);
    }

    static class NotHaveColumnAnnotation {
        private String name;
    }

    static class UnspecifiedLength {

        @Column(length = 3)
        private String name;
    }
}
