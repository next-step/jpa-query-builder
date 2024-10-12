package persistence.sql.dml;

import persistence.sql.QueryBuilder;
import persistence.sql.util.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class InsertQueryBuilder extends QueryBuilder {
    private static final String QUERY_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s)";

    public InsertQueryBuilder(Object entity) {
        super(entity);
    }

    @Override
    public String build() {
        return super.build(QUERY_TEMPLATE, getColumnClause(), getValueClause());
    }

    private String getColumnClause() {
        final List<String> columnDefinitions = getColumns().stream()
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
        final List<String> columnDefinitions = getColumns().stream()
                .filter(this::isNotNeeded)
                .map(this::getColumnValue)
                .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }
}
