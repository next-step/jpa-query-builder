package persistence.sql.dml.column;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ColumnValueTest {

    @Test
    @DisplayName("변수 값에 해당하는 문자열을 반환한다")
    public void columnValue() {
        ColumnValue stringColumnValue = new ColumnValue("test");
        ColumnValue longColumnValue = new ColumnValue(961028L);
        ColumnValue integerColumnValue = new ColumnValue(123);

        assertAll(
                () -> assertThat(stringColumnValue.value()).isEqualTo("'test'"),
                () -> assertThat(longColumnValue.value()).isEqualTo("961028"),
                () -> assertThat(integerColumnValue.value()).isEqualTo("123")
        );
    }
}