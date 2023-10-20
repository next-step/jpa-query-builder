package persistence.sql.ddl;

import jakarta.persistence.Entity;

public class EntityMetaData {

    private final TableMetaData tableMetaData;
    private final FieldMetaDatas fieldMetaDatas;

    public EntityMetaData(Class<?> type) {
        if (!type.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("No @Entity annotation");
        }

        tableMetaData = new TableMetaData(type);
        fieldMetaDatas = new FieldMetaDatas(type);
    }

    public String getTableName() {
        return tableMetaData.getTableName();
    }

    public String getColumnInfo() {
        return fieldMetaDatas.getDefinition();
    }

}
