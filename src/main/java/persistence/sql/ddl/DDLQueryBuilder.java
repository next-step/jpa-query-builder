package persistence.sql.ddl;

import persistence.inspector.EntityColumn;
import persistence.inspector.EntityMetadataInspector;

import java.util.List;
import java.util.stream.Collectors;

public class DDLQueryBuilder {

    private static final DDLQueryBuilder instance = new DDLQueryBuilder();
    private EntityMetadataInspector entityMetadataInspector;

    private DDLQueryBuilder() {
    }

    public static DDLQueryBuilder getInstance() {
        return instance;
    }

    public String createTableQuery(Class<?> clazz) {
        entityMetadataInspector = new EntityMetadataInspector();
        return String.format("CREATE TABLE %s (%s%s)",
                getTableName(clazz),
                columnsClause(clazz),
                primaryKeyClause(clazz)
        );
    }

    public String dropTableQuery(Class<?> clazz) {
        return String.format("DROP TABLE %s", getTableName(clazz));
    }

    private String getTableName(Class<?> clazz) {
        return entityMetadataInspector.getTableName(clazz);
    }

    private String columnsClause(Class<?> clazz) {
        List<EntityColumn> entityColumns = entityMetadataInspector.getEntityColumns(clazz);

        List<String> columns = entityColumns.stream().map(this::getColumn).collect(Collectors.toList());

        return String.join(", ", columns);
    }

    private String primaryKeyClause(Class<?> clazz) {
        List<String> primaryKeys = getPrimaryKeyColumns(clazz);

        if (!primaryKeys.isEmpty()) {
            return ", PRIMARY KEY (" + String.join(", ", primaryKeys) + ")";
        }

        return "";
    }

    private List<String> getPrimaryKeyColumns(Class<?> clazz) {
        return entityMetadataInspector.getIdFields(clazz)
                .stream()
                .map(entityMetadataInspector::getColumnName)
                .collect(Collectors.toList());
    }

    private String getColumn(EntityColumn column) {
        return column.getColumnName() + " " + column.getType().getMysqlType() + " " + getColumnProperty(column);
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
