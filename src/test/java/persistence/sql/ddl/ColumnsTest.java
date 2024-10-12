package persistence.sql.ddl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.fixture.NotIncludeId;

class ColumnsTest {

    @DisplayName("필드에 `@Id`가 없으면 예외가 발생한다")
    @Test
    void notIncludeId() {
        Assertions.assertThatThrownBy(() -> Columns.from(NotIncludeId.class))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
