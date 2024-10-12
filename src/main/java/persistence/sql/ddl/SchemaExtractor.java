package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class SchemaExtractor {

    static class EntityValidator {
        static void validateEntity(Class<?> clazz) {
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

    public EntityInfo extract(Class<?> clazz) {
        EntityValidator.validateEntity(clazz);

        String tableName = clazz.getSimpleName().toLowerCase();
        FieldInfo[] fields =
                Arrays.stream(clazz.getDeclaredFields())
                        .map(FieldInfo::new)
                        .toArray(FieldInfo[]::new);

        return new EntityInfo(tableName, fields);
    }
}
