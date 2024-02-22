package persistence.inspector;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityMetadataInspector extends EntityFieldInspector{

    public static String getTableName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class) && !clazz.getAnnotation(Table.class).name().isBlank()) {
            return clazz.getAnnotation(Table.class).name();
        }
        return clazz.getSimpleName().toLowerCase();
    }

    public static List<Field> getFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .collect(Collectors.toList());
    }

    public static Field getIdField(Class<?> clazz) {
        return getFields(clazz).stream().filter(EntityMetadataInspector::isPrimaryKey).findFirst().orElse(null);
    }

}
