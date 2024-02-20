package persistence.sql.ddl;

import jakarta.persistence.*;
import persistence.inspector.EntityColumn;
import persistence.inspector.EntityMetadataInspector;

import java.util.List;
import java.util.stream.Collectors;

public class DDLQueryBuilder {

    private static final DDLQueryBuilder instance = new DDLQueryBuilder();
    private EntityMetadataInspector<?> entityMetadataInspector;

    private DDLQueryBuilder() {
    }

    public static DDLQueryBuilder getInstance() {
        return instance;
    }

    public String createTableQuery(Class<?> clazz) {
        entityMetadataInspector = new EntityMetadataInspector<>(clazz);
        return String.format("%s (%s%s)",
                createTablePreQuery(),
                createColumnsSql(),
                createPrimaryKeySql()
        );
    }

    public String dropTableQuery(Class<?> clazz) {
        return String.format("DROP TABLE %s", getTableName(clazz));
    }

    private String createTablePreQuery() {
        return String.format("CREATE TABLE %s", getTableName());
    }

    private String getTableName(Class<?> clazz) {
        return clazz.isAnnotationPresent(Table.class) ? clazz.getAnnotation(Table.class).name() : clazz.getSimpleName().toLowerCase();
    }

    private String getTableName() {
        return entityMetadataInspector.getTableName();
    }

    private String createColumnsSql() {
        List<EntityColumn> entityColumns = entityMetadataInspector.getEntityColumns();

        List<String> columns = entityColumns.stream().map(this::getColumn).collect(Collectors.toList());

        return String.join(", ", columns);
    }

    private String createPrimaryKeySql() {
        List<String> primaryKeys = entityMetadataInspector.getPrimaryKeys().stream().map(EntityColumn::getColumnName).collect(Collectors.toList());

        if (!primaryKeys.isEmpty()) {
            return ", PRIMARY KEY (" + String.join(", ", primaryKeys) + ")";
        }

        return "";
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
