package persistence.sql.dml;

import persistence.core.EntityMetadata;
import persistence.core.EntityMetadataProvider;
import persistence.dialect.Dialect;
import persistence.util.ReflectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class DmlGenerator {

    private final EntityMetadataProvider entityMetadataProvider;
    private final Dialect dialect;

    public DmlGenerator(final Dialect dialect) {
        this.entityMetadataProvider = EntityMetadataProvider.getInstance();
        this.dialect = dialect;
    }

    public String insert(final Object entity) {
        final EntityMetadata<?> entityMetadata = entityMetadataProvider.getEntityMetadata(entity.getClass());
        final List<String> columnNames = entityMetadata.getInsertableColumnNames();
        final List<Object> values = ReflectionUtils.getFieldValues(entity, entityMetadata.getInsertableColumnFieldNames());
        return InsertQueryBuilder.builder()
                .table(entityMetadata.getTableName())
                .addData(columnNames, convertToStrings(values))
                .build();
    }

    public String findAll(final Class<?> clazz) {
        final EntityMetadata<?> entityMetadata = entityMetadataProvider.getEntityMetadata(clazz);
        return new SelectQueryBuilder(dialect)
                .table(entityMetadata.getTableName())
                .column(entityMetadata.getColumnNames())
                .build();
    }

    public String findById(final Class<?> clazz, final Object id) {
        final EntityMetadata<?> entityMetadata = entityMetadataProvider.getEntityMetadata(clazz);
        return new SelectQueryBuilder(dialect)
                .table(entityMetadata.getTableName())
                .column(entityMetadata.getColumnNames())
                .where(entityMetadata.getIdColumnName(), String.valueOf(id))
                .build();
    }

    public String delete(final Object entity) {
        final EntityMetadata<?> entityMetadata = entityMetadataProvider.getEntityMetadata(entity.getClass());
        final Object value = ReflectionUtils.getFieldValue(entity, entityMetadata.getIdColumnFieldName());
        return DeleteQueryBuilder.builder()
                .table(entityMetadata.getTableName())
                .where(entityMetadata.getIdColumnName(), convertToString(value))
                .build();
    }

    private String convertToString(final Object object) {
        return object instanceof String ?
                addSingleQuote(object) : String.valueOf(object);
    }

    private List<String> convertToStrings(final List<Object> data) {
        return data.stream()
                .map(this::convertToString)
                .collect(Collectors.toList());
    }

    private String addSingleQuote(final Object value) {
        return "'" + value + "'";
    }

}
