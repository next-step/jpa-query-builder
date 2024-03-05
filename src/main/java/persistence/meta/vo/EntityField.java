package persistence.meta.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EntityField {
    private final String originalFieldName;
    private final Optional<ColumnAnnotationData> columnAnnotationData;
    private final Class<?> fieldType;

    public static List<EntityField> createFromClass(Class<?> cls) {
            return Arrays.stream(cls.getDeclaredFields())
                         .filter(field -> !field.isAnnotationPresent(Id.class))
                         .filter(field -> !field.isAnnotationPresent(Transient.class))
                         .map(field -> createFromField(field))
                         .collect(Collectors.toList());
    }

    public static EntityField createFromField(Field field) {
        return new EntityField(field.getName(), getColumAnnotationData(field), field.getType());
    }

    private static Optional<ColumnAnnotationData> getColumAnnotationData(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return Optional.empty();
        }
        Column columnAnnotation = field.getAnnotation(Column.class);
        return Optional.of(new ColumnAnnotationData(getTableFieldName(field), columnAnnotation.nullable()));
    }

    private static String getTableFieldName(Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        if(columnAnnotation.name() == null || columnAnnotation.name().isBlank()) {
            return field.getName();
        }
        return columnAnnotation.name();
    }

    private EntityField(String originalFieldName, Optional<ColumnAnnotationData> columnAnnotationData, Class<?> fieldType) {
        this.originalFieldName = originalFieldName;
        this.columnAnnotationData = columnAnnotationData;
        this.fieldType = fieldType;
    }

    public String getTableFieldName() {
        return columnAnnotationData.isEmpty() ? originalFieldName : columnAnnotationData.get().getTableFieldName();
    }

    public boolean isNullable() {
        return columnAnnotationData.isEmpty() ? true : columnAnnotationData.get().isNullable();
    }

    public Class<?> getFieldType() {
        return fieldType;
    }


}
