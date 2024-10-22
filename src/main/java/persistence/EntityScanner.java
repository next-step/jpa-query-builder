package persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.NameUtils;
import persistence.sql.ddl.create.CreateQueryBuilder;
import persistence.sql.ddl.create.component.column.ColumnComponentBuilder;
import persistence.sql.ddl.create.component.constraint.ConstraintComponentBuilder;
import persistence.sql.ddl.drop.DropQueryBuilder;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityScanner {
    private static final Logger logger = LoggerFactory.getLogger(EntityScanner.class);

    private final Set<Class<?>> entityClasses = new HashSet<>();

    public void scan(String basePackage) {
        List<Class<?>> classes;
        try {
            classes = findClassesInPackage(basePackage);
        } catch (ClassNotFoundException e) {
            logger.error("Error while scanning entity!");
            throw new RuntimeException(e);
        }

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

    public List<String> getDdlDropQueries() {
        return this.entityClasses.stream()
                .map(this::generateDdlDropQuery)
                .collect(Collectors.toList());
    }

    private String generateDdlCreateQuery(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        CreateQueryBuilder queryBuilder = CreateQueryBuilder.newInstance();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            queryBuilder.add(ColumnComponentBuilder.from(field));
            queryBuilder.add(ConstraintComponentBuilder.from(field));
        }
        return queryBuilder.build(NameUtils.getTableName(entityClass));
    }

    private String generateDdlDropQuery(Class<?> entityClass) {
        DropQueryBuilder dropQueryBuilder = DropQueryBuilder.newInstance();
        return dropQueryBuilder.build(NameUtils.getTableName(entityClass));
    }
}
