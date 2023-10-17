package hibernate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

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
}
