package persistence.sql.ddl.dialect;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.sql.ddl.SqlJdbcTypes;
import persistence.sql.ddl.exception.NotSupportException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class H2DialectTest {
    @MethodSource("fields")
    @ParameterizedTest
    void 필드_정의를_구할_수_있다(Class<?> clazz, String definition) {
        H2Dialect h2Dialect = new H2Dialect();
        Integer type = SqlJdbcTypes.typeOf(clazz);

        String result = h2Dialect.getFieldDefinition(type);

        assertThat(result).isEqualTo(definition);
    }

    @Test
    void 정의되지_않은_클래스의_경우_필드_정의가_실패한다() {
        H2Dialect h2Dialect = new H2Dialect();

        assertThatExceptionOfType(NotSupportException.class)
            .isThrownBy(() -> h2Dialect.getFieldDefinition(Integer.MAX_VALUE));
    }

    @MethodSource("idFields")
    @ParameterizedTest
    void 아이디_필드_정의를_구할_수_있다(Class<?> clazz, String definition) {
        H2Dialect h2Dialect = new H2Dialect();
        Integer type = SqlJdbcTypes.typeOf(clazz);

        String result = h2Dialect.getIdFieldDefinition(type);

        assertThat(result).isEqualTo(definition);
    }

    @Test
    void 정의되지_않은_클래스의_경우_아이디_필드_정의가_실패한다() {
        H2Dialect h2Dialect = new H2Dialect();

        assertThatExceptionOfType(NotSupportException.class)
            .isThrownBy(() -> h2Dialect.getIdFieldDefinition(Integer.MAX_VALUE));
    }

    static Stream<Arguments> fields() {
        return Stream.of(
            Arguments.of(Long.class, "BIGINT"),
            Arguments.of(String.class, "VARCHAR(%d)"),
            Arguments.of(Integer.class, "INTEGER")
        );
    }

    static Stream<Arguments> idFields() {
        return Stream.of(
            Arguments.of(Long.class, "BIGINT"),
            Arguments.of(Integer.class, "INTEGER")
        );
    }
}
