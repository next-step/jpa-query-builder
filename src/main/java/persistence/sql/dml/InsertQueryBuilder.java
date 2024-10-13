package persistence.sql.dml;

import persistence.sql.meta.EntityField;
import persistence.sql.meta.EntityTable;

import java.util.List;
import java.util.stream.Collectors;

public class InsertQueryBuilder {
    private static final String QUERY_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s)";

    private final EntityTable entityTable;
    private final Object entity;

    public InsertQueryBuilder(Object entity) {
        this.entityTable = new EntityTable(entity.getClass());
        this.entity = entity;
    }

    public String insert() {
        return entityTable.getQuery(QUERY_TEMPLATE, entityTable.getTableName(), getColumnClause(), getValueClause());
    }

    private String getColumnClause() {
        final List<String> columnDefinitions = entityTable.getEntityFields()
                .stream()
                .filter(this::isNotNeeded)
                .map(EntityField::getColumnName)
                .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }

    private String getValueClause() {
        final List<String> columnDefinitions = entityTable.getEntityFields()
                .stream()
                .filter(this::isNotNeeded)
                .map(this::getColumnValue)
                .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }

    private boolean isNotNeeded(EntityField entityField) {
        return !entityField.isGeneration() && entityField.isPersistent();
    }

    private String getColumnValue(EntityField entityField) {
        final String value = String.valueOf(entityField.getValue(entity));
        if (entityField.isQuotesNeeded()) {
            return String.format("'%s'", value);
        }
        return value;
    }
}
