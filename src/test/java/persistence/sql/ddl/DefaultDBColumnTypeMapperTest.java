package persistence.sql.ddl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultDBColumnTypeMapperTest {

    private DefaultDBColumnTypeMapper columnTypeMapper;

    @BeforeEach
    void setUp() {
        columnTypeMapper = DefaultDBColumnTypeMapper.getInstance();
    }

    @ParameterizedTest(name = "{0} - {1}")
    @MethodSource("classArgumentProvider")
    @DisplayName("getColumnName 테스트")
    void getColumnNameTest(final Class<?> clazz, final String expected) {
        final String result = columnTypeMapper.getColumnName(clazz);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("getColumnName 실패 테스트 - 타입 맵핑 정보가 존재하지 않음")
    void getColumnNameTypeNotExistTest() {
        assertThatThrownBy(() -> columnTypeMapper.getColumnName(TestClass.class))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> classArgumentProvider() {
        return Stream.of(
                Arguments.of(Long.class, "bigint")
                , Arguments.of(String.class, "varchar")
                , Arguments.of(Integer.class, "int")
        );
    }

    static class TestClass {
    }
}
