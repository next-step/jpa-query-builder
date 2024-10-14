package persistence.sql.ddl.mapping;

import jakarta.persistence.Entity;

public class Table {

    private final String name;

    public static Table from(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("@Entity not exist. class = " + clazz.getName());
        }
        
        if (clazz.isAnnotationPresent(jakarta.persistence.Table.class)) {
            jakarta.persistence.Table annotation = clazz.getAnnotation(jakarta.persistence.Table.class);
            return new Table(annotation.name());
        }

        Entity annotation = clazz.getAnnotation(Entity.class);
        return new Table(annotation.name());
    }

    public Table(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }
    
}
