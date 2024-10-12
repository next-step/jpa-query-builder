package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnTypeTest {

    @DisplayName("Long은 bigint로 해석한다")
    @Test
    void javaLongToBigint() {
        ColumnType columnType = ColumnType.of(Long.class);

        String columnDefinition = columnType.getColumnDefinition();

        assertThat(columnDefinition).isEqualTo("bigint");
    }
}
