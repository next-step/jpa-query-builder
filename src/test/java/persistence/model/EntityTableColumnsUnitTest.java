package persistence.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.Dialect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class EntityTableColumnsUnitTest {
    private EntityTableColumns columns;

    @BeforeEach
    public void setUp() {
        Dialect dialect = mock(Dialect.class);
        columns = EntityTableColumns.build(UnitTestEntity.class, dialect);
    }

    @Test
    @DisplayName("테이블의 전체 컬럼을 조회할 수 있다.")
    void testGetAll() {
        assertEquals(4, columns.getAll().size());
    }

    @Test
    @DisplayName("primary 컬럼을 조회할 수 있다.")
    void testGetPrimaryColumns() {
        assertEquals(1, columns.getPrimaryColumns().size());
    }
}
