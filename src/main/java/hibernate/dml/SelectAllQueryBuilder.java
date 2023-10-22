package hibernate.dml;

import hibernate.entity.EntityClass;
import hibernate.entity.column.EntityColumn;

import java.util.stream.Collectors;

public class SelectAllQueryBuilder {

    private static final String SELECT_ALL_QUERY = "select %s from %s;";

    private static final String SELECT_AlL_QUERY_COLUMN_DELIMITER = ", ";

    public String generateQuery(final EntityClass<?> entityClass) {
        return String.format(SELECT_ALL_QUERY, parseColumnQueries(entityClass), entityClass.tableName());
    }

    private String parseColumnQueries(final EntityClass<?> entityClass) {
        return entityClass.getEntityColumns()
                .stream()
                .map(EntityColumn::getFieldName)
                .collect(Collectors.joining(SELECT_AlL_QUERY_COLUMN_DELIMITER));
    }
}
