package persistence.sql;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import persistence.sql.domain.DatabaseColumn;

class MySqlDialectTest {

    private final MySqlDialect dialect = new MySqlDialect();

    @ParameterizedTest
    @CsvSource(value = {"id,BIGINT", "age,INT", "name,VARCHAR(255)"}, delimiter = ',')
    void should_get_jdbc_type_from_java_class(String columnName, String expectJdbcType) throws NoSuchFieldException {
        DatabaseColumn column = DatabaseColumn.fromField(TestClass.class.getDeclaredField(columnName), null);

        String jdbcType = dialect.getJdbcTypeFromJavaClass(column);

        Assertions.assertThat(jdbcType).isEqualTo(expectJdbcType);
    }

    private class TestClass {
        private Long id;

        private Integer age;

        private String name;
    }
}
