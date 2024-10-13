package persistence.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.Dialect;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EntityTableUnitTest {
    EntityTable entityTable;
    Dialect dialect;

    @BeforeEach
    void setup() {
        entityTable = EntityTable.build(UnitTestEntity.class);
        dialect = mock(Dialect.class);
    }

    @Test
    @DisplayName("EntityTable 프로퍼티 확인")
    public void testEntityTableProperties() {
        assertEquals("users", entityTable.getName());
    }

    @Test
    @DisplayName("EntityTable에 컬럼을 셋팅할 수 있다.")
    public void testSetColumns() throws NoSuchFieldException {
        // given
        entityTable.setColumns(List.of(
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("id"), dialect),
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("name"), dialect),
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("email"), dialect),
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("age"), dialect)
        ));

        assertEquals(4, entityTable.getColumns().size());
    }

    @Test
    @DisplayName("테이블의 PK 컬럼을 조회할 수 있다.")
    public void testGetPrimary() throws NoSuchFieldException {
        // given
        entityTable.setColumns(List.of(
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("id"), dialect),
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("name"), dialect),
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("email"), dialect),
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("age"), dialect)
        ));

        assertEquals(1, entityTable.getPrimaryColumns().size());
    }
}
