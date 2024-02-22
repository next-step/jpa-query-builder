package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.mapping.Value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class InsertQueryStringValueBinderTest {

    private final QueryValueBinder queryValueBinder = new QueryStringValueBinder();

    @DisplayName("DB 에서 문자 형식에 매핑되는 Value 를 받으면 true 를 반환한다")
    @Test
    public void support() throws Exception {
        // given
        final Value stringValue = new Value(String.class, 1);
        final Value intValue = new Value(Integer.class, 1);

        // when
        final boolean result1 = queryValueBinder.support(stringValue);
        final boolean result2 = queryValueBinder.support(intValue);

        // then
        assertAll(
                () -> assertThat(result1).isTrue(),
                () -> assertThat(result2).isFalse()
        );
    }

    @DisplayName("문자열 값을 받아 따옴표가 붙은 문자열로 변형하여 반환한다")
    @Test
    public void bind() throws Exception {
        // given
        final String intValue = "message";

        // when
        final String result = queryValueBinder.bind(intValue);

        // then
        assertThat(result).isEqualTo("'message'");
    }

}