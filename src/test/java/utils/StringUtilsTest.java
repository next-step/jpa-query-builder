package utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("StringUtils class 의")
class StringUtilsTest {

    @DisplayName("convertCamelToSnakeString 메서드는 문자열을 snake case로 변환한다.")
    @ParameterizedTest
    @CsvSource({
        "testCase, test_case",
        "TestCase, test_case",
        "TeStCaSe, te_st_ca_se"
    })
    void testConvertCamelToSnakeString(String value, String value2) {

        //when
        String convertStr = StringUtils.convertCamelToSnakeString(value);

        //then
        assertThat(convertStr).isEqualTo(value2);
    }
}
