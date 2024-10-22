package persistence.sql.ddl.metadata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnTypeTest {

    @DisplayName("java type을 sql type으로 변환")
    @ParameterizedTest(name = "{0}은 {1}로 해석한다")
    @MethodSource("provideColumnTypes")
    void javaTypeToSqlType(Class<?> javaType, String expectedSqlType) {
        ColumnType columnType = ColumnType.of(javaType);

        String sqlType = columnType.getSqlType();

        assertThat(sqlType).isEqualTo(expectedSqlType);
    }

    private static Stream<Arguments> provideColumnTypes() {
        return Stream.of(
                Arguments.of(Long.class, "bigint"),
                Arguments.of(Integer.class, "integer"),
                Arguments.of(String.class, "varchar(255)")
        );
    }
}
