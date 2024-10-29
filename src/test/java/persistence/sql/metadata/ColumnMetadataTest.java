package persistence.sql.metadata;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.fixture.IdentityStrategy;
import persistence.sql.ddl.fixture.IncludeId;
import persistence.sql.ddl.fixture.NotIncludeId;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnMetadataTest {

    @DisplayName("필드에 `@Id`가 없으면 예외가 발생한다")
    @Test
    void notIncludeId() {
        Assertions.assertThatThrownBy(() -> ColumnMetadata.from(NotIncludeId.class))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("@Id가 지정된 컬럼을 반환한다")
    @Test
    void getIdField() throws Exception {
        ColumnMetadata<IncludeId> columnMetadata = ColumnMetadata.from(IncludeId.class);

        Column expected = Column.from(IncludeId.class.getDeclaredField("id"));

        assertThat(columnMetadata.getPrimaryKey()).isEqualTo(expected);
    }

    @DisplayName("@Transient 필드를 제외한 컬럼 목록을 반환한다")
    @Test
    void ignoreTransient() {
        ColumnMetadata<IdentityStrategy> columnMetadata = ColumnMetadata.from(IdentityStrategy.class);

        List<String> insertColumnNames = columnMetadata.getInsertColumnNames();

        assertThat(insertColumnNames).doesNotContain("invalid");
    }

    @DisplayName("Identity 전략을 사용하는 경우 id 컬럼을 제외한 컬럼 이름 목록을 반환한다")
    @Test
    void getInsertColumnNames() {
        ColumnMetadata<IdentityStrategy> columnMetadata = ColumnMetadata.from(IdentityStrategy.class);

        List<String> insertColumnNames = columnMetadata.getInsertColumnNames();

        assertThat(insertColumnNames).doesNotContain("id");
    }

}
