package persistence.sql.ddl;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.JDBCType;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class JavaJdbcFiledMapperTest {

    @ParameterizedTest
    @DisplayName("javaType을 JdbcType으로 변환해준다")
    @MethodSource("javaType과_Jdbc_타입변환_파라미터")
    void convert(Class<?> type, JDBCType result) {
        JavaJdbcFiledMapper javaJdbcFiledMapper = new JavaJdbcFiledMapper();

        final JDBCType convert = javaJdbcFiledMapper.convert(type);

        assertThat(convert).isEqualTo(result);
    }

    private static Stream<Arguments> javaType과_Jdbc_타입변환_파라미터() {
        return Stream.of(
                Arguments.of(String.class, JDBCType.VARCHAR),
                Arguments.of(Integer.class, JDBCType.INTEGER),
                Arguments.of(int.class, JDBCType.INTEGER),
                Arguments.of(Long.class, JDBCType.BIGINT),
                Arguments.of(long.class, JDBCType.BIGINT)
        );
    }

}
