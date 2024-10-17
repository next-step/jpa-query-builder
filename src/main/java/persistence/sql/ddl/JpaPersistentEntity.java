package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

        String tableName = getTableName(entityClass);
        StringBuilder sql = new StringBuilder(
            "CREATE TABLE IF NOT EXISTS " +
                tableName +
                " (\n"
        );

        Map<String, String> columnDefinitions = getColumnDefinitions(entityClass);
        for (Entry<String, String> entry : columnDefinitions.entrySet()) {
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

    public void dropEntity(Class<?> entityClass) {
        isEntity(entityClass);
        String tableName = getTableName(entityClass);
        String sql = "DROP TABLE IF EXISTS " + tableName + ";";
        System.out.println("Generated SQL: " + sql);
        jdbcTemplate.execute(sql);
    }

    private void isEntity(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(
                "Provided class is not an entity: " +
                    entityClass.getName()
            );
        }
    }

    private String getTableName(Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            return table.name();
        } else {
            return entityClass.getSimpleName();
        }
    }


    private Map<String, String> getColumnDefinitions(Class<?> entityClass) {
        Map<String, String> columnDefinitions = new LinkedHashMap<>();
        Field[] fields = entityClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }

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
