package persistence.sql.dml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.meta.Column;
import persistence.sql.meta.Table;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class InsertQueryBuilder {
    private static final Logger logger = LoggerFactory.getLogger(InsertQueryBuilder.class);

    private static final String QUERY_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s)";

    private final Table table;
    private final Object entity;

    public InsertQueryBuilder(Object entity) {
        this.table = new Table(entity.getClass());
        this.entity = entity;
    }

    public String insert() {
        return table.getQuery(QUERY_TEMPLATE, table.getTableName(), getColumnClause(), getValueClause());
    }

    private String getColumnClause() {
        final List<String> columnDefinitions = table.getFields()
                .stream()
                .filter(this::isNotNeeded)
                .map(this::getColumnName)
                .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }

    private boolean isNotNeeded(Field field) {
        final Column column = new Column(field);
        return !column.isGeneration() && !column.isTransient();
    }

    private String getColumnName(Field field) {
        return new Column(field).getColumnName();
    }

    private String getColumnValue(Field field) {
        field.setAccessible(true);
        final String value = String.valueOf(getValue(field));
        final Column column = new Column(field);

        if (column.isQuotesNeeded()) {
            return String.format("'%s'", value);
        }
        return value;
    }

    private String getValueClause() {
        final List<String> columnDefinitions = table.getFields()
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
