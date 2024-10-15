package persistence.sql.dml;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Transient;
import persistence.sql.model.EntityColumnName;
import persistence.sql.model.EntityColumnValue;
import persistence.sql.model.TableName;

import java.util.Arrays;
import java.util.stream.Collectors;

public class H2InsertQueryBuilder implements InsertQueryBuilder {

    private final Object object;

    public H2InsertQueryBuilder(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("object가 존재하지 않습니다.");
        }
        this.object = object;
    }

    @Override
    public String makeQuery() {
        TableName tableName = new TableName(object.getClass());

        String tableNameValue = tableName.getValue();
        String columnClause = columnsClause(object.getClass());
        String valueClause = valueClause(object);
        return String.format("INSERT INTO %s (%s) VALUES (%s)", tableNameValue, columnClause, valueClause);
    }

    private String columnsClause(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !field.isAnnotationPresent(GeneratedValue.class))
                .map(field -> new EntityColumnName(field).getValue())
                .collect(Collectors.joining(", "));
    }

    private String valueClause(Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !field.isAnnotationPresent(GeneratedValue.class))
                .map(field -> new EntityColumnValue(field, object).getValue())
                .collect(Collectors.joining(", "));
    }
}
