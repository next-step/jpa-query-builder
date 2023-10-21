package persistence.sql.dml.h2;

import persistence.core.EntityColumn;
import persistence.core.EntityMetadataModel;
import persistence.core.EntityMetadataModelHolder;
import persistence.exception.NotFoundEntityException;
import persistence.sql.dml.SelectQueryBuilder;
import persistence.sql.dml.where.FetchWhereQueries;

import java.util.List;

public class H2SelectQueryBuilder implements SelectQueryBuilder {

    private static final String SELECT = "select";

    private static final String FROM = "from";

    private static final String WHERE = "where";

    private static final String BLANK_SPACE = " ";

    private static final String COMMA = ", ";

    private final EntityMetadataModelHolder entityMetadataModelHolder;

    public H2SelectQueryBuilder(EntityMetadataModelHolder entityMetadataModelHolder) {
        this.entityMetadataModelHolder = entityMetadataModelHolder;
    }

    @Override
    public String findAll(Class<?> entity) {
        return createSelectQuery(entity);
    }

    @Override
    public String findBy(Class<?> entity, FetchWhereQueries fetchWhereQueries) {
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

    @Override
    public String whereClause(String selectQuery, Class<?> entity, FetchWhereQueries fetchWhereQueries) {
        List<String> whereQueries = fetchWhereQueries.getQueries(entityMetadataModelHolder.getEntityMetadataModel(entity));

        StringBuilder builder = new StringBuilder();
        builder.append(selectQuery)
                .append(BLANK_SPACE)
                .append(WHERE)
                .append(BLANK_SPACE)
                .append(String.join(BLANK_SPACE, whereQueries));

        return builder.toString();
    }
}
