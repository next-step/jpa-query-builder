package persistence.sql.dialect.h2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.TypeDialect;

import static org.assertj.core.api.Assertions.assertThat;

class H2TypeDialectTest {
    private TypeDialect dialect;

    @BeforeEach
    void setUp() {
        dialect = H2TypeDialect.getInstance();
    }

    @Test
    @DisplayName("Integer 타입은 INTEGER 로 변환된다.")
    void integerToType() {
        assertThat(
                dialect.getSqlType(Integer.class)
        ).isEqualTo("INTEGER");
    }

    @Test
    @DisplayName("Long 타입은 BIGINT 로 변환된다.")
    void longToType() {
        assertThat(
                dialect.getSqlType(Long.class)
        ).isEqualTo("BIGINT");
    }

    @Test
    @DisplayName("String 타입은 VARCHAR 로 변환된다.")
    void stringToType() {
        assertThat(
                dialect.getSqlType(String.class)
        ).isEqualTo("VARCHAR");
    }
}
