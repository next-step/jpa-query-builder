package persistence.sql.dml;

import persistence.sql.QueryBuilder;
import persistence.sql.util.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class SelectQueryBuilder extends QueryBuilder {
    private static final String FIND_ALL_QUERY_TEMPLATE = "SELECT %s FROM %s";
    private static final String FIND_BY_ID_QUERY_TEMPLATE = "SELECT %s FROM %s WHERE %s";

    public SelectQueryBuilder(Class<?> entityClass) {
        super(entityClass);
    }

    public String findAll() {
        return super.build(FIND_ALL_QUERY_TEMPLATE, getColumnClause(), getTableName());
    }

    public String findById(Object id) {
        return super.build(FIND_BY_ID_QUERY_TEMPLATE, getColumnClause(), getTableName(), getWhereClause(id));
    }

    private String getColumnClause() {
        final List<String> columnDefinitions = getFields().stream()
                .filter(this::isNotNeeded)
                .map(FieldUtils::getColumnName)
                .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }

    private boolean isNotNeeded(Field field) {
        return !FieldUtils.isTransient(field);
    }

    private String getWhereClause(Object id) {
        final Field field = getIdField();
        return FieldUtils.getColumnName(field) + " = " + id;
    }
}
