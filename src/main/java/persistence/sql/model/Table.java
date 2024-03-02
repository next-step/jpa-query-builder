package persistence.sql.model;

public class Table {

    public String name(Class<?> clz) {
        final jakarta.persistence.Table tableAnnotation = clz.getAnnotation(jakarta.persistence.Table.class);

        if (existTableNameProperty(tableAnnotation)) {
            return tableAnnotation.name().toLowerCase();
        }

        return clz.getSimpleName().toLowerCase();
    }

    private static boolean existTableNameProperty(jakarta.persistence.Table tableAnnotation) {
        return tableAnnotation != null && !tableAnnotation.name().isEmpty();
    }

}
