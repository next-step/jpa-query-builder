package persistence.sql.dml;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.entity.EntityFields;
import persistence.sql.exception.ExceptionMessage;
import persistence.sql.exception.RequiredObjectException;
import persistence.sql.model.EntityColumnName;
import persistence.sql.model.EntityColumnValue;
import persistence.sql.model.TableName;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class UpdateQuery {

    private final Object object;

    public UpdateQuery(Object object) {
        if (object == null) {
            throw new RequiredObjectException(ExceptionMessage.REQUIRED_OBJECT);
        }
        this.object = object;
    }

    public String makeQuery() {
        TableName tableName = new TableName(object.getClass());

        String tableNameValue = tableName.getValue();
        return String.format("UPDATE %s SET %s WHERE %s", tableNameValue, makeSetClause(), makeWhereClause(object));
    }

    private String makeSetClause() {
        List<Field> fields = Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !field.isAnnotationPresent(GeneratedValue.class))
                .collect(Collectors.toList());

        List<String> setCaluses = new ArrayList<>();
        for (Field field : fields) {
            EntityColumnName entityColumnName = new EntityColumnName(field);
            EntityColumnValue entityColumnValue = new EntityColumnValue(field, object);
            setCaluses.add(String.format("%s = %s", entityColumnName.getValue(), entityColumnValue.getValueInClause()));
        }

        return String.join(",", setCaluses);
    }

    private String makeWhereClause(Object object) {
        EntityFields entityFields = new EntityFields(object.getClass());
        List<Field> fields = entityFields.getIdFields();

        List<String> whereQueries = new ArrayList<>();
        for (Field field : fields) {
            EntityColumnName entityColumnName = new EntityColumnName(field);
            EntityColumnValue entityColumnValue = new EntityColumnValue(field, object);
            whereQueries.add(String.format("%s = %s", entityColumnName.getValue(), entityColumnValue.getValueInClause()));
        }

        return whereQueries.stream().collect(Collectors.joining(","));
    }

}
