package persistence.sql.dml;

import persistence.core.EntityMetadataModelHolder;
import persistence.sql.dml.where.FetchWhereQuery;

public class AbstractQueryBuilder implements WhereQueryFetcher {

    protected static final String BLANK_SPACE = " ";

    protected static final String COMMA = ", ";

    protected final EntityMetadataModelHolder entityMetadataModelHolder;

    public AbstractQueryBuilder(EntityMetadataModelHolder entityMetadataModelHolder) {
        this.entityMetadataModelHolder = entityMetadataModelHolder;
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
