package persistence;

import jakarta.persistence.Entity;
import persistence.sql.ddl.create.component.DdlCreateQueryBuilder;
import persistence.sql.ddl.create.component.column.ColumnComponentBuilder;
import persistence.sql.ddl.create.component.constraint.ConstraintComponentBuilder;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityScanner {

    private final Set<Class<?>> entityClasses = new HashSet<>();

    public void scan(String basePackage) throws ClassNotFoundException {
        List<Class<?>> classes = findClassesInPackage(basePackage);

        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Entity.class)) {
                this.entityClasses.add(clazz);
            }
        }
    }

    private List<Class<?>> findClassesInPackage(String basePackage) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        String path = basePackage.replace('.', '/');

        URL resource = Thread.currentThread().getContextClassLoader().getResource(path);
        if (resource == null) {
            throw new IllegalArgumentException("Package not found! : " + basePackage);
        }

        File directory = new File(resource.getFile());
        if (!directory.exists()) {
            return Collections.emptyList();
        }
        for (String file : directory.list()) {
            if (file.endsWith(".class")) {
                String className = basePackage + "." + file.substring(0, file.length() - 6);
                classes.add(Class.forName(className));
            }
        }

        return classes;
    }

    public List<String> getDdlCreateQueries() {
        return this.entityClasses.stream()
                .map(this::generateDdlCreateQuery)
                .collect(Collectors.toList());
    }

    private String generateDdlCreateQuery(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        DdlCreateQueryBuilder queryBuilder = DdlCreateQueryBuilder.newInstance();
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
