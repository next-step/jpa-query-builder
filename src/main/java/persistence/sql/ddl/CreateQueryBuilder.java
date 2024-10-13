package persistence.sql.ddl;

import persistence.sql.meta.EntityField;
import persistence.sql.meta.EntityTable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class CreateQueryBuilder {
    private static final String QUERY_TEMPLATE = "CREATE TABLE %s (%s)";
    private static final String NOT_NULL_COLUMN_DEFINITION = "NOT NULL";
    private static final String GENERATION_COLUMN_DEFINITION = "AUTO_INCREMENT";
    private static final String PRIMARY_KEY_COLUMN_DEFINITION = "PRIMARY KEY";

    private final EntityTable entityTable;

    public CreateQueryBuilder(Class<?> entityClass) {
        this.entityTable = new EntityTable(entityClass);
    }

    public String create() {
        return entityTable.getQuery(QUERY_TEMPLATE, entityTable.getTableName(), getColumnClause());
    }

    private String getColumnClause() {
        final List<String> columnDefinitions = entityTable.getFields()
                .stream()
                .filter(this::isNotNeede)
                .map(this::getColumnDefinition)
                .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }

    private boolean isNotNeede(Field field) {
        return !new EntityField(field).isTransient();
    }

    private String getColumnDefinition(Field field) {
        final EntityField entityField = new EntityField(field);
        
        String columDefinition = entityField.getColumnName() + " " + entityField.getDbType();

        if (entityField.isNotNull()) {
            columDefinition += " " + NOT_NULL_COLUMN_DEFINITION;
        }

        if (entityField.isGeneration()) {
            columDefinition += " " + GENERATION_COLUMN_DEFINITION;
        }

        if (entityField.isId()) {
            columDefinition += " " + PRIMARY_KEY_COLUMN_DEFINITION;
        }

        return columDefinition;
    }
}
