package persistence.sql.dml;

import persistence.sql.Table;
import persistence.sql.util.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class SelectQueryBuilder {
    private static final String FIND_ALL_QUERY_TEMPLATE = "SELECT %s FROM %s";
    private static final String FIND_BY_ID_QUERY_TEMPLATE = "SELECT %s FROM %s WHERE %s";

    private final Table table;

    public SelectQueryBuilder(Class<?> entityClass) {
        this.table = new Table(entityClass);
    }

    public String findAll() {
        return table.getQuery(FIND_ALL_QUERY_TEMPLATE, getColumnClause(), table.getTableName());
    }

    public String findById(Object id) {
        return table.getQuery(FIND_BY_ID_QUERY_TEMPLATE, getColumnClause(), table.getTableName(), table.getWhereClause(id));
    }

    private String getColumnClause() {
        final List<String> columnDefinitions = table.getFields()
                .stream()
                .filter(this::isNotNeeded)
                .map(FieldUtils::getColumnName)
                .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }

    private boolean isNotNeeded(Field field) {
        return !FieldUtils.isTransient(field);
    }
}
