package persistence.core;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Objects;

public class EntityColumn {

    private static final String DEFAULT_COLUMN_NAME = "";

    private static final int DEFAULT_LENGTH = 255;

    private final String name;

    private final Class<?> type;

    private GeneratedValue generatedValue;

    private boolean isPrimaryKey;

    private boolean isUnique;

    private boolean isNullable;

    private final boolean hasTransient;

    private int length;

    public EntityColumn(Field columnField) {
        assert columnField != null;

        this.type = columnField.getType();
        this.name = getColumnName(columnField);

        if (columnField.isAnnotationPresent(Id.class)) {
            setPkColumn(columnField);
        } else {
            setColumn(columnField);
        }

        this.hasTransient = columnField.isAnnotationPresent(Transient.class);
    }

    private void setPkColumn(Field columnField) {
        this.generatedValue = columnField.getAnnotation(GeneratedValue.class);
        this.isPrimaryKey = true;
        this.isNullable = false;
        this.isUnique = false;
    }

    private void setColumn(Field columnField) {
        if (columnField.isAnnotationPresent(Column.class)) {
            Column columnAnnotation = columnField.getAnnotation(Column.class);
            this.isUnique = columnAnnotation.unique();
            this.isNullable = columnAnnotation.nullable();
            this.length = columnAnnotation.length();
        } else {
            this.isUnique = false;
            this.isNullable = true;
            this.length = DEFAULT_LENGTH;
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

    public String getName() {
        return this.name;
    }

    public int getLength() {
        return length;
    }

    public boolean isUnique() {
        return this.isUnique;
    }

    public boolean isNullable() {
        return this.isNullable;
    }

    public boolean hasTransient() {
        return this.hasTransient;
    }

    public boolean isStringType() {
        return this.type == String.class;
    }

    public boolean isAutoIncrement() {
        if (this.generatedValue == null) {
            return false;
        }

        return this.generatedValue.strategy() == GenerationType.IDENTITY;
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
