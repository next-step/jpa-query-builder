package database.sql.ddl;

import database.sql.IQueryBuilder;
import database.sql.util.EntityClassInspector;

public class DropQueryBuilder implements IQueryBuilder {
    private final Class<?> entityClass;

    public DropQueryBuilder(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public String buildQuery() {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        String tableName = inspector.getTableName();

        return String.format("DROP TABLE %s", tableName);
    }
}
