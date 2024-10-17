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

    @BeforeEach
    public void setUp() {
        entityTableColumns = new EntityTableColumns();
    }

    @Test
    @DisplayName("테이블에 컬럼을 셋팅한다.")
    void testSetColumns() throws NoSuchFieldException {
        int initialColumnSize = entityTableColumns.getAll().size();

        // given
        entityTableColumns.setColumns(List.of(
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("id"), Optional.empty()),
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("name"), Optional.empty()),
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("email"), Optional.empty()),
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("age"), Optional.empty())
        ));

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
        entityTableColumns.setColumns(List.of(
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("id"), Optional.empty())
        ));

        assertEquals(1, entityTableColumns.getPrimaryColumns().size());
    }
}
