package persistence.sql.dialect;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DialectFactoryTest {

    @Test
    @DisplayName("미지원 DBMS")
    void notSupport() {
        assertThrows(IllegalArgumentException.class, () -> DialectFactory.getInstance().getDialect(""), "지원하지 않는 데이터베이스입니다.");
    }

}
