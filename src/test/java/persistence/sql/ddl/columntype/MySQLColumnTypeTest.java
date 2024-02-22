package persistence.sql.ddl.columntype;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.sql.domain.*;

import java.util.stream.Stream;

class MySQLColumnTypeTest {

    static Stream<Arguments> mappingArguments() {
        return Stream.of(
                Arguments.arguments(Integer.class, "INT"),
                Arguments.arguments(Long.class, "BIGINT"),
                Arguments.arguments(String.class, "VARCHAR(255)")
        );
    }

    @ParameterizedTest
    @MethodSource("mappingArguments")
    void should_convert_java_type_to_mysql_column(Class<?> javaType, String dbType) {
        ColumnName name = new ColumnName("test");
        ColumnValue value = new ColumnValue(javaType, null);
        ColumnLength length = new ColumnLength(255);

        DatabaseColumn column = new DatabaseColumn(name, value, length, ColumnNullable.NULLABLE);
        String type = MySQLColumnType.convert(column);

        Assertions.assertThat(type).isEqualTo(dbType);
    }
}