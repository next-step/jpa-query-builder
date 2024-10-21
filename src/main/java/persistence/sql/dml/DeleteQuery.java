package persistence.sql.dml;

import persistence.sql.ddl.TableName;
import persistence.sql.ddl.ValidateEntity;
import persistence.sql.dml.querybuilder.QueryBuilder;

public class DeleteQuery<T> {

    private final T entity;

    public DeleteQuery(T entity) {
        new ValidateEntity(entity.getClass());
        this.entity = entity;
    }

    public String generateQuery() {
        return new QueryBuilder()
            .delete()
            .from(new TableName(entity.getClass()).getTableName())
            .where("id = " + new EntityId<>(entity).getId())
            .build();
    }

}
