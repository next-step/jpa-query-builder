package persistence.sql.ddl.metadata;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.fixture.IncludeId;
import persistence.sql.ddl.fixture.NotIncludeId;

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
        ColumnMetadata columnMetadata = ColumnMetadata.from(IncludeId.class);
        Column expected = Column.from(IncludeId.class.getDeclaredField("id"));
        assertThat(columnMetadata.getPrimaryKeys()).containsExactly(expected);
    }
}
