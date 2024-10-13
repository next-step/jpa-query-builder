package builder.h2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class H2DataTypeTest {

    @DisplayName("변수 데이터타입에 따른 H2 컬럼 데이터타입을 가져온다.")
    @ParameterizedTest
    @CsvSource(value = {"java.lang.String:VARCHAR(255)", "java.lang.Integer:INTEGER", "java.lang.Long:BIGINT"}, delimiter = ':')
    void getDataTypeTest(String dataType, String h2DataType) {
        assertThat(H2DataType.findH2DataTypeByDataType(dataType)).isEqualTo(h2DataType);
    }
}
