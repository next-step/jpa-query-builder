package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.ddl.dialect.Dialect;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;

public class EntityMetadata {

    private final TableMetadataExtractor tableMetaDataExtractor;
    private final FieldMetadataExtractors fieldMetadataExtractors;
    private final Dialect dialect;
    private final Class<?> type;

    public EntityMetadata(Class<?> type, Dialect dialectParam) {
        if (!type.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("No @Entity annotation");
        }

        tableMetaDataExtractor = new TableMetadataExtractor(type);
        fieldMetadataExtractors = new FieldMetadataExtractors(type);
        dialect = dialectParam;
        this.type = type;
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

    public String getColumnNames() {
        return fieldMetadataExtractors.getColumnNames();
    }

    public String getValueFrom(Object entity) {
        return fieldMetadataExtractors.getValueFrom(entity);
    }

    public String getIdColumnName() {
        return fieldMetadataExtractors.getIdColumnName();
    }

    public <T> T getEntity(ResultSet resultSet) {
        try {
            Constructor<T> constructor = (Constructor<T>) type.getDeclaredConstructor();
            constructor.setAccessible(true);
            T instance = constructor.newInstance();
            fieldMetadataExtractors.setInstanceValue(instance, resultSet);

            return instance;
        } catch (Exception e) {
            throw new RuntimeException("엔티티 객체를 생성하는데 오류가 발생하였습니다.", e);
        }
    }

}
