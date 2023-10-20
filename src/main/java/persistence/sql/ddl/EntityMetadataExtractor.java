package persistence.sql.ddl;

import jakarta.persistence.Entity;

public class EntityMetadataExtractor {

    private final TableMetadataExtractor tableMetaDataExtractor;
    private final FieldMetadataExtractors fieldMetaDatas;

    public EntityMetadataExtractor(Class<?> type) {
        if (!type.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("No @Entity annotation");
        }

        tableMetaDataExtractor = new TableMetadataExtractor(type);
        fieldMetaDatas = new FieldMetadataExtractors(type);
    }

    public String getTableName() {
        return tableMetaDataExtractor.getTableName();
    }

    public String getColumnInfo() {
        return fieldMetaDatas.getDefinition();
    }

}
