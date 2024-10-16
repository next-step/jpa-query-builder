package persistence.sql.ddl.mapping;

import jakarta.persistence.Entity;
import persistence.sql.ddl.exception.NotExistException;

public class Table {

    private final String name;

    public static Table from(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new NotExistException("@Entity annotation. class = " + clazz.getName());
        }
        
        if (clazz.isAnnotationPresent(jakarta.persistence.Table.class)) {
            jakarta.persistence.Table annotation = clazz.getAnnotation(jakarta.persistence.Table.class);
            return new Table(annotation.name());
        }

        Entity annotation = clazz.getAnnotation(Entity.class);
        if (!annotation.name().isEmpty()) {
            return new Table(annotation.name());
        }

        return new Table(clazz.getSimpleName());
    }

    public Table(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }
    
}
