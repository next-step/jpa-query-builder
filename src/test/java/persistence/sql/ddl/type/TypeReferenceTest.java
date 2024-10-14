package persistence.sql.ddl.type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TypeReferenceTest {

    @Test
    @DisplayName("[성공] Java 타입에 해당하는 Sql 타입 반환")
    void returnSqlType() {
        Assertions.assertEquals(SchemaDataTypeReference.getSqlType(String.class), "varchar");
    }

    @Test
    @DisplayName("[실패] Java 타입에 해당하는 Sql 타입이 없는 경우, RuntimeException 발생")
    void getNotExistSqlType() {
        assertThatThrownBy(() -> SchemaDataTypeReference.getSqlType(Boolean.class))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("class '" + Boolean.class.getName() + "' sql type not exist.");
    }

}
