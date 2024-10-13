package persistence.sql.ddl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.sql.ddl.exception.NotSupportException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class SqlJdbcTypesTest {
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

    static Stream<Arguments> types() {
        return Stream.of(
                Arguments.arguments(String.class, Types.VARCHAR),
                Arguments.arguments(boolean.class, Types.BOOLEAN),
                Arguments.arguments(Boolean.class, Types.BOOLEAN),
                Arguments.arguments(byte.class, Types.TINYINT),
                Arguments.arguments(Byte.class, Types.TINYINT),
                Arguments.arguments(short.class, Types.SMALLINT),
                Arguments.arguments(Short.class, Types.SMALLINT),
                Arguments.arguments(int.class, Types.INTEGER),
                Arguments.arguments(Integer.class, Types.INTEGER),
                Arguments.arguments(long.class, Types.BIGINT),
                Arguments.arguments(Long.class, Types.BIGINT),
                Arguments.arguments(BigInteger.class, Types.BIGINT),
                Arguments.arguments(float.class, Types.FLOAT),
                Arguments.arguments(Float.class, Types.FLOAT),
                Arguments.arguments(double.class, Types.DOUBLE),
                Arguments.arguments(Double.class, Types.DOUBLE),
                Arguments.arguments(BigDecimal.class, Types.DECIMAL),
                Arguments.arguments(LocalDate.class, Types.DATE),
                Arguments.arguments(LocalTime.class, Types.TIME),
                Arguments.arguments(LocalDateTime.class, Types.TIMESTAMP),
                Arguments.arguments(java.sql.Date.class, Types.DATE),
                Arguments.arguments(java.sql.Time.class, Types.TIME),
                Arguments.arguments(java.sql.Timestamp.class, Types.TIMESTAMP),
                Arguments.arguments(Blob.class, Types.BLOB),
                Arguments.arguments(Clob.class, Types.CLOB)
        );
    }
}
