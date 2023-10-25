package persistence.sql.dml;

import persistence.core.EntityColumn;
import persistence.core.EntityMetadataModel;
import persistence.core.EntityMetadataModelHolder;
import persistence.exception.NotFoundEntityException;
import persistence.sql.dml.where.FetchWhereQuery;

public class SelectQueryBuilder extends AbstractSelectQueryBuilder {

    public SelectQueryBuilder(EntityMetadataModelHolder entityMetadataModelHolder) {
        super(entityMetadataModelHolder);
    }

    public String findAll(Class<?> entity) {
        return createSelectQuery(entity);
    }

    public String findBy(Class<?> entity, FetchWhereQuery fetchWhereQueries) {
        String selectQuery = createSelectQuery(entity);
        return whereClause(selectQuery, entity, fetchWhereQueries);
    }

    private String createSelectQuery(Class<?> entity) {
        EntityMetadataModel entityMetadataModel = entityMetadataModelHolder.getEntityMetadataModel(entity);

        if (entityMetadataModel == null) {
            throw new NotFoundEntityException(entity);
        }

        EntityColumn primaryKeyColumn = entityMetadataModel.getPrimaryKeyColumn();
        StringBuilder builder = new StringBuilder();

        builder.append(SELECT)
                .append(BLANK_SPACE)
                .append(primaryKeyColumn.getName())
                .append(COMMA)
                .append(String.join(COMMA, entityMetadataModel.getColumnNames()))
                .append(BLANK_SPACE)
                .append(FROM)
                .append(BLANK_SPACE)
                .append(entityMetadataModel.getTableName());

        return builder.toString();
    }
}
