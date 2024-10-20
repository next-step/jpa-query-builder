package persistence.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTableUnitTest {
    EntityTable entityTable;

    private EntityColumn idColumn;

    private EntityColumn nameColumn;

    private EntityColumn emailColumn;

    private EntityColumn ageColumn;

    @BeforeEach
    void setup() throws NoSuchFieldException {
        entityTable = EntityTable.build(UnitTestEntity.class);

        Field idField = UnitTestEntity.class.getDeclaredField("id");
        Field nameField = UnitTestEntity.class.getDeclaredField("name");
        Field emailField = UnitTestEntity.class.getDeclaredField("email");
        Field ageField = UnitTestEntity.class.getDeclaredField("age");

        idColumn = EntityColumn.build(idField, Optional.empty());
        nameColumn = EntityColumn.build(nameField, Optional.empty());
        emailColumn = EntityColumn.build(emailField, Optional.empty());
        ageColumn = EntityColumn.build(ageField, Optional.empty());
    }

    @Test
    @DisplayName("EntityTable 프로퍼티 확인")
    public void testEntityTableProperties() {
        assertEquals("users", entityTable.getName());
    }

    @Test
    @DisplayName("EntityTable에 컬럼을 셋팅할 수 있다.")
    public void testSetColumns() {
        // given
        entityTable.setColumns(List.of(idColumn, nameColumn, emailColumn, ageColumn));

        assertEquals(4, entityTable.getColumns().size());
    }

    @Test
    @DisplayName("테이블의 PK 컬럼을 조회할 수 있다.")
    public void testGetPrimary() {
        // given
        entityTable.setColumns(List.of(idColumn, nameColumn, emailColumn, ageColumn));

        assertEquals(1, entityTable.getPrimaryColumns().size());
    }

    @Test
    @DisplayName("테이블의 PK 컬럼에 값이 셋팅되어 있는지 확인할 수 있다.")
    public void testIsPrimaryColumnsSet() {
        EntityTable tableWithId = EntityFactory.createPopulatedSchema(
                new UnitTestEntity(1L, "홍길동", "test@test.com", 1)
        );
        EntityTable tableWithoutId = EntityFactory.createPopulatedSchema(new UnitTestEntity());

        assertAll(
                () -> assertTrue(tableWithId.isPrimaryColumnsValueSet()),
                () -> assertFalse(tableWithoutId.isPrimaryColumnsValueSet())
        );
    }
}
