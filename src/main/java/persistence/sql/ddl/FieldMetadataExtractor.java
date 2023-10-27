package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import persistence.sql.ddl.dialect.Dialect;
import utils.CustomStringBuilder;

import java.lang.reflect.Field;

import static utils.JdbcTypeMapper.getJdbcTypeForClass;

public class FieldMetadataExtractor {

    private final Field field;

    public FieldMetadataExtractor(Field field) {
        this.field = field;
    }

    public String getDefinition(Dialect dialect) {
        return new CustomStringBuilder()
                .append(getColumnName(field))
                .append(dialect.getType(getJdbcTypeForClass(field.getType())))
                .append(getColumnOptionValue(dialect))
                .toString();
    }

    private String getColumnOptionValue(Dialect dialect) {
        return ColumnOptionFactory.createColumnOption(field, dialect);
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

    public boolean isId() {
        return field.isAnnotationPresent(Id.class);
    }

}
