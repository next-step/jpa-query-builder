package domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.lang.reflect.Field;
import java.util.Arrays;

import static domain.utils.StringUtils.isBlankOrEmpty;

public class EntityMetaData {

    private final Class<?> clazz;

    public EntityMetaData(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalStateException("Entity 클래스가 아닙니다.");
        }
        this.clazz = clazz;
    }

    public String getTableName() {
        return clazz.isAnnotationPresent(Table.class) ? clazz.getAnnotation(Table.class).name()
                : clazz.getSimpleName().toLowerCase();
    }

    public String getFieldName(Field field) {
        if (isColumnField(field)) {
            return isBlankOrEmpty(field.getAnnotation(Column.class).name()) ? field.getName()
                    : field.getAnnotation(Column.class).name();
        }
        return field.getName();
    }

    public Object getIdField() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isIdField)
                .map(this::getFieldName)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Id 필드가 존재하지 않습니다."));
    }

    public boolean isIdField(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    public boolean isColumnField(Field field) {
        return field.isAnnotationPresent(Column.class);
    }
}
