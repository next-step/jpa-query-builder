package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Arrays;
import java.util.List;

public class SchemaExtractor {
    private String extractTableName(Class<?> clazz) {
        String tableName = clazz.getSimpleName();
        if (clazz.isAnnotationPresent(Entity.class)) {
            Entity entityAnnotation = clazz.getAnnotation(Entity.class);
            if (!entityAnnotation.name().isEmpty()) {
                tableName = entityAnnotation.name();
            }
        }

        if (clazz.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            if (!tableAnnotation.name().isEmpty()) {
                tableName = tableAnnotation.name();
            }
        }

        return tableName;
    }

    public TableInfo extract(Class<?> clazz) {
        EntityClassValidator.validate(clazz);

        String tableName = extractTableName(clazz);
        List<TableColumn> columns =
                Arrays.stream(clazz.getDeclaredFields())
                        .map(TableColumn::new)
                        .toList();

        return new TableInfo(tableName, columns);
    }
}
