package persistence.sql.ddl.impl;

import jakarta.persistence.Entity;
import org.reflections.Reflections;
import persistence.sql.ddl.TableScanner;
import persistence.sql.node.EntityNode;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 애노테이션 기반 테이블 스캐너
 */
public class AnnotatedTableScanner implements TableScanner {
    private Reflections reflections;

    @Override
    @SuppressWarnings("unchecked")
    public Set<EntityNode<?>> scan(String basePackage) {
        reflections = new Reflections(basePackage);

        return getTypesAnnotatedWith(Entity.class).stream()
                .map(EntityNode::from)
                .collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
        Set<Class<?>> beans = new HashSet<>();
        for (Class<? extends Annotation> annotation : annotations) {
            beans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return beans;
    }
}
