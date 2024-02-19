package persistence.sql.ddl;

import jakarta.persistence.*;
import persistence.sql.QueryException;
import persistence.sql.query.Column;
import persistence.sql.query.Query;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public interface DdlQueryBuilder {

    String EMPTY = "";

    String SPACE = "    ";

    default Query generateQuery(final Class<?> clazz) {
        final persistence.sql.query.Table table = new persistence.sql.query.Table(createTableName(clazz));

        final List<Column> columns = Arrays.stream(clazz.getDeclaredFields())
                .map(this::createColumn)
                .filter(Objects::nonNull)
                .toList();

        return new Query(table, columns);
    }

    private String createTableName(final Class<?> clazz) {
        validationEntityClazz(clazz);

        final Table tableAnnotation = clazz.getAnnotation(Table.class);

        if (tableAnnotation == null || tableAnnotation.name().isBlank()) return clazz.getSimpleName();

        return tableAnnotation.name();
    }

    private void validationEntityClazz(final Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) throw new QueryException(clazz.getSimpleName() + " is not entity");
    }

    private Column createColumn(final Field field) {
        final boolean isTransient = field.isAnnotationPresent(Transient.class);
        if (isTransient) return null;

        final Column.Builder builder = new Column.Builder(field);

        final boolean isPk = field.isAnnotationPresent(Id.class);

        if (isPk) {
            builder.setPk(true)
                    .setPkStrategy(field.getAnnotation(GeneratedValue.class));
        }

        return builder.build();
    }

    String buildCreateQuery(final Query query);

    String buildDropQuery(final Query query);

}
