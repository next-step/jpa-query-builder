package persistence.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.Dialect;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class EntityTableColumnsUnitTest {
    private EntityTableColumns entityTableColumns;

    private Dialect dialect;

    @BeforeEach
    public void setUp() {
        entityTableColumns = new EntityTableColumns();
        dialect = mock(Dialect.class);
    }

    @Test
    @DisplayName("테이블에 컬럼을 셋팅한다.")
    void testSetColumns() throws NoSuchFieldException {
        int initialColumnSize = entityTableColumns.getAll().size();

        // given
        entityTableColumns.setColumns(List.of(
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("id"), dialect),
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("name"), dialect),
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("email"), dialect),
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("age"), dialect)
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
                EntityColumn.build(UnitTestEntity.class.getDeclaredField("id"), dialect)
        ));

        assertEquals(1, entityTableColumns.getPrimaryColumns().size());
    }
}
