package persistence.sql.entitymetadata.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Objects;

public class EntityColumn<E, T> {
    private Class<E> entityClass;
    private boolean idColumn;
    private GenerationType idGenerateType;
    private Class<T> type;
    private String dbColumnName;
    private String entityFieldName;
    private Integer length;
    private boolean nullable;
    private Field columnField;

    public EntityColumn(Class<E> entityClass, Field entityColumnField) {
        this.entityClass = entityClass;
        this.idColumn = entityColumnField.isAnnotationPresent(Id.class);
        this.idGenerateType = createIdGenerateType(entityColumnField);
        this.type = (Class<T>) entityColumnField.getType();
        this.dbColumnName = createColumnName(entityColumnField);
        this.entityFieldName = entityColumnField.getName();
        this.length = createColumnLength(entityColumnField);
        this.nullable = createColumnNullable(entityColumnField);
        this.columnField = entityColumnField;
    }

    private GenerationType createIdGenerateType(Field entityColumnField) {
        if (this.idColumn && entityColumnField.isAnnotationPresent(GeneratedValue.class)) {
            return entityColumnField.getAnnotation(GeneratedValue.class).strategy();
        }

        return null;
    }

    private boolean createColumnNullable(Field entityColumnField) {
        if (entityColumnField.isAnnotationPresent(Column.class)) {
            return entityColumnField.getAnnotation(Column.class).nullable();
        }

        return true;
    }

    private Integer createColumnLength(Field entityColumnField) {
        if (entityColumnField.isAnnotationPresent(Column.class) && this.type == String.class) {
            return entityColumnField.getAnnotation(Column.class).length();
        }

        return null;
    }

    private String createColumnName(Field entityColumnField) {
        if (entityColumnField.isAnnotationPresent(Column.class)) {
            return createColumnName(entityColumnField, entityColumnField.getAnnotation(Column.class));
        }

        return entityColumnField.getName();
    }

    private String createColumnName(Field entityColumnField, Column column) {
        String columnAnnotationName = column.name();

        if ("".equals(columnAnnotationName)) {
            return entityColumnField.getName();
        }

        return columnAnnotationName;
    }

    public String getDbColumnName() {
        return dbColumnName;
    }

    public String getEntityFieldName() {
        return entityFieldName;
    }

    public Class<T> getType() {
        return type;
    }

    public Integer getLength() {
        return length;
    }

    public Boolean isNullable() {
        return nullable;
    }

    public boolean isIdColumn() {
        return idColumn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityColumn<?, ?> that = (EntityColumn<?, ?>) o;

        if (!Objects.equals(entityClass, that.entityClass)) return false;
        if (!Objects.equals(type, that.type)) return false;
        return Objects.equals(dbColumnName, that.dbColumnName);
    }

    @Override
    public int hashCode() {
        int result = entityClass != null ? entityClass.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (dbColumnName != null ? dbColumnName.hashCode() : 0);
        return result;
    }

    public GenerationType getIdGenerateType() {
        return idGenerateType;
    }

    public T getValue(E entityInstance) {
        try {
            columnField.setAccessible(true);
            T columnValue = (T) columnField.get(entityInstance);
            columnField.setAccessible(false);

            return columnValue;
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Cannot get value from field " +
                    columnField.getName() +
                    " of entity " +
                    entityClass.getName(),
                    e);
        }
    }

    public void setValue(E entityInstance, Object value) {
        try {
            columnField.setAccessible(true);
            columnField.set(entityInstance, value);
            columnField.setAccessible(false);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Cannot set value from field %s of entity %s",
                    columnField.getName(),
                    entityClass.getName()));
        }
    }
}
