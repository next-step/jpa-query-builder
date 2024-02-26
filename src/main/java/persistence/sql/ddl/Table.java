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
    private final persistence.sql.ddl.Id id; // TODO: (질문) 매번 패키명을 적어야하는데 네이밍을 바꾸는게 나을까요?
    private final Columns columns;

    public Table(Class<?> entity) {
        this.name = getTableName(entity);
        this.id = extractIdFrom(entity);
        this.columns = extractColumnsFrom(entity);
    }

    private static Columns extractColumnsFrom(Class<?> entity) {
        List<Field> fields = Arrays.stream(entity.getDeclaredFields())
                .filter(x -> !x.isAnnotationPresent(jakarta.persistence.Id.class))
                .toList();

        return new Columns(fields);
    }

    private static persistence.sql.ddl.Id extractIdFrom(Class<?> entity) {
        return Stream.of(entity.getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(Id.class))
                .findFirst()
                .map(persistence.sql.ddl.Id::new)
                .orElseThrow(NotIdException::new);
    }

    public String getName() {
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

    public String getIdCreateQuery() {
        return id.getQuery();
    }

    public String getIdName() {
        return id.getName();
    }

    public List<String> getColumnQueries() {
        return columns.getQueries();
    }

    public List<String> columnNames() {
        return columns.getNames();
    }
}
