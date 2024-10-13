package persistence.sql.ddl;

import jakarta.persistence.Table;

public class DropQueryBuilder {
    public String dropTableQuery(Class<?> clazz) {
        String tableName = getTableName(clazz);
        return String.format("drop table %s", tableName);
    }

    private static String getTableName(Class<?> clazz) {
        Table annotation = clazz.getAnnotation(Table.class);
        if (annotation == null) {
            return clazz.getSimpleName().toLowerCase();
        }

        if (!annotation.name().isEmpty()) {
            return annotation.name();
        }

        return clazz.getSimpleName().toLowerCase();
    }
}
