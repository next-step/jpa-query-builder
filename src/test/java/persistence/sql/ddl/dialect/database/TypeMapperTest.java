package persistence.sql.ddl.dialect.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.exception.InvalidJavaClassException;
import persistence.sql.ddl.dialect.h2.H2Dialect;
import persistence.sql.ddl.query.model.ColumnType;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TypeMapperTest {

    private Dialect dialect;
    private TypeMapper typeMapper;

    @BeforeEach
    void DialectTest() {
        this.dialect = new H2Dialect();
        this.typeMapper = dialect.getTypeMapper();
    }

    @DisplayName("H2 데이터베이스에 들어갈 TypeMapper을 반환한다.")
    @ParameterizedTest
    @MethodSource("h2TypeMapper")
    void getTypeMapper(Class<?> clazz, String type) {
        String sqlType = typeMapper.toSqlType(clazz);

        assertThat(sqlType).isEqualTo(type);
    }

    private static Stream<Arguments> h2TypeMapper() {
        return Stream.of(
                Arguments.of(String.class, ColumnType.VARCHAR.name()),
                Arguments.of(Boolean.class, ColumnType.BOOLEAN.name()),
                Arguments.of(Integer.class, ColumnType.INTEGER.name()),
                Arguments.of(Long.class, ColumnType.BIGINT.name())
        );
    }

    @Test
    @DisplayName("h2 데이터베이스에 지원하는 타입이 아니면 에러를 반환한다.")
    void toSqlTypeException() {
        assertThatThrownBy(() -> typeMapper.toSqlType(Optional.class))
                .isInstanceOf(InvalidJavaClassException.class)
                .hasMessage("지원하지 않는 타입이 들어왔습니다.");
    }
}
