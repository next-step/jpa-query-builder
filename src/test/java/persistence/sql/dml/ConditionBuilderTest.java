package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConditionBuilderTest {
    @Test
    @DisplayName("조건 builder를 통해 만든 문자열이 정확한지 검증")
    void success() {
        //given
        final String expectedResult = "WHERE id = 3";

        final String fieldName = "id";
        final Object value = 3L;

        //when
        String result = ConditionBuilder.getCondition(fieldName, value);

        //then
        assertThat(result).isEqualTo(expectedResult);
    }
}