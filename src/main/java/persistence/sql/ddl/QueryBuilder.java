package persistence.sql.ddl;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {
    Class<?> clazz;
    SqlTypeMapper sqlTypeMapper;

    public QueryBuilder(Class<?> clazz, SqlTypeMapper sqlTypeMapper) {
        this.clazz = clazz;
        this.sqlTypeMapper = sqlTypeMapper;
    }

    public String builder() {
        return "CREATE TABLE " + getTableName() + "(" + getPersonMetadata() + ")";
    }

    public String dropper() {
        return "DROP TABLE " + getTableName() + " IF EXISTS";
    }

    @NotNull
    private String getTableName() {
        if (clazz.isAnnotationPresent(Table.class)) {
            return clazz.getAnnotation(Table.class).name();
        }
        return clazz.getSimpleName();
    }


    public String getPersonMetadata() {
        StringBuilder query = new StringBuilder();
        List<String> columnArrayList = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Transient.class)) continue;

            String column = getColumnName(field) + " " + getSqlType(field) + getPrimaryKey(field) + getAutoIncrement(field) + getNullable(field);
            columnArrayList.add(column);
        }
        query.append(String.join(", ", columnArrayList));

        return query.toString();
    }

    private String getNullable(Field field) {
        if (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).nullable()) {
            return " NOT NULL";
        }
        return "";
    }

    private String getColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).name().isEmpty()) {
            return field.getAnnotation(Column.class).name();
        }
        return field.getName();
    }

    private static String getAutoIncrement(Field field) {
        if (field.isAnnotationPresent(GeneratedValue.class) == true) {
            return " AUTO_INCREMENT";
        }
        return "";
    }

    private static String getPrimaryKey(Field field) {
        if (field.isAnnotationPresent(Id.class) == true) {
            return " PRIMARY KEY";
        }
        return "";
    }

    @NotNull
    private static String getSqlType(Field field) {
        if ("Long".equals(field.getType().getSimpleName())) {
            return "BIGINT";
        } else if ("String".equals(field.getType().getSimpleName())) {
            return "VARCHAR(255)";
        }
        return field.getType().getSimpleName();
    }

}