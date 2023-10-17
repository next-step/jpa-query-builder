package persistence.meta;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.sql.JDBCType;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import persistence.exception.NumberRangeException;

@DisplayName("ColumnType 테스트")
class ColumnTypeTest {

    @ParameterizedTest
    @DisplayName("길이가 1보다 작으면 예외가 발생한다.")
    @ValueSource(ints = {-1, -2, 0})
    void negativeLength(Integer integer) {
        assertThatExceptionOfType(NumberRangeException.class)
                .isThrownBy(() -> ColumnType.createVarchar(integer));
    }

    @Test
    @DisplayName("JDBC 타입에 맞는 칼럼이 생성된다")
    void getColumnType() {
        assertSoftly((it) -> {
            assertThat(ColumnType.createColumn(JDBCType.INTEGER).getColumType()).isEqualTo("integer");
            assertThat(ColumnType.createColumn(JDBCType.BIGINT).getColumType()).isEqualTo("bigint");
            assertThat(ColumnType.createColumn(JDBCType.VARCHAR).getColumType()).isEqualTo("varchar(255)");
        });
    }

    @ParameterizedTest
    @DisplayName("VARCHAR는 길이조절이 가능하다.")
    @MethodSource("가변적인_문자열의_길이")
    void getVarchar(ColumnType varchar, String result) {
        assertThat(varchar.getColumType()).isEqualTo(result);
    }

    private static Stream<Arguments> 가변적인_문자열의_길이() {
        return Stream.of(
                Arguments.of(ColumnType.createVarchar(1000), "varchar(1000)"),
                Arguments.of(ColumnType.createVarchar(100), "varchar(100)"),
                Arguments.of(ColumnType.createVarchar(), "varchar(255)")
        );
    }

}
