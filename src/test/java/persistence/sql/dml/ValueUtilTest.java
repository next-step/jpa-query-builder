package persistence.sql.dml;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class ValueUtilTest {
    @Test
    void testGetValueString() {
        assertSoftly(softly -> {
            softly.assertThat(ValueUtil.getValueString(1)).isEqualTo("1");
            softly.assertThat(ValueUtil.getValueString("test")).isEqualTo("'test'");
            softly.assertThat(ValueUtil.getValueString(null)).isEqualTo("null");
        });
    }
}
