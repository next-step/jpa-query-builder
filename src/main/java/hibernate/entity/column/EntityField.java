package hibernate.entity.column;

import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;

public class EntityField implements EntityColumn {

    private final String fieldName;
    private final ColumnType columnType;
    private final boolean isNullable;

    public EntityField(final Field field) {
        this.fieldName = parseFieldName(field);
        this.columnType = ColumnType.valueOf(field.getType());
        this.isNullable = parseNullable(field);
    }

    private String parseFieldName(final Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return field.getName();
        }
        String fieldName = field.getAnnotation(Column.class).name();
        if (fieldName.isEmpty()) {
            return field.getName();
        }
        return fieldName;
    }

    private boolean parseNullable(final Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return true;
        }
        return field.getAnnotation(Column.class).nullable();
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public ColumnType getColumnType() {
        return columnType;
    }

    @Override
    public boolean isNullable() {
        return isNullable;
    }

    @Override
    public boolean isId() {
        return false;
    }

    @Override
    public GenerationType getGenerationType() {
        throw new IllegalStateException("일반 Field는 GenerationType을 호출할 수 없습니다.");
    }
}
