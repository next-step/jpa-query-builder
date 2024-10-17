package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilTest {

    @DisplayName("문자열을 작음따옴표로 묶어준다.")
    @Test
    void wrapSingleQuoteTest() {
        String name = "sangkihan";

        assertThat(StringUtil.wrapSingleQuote(name)).isEqualTo("'sangkihan'");
    }
}
