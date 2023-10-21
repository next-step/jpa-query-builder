package persistence.sql.dml;

import persistence.core.EntityMetadataModelHolder;
import persistence.sql.dml.where.FetchWhereQuery;

public abstract class AbstractSelectQueryBuilder implements SelectQueryBuilder {

    protected static final String SELECT = "select";

    protected static final String FROM = "from";

    protected static final String WHERE = "where";

    protected static final String BLANK_SPACE = " ";

    protected static final String COMMA = ", ";

    protected final EntityMetadataModelHolder entityMetadataModelHolder;

    protected AbstractSelectQueryBuilder(EntityMetadataModelHolder entityMetadataModelHolder) {
        this.entityMetadataModelHolder = entityMetadataModelHolder;
    }

    protected String whereClause(String selectQuery, Class<?> entity, FetchWhereQuery fetchWhereQueries) {
        String whereQueries = fetchWhereQueries.getQueries(entityMetadataModelHolder.getEntityMetadataModel(entity));

        StringBuilder builder = new StringBuilder();
        builder.append(selectQuery)
                .append(BLANK_SPACE)
                .append(WHERE)
                .append(BLANK_SPACE)
                .append(String.join(BLANK_SPACE, whereQueries));

        return builder.toString();
    }
}
