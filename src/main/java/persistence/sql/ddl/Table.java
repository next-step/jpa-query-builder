package persistence.sql.ddl;


import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import persistence.sql.ddl.column.Columns;
import persistence.sql.exception.NotIdException;

public class Table {
    private final String name;
    private final PrimaryKey primaryKey;
    private final Columns columns;

    public Table(Class<?> entity) {
        this.name = getTableName(entity);
        this.primaryKey = extractIdFrom(entity);
        this.columns = extractColumnsFrom(entity);
    }

    private static Columns extractColumnsFrom(Class<?> entity) {
        List<Field> fields = Arrays.stream(entity.getDeclaredFields())
                .filter(x -> !x.isAnnotationPresent(jakarta.persistence.Id.class))
                .toList();

        return new Columns(fields);
    }

    private static PrimaryKey extractIdFrom(Class<?> entity) {
        return Stream.of(entity.getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(Id.class))
                .findFirst()
                .map(PrimaryKey::new)
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
        return primaryKey.getQuery();
    }

    public String primaryKeyName() {
        return primaryKey.name();
    }

    public List<String> columnQueries() {
        return columns.getQueries();
    }

    public List<String> columnNames() {
        return columns.getNames();
    }
}
