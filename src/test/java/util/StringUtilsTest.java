package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class StringUtilsTest {

    @DisplayName("문자열이 비어있으면 false, 값이 있으면 true 를 반환한다")
    @Test
    public void testStringNotBlank() throws Exception {
        // given
        final String blank = "";
        final String notBlank = "s";

        // when
        final boolean result1 = StringUtils.isNotBlank(blank);
        final boolean result2 = StringUtils.isNotBlank(notBlank);

        // then
        assertAll(
                () -> assertThat(result1).isFalse(),
                () -> assertThat(result2).isTrue()
        );
    }

}
