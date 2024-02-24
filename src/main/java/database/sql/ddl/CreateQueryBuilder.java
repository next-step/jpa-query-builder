package database.sql.ddl;

import database.sql.util.EntityMetadata;
import database.sql.util.type.TypeConverter;

public class CreateQueryBuilder {
    private final String tableName;
    private final String columnsWithDefinition;

    public CreateQueryBuilder(Class<?> entityClass, TypeConverter typeConverter) {
        EntityMetadata metadata = new EntityMetadata(entityClass);

        this.tableName = metadata.getTableName();
        this.columnsWithDefinition = String.join(", ", metadata.getColumnDefinitions(typeConverter));
    }

    public String buildQuery() {
        return String.format("CREATE TABLE %s (%s)", tableName, columnsWithDefinition);
    }
}
