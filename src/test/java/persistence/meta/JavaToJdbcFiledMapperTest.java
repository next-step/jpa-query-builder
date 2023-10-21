package persistence.meta;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.sql.JDBCType;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.StringUtils;

class JavaToJdbcFiledMapperTest {

    @ParameterizedTest
    @DisplayName("javaType을 JdbcType으로 변환해준다")
    @MethodSource("javaType과_Jdbc_타입변환_파라미터")
    void convert(Class<?> type, JDBCType result) {
        final JDBCType convert = JavaToJdbcFiledMapper.convert(type);

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


    @Test
    @DisplayName("지원하지 않은 타입은 예외가 발생한다.")
    void notSupport() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> JavaToJdbcFiledMapper.convert(StringUtils.class));
    }

}
