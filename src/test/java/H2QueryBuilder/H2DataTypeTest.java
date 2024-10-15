package H2QueryBuilder;

import common.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class H2DataTypeTest {

    @DisplayName("변수 데이터타입에 따른 컬럼 데이터타입 검증")
    @ParameterizedTest
    @CsvSource(value = {"java.lang.String:VARCHAR", "java.lang.Integer:INT", "java.lang.Long:BIGINT"}, delimiter = ':')
    void getDataTypeTest(String dataType, String h2DataType) {
        assertThat(H2DataType.findH2DataTypeByDataType(getClassForName(dataType))).isEqualTo(h2DataType);
    }

    private Class<?> getClassForName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(ErrorCode.NOT_EXIST_ENTITY_ANNOTATION.getErrorMsg());
        }
    }
}