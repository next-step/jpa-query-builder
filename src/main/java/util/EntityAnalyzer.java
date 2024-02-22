package util;

import jakarta.persistence.Entity;
import persistence.sql.model.Column;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityAnalyzer {

    public static String getTableName(Class<?> clazz) {
        validateEntity(clazz);

        String tableName = clazz.getSimpleName();
        return CaseConverter.pascalToSnake(tableName);
    }

    public static List<Column> getColumns(Class<?> clazz) {
        validateEntity(clazz);

        Field[] declaredFields = clazz.getDeclaredFields();
        return Arrays.stream(declaredFields)
                .map(Column::new)
                .collect(Collectors.toList());
    }

    private static void validateEntity(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class is not an entity: " + clazz.getSimpleName());
        }
    }
}
