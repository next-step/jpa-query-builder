package persistence.sql.ddl;

import persistence.sql.Metadata;

public class DropQueryBuilder {
    public String dropTableQuery(Class<?> clazz) {
        Metadata metadata = new Metadata(clazz);
        String tableName = metadata.getTableName();
        return String.format("drop table %s", tableName);
    }
}
