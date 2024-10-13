package persistence.sql.dml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.meta.EntityField;
import persistence.sql.meta.EntityTable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class InsertQueryBuilder {
    private static final Logger logger = LoggerFactory.getLogger(InsertQueryBuilder.class);

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
        final List<String> columnDefinitions = entityTable.getFields()
                .stream()
                .filter(this::isNotNeeded)
                .map(this::getColumnName)
                .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }

    private boolean isNotNeeded(Field field) {
        final EntityField entityField = new EntityField(field);
        return !entityField.isGeneration() && !entityField.isTransient();
    }

    private String getColumnName(Field field) {
        return new EntityField(field).getColumnName();
    }

    private String getColumnValue(Field field) {
        field.setAccessible(true);
        final String value = String.valueOf(getValue(field));
        final EntityField entityField = new EntityField(field);

        if (entityField.isQuotesNeeded()) {
            return String.format("'%s'", value);
        }
        return value;
    }

    private String getValueClause() {
        final List<String> columnDefinitions = entityTable.getFields()
                .stream()
                .filter(this::isNotNeeded)
                .map(this::getColumnValue)
                .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }

    protected Object getValue(Field field) {
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
