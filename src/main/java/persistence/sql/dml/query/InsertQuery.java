package persistence.sql.dml.query;

import static persistence.validator.AnnotationValidator.isNotPresent;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.util.Arrays;
import java.util.List;
import persistence.sql.metadata.TableName;


public record InsertQuery(TableName tableName,
                          List<ColumnNameValue> columns) {
    public InsertQuery(Object object) {
        this(
                new TableName(object.getClass()),
                Arrays.stream(object.getClass().getDeclaredFields())
                        .filter(field -> isNotPresent(field, Transient.class))
                        .filter(field -> isNotPresent(field, Id.class))
                        .map(field -> new ColumnNameValue(object, field))
                        .toList()
        );
    }

}
