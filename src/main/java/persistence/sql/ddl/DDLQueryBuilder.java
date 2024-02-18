package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DDLQueryBuilder {

    public String createTableQuery(Class<?> clazz) {

        return String.format("CREATE TABLE %s (%s%s)",
                getTableName(clazz),
                createFieldsSql(clazz),
                createPrimaryKeySql(clazz)
        );
    }

    private String getTableName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase();
    }

    private String createFieldsSql(Class<?> clazz) {
        List<String> columns = extractColumns(clazz);

        return String.join(", ", columns);
    }

    private List<String> extractColumns(Class<?> clazz) {
        List<String> columns = new ArrayList<>();

        for (Field declaredField : clazz.getDeclaredFields()) {
            columns.add(getColumn(declaredField));
        }
        return columns;
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
        return field.getName() + " " + getColumnType(field);
    }

    private String getColumnType(Field field) {
        switch (field.getType().getSimpleName()) {
            case "Long":
                return "BIGINT";
            case "Integer":
                return "INT";
            default:
                return "VARCHAR(255)";
        }
    }

}
