package persistence.sql.dml.query;

import static persistence.validator.AnnotationValidator.isNotPresent;

import jakarta.persistence.Transient;
import java.util.Arrays;
import java.util.List;
import persistence.sql.metadata.ColumnName;
import persistence.sql.metadata.TableName;


public record InsertQuery(TableName tableName,
                          List<ColumnName> columnNames) {
    public InsertQuery(Class<?> clazz) {
        this(
                new TableName(clazz),
                Arrays.stream(clazz.getDeclaredFields())
                        .filter(field -> isNotPresent(field, Transient.class))
                        .map(ColumnName::new)
                        .toList()
        );
    }

}
