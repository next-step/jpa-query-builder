package util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StringUtilTest {

    @Test
    void addStringOnBothSides_1() {
        // given
        String origin = "origin";

        // when
        String result = StringUtil.addStringOnBothSides(origin, "'");

        // then
        assertThat(result).isEqualTo("'origin'");
    }
}