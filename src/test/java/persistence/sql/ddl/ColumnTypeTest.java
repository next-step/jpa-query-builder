package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static persistence.sql.ddl.ColumnType.VARCHAR;

class ColumnTypeTest {

    @Test
    @DisplayName("String 필드로 VARCHAR column type 을 찾는다")
    void findColumnType_success() {
        // given
        Class<String> string = String.class;

        // when
        ColumnType columnType = ColumnType.findColumnType(string);

        // then
        assertThat(columnType).isEqualTo(VARCHAR);
    }

    @Test
    @DisplayName("LocalDateTime 필드로 column type 을 찾지 못한다")
    void findColumnType_fail() {
        // given
        Class<LocalDateTime> localDateTime = LocalDateTime.class;

        // when
        Throwable throwable = catchThrowable(() -> ColumnType.findColumnType(localDateTime));

        // then
        assertThat(throwable)
                .hasMessage("Invalid field Type: java.time.LocalDateTime")
                .isInstanceOf(IllegalArgumentException.class);
    }

}
