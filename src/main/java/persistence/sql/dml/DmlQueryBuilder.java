package persistence.sql.dml;

import persistence.sql.metadata.ColumnData;
import persistence.sql.metadata.EntityData;
import persistence.sql.metadata.EntityWrapper;
import persistence.sql.metadata.EntityMetadata;

import java.util.List;

public class DmlQueryBuilder {

    private static final String JOIN_DELIMITER = ", ";

    public String buildInsertQuery(Object entity) {
        EntityWrapper entityWrapper = EntityWrapper.from(entity);
        EntityData insertColumns = entityWrapper.getInsertColumns();
        return """
                insert into %s (%s)\s
                values (%s)
                ;""".formatted(entityWrapper.getTableName(), String.join(", ", insertColumns.getColumnNames()), String.join(JOIN_DELIMITER, insertColumns.getColumnValues()));
    }

    public String buildSelectAllQuery(Class<?> entityClass) {
        EntityMetadata metadata = EntityMetadata.from(entityClass);

        return """
                select %s\s
                from %s
                ;""".formatted(String.join(JOIN_DELIMITER, metadata.getColumnNames()), metadata.getTableName());
    }

    public String buildSelectByIdQuery(Class<?> entityClass, Object id) {
        EntityMetadata metadata = EntityMetadata.from(entityClass);

        return """
                select %s\s
                from %s\s
                where %s = %s
                ;""".formatted(String.join(JOIN_DELIMITER, metadata.getColumnNames()), metadata.getTableName(), metadata.getPrimaryKeyName(), id);
    }

    public String buildDeleteQuery(Object entity) {
        EntityWrapper entityWrapper = EntityWrapper.from(entity);

        return """
                delete\s
                from %s\s
                where %s
                ;""".formatted(entityWrapper.getTableName(), equalityExpression(entityWrapper.getPrimaryKey()));
    }

    public String buildUpdateQuery(Object entity) {
        EntityWrapper entityWrapper = EntityWrapper.from(entity);
        List<ColumnData> columns = entityWrapper.getColumns().getAll();

        return """
                update %s\s
                set %s\s
                where %s
                ;""".formatted(entityWrapper.getTableName(), String.join(JOIN_DELIMITER, equalityExpressions(columns)), equalityExpression(entityWrapper.getPrimaryKey()));
    }

    private List<String> equalityExpressions(List<ColumnData> columns) {
        return columns.stream()
                .map(this::equalityExpression)
                .toList();
    }

    private String equalityExpression(ColumnData columnData) {
        return columnData.getName() + " = " + columnData.getValue();
    }
}
