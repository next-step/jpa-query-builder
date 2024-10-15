package persistence.sql.ddl;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryBuilder {
    private final FieldTypeMapper fieldTypeMapper = new FieldTypeMapper();
    private final FieldAnnotationMapper fieldAnnotationMapper = new FieldAnnotationMapper();

    public String create(Class<?> entity) {
        String createTableQuery = this.getCreateTableQuery(entity);
        String columnDefinitions = this.generateColumnDefinitions(entity);

        return "%s (%s);".formatted(createTableQuery, columnDefinitions);
    }

    public String drop(Class<?> entity) {
        String tableName = entity.isAnnotationPresent(Table.class) ? entity.getAnnotation(Table.class).name() : entity.getSimpleName();;
        return "DROP TABLE %s;".formatted(tableName);
    }

    private String getCreateTableQuery(Class<?> entity) {
        String tableName = entity.isAnnotationPresent(Table.class) ? entity.getAnnotation(Table.class).name() : entity.getSimpleName();;

        return "CREATE TABLE %s".formatted(tableName);
    }

    private String generateColumnDefinitions(Class<?> entity) {
        Field[] fields = entity.getDeclaredFields();
        List<String> columns = Arrays.stream(fields).filter(field -> !field.isAnnotationPresent(Transient.class)).map(field -> "%s %s".formatted(this.getColumnNameFromAnnotation(field).isEmpty() ? field.getName() : this.getColumnNameFromAnnotation(field), this.fieldTypeMapper.mapFieldTypeToSQLType(field) + this.fieldAnnotationMapper.mapFieldAnnotationToSQLType((field)))).toList();
        return String.join(", ", columns);
    }

    private String getColumnNameFromAnnotation(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return "";
        }
        Column column = field.getAnnotation(Column.class);
        return column.name();
    }
}
