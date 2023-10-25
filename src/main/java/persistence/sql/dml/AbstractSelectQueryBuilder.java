package persistence.sql.dml;

import persistence.core.EntityMetadataModelHolder;
import persistence.sql.dml.where.FetchWhereQuery;

public abstract class AbstractSelectQueryBuilder extends AbstractQueryBuilder {

    protected static final String SELECT = "select";

    protected static final String FROM = "from";

    public AbstractSelectQueryBuilder(EntityMetadataModelHolder entityMetadataModelHolder) {
        super(entityMetadataModelHolder);
    }

    @Override
    public String whereClause(String selectQuery, Class<?> entity, FetchWhereQuery fetchWhereQueries) {
        String whereQueries = fetchWhereQueries.getQueries(entityMetadataModelHolder.getEntityMetadataModel(entity));

        StringBuilder builder = new StringBuilder();
        builder.append(selectQuery)
                .append(BLANK_SPACE)
                .append("where")
                .append(BLANK_SPACE)
                .append(String.join(BLANK_SPACE, whereQueries));

        return builder.toString();
    }
}
