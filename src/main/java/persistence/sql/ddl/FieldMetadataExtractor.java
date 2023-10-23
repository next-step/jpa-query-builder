package persistence.sql.ddl;

import jakarta.persistence.Column;
import utils.CustomStringBuilder;

import java.lang.reflect.Field;

public class FieldMetadataExtractor {

    private final Field field;

    public FieldMetadataExtractor(Field field) {
        this.field = field;
    }

    public String getDefinition() {
        return new CustomStringBuilder()
                .append(getColumnName(field))
                .append(map(field.getType()))
                .append(getColumnOptionValue())
                .toString();
    }

    private String getColumnOptionValue() {
        return ColumnOptionFactory.createColumnOption(field);
    }

    public String getColumnName(Object entity) throws NoSuchFieldException, IllegalAccessException {
        Field entityFiled = entity.getClass().getDeclaredField(field.getName());
        entityFiled.setAccessible(true);
        if (entityFiled.get(entity) != null) {
            return getColumnName(field);
        }

        return "";
    }

    public String getColumnName(Class<?> type) throws NoSuchFieldException, IllegalAccessException {
        return getColumnName(type.getDeclaredField(field.getName()));
    }

    public String getValueFrom(Object entity) throws NoSuchFieldException, IllegalAccessException {
        Field entityFiled = entity.getClass().getDeclaredField(field.getName());
        entityFiled.setAccessible(true);
        Object object = entityFiled.get(entity);

        if (object instanceof String) {
            return "'" + object + "'";
        } else if (object instanceof Integer) {
            return String.valueOf(object);
        } else if (object instanceof Long) {
            return String.valueOf(object);
        }

        return "";
    }

    String map(Class<?> type) {
        // TODO 리팩토링
        if (type == String.class) {
            return "VARCHAR(255)";
        } else if (type == int.class || type == Integer.class) {
            return "INT";
        } else if (type == long.class || type == Long.class) {
            return "BIGINT";
        }

        throw new IllegalArgumentException("지원하지 않는 타입입니다.");
    }

    private String getColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)
                && !isAnnotationNameEmpty(field)) {
            Column column = field.getAnnotation(Column.class);
            return column.name();
        }

        return field.getName();
    }

    private boolean isAnnotationNameEmpty(Field field) {
        return field.getAnnotation(Column.class).name().equals("");
    }

}
