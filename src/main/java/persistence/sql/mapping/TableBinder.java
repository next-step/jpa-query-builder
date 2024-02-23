package persistence.sql.mapping;

import jakarta.persistence.Entity;
import persistence.sql.QueryException;

public class TableBinder {

    public Table createTable(final Class<?> clazz) {

        return new Table(toTableName(clazz));
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
