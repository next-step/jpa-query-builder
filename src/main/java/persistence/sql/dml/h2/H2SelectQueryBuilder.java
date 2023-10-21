package persistence.sql.dml.h2;

import persistence.core.EntityColumn;
import persistence.core.EntityMetadataModel;
import persistence.core.EntityMetadataModelHolder;
import persistence.exception.NotFoundEntityException;
import persistence.sql.dml.SelectQueryBuilder;

public class H2SelectQueryBuilder implements SelectQueryBuilder {

    private static final String SELECT = "select";

    private static final String FROM = "from";

    private static final String BLANK_SPACE = " ";

    private static final String COMMA = ", ";

    private final EntityMetadataModelHolder entityMetadataModelHolder;

    public H2SelectQueryBuilder(EntityMetadataModelHolder entityMetadataModelHolder) {
        this.entityMetadataModelHolder = entityMetadataModelHolder;
    }

    @Override
    public String findAll(Class<?> entity) {
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
