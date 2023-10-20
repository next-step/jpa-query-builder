package persistence.dialect;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.dialect.h2.H2ColumnTypeMapper;
import persistence.exception.NoSuchTypeException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class H2ColumnTypeMapperTest {

    private H2ColumnTypeMapper columnTypeMapper;

    @BeforeEach
    void setUp() {
        columnTypeMapper = H2ColumnTypeMapper.getInstance();
    }

    @ParameterizedTest(name = "{0} - {1}")
    @MethodSource("classArgumentProvider")
    @DisplayName("columnTypeMapper.getColumName 으로 맵핑된 DB 타입을 가져올 수 있다.")
    void getColumnNameTest(final Class<?> clazz, final String expected) {
        final String result = columnTypeMapper.getColumnName(clazz);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("columnTypeMapper 에 존재하지 않는 타입을 조회할 수 없다.")
    void getColumnNameTypeNotExistTest() {
        assertThatThrownBy(() -> columnTypeMapper.getColumnName(TestClass.class))
                .isInstanceOf(NoSuchTypeException.class);
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
