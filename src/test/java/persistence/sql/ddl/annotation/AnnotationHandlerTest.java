package persistence.sql.ddl.annotation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.H2Dialect;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AnnotationHandlerTest {

    @Test
    @DisplayName("Field에 Column 어노테이션이 없으면 ColumnInfo 생성자에서 예외 발생")
    void constructor() throws NoSuchFieldException {
        Field field = Person.class.getDeclaredField("index");
        Dialect dialect = new H2Dialect();

        assertThrows(IllegalArgumentException.class, () -> {
            new ColumnAnnotationHandler(field, dialect);
        });
    }

}
