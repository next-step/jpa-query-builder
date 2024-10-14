package persistence.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTableUnitTest {
    EntityTable entityTable;

    @BeforeEach
    void setup() {
        entityTable = EntityTable.build(UnitTestEntity.class);
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
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("id")),
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("name")),
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("email")),
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("age"))
        ));

        assertEquals(4, entityTable.getColumns().size());
    }

    @Test
    @DisplayName("테이블의 PK 컬럼을 조회할 수 있다.")
    public void testGetPrimary() throws NoSuchFieldException {
        // given
        entityTable.setColumns(List.of(
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("id")),
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("name")),
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("email")),
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("age"))
        ));

        assertEquals(1, entityTable.getPrimaryColumns().size());
    }
}
