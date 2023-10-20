package persistence.sql.entitymetadata.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class EntityValidatable<E> {
    protected Class<E> entityClass;
    protected List<Field> entityColumnFields;

    public EntityValidatable(Class<E> entityClass) {
        this.entityClass = entityClass;
        this.entityColumnFields = findOnlyColumnFields(entityClass);

        validate(entityClass);
    }

    private void validate(Class<E> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalStateException("Entity Class must be annotated with @Entity");
        }

        if (entityColumnFields.isEmpty()) {
            throw new IllegalStateException("Entity Class must have at least one column");
        }

        if (entityColumnFields.stream().noneMatch(field -> field.isAnnotationPresent(Id.class))) {
            throw new IllegalStateException("Entity Class must not have @Id columns");
        }
    }

    private List<Field> findOnlyColumnFields(Class<E> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .collect(Collectors.toList());
    }
}
