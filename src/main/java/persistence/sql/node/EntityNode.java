package persistence.sql.node;

import jakarta.persistence.Entity;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 엔티티 노드 정보
 *
 * @param entityClass 엔티티 클래스
 * @param fields 엔티티 필드 노드 목록
 * @param <T> 엔티티 클래스 타입
 */
public record EntityNode<T>(Class<T> entityClass, List<FieldNode> fields) implements SQLNode {

    public static EntityNode<?> from(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class " + entityClass.getName() + " is not annotated with @Entity");
        }

        List<FieldNode> fields = Arrays.stream(entityClass.getDeclaredFields()).map(FieldNode::from).toList();

        return new EntityNode<>(entityClass, fields);
    }

    @Override
    public List<FieldNode> fields() {
        return Collections.unmodifiableList(fields);
    }

    @SafeVarargs
    public final List<FieldNode> getFieldsWithoutExcludeAnnotations(Class<? extends Annotation>... excludeAnnotations) {
        return fields.stream()
                .filter(fieldNode -> !fieldNode.containsAnnotations(excludeAnnotations)).toList();
    }

    public FieldNode getIdField() {
        return fields.stream()
                .filter(FieldNode::isPrimaryKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Primary key not found"));
    }
}
