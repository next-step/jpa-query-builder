package persistence.sql.ddl;

import jakarta.persistence.Table;

public class DropQueryBuilder {
    public String dropTableQuery(Class<?> clazz) {
        Table annotation = clazz.getAnnotation(Table.class);
        String tableName = annotation != null && !annotation.name().isEmpty() ? annotation.name() : clazz.getSimpleName().toLowerCase();
        return String.format("drop table %s", tableName);
    }
}
