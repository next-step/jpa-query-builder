package persistence.sql.ddl.metadata;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import persistence.utils.StringUtils;

public record TableName(
        String value
) {
    public TableName {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("테이블 이름은 비어있을 수 없습니다");
        }
    }

    public static TableName from(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("@Entity가 아닙니다");
        }
        String name = extractName(clazz);
        return new TableName(name);
    }

    private static String extractName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class) && !clazz.getDeclaredAnnotation(Table.class).name().isEmpty()) {
            return clazz.getDeclaredAnnotation(Table.class).name();
        }

        return StringUtils.convertToSnakeCase(clazz.getSimpleName());
    }
}
