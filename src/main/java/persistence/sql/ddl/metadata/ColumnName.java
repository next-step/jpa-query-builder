package persistence.sql.ddl.metadata;

import jakarta.persistence.Column;
import persistence.utils.StringUtils;

import java.lang.reflect.Field;

public record ColumnName(
        String value
) {
    public ColumnName {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("컬럼 이름은 비어있을 수 없습니다");
        }
    }

    public static ColumnName from(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            String name = field.getDeclaredAnnotation(Column.class).name();
            if (!name.isEmpty()) {
                return new ColumnName(name);
            }
        }

        String snakeCase = StringUtils.convertToSnakeCase(field.getName());
        return new ColumnName(snakeCase);
    }
}
