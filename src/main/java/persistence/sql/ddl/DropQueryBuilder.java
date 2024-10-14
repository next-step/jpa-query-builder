package persistence.sql.ddl;

import jakarta.persistence.Table;
import persistence.sql.MetadataUtils;

public class DropQueryBuilder {
    public String dropTableQuery(Class<?> clazz) {
        MetadataUtils metadataUtils = new MetadataUtils(clazz);
        String tableName = metadataUtils.getTableName();
        return String.format("drop table %s", tableName);
    }
}
