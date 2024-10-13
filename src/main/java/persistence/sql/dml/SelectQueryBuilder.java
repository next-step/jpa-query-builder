package persistence.sql.dml;

import persistence.sql.meta.EntityField;
import persistence.sql.meta.EntityTable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class SelectQueryBuilder {
    private static final String FIND_ALL_QUERY_TEMPLATE = "SELECT %s FROM %s";
    private static final String FIND_BY_ID_QUERY_TEMPLATE = "SELECT %s FROM %s WHERE %s";

    private final EntityTable entityTable;

    public SelectQueryBuilder(Class<?> entityClass) {
        this.entityTable = new EntityTable(entityClass);
    }

    public String findAll() {
        return entityTable.getQuery(FIND_ALL_QUERY_TEMPLATE, getColumnClause(), entityTable.getTableName());
    }

    public String findById(Object id) {
        return entityTable.getQuery(FIND_BY_ID_QUERY_TEMPLATE, getColumnClause(), entityTable.getTableName(), entityTable.getWhereClause(id));
    }

    private String getColumnClause() {
        final List<String> columnDefinitions = entityTable.getFields()
                .stream()
                .filter(this::isNotNeeded)
                .map(this::getColumnName)
                .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }

    private boolean isNotNeeded(Field field) {
        return !new EntityField(field).isTransient();
    }

    private String getColumnName(Field field) {
        return new EntityField(field).getColumnName();
    }
}
