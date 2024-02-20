package database.sql.dml;

import database.sql.util.EntityClassInspector;
import database.sql.util.EntityColumn;

import java.util.stream.Collectors;

public class SelectQueryBuilder {
    private final Class<?> entityClass;

    public SelectQueryBuilder(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String buildQuery() {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        String tableName = inspector.getTableName();
        String fieldsForSelecting = inspector.getVisibleColumns()
                .map(EntityColumn::getColumnName)
                .collect(Collectors.joining(", "));
        return "SELECT " + fieldsForSelecting + " FROM " + tableName;
    }
}
