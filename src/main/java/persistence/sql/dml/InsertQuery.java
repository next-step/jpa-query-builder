package persistence.sql.dml;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Transient;
import persistence.sql.exception.ExceptionMessage;
import persistence.sql.exception.RequiredObjectException;
import persistence.sql.model.EntityColumnName;
import persistence.sql.model.EntityColumnValue;
import persistence.sql.model.TableName;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class InsertQuery {

    private final Object object;

    public InsertQuery(Object object) {
        if (object == null) {
            throw new RequiredObjectException(ExceptionMessage.REQUIRED_OBJECT);
        }
        this.object = object;
    }

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
                .map(field -> getValueInClause(object, field))
                .collect(Collectors.joining(", "));
    }

    private String getValueInClause(Object object, Field field) {
        EntityColumnValue entityColumnValue = new EntityColumnValue(field, object);
        return entityColumnValue.getValueInClause();
    }
}
