package persistence.sql.model;

import java.util.List;

public class Table {

    private final String name;
    private final List<Column> columns;

    private Table(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
    }

    public static Table create(Class<?> clazz, List<Column> columns) {
        String tableName = getTableName(clazz);
        validatePrimaryKey(columns);
        return new Table(tableName, columns);
    }

    private static String getTableName(Class<?> clazz) {

        if (clazz.isAnnotationPresent(jakarta.persistence.Table.class) && !clazz.getDeclaredAnnotation(jakarta.persistence.Table.class).name().isEmpty()) {
            return clazz.getDeclaredAnnotation(jakarta.persistence.Table.class).name().toLowerCase();
        }
        return clazz.getSimpleName().toLowerCase();
    }

    private static void validatePrimaryKey(List<Column> columns) {
        columns.stream()
            .filter(column -> column.getPrimaryKeyConstraint().getConstraintStatus())
            .findAny()
            .orElseThrow(PrimaryKeyNotFoundException::new);
    }

    public String getName() {
        return this.name;
    }

    public List<Column> getColumns() {
        return this.columns;
    }

}
