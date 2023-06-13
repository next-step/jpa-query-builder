package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TypeMapperTest {
    @Test
    @DisplayName("Integer 타입은 BIGINT 로 변환된다.")
    void integerToType() {
        assertThat(TypeMapper.toSqlType(Integer.class))
                .isEqualTo("BIGINT");
    }

    @Test
    @DisplayName("Long 타입은 BIGINT 로 변환된다.")
    void longToType() {
        assertThat(TypeMapper.toSqlType(Long.class))
                .isEqualTo("BIGINT");
    }

    @Test
    @DisplayName("String 타입은 TEXT 로 변환된다.")
    void stringToType() {
        assertThat(TypeMapper.toSqlType(String.class))
                .isEqualTo("TEXT");
    }
}
