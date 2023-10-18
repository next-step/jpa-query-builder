package persistence.sql.dml;

import persistence.core.EntityMetadata;
import persistence.core.EntityMetadataProvider;
import persistence.util.ReflectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class DmlGenerator {

    private final EntityMetadataProvider entityMetadataProvider;

    public DmlGenerator() {
        this.entityMetadataProvider = EntityMetadataProvider.getInstance();
    }

    public String insert(final Object entity) {
        final EntityMetadata<?> entityMetadata = entityMetadataProvider.getEntityMetadata(entity.getClass());
        final List<String> columnNames = entityMetadata.getInsertableColumnNames();
        final List<Object> values = ReflectionUtils.getFieldValues(entity, entityMetadata.getInsertableColumnFieldNames());
        return InsertQueryBuilder.builder()
                .table(entityMetadata.getTableName())
                .addData(columnNames, convertToString(values))
                .build();
    }

    public String findAll(final Class<?> clazz) {
        final EntityMetadata<?> entityMetadata = entityMetadataProvider.getEntityMetadata(clazz);
        return SelectQueryBuilder.builder()
                .table(entityMetadata.getTableName())
                .column(entityMetadata.getColumnNames())
                .build();
    }

    public String findById(final Class<?> clazz, final Object id) {
        final EntityMetadata<?> entityMetadata = entityMetadataProvider.getEntityMetadata(clazz);
        return SelectQueryBuilder.builder()
                .table(entityMetadata.getTableName())
                .column(entityMetadata.getColumnNames())
                .where(entityMetadata.getIdColumnName(), String.valueOf(id))
                .build();
    }

    public String delete(final Class<?> clazz) {
        final EntityMetadata<?> entityMetadata = entityMetadataProvider.getEntityMetadata(clazz);
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
