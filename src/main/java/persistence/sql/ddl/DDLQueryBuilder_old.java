package persistence.sql.ddl;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DDLQueryBuilder_old {

    private static final DDLQueryBuilder_old instance = new DDLQueryBuilder_old();

    private DDLQueryBuilder_old() {
    }

    public static DDLQueryBuilder_old getInstance() {
        return instance;
    }

    public String createTableQuery(Class<?> clazz) {
        return String.format("CREATE TABLE %s (%s%s)",
                getTableName(clazz),
                createFieldsSql(clazz),
                createPrimaryKeySql(clazz)
        );
    }

    public String dropTableQuery(Class<?> clazz) {
        return String.format("DROP TABLE %s", getTableName(clazz));
    }

    private String getTableName(Class<?> clazz) {
        return clazz.isAnnotationPresent(Table.class) ? clazz.getAnnotation(Table.class).name() : clazz.getSimpleName().toLowerCase();
    }

    private String createFieldsSql(Class<?> clazz) {
        List<String> columns = extractColumns(clazz);

        return String.join(", ", columns);
    }

    private List<String> extractColumns(Class<?> clazz) {
        List<String> columns = new ArrayList<>();

        for (Field declaredField : clazz.getDeclaredFields()) {
            if (isPersistable(declaredField)) {
                columns.add(getColumn(declaredField));
            }
        }
        return columns;
    }

    private boolean isPersistable(Field declaredField) {
        return !declaredField.isAnnotationPresent(Transient.class);
    }

    private String createPrimaryKeySql(Class<?> clazz) {
        List<String> primaryKeys = getPrimaryKeys(clazz);

        if (!primaryKeys.isEmpty()) {
            return ", PRIMARY KEY (" + String.join(", ", primaryKeys) + ")";
        }

        return "";
    }

    private List<String> getPrimaryKeys(Class<?> clazz) {
        List<String> primaryKeys = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Id.class)) {
                primaryKeys.add(field.getName());
            }
        }

        return primaryKeys;
    }

    private String getColumn(Field field) {
        return getColumnName(field) + " " + getColumnType(field) + " " + getColumnProperty(field);
    }

    private String getColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            return field.getAnnotation(Column.class).name().isEmpty() ? field.getName() : field.getAnnotation(Column.class).name();
        }
        return field.getName();
    }

    private String getColumnProperty(Field field) {
        StringBuilder columnProperty = new StringBuilder();
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (!column.nullable()) {
                columnProperty.append("NOT NULL ");
            }
        }
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            columnProperty.append("AUTO_INCREMENT ");
        }
        return columnProperty.toString();
    }


    private String getColumnType(Field field) {
        if (field.getType().equals(Long.class)) {
            return "BIGINT";
        } else if (field.getType().equals(Integer.class)) {
            return "INT";
        }
        return "VARCHAR(255)";
    }

}
