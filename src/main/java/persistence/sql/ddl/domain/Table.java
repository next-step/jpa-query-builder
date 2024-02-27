package persistence.sql.ddl.domain;

import jakarta.persistence.Entity;

public class Table {

    private final String name;

    public Table(Class<?> clazz) {
        validate(clazz);
        this.name = generateTableName(clazz);
    }

    private void validate(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Does not have an @Entity annotation.");
        }
    }

    private String generateTableName(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(jakarta.persistence.Table.class)) {
            return clazz.getSimpleName();
        }

        jakarta.persistence.Table table = clazz.getAnnotation(jakarta.persistence.Table.class);
        if (table.name().isEmpty()) {
            return clazz.getSimpleName();
        }
        return table.name();
    }

    public String getName() {
        return name;
    }
}
