package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import persistence.sql.ddl.dialect.Dialect;
import utils.CustomStringBuilder;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

import static utils.JdbcTypeMapper.getJdbcTypeForClass;

public class FieldMetadataExtractor {

    private final Field field;

    public FieldMetadataExtractor(Field field) {
        this.field = field;
    }

    public String getDefinition(Dialect dialect) {
        return new CustomStringBuilder()
                .append(getColumnName())
                .append(dialect.getType(getJdbcTypeForClass(field.getType())))
                .append(getColumnOptionValue(dialect))
                .toString();
    }

    public String getColumnName(Object entity) throws NoSuchFieldException, IllegalAccessException {
        Field entityFiled = entity.getClass().getDeclaredField(field.getName());
        entityFiled.setAccessible(true);
        if (entityFiled.get(entity) != null) {
            return getColumnName();
        }

        return "";
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

        return null;
    }

    public String getColumnName() {
        if (field.isAnnotationPresent(Column.class)
                && !isAnnotationNameEmpty(field)) {
            Column column = field.getAnnotation(Column.class);
            return column.name();
        }

        return field.getName();
    }

    public boolean isId() {
        return field.isAnnotationPresent(Id.class);
    }

    public <T> void setInstanceValue(T instance, ResultSet resultSet) throws SQLException, IllegalAccessException {
        field.setAccessible(true);
        Object value = resultSet.getObject(getColumnName());
        field.set(instance, value);
    }

    private boolean isAnnotationNameEmpty(Field field) {
        return field.getAnnotation(Column.class).name().equals("");
    }

    private String getColumnOptionValue(Dialect dialect) {
        return ColumnOptionFactory.createColumnOption(field, dialect);
    }

}
