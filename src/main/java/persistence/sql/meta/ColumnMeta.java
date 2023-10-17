package persistence.sql.meta;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import persistence.sql.util.StringUtils;

import java.lang.reflect.Field;

public class ColumnMeta {

    public static boolean isTransient(Field field) {
        return field.getDeclaredAnnotation(Transient.class) != null;
    }

    public static String getColumnName(Field field) {
        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
        if (columnAnnotation == null || StringUtils.isNullOrEmpty(columnAnnotation.name())) {
            return field.getName().toLowerCase();
        }
        return columnAnnotation.name();
    }
}
