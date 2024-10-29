package persistence.sql.metadata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnValueTest {

    @DisplayName("null 값을 가진 ColumnValue 객체는 'null' 문자열을 반환한다")
    @Test
    void nullValue() {
        ColumnValue columnValue = new ColumnValue(null);

        assertThat(columnValue).hasToString("null");
    }

    @DisplayName("숫자 값을 가진 ColumnValue 객체는 숫자를 문자열로 반환한다")
    @Test
    void numberValue() {
        ColumnValue columnValue = new ColumnValue(1);

        assertThat(columnValue).hasToString("1");
    }

    @DisplayName("문자열 값을 가진 ColumnValue 객체는 문자열을 따옴표로 감싸서 반환한다")
    @Test
    void stringValue() {
        ColumnValue columnValue = new ColumnValue("test");

        assertThat(columnValue).hasToString("'test'");
    }

}
