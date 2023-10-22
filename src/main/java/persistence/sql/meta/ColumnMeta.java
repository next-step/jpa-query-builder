package persistence.sql.meta;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.util.StringUtils;

import java.lang.reflect.Field;

public class ColumnMeta {

    private final Field field;

    private ColumnMeta(Field field) {
        this.field = field;
    }

    public static ColumnMeta of(Field field) {
        return new ColumnMeta(field);
    }

    public boolean isId() {
        return field.isAnnotationPresent(Id.class);
    }

    public boolean isTransient() {
        return field.isAnnotationPresent(Transient.class);
    }

    public String getColumnName() {
        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
        if (columnAnnotation == null || StringUtils.isNullOrEmpty(columnAnnotation.name())) {
            return field.getName().toLowerCase();
        }
        return columnAnnotation.name();
    }

}
