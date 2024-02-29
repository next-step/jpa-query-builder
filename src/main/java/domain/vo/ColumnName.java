package domain.vo;

import jakarta.persistence.Column;

import java.lang.reflect.Field;
import java.util.LinkedList;

import static domain.utils.StringUtils.isBlankOrEmpty;

public class ColumnName {

    private final String name;

    /**
     * 그 필드명에 있어야 name 이 생성된다.
     */
    public ColumnName(LinkedList<Field> fields, Field field) {
        if (!fields.contains(field)) {
            throw new IllegalArgumentException("존재하는 fieldName 이 아닙니다.");
        }
        this.name = getFieldName(field);
    }

    public String getName() {
        return name;
    }

    private String getFieldName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            return isBlankOrEmpty(field.getAnnotation(Column.class).name()) ? field.getName()
                    : field.getAnnotation(Column.class).name();
        }
        return field.getName();
    }
}
