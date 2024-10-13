package persistence.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.Dialect;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EntityTableUnitTest {
    @Test
    @DisplayName("EntityTable 프로퍼티 확인")
    public void testEntityTableProperties() {
        Dialect dialect = mock(Dialect.class);
        EntityTable entityTable = EntityTable.build(UnitTestEntity.class, dialect);

        assertAll(
                () -> assertEquals(4, entityTable.getColumns().size()),
                () -> assertEquals("person", entityTable.getName())
        );
    }
}
