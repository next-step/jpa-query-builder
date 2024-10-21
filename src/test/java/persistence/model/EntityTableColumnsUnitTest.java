package persistence.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityTableColumnsUnitTest {
    private EntityTableColumns entityTableColumns;

    private EntityColumn idColumn;

    private EntityColumn nameColumn;

    private EntityColumn emailColumn;

    private EntityColumn ageColumn;

    @BeforeEach
    public void setUp() throws NoSuchFieldException {
        entityTableColumns = new EntityTableColumns();

        idColumn = EntityColumn.build(UnitTestEntity.class.getDeclaredField("id"), Optional.empty());
        nameColumn = EntityColumn.build(UnitTestEntity.class.getDeclaredField("name"), Optional.empty());
        emailColumn = EntityColumn.build(UnitTestEntity.class.getDeclaredField("email"), Optional.empty());
        ageColumn = EntityColumn.build(UnitTestEntity.class.getDeclaredField("age"), Optional.empty());
    }

    @Test
    @DisplayName("테이블에 컬럼을 셋팅한다.")
    void testSetColumns() throws NoSuchFieldException {
        int initialColumnSize = entityTableColumns.getAll().size();

        // given
        entityTableColumns.setColumns(List.of(idColumn, nameColumn, emailColumn, ageColumn));

        // when
        int setColumnSize = entityTableColumns.getAll().size();

        // then
        assertAll(
                () -> assertEquals(0, initialColumnSize),
                () -> assertEquals(4, setColumnSize)
        );
    }

    @Test
    @DisplayName("primary 컬럼을 조회할 수 있다.")
    void testGetPrimaryColumns() throws NoSuchFieldException {
        entityTableColumns.setColumns(List.of(idColumn));

        assertEquals(1, entityTableColumns.getPrimaryColumns().size());
    }

    @Test
    @DisplayName("컬럼 이름으로 컬럼을 조회할 수 있다.")
    void testGetColumn() throws NoSuchFieldException {
        entityTableColumns.setColumns(List.of(idColumn));

        assertEquals(idColumn, entityTableColumns.findByName("id"));
    }
}
