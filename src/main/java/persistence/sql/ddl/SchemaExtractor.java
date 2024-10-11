package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class SchemaExtractor {

    static class EntityValidator {
        static void validateEntity(Object entity) {
            if (entity == null) {
                throw new IllegalArgumentException("Entity cannot be null");
            }

            if (!entity.getClass().isAnnotationPresent(Entity.class)) {
                throw new IllegalArgumentException("Entity must be annotated with @Entity");
            }

            validateHasIdField(entity.getClass().getDeclaredFields());
        }

        static void validateHasIdField(Field[] fields) {
            List<Field> idFields = Arrays.stream(fields)
                    .filter(field ->
                            field.isAnnotationPresent(Id.class)
                    ).toList();

            if (idFields.size() != 1) {
                throw new IllegalArgumentException("Entity must have exactly one field annotated with @Id");
            }
        }
    }

    public EntityInfo extract(Object entity) {
        EntityValidator.validateEntity(entity);

        String tableName = entity.getClass().getSimpleName().toLowerCase();
        FieldInfo[] fields =
                Arrays.stream(entity.getClass().getDeclaredFields())
                        .map(field ->
                                new FieldInfo(
                                        field.getName(),
                                        field.getType().getSimpleName(),
                                        field.isAnnotationPresent(Id.class)
                                )
                        )
                        .toArray(FieldInfo[]::new);

        return new EntityInfo(tableName, fields);
    }
}
