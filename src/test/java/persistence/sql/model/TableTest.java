package persistence.sql.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("")
class TableTest {

    @DisplayName("전달된 컬럼에 primary key가 없을 경우 예외를 반환한다.")
    @Test
    void primary_key_not_found() {

        List<Column> columns = List.of(Column.create("column1", "BIGINT", true, false));

        assertThatThrownBy(() -> Table.create("test", columns))
            .isInstanceOf(PrimaryKeyNotFoundException.class);
    }

}
