package persistence.core;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Objects;

public class EntityColumn {

    private static final String DEFAULT_COLUMN_NAME = "";

    private final String name;

    private final Class<?> type;

    private final GeneratedValue generatedValue;

    private final boolean isPrimaryKey;

    private final boolean isUnique;

    private final boolean isNullable;


    public EntityColumn(Field columnField) {
        this.type = columnField.getType();
        this.name = getColumnName(columnField);

        if (columnField.isAnnotationPresent(Id.class)) {
            this.generatedValue = columnField.getAnnotation(GeneratedValue.class);
            this.isPrimaryKey = true;
        } else {
            this.generatedValue = null;
            this.isPrimaryKey = false;
        }

        if (columnField.isAnnotationPresent(Column.class)) {
            Column columnAnnotation = columnField.getAnnotation(Column.class);
            this.isUnique = columnAnnotation.unique();
            this.isNullable = columnAnnotation.nullable();
        } else {
            this.isUnique = false;
            this.isNullable = true;
        }
    }

    private String getColumnName(Field columnField) {
        if (columnField.isAnnotationPresent(Column.class)) {
            Column columnAnnotation = columnField.getAnnotation(Column.class);
            return columnAnnotation.name().equals(DEFAULT_COLUMN_NAME) ?
                    columnField.getName() :
                    columnAnnotation.name();
        }

        return columnField.getName();
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public Class<?> getType() {
        return type;
    }

    public GeneratedValue getGeneratedValue() {
        return generatedValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityColumn that = (EntityColumn) o;
        return isPrimaryKey == that.isPrimaryKey &&
                isUnique == that.isUnique &&
                isNullable == that.isNullable &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, isPrimaryKey, isUnique, isNullable);
    }
}
