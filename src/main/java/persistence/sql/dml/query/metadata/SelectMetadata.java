package persistence.sql.dml.query.metadata;

import jakarta.persistence.Transient;

import java.util.Arrays;
import java.util.List;

import static persistence.validator.AnnotationValidator.isNotPresent;

public record SelectMetadata(TableName tableName,
                             List<ColumnName> columnNames,
                             List<WhereCondition> whereConditions) {

    public SelectMetadata(Class<?> clazz, List<WhereCondition> whereConditions) {
        this(
                new TableName(clazz),
                Arrays.stream(clazz.getDeclaredFields())
                        .filter(field -> isNotPresent(field, Transient.class))
                        .map(ColumnName::new)
                        .toList(),
                whereConditions
        );
    }

}
