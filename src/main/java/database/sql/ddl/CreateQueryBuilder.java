package database.sql.ddl;

import database.sql.util.EntityClassInspector;
import database.sql.util.type.MySQLTypeConverter;
import database.sql.util.type.TypeConverter;

import java.util.stream.Collectors;

public class CreateQueryBuilder {
    private final static TypeConverter mysqlTypeConverter = new MySQLTypeConverter();

    private final Class<?> entityClass;

    public CreateQueryBuilder(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String buildQuery() {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        String tableName = inspector.getTableName();
        String columnsWithDefinition = inspector.getColumns()
                .map(column -> column.toColumnDefinition(mysqlTypeConverter))
                .collect(Collectors.joining(", "));

        return String.format("CREATE TABLE %s (%s)", tableName, columnsWithDefinition);
    }
}
