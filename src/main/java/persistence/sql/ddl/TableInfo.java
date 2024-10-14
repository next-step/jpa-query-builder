package persistence.sql.ddl;

import jakarta.persistence.Table;

import java.util.Objects;

public class TableInfo {

    private String tableName;

    private TableInfo(String tableName) {
        this.tableName = tableName;
    }

    public static TableInfo extract(Class<?> clazz) {
        return new TableInfo(extractTableName(clazz));
    }

    private static String extractTableName(Class<?> clazz) {
        final var className = clazz.getSimpleName().toLowerCase();
        final var tableAnnotation = clazz.getAnnotation(Table.class);
        if(Objects.isNull(tableAnnotation)) {
            return className;
        }

        if(tableAnnotation.name().isBlank()) {
            return className;
        }
        return tableAnnotation.name();
    }

    public String getTableName() {
        return tableName;
    }
}
