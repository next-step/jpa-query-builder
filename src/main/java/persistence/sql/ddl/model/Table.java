package persistence.sql.ddl.model;

public class Table {

    private static final String CREATE_TABLE_PREFIX = "CREATE TABLE ";
    private static final String DROP_TABLE_PREFIX = "DROP TABLE ";
    private static final String CLOSE_QUERY = ";";

    public String create(Class<?> clz) {
        return query(clz, CREATE_TABLE_PREFIX);
    }

    public String drop(Class<?> clz) {
        return query(clz, DROP_TABLE_PREFIX) + CLOSE_QUERY;
    }

    private String query(Class<?> clz, String prefix) {
        final jakarta.persistence.Table tableAnnotation = clz.getAnnotation(jakarta.persistence.Table.class);

        if (existTableNameProperty(tableAnnotation)) {
            return prefix + tableAnnotation.name();
        }

        return prefix + clz.getSimpleName().toLowerCase();
    }

    private static boolean existTableNameProperty(jakarta.persistence.Table tableAnnotation) {
        return tableAnnotation != null && !tableAnnotation.name().isEmpty();
    }

}
