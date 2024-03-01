package persistence.sql.ddl.table;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.DDLGenerator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

class TableTest {

    @Test
    @DisplayName("테이블에 @Id 가 없을 경우 예외가 발생한다")
    void failByNotFoundIdAnnotation() {
        // given
        DDLGenerator DDLGenerator = new DDLGenerator(PersonNotHaveIdAnnotation.class);

        // when
        Throwable throwable = catchThrowable(DDLGenerator::generateCreate);

        // then
        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("table should have primary key");
    }
}
