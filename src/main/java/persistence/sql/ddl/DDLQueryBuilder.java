package persistence.sql.ddl;

import persistence.inspector.EntityMetadataInspector;

import java.lang.reflect.Field;
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

        return createTableQuery(getTableName(clazz), createColumnClause(clazz), createPrimaryKeyClause(clazz));
    }

    private String createTableQuery(String tableName, String columnClause, String primaryKeyClause) {

        return String.format("CREATE TABLE %s (%s%s)", tableName, columnClause, primaryKeyClause);
    }

    public String dropTableQuery(Class<?> clazz) {

        return createDropTableQuery(getTableName(clazz));
    }

    private String createDropTableQuery(String tableName) {

        return String.format("DROP TABLE %s", tableName);
    }

    private String getTableName(Class<?> clazz) {

        return EntityMetadataInspector.getTableName(clazz);
    }

    private String createColumnClause(Class<?> clazz) {
        List<String> columnClauses = EntityMetadataInspector.getFields(clazz).stream()
                .map(this::createColumnClause)
                .collect(Collectors.toList());

        return String.join(", ", columnClauses);
    }

    private String createColumnClause(Field field) {
        String columnTypeFormat = "%s %s %s";

        return String.format(columnTypeFormat,
                EntityMetadataInspector.getColumnName(field),
                EntityMetadataInspector.getColumnType(field).getMysqlType(),
                getColumnProperty(field)
        );
    }

    private String createPrimaryKeyClause(Class<?> clazz) {
        final String primaryKeyClauseFormat = ", PRIMARY KEY (%s)";

        return String.format(primaryKeyClauseFormat, getPrimaryKeyColumnName(clazz));
    }

    private String getPrimaryKeyColumnName(Class<?> clazz) {

        return EntityMetadataInspector.getColumnName(EntityMetadataInspector.getIdField(clazz));
    }

    private String getColumnProperty(Field field) {
        StringBuilder columnProperty = new StringBuilder();
        if (!EntityMetadataInspector.isNullable(field)) {
            columnProperty.append("NOT NULL ");
        }
        if (EntityMetadataInspector.isAutoIncrement(field)) {
            columnProperty.append("AUTO_INCREMENT ");
        }
        return columnProperty.toString();
    }

}

