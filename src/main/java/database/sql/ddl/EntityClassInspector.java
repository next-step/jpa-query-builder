package database.sql.ddl;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

public class EntityClassInspector {
    private final Class<?> entityClass;

    public EntityClassInspector(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String getTableName() {
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name();
        } else {
            return entityClass.getSimpleName();
        }
    }

    public Stream<EntityColumn> getVisibleColumns(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(this::notTransientField)
                .map(this::getEntityColumn);
    }

    private boolean notTransientField(Field field) {
        return field.getAnnotation(Transient.class) == null;
    }

    private EntityColumn getEntityColumn(Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        Id idAnnotation = field.getAnnotation(Id.class);
        GeneratedValue GeneratedValueAnnotation = field.getAnnotation(GeneratedValue.class);
        EntityFieldTypeConverter entityFieldTypeConverter = new MysqlEntityFieldTypeConverter();

        return new EntityColumn(
                columnName(columnAnnotation, field.getName()),
                field.getType(),
                columnLength(columnAnnotation, 255),
                idAnnotation != null,
                isAutoIncrement(GeneratedValueAnnotation, false),
                isNullable(columnAnnotation, true),
                entityFieldTypeConverter);
    }

    private String columnName(Column columnAnnotation, String defaultName) {
        if (columnAnnotation != null && !columnAnnotation.name().isEmpty()) {
            return columnAnnotation.name();
        } else {
            return defaultName;
        }
    }

    private static Integer columnLength(Column columnAnnotation, Integer defaultValue) {
        if (columnAnnotation != null) {
            return columnAnnotation.length();
        } else {
            return defaultValue;
        }
    }

    private boolean isAutoIncrement(GeneratedValue generatedValueAnnotation, boolean defaultValue) {
        if (generatedValueAnnotation != null && generatedValueAnnotation.strategy() == GenerationType.IDENTITY) {
            return true;
        } else {
            return defaultValue;
        }
    }

    private boolean isNullable(Column columnAnnotation, boolean defaultValue) {
        if (columnAnnotation != null) {
            return columnAnnotation.nullable();
        } else {
            return defaultValue;
        }
    }
}
