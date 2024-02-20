package persistence.sql.mapping;

import jakarta.persistence.Entity;
import persistence.sql.QueryException;

import java.util.Arrays;

public class TableBinder {

    final ColumnTypeMapper columnTypeMapper;

    public TableBinder(ColumnTypeMapper columnTypeMapper) {
        this.columnTypeMapper = columnTypeMapper;
    }

    public Table createTable(final Class<?> clazz) {
        final Table table = new Table(toTableName(clazz));
        Arrays.stream(clazz.getDeclaredFields())
                .forEach(field -> table.addColumn(field, columnTypeMapper));

        return table;
    }

    private String toTableName(final Class<?> clazz) {
        validationEntityClazz(clazz);

        final jakarta.persistence.Table tableAnnotation = clazz.getAnnotation(jakarta.persistence.Table.class);

        if (tableAnnotation == null || tableAnnotation.name().isBlank()) {
            return clazz.getSimpleName();
        }

        return tableAnnotation.name();
    }

    private void validationEntityClazz(final Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new QueryException(clazz.getSimpleName() + " is not entity");
        }
    }

}
