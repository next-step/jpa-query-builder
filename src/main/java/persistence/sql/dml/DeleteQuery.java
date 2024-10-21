package persistence.sql.dml;

import persistence.sql.ddl.TableName;
import persistence.sql.ddl.ValidateEntity;

public class DeleteQuery<T> {

    private final T entity;

    public DeleteQuery(T entity) {
        new ValidateEntity(entity.getClass());
        this.entity = entity;
    }

    public String generateQuery() {
        return new SimpleQueryBuilder()
            .delete()
            .from(new TableName(entity.getClass()).getTableName())
            .where("id = " + new EntityId<>(entity).getId())
            .build();
    }

}
