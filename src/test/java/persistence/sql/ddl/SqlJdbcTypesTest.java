package persistence.sql.ddl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.sql.ddl.exception.NotSupportException;

import java.sql.Types;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class SqlJdbcTypesTest {
    static Stream<Arguments> types() {
        return Stream.of(
            Arguments.arguments(String.class, Types.VARCHAR),
            Arguments.arguments(int.class, Types.INTEGER),
            Arguments.arguments(Integer.class, Types.INTEGER),
            Arguments.arguments(long.class, Types.BIGINT),
            Arguments.arguments(Long.class, Types.BIGINT)
        );
    }

    @MethodSource("types")
    @ParameterizedTest
    void 클래스로_JDBC타입으로_변경이_된다(Class<?> clazz, int expectedType) {
        assertThat(SqlJdbcTypes.typeOf(clazz)).isEqualTo(expectedType);
    }

    @Test
    void 등록되지_않은_클래스로_JDBC타입_변경시_실패한다() {
        assertThatExceptionOfType(NotSupportException.class)
            .isThrownBy(() -> SqlJdbcTypes.typeOf(SqlJdbcTypes.class));
    }
}
