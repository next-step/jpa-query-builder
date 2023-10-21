package persistence.meta;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.sql.JDBCType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ColumnType 테스트")
class ColumnTypeTest {
    @Test
    @DisplayName("JDBC 타입에 맞는 칼럼이 생성된다")
    void getColumnType() {
        assertSoftly((it) -> {
            assertThat(ColumnType.createColumn(JDBCType.INTEGER).isInteger()).isTrue();
            assertThat(ColumnType.createColumn(JDBCType.BIGINT).isBigInt()).isTrue();
            assertThat(ColumnType.createColumn(JDBCType.VARCHAR).isVarchar()).isTrue();
        });
    }
}
