package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.ddl.dialect.Dialect;

public class EntityMetadata {

    private final TableMetadataExtractor tableMetaDataExtractor;
    private final FieldMetadataExtractors fieldMetadataExtractors;
    private final Dialect dialect;

    public EntityMetadata(Class<?> type, Dialect dialectParam) {
        if (!type.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("No @Entity annotation");
        }

        tableMetaDataExtractor = new TableMetadataExtractor(type);
        fieldMetadataExtractors = new FieldMetadataExtractors(type);
        dialect = dialectParam;
    }

    public static EntityMetadata of(Class<?> type, Dialect dialectParam) {
        return new EntityMetadata(type, dialectParam);
    }

    public String getTableName() {
        return tableMetaDataExtractor.getTableName();
    }

    public String getColumnInfo() {
        return fieldMetadataExtractors.getDefinition(dialect);
    }

    public String getColumnNames(Object entity) {
        return fieldMetadataExtractors.getColumnNames(entity);
    }

    public String getColumnNames(Class<?> type) {
        return fieldMetadataExtractors.getColumnNames(type);
    }

    public String getValueFrom(Object entity) {
        return fieldMetadataExtractors.getValueFrom(entity);
    }

    public String getIdColumnName(Class<?> type) {
        return fieldMetadataExtractors.getIdColumnName(type);
    }

}
