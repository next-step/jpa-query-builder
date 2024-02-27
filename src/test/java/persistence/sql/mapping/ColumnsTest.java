package persistence.sql.mapping;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.exception.AnnotationMissingException;
import persistence.sql.ddl.exception.IdAnnotationMissingException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ColumnsTest {
    @Test
    @DisplayName("기본키 없으면 에러")
    void throwErrorWhenPrimaryKeyIsNotDefined() {
        assertThrows(IdAnnotationMissingException.class, () -> {
            Columns.createColumns(NoPrimaryKeyTest.class);
        });
    }

    @Test
    @DisplayName("Entity 클래스가 아니면 에러")
    void throwErrorWhenClassIsNotForEntity() {
        assertThrows(AnnotationMissingException.class, () -> {
            Columns.createColumns(NoEntityAnnotationTest.class);
        });
    }
}
