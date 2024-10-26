package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DmlColumnValueTest {

    @DisplayName("null 값을 가진 ColumnValue 객체는 'null' 문자열을 반환한다")
    @Test
    void nullValue() {
        DmlColumnValue dmlColumnValue = new DmlColumnValue(null);

        assertThat(dmlColumnValue).hasToString("null");
    }

    @DisplayName("숫자 값을 가진 ColumnValue 객체는 숫자를 문자열로 반환한다")
    @Test
    void numberValue() {
        DmlColumnValue dmlColumnValue = new DmlColumnValue(1);

        assertThat(dmlColumnValue).hasToString("1");
    }

    @DisplayName("문자열 값을 가진 ColumnValue 객체는 문자열을 따옴표로 감싸서 반환한다")
    @Test
    void stringValue() {
        DmlColumnValue dmlColumnValue = new DmlColumnValue("test");

        assertThat(dmlColumnValue).hasToString("'test'");
    }

}
