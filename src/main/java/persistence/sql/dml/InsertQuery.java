package persistence.sql.dml;

import java.util.Map;
import java.util.stream.Collectors;
import persistence.sql.ddl.ColumnName;
import persistence.sql.ddl.ColumnType;
import persistence.sql.ddl.EntityTableMetadata;
import persistence.sql.ddl.ValidateEntity;
import persistence.sql.dml.querybuilder.QueryBuilder;

public class InsertQuery<T> {

    private final T entity;
    private final EntityTableMetadata entityTableMetadata;

    public InsertQuery(T entity) {
        new ValidateEntity(entity.getClass());
        this.entity = entity;
        this.entityTableMetadata = new EntityTableMetadata(entity.getClass());
    }

    public String generateQuery() throws IllegalAccessException {
        String tableName = entityTableMetadata.getTableName();
        String columns = getColumns(entityTableMetadata.getColumnDefinitions());
        String values = new ValueClause<>(entity).getClause();

        return generateInsertQuery(tableName, columns, values);
    }

    private String generateInsertQuery(String tableName, String columns, String values) {
        return new QueryBuilder()
            .insertInto(tableName)
            .columns(columns)
            .values(values)
            .build();
    }

    private String getColumns(Map<ColumnName, ColumnType> columnDefinitions) {
        return columnDefinitions.keySet().stream()
            .map(ColumnName::getColumnName)
            .filter(columnName -> !columnName.equalsIgnoreCase("id"))
            .collect(Collectors.joining(", "));
    }

}
