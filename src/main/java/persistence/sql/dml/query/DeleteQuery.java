package persistence.sql.dml.query;

import persistence.sql.metadata.TableName;

public record DeleteQuery(String tableName) {

    public DeleteQuery(TableName tableName) {
        this(tableName.value());
    }

}
