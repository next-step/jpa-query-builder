package persistence.sql.dml;

import persistence.core.EntityColumn;
import persistence.core.EntityMetadata;
import persistence.core.EntityMetadataCache;
import persistence.exception.PersistenceException;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class DmlGenerator {

    private final EntityMetadataCache entityMetadataCache;

    public DmlGenerator() {
        this.entityMetadataCache = EntityMetadataCache.getInstance();
    }

    public String generateInsertDml(final Object entity) {
        final EntityMetadata<?> entityMetadata = entityMetadataCache.getEntityMetadata(entity.getClass());

        final String columns = columnsClause(entityMetadata);
        final String values = valueClause(entity, entityMetadata);

        return String.format("insert into %s %s values %s", entityMetadata.getTableName(), columns, values);
    }

    public String generateFindAllDml(final Class<?> clazz) {
        final EntityMetadata<?> entityMetadata = entityMetadataCache.getEntityMetadata(clazz);
        final String selectClause = selectClause(entityMetadata);
        return String.format("select %s from %s", selectClause, entityMetadata.getTableName());
    }

    private String selectClause(final EntityMetadata<?> entityMetadata) {
        return entityMetadata.getColumns()
                .stream()
                .map(EntityColumn::getName)
                .collect(Collectors.joining(", "));
    }

    private String columnsClause(final EntityMetadata<?> entityMetadata) {
        final StringBuilder builder = new StringBuilder();
        builder.append("(");
        builder.append(String.join(", ", entityMetadata.getInsertableColumnNames()));
        builder.append(")");
        return builder.toString();
    }

    private String valueClause(final Object entity, final EntityMetadata<?> entityMetadata) {
        final StringBuilder builder = new StringBuilder();
        builder.append("(");
        builder.append(buildValuesClause(entity, entityMetadata));
        builder.append(")");
        return builder.toString();
    }

    private String buildValuesClause(final Object entity, final EntityMetadata<?> entityMetadata) {
        return entityMetadata.getColumns()
                .stream()
                .filter(EntityColumn::isInsertable)
                .map(entityColumn -> buildValueForColumn(entity, entityColumn))
                .collect(Collectors.joining(", "));
    }

    private String buildValueForColumn(final Object entity, final EntityColumn entityColumn) {
        try {
            final Field field = entity.getClass().getDeclaredField(entityColumn.getFieldName());
            field.setAccessible(true);
            final Object value = field.get(entity);
            return entityColumn.isStringValued() ? addSingleQuote(value) : String.valueOf(value);
        } catch (final IllegalAccessException | NoSuchFieldException e) {
            throw new PersistenceException(e);
        }
    }

    private String addSingleQuote(final Object value) {
        return "'" + value + "'";
    }

}
