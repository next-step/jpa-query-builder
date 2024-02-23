package persistence.sql.dialect;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.model.SqlType;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class H2DialectTest {

    private final Dialect dialect = new H2Dialect();

    @Test
    @DisplayName("H2 방언 확인하기")
    void getType() {
        String varchar = dialect.getType(SqlType.VARCHAR);
        String integer = dialect.getType(SqlType.INTEGER);
        String bigint = dialect.getType(SqlType.BIGINT);

        assertSoftly(softly -> {
           softly.assertThat(varchar).isEqualTo("varchar");
           softly.assertThat(integer).isEqualTo("integer");
           softly.assertThat(bigint).isEqualTo("bigint");
        });
    }

    @Test
    void getConstraint() {
    }
}
