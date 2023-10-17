package persistence.sql.dml;

import persistence.core.EntityMetadata;
import persistence.core.EntityMetadataCache;

import java.util.List;
import java.util.stream.Collectors;

public class DmlGenerator {

    private final EntityMetadataCache entityMetadataCache;

    public DmlGenerator() {
        this.entityMetadataCache = EntityMetadataCache.getInstance();
    }

    public String insert(final Object entity) {
        final EntityMetadata<?> entityMetadata = entityMetadataCache.getEntityMetadata(entity.getClass());
        return InsertQueryBuilder.builder()
                .table(entityMetadata.getTableName())
                .addData(entityMetadata.getInsertableColumnNames(), convertToString(entityMetadata.getEntityValues(entity)))
                .build();
    }

    public String findAll(final Class<?> clazz) {
        final EntityMetadata<?> entityMetadata = entityMetadataCache.getEntityMetadata(clazz);
        return SelectQueryBuilder.builder()
                .table(entityMetadata.getTableName())
                .column(entityMetadata.getColumnNames())
                .build();
    }

    public String findById(final Class<?> clazz, final Object id) {
        final EntityMetadata<?> entityMetadata = entityMetadataCache.getEntityMetadata(clazz);
        return SelectQueryBuilder.builder()
                .table(entityMetadata.getTableName())
                .column(entityMetadata.getColumnNames())
                .where(entityMetadata.getIdColumnName(), String.valueOf(id))
                .build();
    }

    public String delete(final Class<?> clazz) {
        final EntityMetadata<?> entityMetadata = entityMetadataCache.getEntityMetadata(clazz);
        return DeleteQueryBuilder.builder()
                .table(entityMetadata.getTableName())
                .build();
    }

    private List<String> convertToString(final List<Object> data) {
        return data.stream()
                .map(object -> object instanceof String ?
                        addSingleQuote(object) : String.valueOf(object)).collect(Collectors.toList());
    }

    private String addSingleQuote(final Object value) {
        return "'" + value + "'";
    }

}
