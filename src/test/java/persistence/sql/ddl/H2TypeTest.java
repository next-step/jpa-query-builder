package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.sql.ddl.model.H2Type;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class H2TypeTest {

    @ParameterizedTest
    @MethodSource("typeFixture")
    @DisplayName("H2Type Converter 를 테스트 한다.")
    void typeConverterTest(Class<?> type, String expected) {
        final var actual = H2Type.converter(type);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("존재하지 않는 타입을 변환할 경우, 예외를 던진다")
    void invalidTypeTest() {
        Class<?> invalidType = Double.class;
        assertThrows(IllegalArgumentException.class, () -> H2Type.converter(invalidType));
    }

    private static Stream<Object[]> typeFixture() {
        return Stream.of(
                new Object[]{Integer.class, "INTEGER"},
                new Object[]{Long.class, "BIGINT"},
                new Object[]{String.class, "VARCHAR(255)"}
        );
    }

}
