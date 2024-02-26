package persistence.sql;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import persistence.sql.domain.DatabaseColumn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MySqlDialectTest {

    private final MySqlDialect dialect = new MySqlDialect();

    @ParameterizedTest
    @CsvSource(value = {"id,BIGINT", "age,INT", "name,VARCHAR(255)"}, delimiter = ',')
    void should_get_jdbc_type_from_java_class(String columnName, String expectJdbcType) throws NoSuchFieldException {
        DatabaseColumn column = DatabaseColumn.fromField(TestClass.class.getDeclaredField(columnName), null);

        String jdbcType = dialect.getJdbcTypeFromJavaClass(column);

        assertThat(jdbcType).isEqualTo(expectJdbcType);
    }

    @Test
    void should_throw_exception_when_matching_jdbc_type_not_exist() throws NoSuchFieldException {
        DatabaseColumn column = DatabaseColumn.fromField(TestClass.class.getDeclaredField("test"), null);

        assertThatThrownBy(() -> dialect.getJdbcTypeFromJavaClass(column))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("does not defined in Dialect");
    }

    private class TestClass {
        private Long id;

        private Integer age;

        private String name;

        private Object test;
    }
}
