package persistence.sql.dml;

import persistence.sql.ddl.TableName;
import persistence.sql.ddl.ValidateEntity;

public class InsertQuery<T> {

    private final Class<?> entityClass;
    private final T entity;

    public InsertQuery(Class<?> entityClass, T entity) {
        new ValidateEntity(entityClass);
        this.entityClass = entityClass;
        this.entity = entity;
    }

    public String generateQuery() throws IllegalAccessException {
        String tableName = new TableName(entityClass).getTableName();
        String columns = new ColumnClause<>(entity).getClause();
        String values = new ValueClause<>(entity).getClause();

        return generateInsertQuery(tableName, columns, values);
    }

    private String generateInsertQuery(String tableName, String columns, String values) {
        return new SimpleQueryBuilder()
            .insertInto(tableName)
            .columns(columns)
            .values(values)
            .build();
    }

}
