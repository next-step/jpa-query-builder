package persistence.sql.ddl.query;

import persistence.sql.metadata.TableName;

public record DropQuery(TableName tableName) {

    public DropQuery(Class<?> clazz) {
        this(new TableName(clazz));
    }

}
