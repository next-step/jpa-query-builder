package persistence.sql.ddl.node;

import jakarta.persistence.Entity;

import java.util.Arrays;
import java.util.List;

public class EntityNode<T> implements SQLNode {
    private Class<T> entityClass;
    private List<FieldNode> fields;

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
}
