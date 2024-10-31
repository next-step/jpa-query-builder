package persistence.sql.dml.query;

import persistence.metadata.TableName;

public record DeleteQuery(TableName tableName) {
    public DeleteQuery(Class<?> clazz) {
        this(new TableName(clazz));
    }
}
