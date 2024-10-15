package persistence.sql.ddl;

import persistence.dialect.Dialect;
import persistence.sql.meta.EntityField;
import persistence.sql.meta.EntityTable;

import java.util.List;
import java.util.stream.Collectors;

public class CreateQueryBuilder {
    private static final String QUERY_TEMPLATE = "CREATE TABLE %s (%s)";
    private static final String NOT_NULL_COLUMN_DEFINITION = "NOT NULL";
    private static final String GENERATION_COLUMN_DEFINITION = "AUTO_INCREMENT";
    private static final String PRIMARY_KEY_COLUMN_DEFINITION = "PRIMARY KEY";

    private final EntityTable entityTable;
    private final Dialect dialect;

    public CreateQueryBuilder(Class<?> entityType, Dialect dialect) {
        this.entityTable = new EntityTable(entityType);
        this.dialect = dialect;
    }

    public String create() {
        return entityTable.getQuery(QUERY_TEMPLATE, entityTable.getTableName(), getColumnClause());
    }

    private String getColumnClause() {
        final List<String> columnDefinitions = entityTable.getEntityFields()
                .stream()
                .filter(EntityField::isPersistent)
                .map(this::getColumnDefinition)
                .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }

    private String getColumnDefinition(EntityField entityField) {
        String columDefinition = entityField.getColumnName() + " " + getDbType(entityField);

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

    private String getDbType(EntityField entityField) {
        final String dbTypeName = dialect.getDbTypeName(entityField);
        final int columnLength = entityField.getColumnLength();

        if (columnLength == 0) {
            return dbTypeName;
        }
        return String.format("%s(%s)", dbTypeName, columnLength);
    }
}
