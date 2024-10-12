package persistence.sql.ddl.node;

import jakarta.persistence.Entity;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EntityNode<T> implements SQLNode {
    private final Class<T> entityClass;
    private final List<FieldNode> fields;

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public EntityNode(Class<T> entityClass, List<FieldNode> fields) {
        this.entityClass = entityClass;
        this.fields = fields;
    }

    public static EntityNode<?> from(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class " + entityClass.getName() + " is not annotated with @Entity");
        }

        List<FieldNode> fields = Arrays.stream(entityClass.getDeclaredFields()).map(FieldNode::from).toList();

        return new EntityNode<>(entityClass, fields);
    }

    public List<FieldNode> getFields() {
        return Collections.unmodifiableList(fields);
    }

    @SafeVarargs
    public final List<FieldNode> getFields(Class<? extends Annotation>... excludeAnnotations) {
        return fields.stream()
                .filter(fieldNode -> !fieldNode.containsAnnotations(excludeAnnotations)).toList();
    }

    public boolean existsConstraint() {
        // TODO: Implement this method
        return false;
    }
}
