package persistence.sql.base;

import jakarta.persistence.Table;

public class TableName {
    private final Class<?> clazz;

    public TableName(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        Table table = clazz.getAnnotation(Table.class);

        if (table != null && !table.name().isBlank()) {
            return table.name();
        }

        return clazz.getSimpleName().toLowerCase();
    }
}
