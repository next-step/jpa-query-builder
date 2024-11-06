package persistence.sql.ddl;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QueryBuilder {
    Class<?> clazz;
    SqlTypeMapper sqlTypeMapper;

    public QueryBuilder(Class<?> clazz, SqlTypeMapper sqlTypeMapper) {
        this.clazz = clazz;
        this.sqlTypeMapper = sqlTypeMapper;
    }

    public String create() {
        return "CREATE TABLE " + getTableName() + "(" + getPersonMetadata() + ")";
    }

    public String drop() {
        return "DROP TABLE " + getTableName() + " IF EXISTS";
    }

    private String getTableName() {
        if (clazz.isAnnotationPresent(Table.class)) {
            return clazz.getAnnotation(Table.class).name();
        }
        return clazz.getSimpleName();
    }

    public String getPersonMetadata() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> getColumnName(field)
                        + " "
                        + sqlTypeMapper.getSqlType(field)
                        + getPrimaryKey(field)
                        + getAutoIncrement(field)
                        + getNullable(field)
                )
                .collect(Collectors.joining(", "));
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

    private String getPrimaryKey(Field field) {
        if (field.isAnnotationPresent(Id.class) == true) {
            return " PRIMARY KEY";
        }
        return "";
    }
}