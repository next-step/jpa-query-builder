package persistence.sql.ddl;


import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import persistence.sql.ddl.column.ColumnClauses;
import persistence.sql.exception.NotIdException;

public class TableClause {
    private final String name;
    private final PrimaryKeyClause primaryKeyClause;
    private final ColumnClauses columnClauses;

    public TableClause(Class<?> entity) {
        this.name = getTableName(entity);
        this.primaryKeyClause = extractIdFrom(entity);
        this.columnClauses = extractColumnsFrom(entity);
    }

    private static ColumnClauses extractColumnsFrom(Class<?> entity) {
        List<Field> fields = Arrays.stream(entity.getDeclaredFields())
                .filter(x -> !x.isAnnotationPresent(jakarta.persistence.Id.class))
                .toList();

        return new ColumnClauses(fields);
    }

    private static PrimaryKeyClause extractIdFrom(Class<?> entity) {
        return Stream.of(entity.getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(Id.class))
                .findFirst()
                .map(PrimaryKeyClause::new)
                .orElseThrow(NotIdException::new);
    }

    public String name() {
        return name;
    }

    private String getTableName(Class<?> entity) {
        if (!entity.isAnnotationPresent(jakarta.persistence.Table.class)) {
            return entity.getSimpleName();
        }
        if (entity.getAnnotation(jakarta.persistence.Table.class).name().isEmpty()) {
            return entity.getSimpleName();
        }
        return entity.getAnnotation(jakarta.persistence.Table.class).name();
    }

    public String createQuery() {
        return primaryKeyClause.getQuery();
    }

    public String primaryKeyName() {
        return primaryKeyClause.name();
    }

    public List<String> columnQueries() {
        return columnClauses.getQueries();
    }

    public List<String> columnNames() {
        return columnClauses.getNames();
    }
}
