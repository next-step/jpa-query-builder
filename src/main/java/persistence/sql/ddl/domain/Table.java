package persistence.sql.ddl.domain;

public class Table {

    private final String name;

    public Table(Class<?> clazz) {
        this.name = generateTableName(clazz);
    }

    private String generateTableName(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(jakarta.persistence.Table.class)) {
            return clazz.getSimpleName().toUpperCase();
        }

        jakarta.persistence.Table table = clazz.getAnnotation(jakarta.persistence.Table.class);
        if (table.name().isEmpty()) {
            return clazz.getSimpleName().toUpperCase();
        }
        return table.name().toUpperCase();
    }

    public String getName() {
        return name;
    }
}
