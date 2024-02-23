package persistence.inspector;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntityInfoExtractor {

    public static List<Field> getEntityFieldsForInsert(Class<?> clazz) {
        try {

            return ClsssMetadataInspector.getFields(clazz).stream()
                    .filter(EntityInfoExtractor::isInsertTargetField)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isInsertTargetField(Field field) {
        return EntityFieldInspector.isPersistable(field) && !EntityFieldInspector.isAutoIncrement(field);
    }

    public static String getIdColumnName(Class<?> clazz) {
        return getColumnName(ClsssMetadataInspector.getIdField(clazz));
    }
    public static String getColumnName(Field field) {
        return EntityFieldInspector.getColumnName(field);
    }

    public static String getTableName(Class<?> clazz) {
        if (ClsssMetadataInspector.hasAnnotation(clazz, jakarta.persistence.Table.class) && (!clazz.getAnnotation(Table.class).name().isBlank())) {

                return clazz.getAnnotation(Table.class).name();
        }
        return clazz.getSimpleName().toLowerCase();
    }

    public static Field getIdField(Class<?> clazz) {

        return ClsssMetadataInspector.getFields(clazz).stream()
                .filter(EntityInfoExtractor::isPrimaryKey)
                .findFirst().orElse(null);
    }

    public static boolean isPrimaryKey(Field id) {
        return EntityFieldInspector.hasAnnotation(id, Id.class);
    }

    public static Object getFieldValue(Object object, Field field) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
