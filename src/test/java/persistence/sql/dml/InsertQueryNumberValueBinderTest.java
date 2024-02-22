package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.mapping.Value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class InsertQueryNumberValueBinderTest {

    private final QueryValueBinder queryValueBinder = new QueryNumberValueBinder();

    @DisplayName("DB 에서 숫자 형식에 매핑되는 Value 를 받으면 true 를 반환한다")
    @Test
    public void support() throws Exception {
        // given
        final Value intValue = new Value(Integer.class, 1);
        final Value longValue = new Value(Long.class, 1);
        final Value stringValue = new Value(String.class, 1);

        // when
        final boolean result1 = queryValueBinder.support(intValue);
        final boolean result2 = queryValueBinder.support(longValue);
        final boolean result3 = queryValueBinder.support(stringValue);

        // then
        assertAll(
                () -> assertThat(result1).isTrue(),
                () -> assertThat(result2).isTrue(),
                () -> assertThat(result3).isFalse()
        );
    }

    @DisplayName("숫자 값을 받아 문자열로 변형하여 반환한다")
    @Test
    public void bind() throws Exception {
        // given
        final int intValue = 1;
        final long longValue = 1L;

        // when
        final String result1 = queryValueBinder.bind(intValue);
        final String result2 = queryValueBinder.bind(longValue);

        // then
        assertAll(
                () -> assertThat(result1).isEqualTo("1"),
                () -> assertThat(result2).isEqualTo("1")
        );
    }

}