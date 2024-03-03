package persistence.sql.dml;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.QueryBuilder;
import persistence.sql.dialect.Dialect;

import java.util.Arrays;
import java.util.stream.Collectors;

public class InsertQueryBuilder extends QueryBuilder {

    public InsertQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    @Override
    public String generateQuery(Class<?> clazz) {
        return String.format("INSERT INTO %s (%s) VALUES (%s)", generateTableName(clazz), columnsClause(clazz), valueClause(clazz));
    }

    private String columnsClause(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .map(this::generateColumnName)
                .collect(Collectors.joining(", "));
    }

    private String valueClause(Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> generateColumnValue(field, object))
                .collect(Collectors.joining(", "));
    }
}
