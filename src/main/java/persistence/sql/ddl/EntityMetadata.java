package persistence.sql.ddl;

import jakarta.persistence.Entity;

public class EntityMetadata {

    private final TableInfo tableInfo;
    private final ColumnInfoCollection columnInfoCollection;

    public EntityMetadata(Class<?> type) {
        if (!type.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("No @Entity annotation");
        }

        tableInfo = new TableInfo(type);
        columnInfoCollection = new ColumnInfoCollection(type);
    }

    public String getTableName() {
        return tableInfo.getTableName();
    }

    public String getColumnInfo() {
        return columnInfoCollection.getDefinition();
    }

}
