package hibernate.dml;

import hibernate.entity.EntityClass;
import hibernate.entity.column.EntityColumn;

import java.util.stream.Collectors;

public class SelectQueryBuilder {

    private static final String SELECT_QUERY = "select %s from %s where %s = %s;";

    private static final String SELECT_QUERY_COLUMN_DELIMITER = ", ";

    public SelectQueryBuilder() {
    }

    public String generateQuery(final EntityClass entityClass, final Object id) {
        return String.format(SELECT_QUERY, parseColumnQueries(entityClass), entityClass.tableName(), entityClass.getEntityId().getFieldName(), id);
    }

    private String parseColumnQueries(final EntityClass entityClass) {
        return entityClass.getEntityColumns()
                .stream()
                .map(EntityColumn::getFieldName)
                .collect(Collectors.joining(SELECT_QUERY_COLUMN_DELIMITER));
    }
}
