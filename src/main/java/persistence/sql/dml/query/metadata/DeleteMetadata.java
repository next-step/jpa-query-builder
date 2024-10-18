package persistence.sql.dml.query.metadata;

import java.util.List;

public record DeleteMetadata(TableName tableName,
                             List<WhereCondition> whereConditions) {

    public DeleteMetadata(Class<?> clazz) {
        this(new TableName(clazz), List.of());
    }

}
