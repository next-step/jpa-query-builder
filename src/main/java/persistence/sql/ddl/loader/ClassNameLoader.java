package persistence.sql.ddl.loader;

import jakarta.persistence.Entity;
import persistence.sql.ddl.ClassComponentType;
import persistence.sql.ddl.dto.javaclass.ClassName;

import java.util.List;

public class ClassNameLoader implements ClassComponentLoader<ClassName> {

    @Override
    public List<ClassName> invoke(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Entity.class)) {
            return List.of(new ClassName(clazz.getSimpleName()));
        }
        return List.of();
    }

    @Override
    public ClassComponentType type() {
        return ClassComponentType.CLASS_NAME;
    }
}