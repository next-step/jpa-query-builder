package persistence.sql.ddl;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Function;

import static persistence.sql.common.SqlConstant.*;
import static persistence.sql.ddl.utils.IdChecker.isId;

/**
 * 애노테이션이 없는 필드는 본 클래스로 column sql문 생성한다.
 */
public class AnnotationFreeColumn {
    private static final Map<Type, Function<String, String>> annotationFreeMap = Map.of(
            String.class, fieldName -> String.format(CREATE_TABLE_VARCHAR_COLUMN_NULLABLE, fieldName),
            Integer.class, fieldName -> String.format(CREATE_TABLE_COLUMN_INT, fieldName)
    );
    private final Field field;

    public AnnotationFreeColumn(Field field) {
        this.field = field;
    }

    public String getColumn() {
        if (isId(field)) {
            return CREATE_TABLE_ID_COLUMN;
        }
        return annotationFreeMap.get(field.getType()).apply(field.getName());
    }
}
