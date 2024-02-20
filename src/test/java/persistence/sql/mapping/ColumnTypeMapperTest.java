package persistence.sql.mapping;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Types;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ColumnTypeMapperTest {

    private final ColumnTypeMapper columnTypeMapper = ColumnTypeMapper.getInstance();

    @DisplayName("java class 정보를 받아 Sql Type 으로 변환한다")
    @Test
    public void convertToJavaClassToSqlType() throws Exception {
        // given
        final Class<String> stringClass = String.class;
        final Class<Integer> integerClass = Integer.class;
        final Class<Long> longClass = Long.class;

        // when
        final int result1 = columnTypeMapper.toSqlType(stringClass);
        final int result2 = columnTypeMapper.toSqlType(integerClass);
        final int result3 = columnTypeMapper.toSqlType(longClass);

        // then
        assertAll(
                () -> assertThat(result1).isEqualTo(Types.VARCHAR),
                () -> assertThat(result2).isEqualTo(Types.INTEGER),
                () -> assertThat(result3).isEqualTo(Types.BIGINT)
        );
    }

    @DisplayName("매핑할 수 없는 java class 는 VARCHAR 타입으로 변환한다")
    @Test
    public void convertToDefault() throws Exception {
        // given
        final Class<Double> doubleClass = Double.class;

        // when
        final int result = columnTypeMapper.toSqlType(doubleClass);

        // then
        assertThat(result).isEqualTo(Types.VARCHAR);
    }

}
