package persistence.sql.model;

public class Table {

    private final Class<?> clz;

    public Table(Class<?> clz) {
        this.clz = clz;
    }

    public String name() {
        final jakarta.persistence.Table tableAnnotation = clz.getAnnotation(jakarta.persistence.Table.class);

        if (existTableNameProperty(tableAnnotation)) {
            return tableAnnotation.name().toLowerCase();
        }

        return clz.getSimpleName().toLowerCase();
    }

    private boolean existTableNameProperty(jakarta.persistence.Table tableAnnotation) {
        return tableAnnotation != null && !tableAnnotation.name().isEmpty();
    }

}
