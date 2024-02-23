package persistence.sql.dialect;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.sql.model.SqlType;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class H2DialectTest {

    private final Dialect dialect = new H2Dialect();

    @DisplayName("H2 방언 확인하기")
    @ParameterizedTest
    @MethodSource
    void getType(SqlType type, String jdbcType) {
        String result = dialect.getType(type);

        assertThat(result).isEqualTo(jdbcType);
    }

    private static Stream<Arguments> getType() {
        return Stream.of(
                Arguments.arguments(SqlType.VARCHAR, "varchar"),
                Arguments.arguments(SqlType.VARCHAR, "varchar"),
                Arguments.arguments(SqlType.VARCHAR, "varchar")
        );
    }

    @Test
    void getConstraint() {
    }
}
