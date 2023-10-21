package hibernate.dml;

import hibernate.entity.EntityObject;
import hibernate.entity.column.EntityColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InsertQueryBuilder {

    private static final String INSERT_QUERY = "insert into %s (%s) values (%s);";

    private static final String INSERT_COLUMN_QUERY_DELIMITER = ", ";

    public String generateQuery(final EntityObject entity) {
        Map<EntityColumn, Object> fieldValues = entity.getFieldValues();
        List<EntityColumn> entityColumns = new ArrayList<>(fieldValues.keySet());
        return String.format(INSERT_QUERY,
                entity.getTableName(), parseColumnQueries(entityColumns), parseColumnValueQueries(entityColumns, fieldValues));
    }

    private String parseColumnQueries(final List<EntityColumn> entityColumns) {
        return entityColumns.stream()
                .map(EntityColumn::getFieldName)
                .collect(Collectors.joining(INSERT_COLUMN_QUERY_DELIMITER));
    }

    private String parseColumnValueQueries(final List<EntityColumn> entityColumns, final Map<EntityColumn, Object> fieldValues) {
        return entityColumns.stream()
                .map(column -> fieldValues.get(column).toString())
                .collect(Collectors.joining(INSERT_COLUMN_QUERY_DELIMITER));
    }
}
