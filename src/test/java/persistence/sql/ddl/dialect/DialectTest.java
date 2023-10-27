package persistence.sql.ddl.dialect;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DialectTest {

    @Test
    @DisplayName("get() 메서드 Exception 테스트")
    public void getExceptionTest() {
        Dialect dialect = new H2Dialect();

        assertThrows(IllegalArgumentException.class, () -> {
            dialect.getType(0);
        });
    }

}
