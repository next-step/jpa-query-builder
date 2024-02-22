package persistence.sql.dml.caluse;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.meta.ColumnName;

import java.lang.reflect.Field;

public class ColumnClause {
    private final Field field;

    public ColumnClause(Field field) {
        this.field = field;
    }

    public String getColumn() {
        if (field.isAnnotationPresent(Id.class)) {
            return getPKColumn();
        }
        if (field.isAnnotationPresent(Transient.class)) {
            return "";
        }
        return new ColumnName(field).getColumnName();
    }

    private String getPKColumn() {
        GenerationType generationType = field.isAnnotationPresent(GeneratedValue.class)
                ? field.getAnnotation(GeneratedValue.class).strategy()
                : GenerationType.AUTO;
        if (generationType.equals(GenerationType.AUTO)) {
            return new ColumnName(field).getColumnName();
        }
        return "";
    }
}
