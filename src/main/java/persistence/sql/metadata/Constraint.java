package persistence.sql.metadata;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class Constraint {
    private static final String PRIMARY_CONSTRAINT = " PRIMARY KEY";
    private static final String NOT_NULL_CONSTRAINT = " NOT NULL";

    private final boolean isPrimaryKey;

    private final boolean isNullable;

    private final String generatedType;

    public Constraint(Field field) {
        this.isPrimaryKey = findIsPrimaryKey(field);
        this.isNullable = findIsNullable(field);
        this.generatedType = findGeneratedType(field);
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public String getGeneratedType() {
        return generatedType;
    }

    private boolean findIsPrimaryKey(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private boolean findIsNullable(Field field) {
        if(!field.isAnnotationPresent(Column.class)) {
            return true;
        }

        Column column = field.getDeclaredAnnotation(Column.class);

        return column.nullable();
    }

    private String findGeneratedType(Field field) {
        if(!field.isAnnotationPresent(GeneratedValue.class)) {
            return "";
        }

        GenerationType strategy = field.getAnnotation(GeneratedValue.class).strategy();

        return strategy.name();
    }

    public String buildPrimaryKey() {
        if(isPrimaryKey) {
            return PRIMARY_CONSTRAINT;
        }

        return "";
    }

    public String buildNullable() {
        if(!isNullable) {
            return NOT_NULL_CONSTRAINT;
        }

        return "";
    }
}
