package hibernate.entity.column;

import hibernate.entity.column.ColumnType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ColumnTypeTest {

    @ParameterizedTest
    @CsvSource({
            "java.lang.Long, BIG_INT",
            "java.lang.Integer, INTEGER",
            "java.lang.String, VAR_CHAR",
    })
    void 자바_클래스를_받아_변환한다(final String input, final ColumnType expected) throws ClassNotFoundException {
        Class<?> inputClass = Class.forName(input);
        ColumnType actual = ColumnType.valueOf(inputClass);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 호환되지_않는_자바_클래스를_입력할_경우_예외가_발생한다() {
        assertThatThrownBy(() -> ColumnType.valueOf(ColumnType.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("호환되는 컬럼을 찾을 수 없습니다.");
    }
}
