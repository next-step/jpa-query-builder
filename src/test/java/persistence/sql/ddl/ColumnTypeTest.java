package persistence.sql.ddl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ColumnTypeTest {

    private static final Logger logger = LoggerFactory.getLogger(ColumnTypeTest.class);

    @Test
    @DisplayName("정의되지 않은 필드의 타입 오류 출력")
    void invalidColumnType() {
        Class<Boolean> booleanClass = Boolean.class;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ColumnType.of(booleanClass);
        });
    }

    @Test
    @DisplayName("Long으로 정의된 필드의 타입을 BigInt로 변환하여 반환")
    void convertLongJavaTypeToBigIntDef() throws Exception {
        Class<Long> longClass = Long.class;
        ColumnType columnDef = ColumnType.of(longClass);
        logger.info("{} 자바 타입을 변환한 결과값 : {}", columnDef.getJavaType(), columnDef.getColumnType());
        assertAll(
                () -> assertThat(columnDef.getColumnType()).isEqualTo(ColumnType.BIGINT.getColumnType()),
                () -> assertThat(columnDef.getJavaType()).isEqualTo(ColumnType.BIGINT.getJavaType()),
                () -> assertThat(columnDef.getColumnType()).isEqualTo("bigint")
        );
    }

    @Test
    @DisplayName("String으로 정의된 필드의 타입을 varchar(255)로 변환하여 반환")
    void convertStringJavaTypeTovarcharDef() throws Exception {
        Class<String> stringClass = String.class;
        ColumnType columnDef = ColumnType.of(stringClass);
        logger.info("{} 자바 타입을 변환한 결과값 : {}", columnDef.getJavaType(), columnDef.getColumnType());
        assertAll(
                () -> assertThat(columnDef.getColumnType()).isEqualTo(ColumnType.VARCHAR.getColumnType()),
                () -> assertThat(columnDef.getJavaType()).isEqualTo(ColumnType.VARCHAR.getJavaType()),
                () -> assertThat(columnDef.getColumnType()).isEqualTo("varchar(255)")
        );
    }

    @Test
    @DisplayName("Integer으로 정의된 필드의 타입을 integer로 변환하여 반환")
    void convertIntegerJavaTypeTointegerDef() throws Exception {
        Class<Integer> integerClass = Integer.class;
        ColumnType columnDef = ColumnType.of(integerClass);
        logger.info("{} 자바 타입을 변환한 결과값 : {}", columnDef.getJavaType(), columnDef.getColumnType());
        assertAll(
                () -> assertThat(columnDef.getColumnType()).isEqualTo(ColumnType.INTEGER.getColumnType()),
                () -> assertThat(columnDef.getJavaType()).isEqualTo(ColumnType.INTEGER.getJavaType()),
                () -> assertThat(columnDef.getColumnType()).isEqualTo("integer")
        );
    }

}
