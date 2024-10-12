package persistence.sql.ddl.metadata;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import persistence.utils.StringUtils;

public class TableMetadata {
    private final String name;

    private TableMetadata(String name) {
        this.name = name;
    }

    public static TableMetadata from(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("@Entity가 아닙니다");
        }
        String name = extractName(clazz);
        return new TableMetadata(name);
    }

    private static String extractName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class) && !clazz.getDeclaredAnnotation(Table.class).name().isEmpty()) {
            return clazz.getDeclaredAnnotation(Table.class).name();
        }

        return StringUtils.convertToSnakeCase(clazz.getSimpleName());
    }

    public String getName() {
        return name;
    }
}
