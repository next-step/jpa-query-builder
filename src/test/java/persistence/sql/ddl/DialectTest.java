package persistence.sql.ddl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.h2.H2Dialect;

class DialectTest {
    private final Dialect dialect = new H2Dialect();

    @Test
    @DisplayName("Integer 타입의 경우 integer 를 반환한다")
    public void columnType() {
        String columnType = dialect.columnType(Integer.class);
        Assertions.assertThat(columnType).isEqualTo("integer");
    }

    @Test
    @DisplayName("Long 타입의 경우 bigint 를 반환한다")
    public void longType() {
        String columnType = dialect.columnType(Long.class);
        Assertions.assertThat(columnType).isEqualTo("bigint");
    }

    @Test
    @DisplayName("String 타입의 경우 varchar 를 반환한다")
    public void stringType() {
        String columnType = dialect.columnType(String.class);
        Assertions.assertThat(columnType).isEqualTo("varchar");
    }
}