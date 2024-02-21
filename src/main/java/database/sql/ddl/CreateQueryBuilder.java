package database.sql.ddl;

import database.sql.util.EntityClassInspector;
import database.sql.util.type.MySQLTypeConverter;
import database.sql.util.type.TypeConverter;

import java.util.stream.Collectors;

public class CreateQueryBuilder {
    private final String query;

    public CreateQueryBuilder(Class<?> entityClass) {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        TypeConverter mysqlTypeConverter = new MySQLTypeConverter();

        String tableName = inspector.getTableName();
        String columnsWithDefinition = inspector.getColumns()
                .map(column -> column.toColumnDefinition(mysqlTypeConverter))
                .collect(Collectors.joining(", "));
        query = String.format("CREATE TABLE %s (%s)", tableName, columnsWithDefinition);
    }

    public String buildQuery() {
        return query;
    }
}
