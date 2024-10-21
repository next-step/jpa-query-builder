package persistence.sql.ddl.metadata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.fixture.EntityWithTable;

import static org.assertj.core.api.Assertions.assertThat;

class EntityMetadataTest {

    @DisplayName("테이블 이름을 반환한다")
    @Test
    void getTableName() {
        EntityMetadata entityMetadata = EntityMetadata.from(EntityWithTable.class);

        String tableName = entityMetadata.getTableName();

        assertThat(tableName).isEqualTo("my_table");
    }

    @DisplayName("기본키 이름 목록을 반환한다")
    @Test
    void getPrimaryKeyNames() {
        EntityMetadata entityMetadata = EntityMetadata.from(EntityWithTable.class);

        assertThat(entityMetadata.getPrimaryKeyNames()).containsExactly("id");
    }

    @DisplayName("컬럼 목록을 반환한다")
    @Test
    void getColumns() {
        EntityMetadata entityMetadata = EntityMetadata.from(EntityWithTable.class);

        assertThat(entityMetadata.getColumns()).hasSize(2);
    }
}
