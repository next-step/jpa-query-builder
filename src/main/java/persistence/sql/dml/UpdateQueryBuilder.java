package persistence.sql.dml;

import persistence.sql.meta.EntityField;
import persistence.sql.meta.EntityTable;

import java.util.List;
import java.util.stream.Collectors;

public class UpdateQueryBuilder {
    private static final String QUERY_TEMPLATE = "UPDATE %s SET %s WHERE %s";

    private final EntityTable entityTable;
    private final Object entity;

    public UpdateQueryBuilder(Object entity) {
        this.entityTable = new EntityTable(entity.getClass());
        this.entity = entity;
    }

    public String update() {
        return entityTable.getQuery(QUERY_TEMPLATE, entityTable.getTableName(), getSetClause(), getWhereClause());
    }

    private String getSetClause() {
        final List<String> columnDefinitions = entityTable.getEntityFields()
                .stream()
                .filter(this::isNotNeeded)
                .map(this::getSetClause)
                .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }

    private boolean isNotNeeded(EntityField entityField) {
        return !entityField.isId() && entityField.isPersistent();
    }

    private String getSetClause(EntityField entityField) {
        return entityField.getColumnName() + " = " + entityField.getValue(entity);
    }

    private String getWhereClause() {
        final Object id = entityTable.getIdValue(entity);
        return entityTable.getWhereClause(id);
    }
}
