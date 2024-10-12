package persistence.sql.dml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.QueryBuilder;
import persistence.sql.util.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class InsertQueryBuilder extends QueryBuilder {
    private static final Logger logger = LoggerFactory.getLogger(QueryBuilder.class);

    private static final String QUERY_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s)";

    private final Object entity;

    public InsertQueryBuilder(Object entity) {
        super(entity.getClass());
        this.entity = entity;
    }

    public String insert() {
        return super.build(QUERY_TEMPLATE, getTableName(), getColumnClause(), getValueClause());
    }

    private String getColumnClause() {
        final List<String> columnDefinitions = getFields().stream()
                .filter(this::isNotNeeded)
                .map(FieldUtils::getColumnName)
                .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }

    private boolean isNotNeeded(Field field) {
        return !FieldUtils.isGeneration(field) && !FieldUtils.isTransient(field);
    }

    private String getColumnValue(Field field) {
        field.setAccessible(true);
        final String value = String.valueOf(getValue(field));

        if (FieldUtils.isQuotesNeeded(field)) {
            return String.format("'%s'", value);
        }
        return value;
    }

    private String getValueClause() {
        final List<String> columnDefinitions = getFields().stream()
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
