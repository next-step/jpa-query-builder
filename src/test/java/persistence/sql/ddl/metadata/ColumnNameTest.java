package persistence.sql.ddl.metadata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.fixture.EntityWithColumn;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnNameTest {

    @DisplayName("@Column을 포함하지 않은 경우 필드 이름 스네이크케이스가 컬럼 이름이 된다")
    @Test
    void notIncludeColumn() throws NoSuchFieldException {
        ColumnName columnName = ColumnName.from(EntityWithColumn.class.getDeclaredField("withoutColumn"));

        assertThat(columnName.name()).isEqualTo("without_column");
    }

    @DisplayName("@Column이 name을 포함한 경우 그 값이 컬럼 이름이 된다")
    @Test
    void includeColumn() throws NoSuchFieldException {
        ColumnName columnName = ColumnName.from(EntityWithColumn.class.getDeclaredField("withColumn"));

        assertThat(columnName.name()).isEqualTo("my_column");

    }

}
