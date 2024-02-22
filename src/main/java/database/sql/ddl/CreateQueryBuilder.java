package database.sql.ddl;

import database.sql.util.EntityClassInspector;
import database.sql.util.type.TypeConverter;

public class CreateQueryBuilder {
    private final String tableName;
    private final String columnsWithDefinition;

    public CreateQueryBuilder(Class<?> entityClass, TypeConverter typeConverter) {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);

        this.tableName = inspector.getTableName();
        this.columnsWithDefinition = String.join(", ", inspector.getColumnDefinitions(typeConverter));
    }

    public String buildQuery() {
        return String.format("CREATE TABLE %s (%s)", tableName, columnsWithDefinition);
    }
}
