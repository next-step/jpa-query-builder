package persistence.sql.ddl;


import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import persistence.sql.ddl.column.ColumnClauses;
import persistence.sql.exception.NotIdException;

public class TableClause {
    private final String name;
    private final PrimaryKeyClause primaryKeyClause;
    private final ColumnClauses columnClauses;
    private final Object instanceOfTable;

    public TableClause(Class<?> entity) {
        this.name = getTableName(entity);
        this.primaryKeyClause = extractIdFrom(entity);
        this.columnClauses = extractColumnsFrom(entity);
        this.instanceOfTable = getInstanceOfTable(entity);
    }

    private Object getInstanceOfTable(Class<?> entity) {
        try {
            return Arrays.stream(entity.getDeclaredConstructors())
                    .filter(x -> x.getParameterCount() == 0)
                    .findFirst().get().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e){
            throw new RuntimeException("새로운 인스턴스 생성에 실패하였습니다.");
        }
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

    public Object newInstance() {
        return this.instanceOfTable;
    }
}
