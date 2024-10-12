package persistence.sql.ddl;

import persistence.sql.QueryBuilder;
import persistence.sql.util.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class CreateQueryBuilder extends QueryBuilder {
    private static final String CREATE_QUERY_TEMPLATE = "CREATE TABLE %s ( %s )";

    private static final String NOT_NULL_COLUMN_DEFINITION = "NOT NULL";
    private static final String GENERATION_COLUMN_DEFINITION = "AUTO_INCREMENT";
    private static final String PRIMARY_KEY_COLUMN_DEFINITION = "PRIMARY KEY";

    public CreateQueryBuilder(Object entity) {
        super(entity);
    }

    @Override
    public String build() {
        return super.build(CREATE_QUERY_TEMPLATE, getColumnDefinition());
    }

    private String getColumnDefinition() {
        final List<String> columnDefinitions = getColumns().stream()
                .filter(field -> !FieldUtils.isTransient(field))
                .map(this::getColumnDefinition)
                .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }

    private String getColumnDefinition(Field field) {
        String columDefinition = FieldUtils.getColumnName(field) + " " + FieldUtils.getDbType(field);

        if (FieldUtils.isNotNull(field)) {
            columDefinition += " " + NOT_NULL_COLUMN_DEFINITION;
        }

        if (FieldUtils.isGeneration(field)) {
            columDefinition += " " + GENERATION_COLUMN_DEFINITION;
        }

        if (FieldUtils.isPrimaryKey(field)) {
            columDefinition += " " + PRIMARY_KEY_COLUMN_DEFINITION;
        }

        return columDefinition;
    }
}
