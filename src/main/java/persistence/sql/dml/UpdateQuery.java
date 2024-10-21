package persistence.sql.dml;

import java.util.Map;
import java.util.stream.Collectors;
import persistence.sql.ddl.ColumnName;
import persistence.sql.ddl.ColumnType;
import persistence.sql.ddl.EntityTableMetadata;
import persistence.sql.dml.querybuilder.QueryBuilder;

public class UpdateQuery<T> {

    private final T entity;
    private final EntityTableMetadata entityTableMetadata;

    public UpdateQuery(T entity) {
        this.entity = entity;
        this.entityTableMetadata = new EntityTableMetadata(entity.getClass());
    }

    public String generateQuery() throws IllegalAccessException {
        String tableName = entityTableMetadata.getTableName();
        String columns = getColumns(entityTableMetadata.getColumnDefinitions());
        String values = new ValueClause<>(entity).getClause();
        Long id = new EntityId<T>(entity).getId();

        return generateUpdateQuery(tableName, columns, values, id);
    }

    private String generateUpdateQuery(
        String tableName,
        String columns,
        String values,
        Long id
    ) {
        return new QueryBuilder()
            .update(tableName)
            .columns(columns)
            .values(values)
            .where("id = " + id)
            .build();
    }

    private String getColumns(Map<ColumnName, ColumnType> columnDefinitions) {
        return columnDefinitions.keySet().stream()
            .map(ColumnName::getColumnName)
            .filter(columnName -> !columnName.equalsIgnoreCase("id"))
            .collect(Collectors.joining(", "));
    }

}
