package persistence.sql.dml;

import persistence.core.EntityMetadataModel;
import persistence.core.EntityMetadataModelHolder;
import persistence.exception.NotFoundEntityException;
import persistence.sql.dml.AbstractQueryBuilder;
import persistence.sql.dml.where.FetchWhereQuery;

public class DeleteQueryBuilder extends AbstractQueryBuilder {

    private static final String DELETE = "delete";

    private static final String FROM = "from";

    public DeleteQueryBuilder(EntityMetadataModelHolder entityMetadataModelHolder) {
        super(entityMetadataModelHolder);
    }

    public String delete(Class<?> entity, FetchWhereQuery fetchWhereQuery) {
        EntityMetadataModel entityMetadataModel = entityMetadataModelHolder.getEntityMetadataModel(entity);

        if (entityMetadataModel == null) {
            throw new NotFoundEntityException(entity);
        }

        String whereQueries = fetchWhereQuery.getQueries(entityMetadataModel);

        StringBuilder builder = new StringBuilder();

        builder.append(DELETE)
                .append(BLANK_SPACE)
                .append(FROM)
                .append(BLANK_SPACE)
                .append(entityMetadataModel.getTableName())
                .append(BLANK_SPACE)
                .append("where")
                .append(BLANK_SPACE)
                .append(String.join(BLANK_SPACE, whereQueries));

        return builder.toString();
    }
}
