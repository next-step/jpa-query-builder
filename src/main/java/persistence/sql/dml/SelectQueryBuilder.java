package persistence.sql.dml;

import persistence.sql.QueryBuilder;
import persistence.sql.util.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class SelectQueryBuilder extends QueryBuilder {
    private static final String QUERY_TEMPLATE = "SELECT %s FROM %s";

    public SelectQueryBuilder(Class<?> entityClass) {
        super(entityClass);
    }

    public String findAll() {
        return super.build(QUERY_TEMPLATE, getColumnClause(), getTableName());
    }

    private String getColumnClause() {
        final List<String> columnDefinitions = getColumns().stream()
                .filter(this::isNotNeeded)
                .map(FieldUtils::getColumnName)
                .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }

    private boolean isNotNeeded(Field field) {
        return !FieldUtils.isTransient(field);
    }
}
