package persistence.sql.mapping;

import jakarta.persistence.Table;

public class TableData {
    private final String name;

    private TableData(String name) {
        this.name = name;
    }

    public static TableData from(Class<?> entityClazz) {
        return new TableData(extractName(entityClazz));
    }

    private static String extractName(Class<?> entityClazz) {
        Table table = entityClazz.getAnnotation(Table.class);
        if (table == null) {
            return entityClazz.getSimpleName().toLowerCase();
        }
        return table.name();
    }
    public String getName() {
        return name;
    }
}
