package persistence.sql;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.core.Column;
import persistence.sql.core.Columns;
import persistence.sql.core.PrimaryKey;
import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.H2Dialect;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class QueryBuilder {

    public static final String DELIMITER = ", ";
    public static final String WHERE_CLAUSE_BLANK = "";
    public static final String WHERE_CLAUSE_TEMPLATE = "WHERE %s = %s";

    protected final Dialect DIALECT = new H2Dialect();

    public abstract String build();

    protected static List<persistence.sql.core.Column> createColumns(Class<?> clazz, Object entity) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> Objects.nonNull(entity) ? persistence.sql.core.Column.of(field, entity) : persistence.sql.core.Column.of(field)).toList();
    }

    protected static persistence.sql.core.Column generatePrimaryKey(Class<?> clazz) {
        return createColumns(clazz, null).stream()
                .filter(column -> column.getAnnotations().stream()
                        .anyMatch(annotation -> annotation.annotationType().equals(Id.class)))
                .findFirst()
                .orElse(null);
    }

    protected String whereClause(PrimaryKey primaryKey, Object id) {
        if (Objects.isNull(id)) {
            return WHERE_CLAUSE_BLANK;
        }

        return String.format(WHERE_CLAUSE_TEMPLATE, primaryKey.getName(), id);
    }

    protected String columnsClause(Columns columns) {
        return columns.getColumns().stream()
                .map(Column::getName)
                .collect(Collectors.joining(DELIMITER));
    }
}
