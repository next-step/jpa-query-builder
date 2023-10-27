package persistence.sql;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class TableSQLMapper {
    public static String getColumnName(Field field) {
        String columnName = field.getName();
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (!column.name().isEmpty()) {
                columnName = column.name();
            }
        }

        return columnName;
    }

    public static Field[] getTableColumnFields(Class<?> clazz) {
        return Arrays
            .stream(clazz.getDeclaredFields())
            .filter(TableSQLMapper::isAvailableField)
            .toArray(Field[]::new);
    }

    private static boolean isAvailableField(Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

    public static Field getPrimaryKeyColumnField(Class<?> clazz) {
        return Arrays
            .stream(getTableColumnFields(clazz))
            .filter(f -> f.isAnnotationPresent(Id.class))
            .findFirst()
            .orElse(null);
    }

    public static String getTableName(Class<?> clazz) {
        String tableName = clazz.getSimpleName().toLowerCase();
        if (clazz.isAnnotationPresent(Table.class)) {
            Table annotation = clazz.getAnnotation(Table.class);
            if (!annotation.name().isEmpty()) {
                tableName = annotation.name().toLowerCase();
            }
        }

        return tableName;
    }

    public static Object getValueOfColumn(Field field, Object object) {
        try {
            field.setAccessible(true);
            Object value = field.get(object);
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                if (!column.nullable() && value == null) {
                    throw new RuntimeException(TableSQLMapper.getColumnName(field) + " is not nullable!!");
                }
            }

            return value;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static String changeColumnValueToString(Object value) {
        if (value == null) {
            return "NULL";
        }
        else if (value instanceof String) {
            return SQLEscaper.escapeNameBySingleQuote(value.toString());
        } else {
            return value.toString();
        }
    }

    public static String[] getAllEscapedColumnNamesOfTable(Class<?> clazz) {
        String tableName = getTableName(clazz);
        return Arrays
            .stream(getTableColumnFields(clazz))
            .map(TableSQLMapper::getColumnName)
            .map(SQLEscaper::escapeNameByBacktick)
            .map(x ->  tableName + "." + x)
            .toArray(String[]::new);
    }

    public static Map<String, Field> getAllColumnNamesToFieldMap(Class<?> clazz) {
        return Arrays
            .stream(getTableColumnFields(clazz))
            .collect(Collectors.toMap(TableSQLMapper::getColumnName, field -> field));
    }

    public static boolean isValidColumn(Field field, Object object) {
        if (field.isAnnotationPresent(Id.class)) {
            try {
                field.setAccessible(true);
                Object value = field.get(object);
                return value != null;
            } catch (IllegalAccessException e) {
                return false;
            }
        }

        return true;
    }

    public static Field[] getValidFields(Object object) {
        return Arrays
            .stream(getTableColumnFields(object.getClass()))
            .filter(field -> TableSQLMapper.isValidColumn(field, object))
            .toArray(Field[]::new);
    }
}
