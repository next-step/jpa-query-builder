package persistence.sql.dml;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class InsertQueryBuilder {
        public String insert(Object entity) {
            Class<?> entityClass = entity.getClass();
            if (!entityClass.isAnnotationPresent(Entity.class)) {
                throw new IllegalArgumentException("This Class is not an Entity ");
            }

            String tableName = getTableName(entityClass);
            String columns = columnsClause(entityClass);
            String values = valueClause(entity);

            return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ");";
        }

        private String getTableName(Class<?> field) {
            if (field.isAnnotationPresent(Table.class)) {
                Table table = field.getAnnotation(Table.class);
                return !table.name().isEmpty() ? table.name() : field.getSimpleName();
            }
            return field.getSimpleName();
        }

        private String columnsClause(Class<?> field) {
            return Arrays.stream(field.getDeclaredFields())
                    .filter(c -> !c.isAnnotationPresent(Id.class) || !c.isAnnotationPresent(GeneratedValue.class))
                    .filter(c -> !c.isAnnotationPresent(Transient.class)).map(this::getColumnName).collect(Collectors.joining(", "));
        }

        private String valueClause(Object entity) {
            return Arrays.stream(entity.getClass().getDeclaredFields())
                    .filter(field -> !field.isAnnotationPresent(Id.class) || !field.isAnnotationPresent(GeneratedValue.class))
                    .filter(field -> !field.isAnnotationPresent(Transient.class)).map(field -> {
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    if (value instanceof String) {
                        return "'" + value + "'";
                    }
                    return value != null ? value.toString() : "NULL";
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.joining(", "));
        }

        private String getColumnName(Field field) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                if (!column.name().isEmpty()) {
                    return column.name();
                }
            }
            return field.getName();
        }
}