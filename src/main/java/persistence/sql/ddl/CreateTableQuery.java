package persistence.sql.ddl;

import java.util.Map;
import java.util.Map.Entry;

public class CreateTableQuery {
    private static final String TABLE_CREATION_PREFIX = "CREATE TABLE IF NOT EXISTS";

    private final EntityTableMetadata entityTableMetadata;

    public CreateTableQuery(Class<?> entityClass) {
        new ValidateEntity(entityClass);
        this.entityTableMetadata = new EntityTableMetadata(entityClass);
    }

    public String generateQuery() {
        String tableName = entityTableMetadata.getTableName();
        Map<ColumnName, ColumnType> columnDefinitions = entityTableMetadata.getColumnDefinitions();

        return generateSql(tableName, columnDefinitions);
    }

    private String generateSql(String tableName, Map<ColumnName, ColumnType> columnDefinitions) {
        StringBuilder sql = new StringBuilder(TABLE_CREATION_PREFIX)
            .append(" ")
            .append(tableName)
            .append(" (\n");

        for (Entry<ColumnName, ColumnType> entry : columnDefinitions.entrySet()) {
            sql.append(entry.getKey().getColumnName())
                .append(" ")
                .append(entry.getValue().getColumnType())
                .append(",\n");
        }

        sql.setLength(sql.length() - 2);
        sql.append("\n);");

        return sql.toString();
    }

}
