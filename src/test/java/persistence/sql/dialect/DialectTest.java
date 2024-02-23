package persistence.sql.dialect;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.model.SqlConstraint;
import persistence.sql.model.SqlType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class DialectTest {

    private final Dialect dialect = new H2Dialect();

    @Test
    @DisplayName("타입 방언 등록하기")
    void registerTypeDialect() {
        dialect.registerTypeDialect(SqlType.VARCHAR, "varchar2");

        String result = dialect.getType(SqlType.VARCHAR);

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo("varchar2");
            softly.assertThat(result).isNotEqualTo("varchar");
        });
    }

    @Test
    @DisplayName("제약조건 방언 등록하기")
    void registerConstraintDialect() {
        dialect.registerConstraintDialect(SqlConstraint.IDENTITY, "TEST_INCREMENT");

        String result = dialect.getConstraint(SqlConstraint.IDENTITY);

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo("TEST_INCREMENT");
            softly.assertThat(result).isNotEqualTo("auto_increment");
        });
    }
}
