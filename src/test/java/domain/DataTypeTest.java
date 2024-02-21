package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DataTypeTest {

    @DisplayName("정의한 데이터 타입이 아닌 경우 에러를 던진다.")
    @ParameterizedTest
    @CsvSource(value = {"'big'", "'INTT'"})
    void 데이터_타입_exception_테스트(String input) {
        assertThatThrownBy(() -> DataType.from(input)).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("VARCHAR 일 경우 true 를 던진다.")
    @ParameterizedTest
    @CsvSource(value = {"'String', true", "'Integer', false"})
    void varchar_타입_여부_검사_테스트(String input, boolean result) {
        Assertions.assertThat(DataType.from(input).isVarcharType()).isEqualTo(result);
    }
}
