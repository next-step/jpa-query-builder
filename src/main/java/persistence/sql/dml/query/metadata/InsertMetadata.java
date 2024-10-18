package persistence.sql.dml.query.metadata;

import jakarta.persistence.Transient;

import java.util.Arrays;
import java.util.List;

import static persistence.validator.AnnotationValidator.isNotPresent;


public record InsertMetadata(TableName tableName,
                             List<ColumnName> columnNames) {
    public InsertMetadata(Class<?> clazz) {
        this(
                new TableName(clazz),
                Arrays.stream(clazz.getDeclaredFields())
                        .filter(field -> isNotPresent(field, Transient.class))
                        .map(ColumnName::new)
                        .toList()
        );
    }

}
