package persistence.sql.schema;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ValueMeta 테스트")
class ValueMetaTest {

    @Test
    @DisplayName("String 타입의 값을 SQL의 문자열로 표현할 수 있다.")
    void canMakeAnyTypeValueMeta() {
        final ValueMeta stringValueMeta = ValueMeta.of("value");

        assertThat(stringValueMeta.getFormattedValue()).isEqualTo("'value'");
    }
}