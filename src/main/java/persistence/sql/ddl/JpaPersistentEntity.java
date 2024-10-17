package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdbc.JdbcTemplate;

public class JpaPersistentEntity {

    private final JdbcTemplate jdbcTemplate;

    public JpaPersistentEntity(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTables(List<Class<?>> entityClasses) {
        for (Class<?> entityClass : entityClasses) {
            createTable(entityClass);
        }
    }

    public void createTable(Class<?> entityClass) {
        isEntity(entityClass);

        String tableName = entityClass.getSimpleName();
        StringBuilder sql = new StringBuilder(
            "CREATE TABLE IF NOT EXISTS " +
                tableName +
                " (\n"
        );

        Field[] fields = entityClass.getDeclaredFields();
        Map<String, String> columnDefinitions = getColumnDefinitions(fields);

        for (Map.Entry<String, String> entry : columnDefinitions.entrySet()) {
            sql.append(entry.getKey())
                .append(" ")
                .append(entry.getValue())
                .append(",\n");
        }
        sql.setLength(sql.length() - 2);
        sql.append("\n);");

        System.out.println("Generated SQL: " + sql);
        jdbcTemplate.execute(sql.toString());
    }

    private void isEntity(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(
                "Provided class is not an entity: " +
                    entityClass.getName()
            );
        }
    }

    private Map<String, String> getColumnDefinitions(Field[] fields) {
        Map<String, String> columnDefinitions = new HashMap<>();

        for (Field field : fields) {
            field.setAccessible(true);

            String columnName;
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                columnName = column.name().isEmpty() ? field.getName() : column.name();
            } else {
                columnName = field.getName();
            }

            Class<?> fieldType = field.getType();
            String columnType = getSqlTypeForField(fieldType);

            if (field.isAnnotationPresent(Id.class)) {
                columnType += " PRIMARY KEY";

                if (field.isAnnotationPresent(GeneratedValue.class)) {
                    GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
                    if (generatedValue.strategy() == GenerationType.IDENTITY) {
                        columnType += " AUTO_INCREMENT";
                    }
                }
            }

            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                if (!column.nullable()) {
                    columnType += " NOT NULL";
                }
            }

            columnDefinitions.put(columnName, columnType);
        }
        return columnDefinitions;
    }

    private String getSqlTypeForField(Class<?> fieldType) {
        if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
            return "BIGINT";
        } else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
            return "INT";
        } else if (fieldType.equals(String.class)) {
            return "VARCHAR(255)";
        } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
            return "DOUBLE";
        } else if (fieldType.equals(Float.class) || fieldType.equals(float.class)) {
            return "FLOAT";
        }
        throw new IllegalArgumentException("Unsupported field type: " + fieldType.getName());
    }
}
