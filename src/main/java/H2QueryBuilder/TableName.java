package H2QueryBuilder;

import jakarta.persistence.Table;

public class TableName {
    private final String name;

    public TableName(Class<?> clazz) {
        this.name = getTableName(clazz);
    }

    private String getTableName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            String name = clazz.getAnnotation(Table.class).name();
            return name.isEmpty() ? clazz.getSimpleName().toLowerCase() : name;
        }
        return clazz.getSimpleName().toLowerCase();
    }

    public String getName() {
        return name;
    }
}
