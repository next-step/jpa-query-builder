package persistence.sql.ddl.columntype;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import persistence.sql.domain.DatabaseColumn;

class MySQLColumnTypeTest {

    @ParameterizedTest
    @CsvSource(value = {"id,BIGINT", "age,INT", "name,VARCHAR(255)"}, delimiter = ',')
    void should_convert_java_type_to_mysql_column(String columnName, String dbType) throws NoSuchFieldException {
        DatabaseColumn column = DatabaseColumn.fromField(TestClass.class.getDeclaredField(columnName), null);

        String type = MySQLColumnType.convert(column);

        Assertions.assertThat(type).isEqualTo(dbType);
    }

    private class TestClass {
        private Long id;

        private Integer age;

        private String name;
    }
}
