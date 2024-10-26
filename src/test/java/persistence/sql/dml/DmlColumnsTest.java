package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.fixture.IdentityStrategy;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class DmlColumnsTest {

    @DisplayName("Instance를 받아 컬럼 목록을 생성한다")
    @Test
    void from() {
        IdentityStrategy entity = new IdentityStrategy(1L, "test", "transient");

        assertThatCode(() -> DmlColumns.from(entity))
                .doesNotThrowAnyException();
    }

    @DisplayName("Identity 전략을 사용하는 경우 id 컬럼을 제외한 컬럼 이름 목록을 반환한다")
    @Test
    void getInsertColumnNames() {
        IdentityStrategy entity = new IdentityStrategy(1L, "test", "transient");
        DmlColumns columns = DmlColumns.from(entity);

        List<String> columnNames = columns.getInsertColumnNames();

        assertThat(columnNames).doesNotContain("invalid");
    }

    @DisplayName("Identity 전략을 사용하는 경우 id 컬럼을 제외한 컬럼 이름 목록을 반환한다")
    @Test
    void ignoreTransient() {
        IdentityStrategy entity = new IdentityStrategy(1L, "test", "transient");
        DmlColumns columns = DmlColumns.from(entity);

        List<String> columnNames = columns.getInsertColumnNames();

        assertThat(columnNames).doesNotContain("id");
    }

    @DisplayName("Identity 전략을 사용하는 경우 id 컬럼을 제외한 컬럼 값 목록을 반환한다")
    @Test
    void getInsertColumnValues() {
        IdentityStrategy entity = new IdentityStrategy(1L, "test", "transient");
        DmlColumns columns = DmlColumns.from(entity);

        List<String> columnValues = columns.getInsertColumnValues();

        assertThat(columnValues).doesNotContain("1");
    }
}
