package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Objects;

public class FieldInfo {

    private final String name;
    private final String type;
    private final boolean nullable;

    private final boolean isPrimaryKey;
    private PrimaryKey primaryKey;

    public FieldInfo(Field field) {
        boolean hasColumnAnnotation = field.isAnnotationPresent(Column.class);
        boolean nullable = false;
        boolean isPrimaryKey = field.isAnnotationPresent(Id.class);
        String columnType = field.getType().getSimpleName();
        String columnDefinition = field.getName();

        if (hasColumnAnnotation) {
            Column column = field.getAnnotation(Column.class);
            columnDefinition = Objects.equals(column.name(), "") ? columnDefinition : column.name();
            nullable = column.nullable();
        }

        if (isPrimaryKey) {
            GeneratedValue generatedValue = null;

            if (field.isAnnotationPresent(GeneratedValue.class)) {
                generatedValue = field.getAnnotation(GeneratedValue.class);
            }

            this.primaryKey = new PrimaryKey(this, generatedValue);
        }

        this.name = columnDefinition;
        this.type = columnType;
        this.nullable = nullable;
        this.isPrimaryKey = isPrimaryKey;
    }

    public Boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public PrimaryKey primaryKey() {
        return primaryKey;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    public boolean notNullable() {
        return !nullable;
    }
}
