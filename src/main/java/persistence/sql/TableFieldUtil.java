package persistence.sql;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;

public class TableFieldUtil {
    static public String replaceNameByBacktick(String columnName) {
        return "`" + columnName + "`";
    }

    static public String replaceNameBySingleQuote(String columnName) {
        return "'" + columnName + "'";
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

    static public String[] getColumnNames(Field[] fields) {
        return Arrays
            .stream(fields)
            .map(TableFieldUtil::getColumnName)
            .map(TableFieldUtil::replaceNameByBacktick)
            .toArray(String[]::new);
    }

    static public Field[] getAvailableFields(Class<?> clazz) {
        return Arrays
            .stream(clazz.getDeclaredFields())
            .filter(x -> !x.isAnnotationPresent(Transient.class))
            .toArray(Field[]::new);
    }
}
