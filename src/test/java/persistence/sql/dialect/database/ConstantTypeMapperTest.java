package persistence.sql.dialect.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.database.ConstraintsMapper;
import persistence.sql.dialect.exception.InvalidConstantTypeException;
import persistence.sql.dialect.h2.H2Dialect;
import persistence.sql.entity.model.Constraints;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ConstantTypeMapperTest {

    private Dialect dialect;
    private ConstraintsMapper constantTypeMapper;

    @BeforeEach
    void DialectTest() {
        this.dialect = new H2Dialect();
        this.constantTypeMapper = dialect.getConstantTypeMapper();
    }

    @DisplayName("H2에 해당하는 제약조건을 반환한다.")
    @ParameterizedTest
    @MethodSource("constantTypes")
    void getConstantMapper(Constraints constantType, String expected) {
        String actual = constantTypeMapper.getConstantType(constantType);

        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> constantTypes() {
        return Stream.of(
                Arguments.of(Constraints.PK, "PRIMARY KEY AUTO_INCREMENT"),
                Arguments.of(Constraints.PRIMARY_KEY, "PRIMARY KEY"),
                Arguments.of(Constraints.NOT_NULL, "NOT NULL")
        );
    }

    @DisplayName("지원하지 않는 제약조건일시, 에러를 반환한다.")
    @Test
    void invalidConstantType() {
        assertThatThrownBy(() -> constantTypeMapper.getConstantType(Constraints.UNIQUE_KEY))
                .isInstanceOf(InvalidConstantTypeException.class)
                .hasMessage("지원하지 않는 제약조건 값이 들어왔습니다.");
    }

}
