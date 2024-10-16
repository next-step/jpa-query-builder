package persistence.sql.node;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 필드 노드 정보
 */
public class FieldNode implements SQLNode{
    private final Field field;

    public FieldNode(Field field) {
        this.field = field;
    }

    public static FieldNode from(Field field) {
        return new FieldNode(field);
    }

    public Field getField() {
        return field;
    }

    public boolean isPrimaryKey() {
        return field.isAnnotationPresent(Id.class);
    }

    public String getFieldName() {
        Column columnAnno = field.getAnnotation(Column.class);

        if (columnAnno == null || columnAnno.name().isBlank()) {
            return field.getName();
        }

        return columnAnno.name();
    }

    public Class<?> getFieldType() {
        return field.getType();
    }

    public <T extends Annotation> T getAnnotation(Class<T> columnClass) {
        return field.getAnnotation(columnClass);
    }

    @SafeVarargs
    public final boolean containsAnnotations(Class<? extends Annotation>... excludeAnnotations) {
        return Arrays.stream(excludeAnnotations).anyMatch(field::isAnnotationPresent);
    }

    public Object extractFieldValue(Object entity) throws IllegalAccessException {
        field.setAccessible(true);

        return field.get(entity);
    }
}
