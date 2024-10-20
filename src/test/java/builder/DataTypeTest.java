package builder;

import builder.ddl.dataType.DataType;
import builder.ddl.dataType.H2DataType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/*
- 변수 데이터타입에 따른 컬럼 데이터타입을 가져온다.
*/
public class DataTypeTest {

    @DisplayName("변수 데이터타입에 따른 컬럼 데이터타입을 가져온다.")
    @ParameterizedTest
    @CsvSource(value = {"java.lang.String:VARCHAR(255)", "java.lang.Integer:INTEGER", "java.lang.Long:BIGINT"}, delimiter = ':')
    void getDataTypeTest(String dataType, String DBDataType) {
        DataType h2DataType = new H2DataType();
        Assertions.assertThat(h2DataType.findDataTypeByClass(getClassForName(dataType))).isEqualTo(DBDataType);
    }

    private Class<?> getClassForName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("존재하지 않는 클래스입니다.");
        }
    }
}
