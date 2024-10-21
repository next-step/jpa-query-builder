package persistence.sql.dml;

import persistence.sql.ddl.TableName;
import persistence.sql.ddl.ValidateEntity;
import persistence.sql.dml.querybuilder.QueryBuilder;

public class DeleteAllQuery {

    private final Class<?> entityClass;

    public DeleteAllQuery(Class<?> entityClass) {
        new ValidateEntity(entityClass);
        this.entityClass = entityClass;
    }

    public String generateQuery() {
       return new QueryBuilder()
            .delete()
            .from(new TableName(entityClass).getTableName())
            .build();
    }

}
