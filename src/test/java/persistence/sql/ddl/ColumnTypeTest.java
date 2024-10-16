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
            H2ColumnType.of(booleanClass);
        });
    }

    @Test
    @DisplayName("Long으로 정의된 필드의 타입을 BigInt로 변환하여 반환")
    void convertLongJavaTypeToBigIntDef() throws Exception {
        Class<Long> longClass = Long.class;
        H2ColumnType columnDef = H2ColumnType.of(longClass);
        logger.info("{} 자바 타입을 변환한 결과값 : {}", columnDef.getJavaType(), columnDef.getQueryDefinition());

        assertThat(columnDef).isEqualTo(H2ColumnType.BIGINT);
    }

    @Test
    @DisplayName("String으로 정의된 필드의 타입을 varchar(255)로 변환하여 반환")
    void convertStringJavaTypeTovarcharDef() throws Exception {
        Class<String> stringClass = String.class;
        H2ColumnType columnDef = H2ColumnType.of(stringClass);
        logger.info("{} 자바 타입을 변환한 결과값 : {}", columnDef.getJavaType(), columnDef.getQueryDefinition());
        assertThat(columnDef).isEqualTo(H2ColumnType.VARCHAR);
    }

    @Test
    @DisplayName("Integer으로 정의된 필드의 타입을 integer로 변환하여 반환")
    void convertIntegerJavaTypeTointegerDef() throws Exception {
        Class<Integer> integerClass = Integer.class;
        H2ColumnType columnDef = H2ColumnType.of(integerClass);
        logger.info("{} 자바 타입을 변환한 결과값 : {}", columnDef.getJavaType(), columnDef.getQueryDefinition());
        assertThat(columnDef).isEqualTo(H2ColumnType.INTEGER);
    }

}
