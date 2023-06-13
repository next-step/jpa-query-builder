package persistence;

import org.junit.jupiter.api.Test;

import java.util.DuplicateFormatFlagsException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ColumnsTest {

    @Test
    void 중복이름_예외발생() {
        assertThrows(DuplicateFormatFlagsException.class, () -> {
            new Columns(List.of(Column.of("id", Long.class, true), Column.of("id", Long.class, false)));
        });
    }

    @Test
    void 유니크는_하나만_존재해야함() {
        assertThrows(DuplicateFormatFlagsException.class, () -> {
            new Columns(List.of(Column.of("id", Long.class, true), Column.of("name", String.class, true)));
        });
    }

    @Test
    void 컬럼목록출력() {
        Columns columns = new Columns(List.of(Column.of("id", Long.class, true), Column.of("name", String.class, false)));

        String query = columns.expression();

        assertThat(query).isEqualTo("id bigint,name varchar(255),primary key (id)");
    }
}
