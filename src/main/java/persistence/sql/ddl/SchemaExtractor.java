package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class SchemaExtractor {

    static class EntityClassValidator {
        static void validate(Class<?> clazz) {
            if (!clazz.isAnnotationPresent(Entity.class)) {
                throw new IllegalArgumentException("Entity must be annotated with @Entity");
            }

            validateHasId(clazz.getDeclaredFields());
        }

        static void validateHasId(Field[] fields) {
            List<Field> idFields = Arrays.stream(fields)
                    .filter(field ->
                            field.isAnnotationPresent(Id.class)
                    ).toList();

            if (idFields.size() != 1) {
                throw new IllegalArgumentException("Entity must have exactly one field annotated with @Id");
            }
        }
    }

    static class TableNameExtractor {
        static String extract(Class<?> clazz) {
            String tableName = clazz.getSimpleName();
            if (clazz.isAnnotationPresent(Entity.class)) {
                Entity entityAnnotation = clazz.getAnnotation(Entity.class);
                if (!entityAnnotation.name().isEmpty()) {
                    tableName = entityAnnotation.name();
                }
            }

            if (clazz.isAnnotationPresent(Table.class)) {
                Table tableAnnotation = clazz.getAnnotation(Table.class);
                if (!tableAnnotation.name().isEmpty()) {
                    tableName = tableAnnotation.name();
                }
            }

            return tableName;
        }
    }

    public TableInfo extract(Class<?> clazz) {
        EntityClassValidator.validate(clazz);

        String tableName = TableNameExtractor.extract(clazz);
        List<TableColumn> columns =
                Arrays.stream(clazz.getDeclaredFields())
                        .map(TableColumn::new)
                        .toList();

        return new TableInfo(tableName, columns);
    }
}
