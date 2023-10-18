package persistence.sql.ddl;

public class Table {
    private String name;

    public Table(Class<?> clazz) {
        this.name = findTableName(clazz);
    }

    public String getName() {
        return name;
    }

    private String findTableName(Class<?> clazz) {
        if(clazz.isAnnotationPresent(jakarta.persistence.Table.class)) {
            return clazz.getDeclaredAnnotation(jakarta.persistence.Table.class).name();
        }

        return clazz.getSimpleName();
    }
}
