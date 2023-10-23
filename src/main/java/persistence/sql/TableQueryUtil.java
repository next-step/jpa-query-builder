package persistence.sql;

import jakarta.persistence.Table;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TableQueryUtil {
    public static String getTableName(Class<?> clazz) {
        String tableName = clazz.getSimpleName().toLowerCase();
        if (clazz.isAnnotationPresent(Table.class)) {
            Table annotation = clazz.getAnnotation(Table.class);
            if (!annotation.name().isEmpty()) {
                tableName = annotation.name().toLowerCase();
            }
        }

        return tableName;
    }

    public static String getSelectedColumns(Object object) {
        return Arrays
            .stream(TableFieldUtil.getColumnNames(TableFieldUtil.getAvailableFields(object.getClass())))
            .map(x -> TableQueryUtil.getTableName(object.getClass()) + "." + x)
            .collect(Collectors.joining(", "));
    }
}
