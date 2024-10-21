package persistence.sql.ddl.metadata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.fixture.EntityWithTable;
import persistence.sql.ddl.fixture.EntityWithoutTable;
import persistence.sql.ddl.fixture.IncludeId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TableNameTest {

    @DisplayName("@Entity를 포함하지 않은 경우 예외가 발생한다")
    @Test
    void notIncludeEntity() {
        assertThatThrownBy(() -> TableName.from(IncludeId.class))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("@Table을 포함하지 않은 경우 클래스 이름을 스네이크 케이스로 만든 문자열이 이름이 된다")
    @Test
    void notIncludeTable() {
        TableName tableName = TableName.from(EntityWithoutTable.class);

        assertThat(tableName.value()).isEqualTo("entity_without_table");
    }

    @DisplayName("@Table을 포함하고 이름이 주어진 경우 주어진 이름이 이름이 된다")
    @Test
    void includeTable() {
        TableName tableName = TableName.from(EntityWithTable.class);

        assertThat(tableName.value()).isEqualTo("my_table");
    }
}
