package persistence.sql.dml.query;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.metadata.FieldValue;
import persistence.metadata.TableName;

import java.util.Arrays;
import java.util.List;

import static common.AnnotationValidation.isNotPresent;

public record InsertQuery(TableName tableName,
                          List<FieldValue> columns) {
    public InsertQuery(Object object) {
        this(
                new TableName(object.getClass()),
                Arrays.stream(object.getClass().getDeclaredFields())
                        .filter(field -> isNotPresent(field, Transient.class))
                        .filter(field -> isNotPresent(field, Id.class))
                        .map(field -> new FieldValue(object, field))
                        .toList()
        );
    }

}
