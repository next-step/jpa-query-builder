package persistence.sql.ddl;

import persistence.inspector.EntityColumn;
import persistence.inspector.EntityMetadataInspector;

import java.util.List;
import java.util.stream.Collectors;

public class DDLQueryBuilder {

    private static final DDLQueryBuilder instance = new DDLQueryBuilder();

    private DDLQueryBuilder() {
    }

    public static DDLQueryBuilder getInstance() {
        return instance;
    }

    public String createTableQuery(Class<?> clazz) {
        return DDLQueryFormatter.createTableQuery(getTableName(clazz), columnsClause(clazz), primaryKeyClause(clazz));
    }

    public String dropTableQuery(Class<?> clazz) {
        return String.format("DROP TABLE %s", getTableName(clazz));
    }

    private String getTableName(Class<?> clazz) {
        return EntityMetadataInspector.getTableName(clazz);
    }

    private String columnsClause(Class<?> clazz) {
        List<EntityColumn> entityColumns = EntityMetadataInspector.getEntityColumns(clazz);

        List<String> columns = entityColumns.stream().map(this::createColumnsClause).collect(Collectors.toList());

        return String.join(", ", columns);
    }

    private String primaryKeyClause(Class<?> clazz) {
        List<String> primaryKeys = getPrimaryKeyColumnNames(clazz);

        if (!primaryKeys.isEmpty()) {
            return ", PRIMARY KEY (" + String.join(", ", primaryKeys) + ")";
        }

        return "";
    }

    private List<String> getPrimaryKeyColumnNames(Class<?> clazz) {
        return EntityMetadataInspector.getIdFields(clazz)
            .stream()
            .map(EntityMetadataInspector::getColumnName)
            .collect(Collectors.toList());
    }

    private String createColumnsClause(EntityColumn column) {
        String columnTypeFormat = "%s %s %s";

        return String.format(columnTypeFormat,
            column.getColumnName(),
            column.getType().getMysqlType(),
            getColumnProperty(column)
        );
    }

    private String getColumnProperty(EntityColumn column) {
        StringBuilder columnProperty = new StringBuilder();
        if (!column.isNullable()) {
            columnProperty.append("NOT NULL ");
        }
        if (column.isAutoIncrement()) {
            columnProperty.append("AUTO_INCREMENT ");
        }
        return columnProperty.toString();
    }

}

