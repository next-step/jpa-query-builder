package persistence.sql.ddl;


public class Table {
    private final String name;

    public Table(Class<?> entity) {
        this.name = getTableName(entity);
    }
    public String getName() {
        return name;
    }
    private String getTableName(Class<?> entity) {
        if (!hasTableAnnotation(entity)) {
            return entity.getSimpleName();
        }
        if (entity.getAnnotation(jakarta.persistence.Table.class).name().isEmpty()) {
            return entity.getSimpleName();
        }
        return entity.getAnnotation(jakarta.persistence.Table.class).name();
    }

    private static boolean hasTableAnnotation(Class<?> entity) {
        return entity.isAnnotationPresent(jakarta.persistence.Table.class);
    }
}
