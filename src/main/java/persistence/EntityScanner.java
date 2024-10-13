package persistence;

import jakarta.persistence.Entity;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import persistence.sql.ddl.component.DdlQueryBuilder;
import persistence.sql.ddl.component.column.ColumnComponentBuilder;
import persistence.sql.ddl.component.constraint.ConstraintComponentBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityScanner {

    private Set<Class<?>> entityClasses;

    public void scan(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        this.entityClasses = reflections.get(
                Scanners.TypesAnnotated.with(Entity.class).asClass()
        );
    }

    public List<String> getDdlQueries() {
        return this.entityClasses.stream()
                .map(this::generateDdlQuery)
                .collect(Collectors.toList());
    }

    private String generateDdlQuery(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        DdlQueryBuilder queryBuilder = DdlQueryBuilder.newInstance();
        for (Field field : fields) {
            queryBuilder.add(ColumnComponentBuilder.of(field));
        }
        for (Field field : fields) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                queryBuilder.add(ConstraintComponentBuilder.from(field, annotation));
            }
        }
        return queryBuilder.build(entityClass.getSimpleName());
    }
}
