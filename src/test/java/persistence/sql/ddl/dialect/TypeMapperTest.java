package persistence.sql.ddl.dialect;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.sql.ddl.dialect.database.TypeMapper;
import persistence.sql.ddl.dialect.h2.H2TypeMapper;
import persistence.sql.ddl.query.model.ColumnType;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TypeMapperTest {

    @DisplayName("클래스를 컬럼으로 변경한다.")
    @ParameterizedTest
    @MethodSource("typeMappers")
    void classToColumn(Class<?> clazz, String result) {
        TypeMapper mapper = H2TypeMapper.newInstance();

        assertThat(mapper.toSqlType(clazz)).isEqualTo(result);
    }

    private static Stream<Arguments> typeMappers() {
        return Stream.of(
                Arguments.of(String.class, ColumnType.VARCHAR.name()),
                Arguments.of(Boolean.class, ColumnType.BOOLEAN.name()),
                Arguments.of(Integer.class, ColumnType.INTEGER.name()),
                Arguments.of(Long.class, ColumnType.BIGINT.name())
        );
    }

}