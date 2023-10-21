package persistence.sql.dml.h2;

import persistence.core.EntityMetadataModel;
import persistence.core.EntityMetadataModelHolder;
import persistence.exception.NotFoundEntityException;
import persistence.sql.dml.AbstractQueryBuilder;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.where.FetchWhereQuery;

public class H2DeleteQueryBuilder extends AbstractQueryBuilder implements DeleteQueryBuilder {

    private static final String DELETE = "delete";

    private static final String FROM = "from";

    public H2DeleteQueryBuilder(EntityMetadataModelHolder entityMetadataModelHolder) {
        super(entityMetadataModelHolder);
    }

    @Override
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
                .append(WHERE)
                .append(BLANK_SPACE)
                .append(String.join(BLANK_SPACE, whereQueries));

        return builder.toString();
    }
}
