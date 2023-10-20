package persistence.sql.dialect.h2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class H2ColumnTypePropertiesTest {


    private static final int TEST_LENGTH = 300;

    @Entity
    private static class ColumnTestClass {
        @Id
        private Long id;

        @Column
        private String withoutLength;

        private String withoutColumn;

        @Column(length = TEST_LENGTH)
        private String withColumnAndLength;
    }

    @DisplayName("String 타입이고 @Column이 없거나 length 값이 없는 경우 기본 길이는 255이다.")
    @ParameterizedTest
    @CsvSource("withoutLength,withoutColumn")
    void varcharLengthTestWithoutDeclaredLength(String fieldName) throws Exception {
        assertThat(H2ColumnTypeProperties.getVarcharLength(ColumnTestClass.class.getDeclaredField(fieldName)))
                .isEqualTo("(255)");
    }

    @DisplayName("String 타입이고 @Column(length = 값)에 값이 명시된 경우 그 값을 따른다.")
    @ParameterizedTest
    @CsvSource("withColumnAndLength")
    void varcharLengthTestWithDeclaredLength(String fieldName) throws Exception {
        assertThat(H2ColumnTypeProperties.getVarcharLength(ColumnTestClass.class.getDeclaredField(fieldName)))
                .isEqualTo("(" + TEST_LENGTH + ")");
    }

}
