package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;

public class TableFieldUtil {
    static public String getColumnNameBySingleQuote(String columnName) {
        return "`" + columnName + "`";
    }

    static public String getColumnName(Field field) {
        String columnName = field.getName();
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (!column.name().isEmpty()) {
                columnName = column.name();
            }
        }

        return columnName;
    }

    static public boolean skip(Field field) {
        return field.isAnnotationPresent(Transient.class);
    }
}
