package persistence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnTest {

    @Test
    void 컬럼_표현식() {
        ColumnNode actual = ColumnNode.of("name", Long.class, 255, false, true);

        assertThat(actual.expression()).isEqualTo("name bigint ");
    }
}
