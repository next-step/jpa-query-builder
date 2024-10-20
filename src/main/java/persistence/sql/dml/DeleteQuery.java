package persistence.sql.dml;

import persistence.sql.ddl.TableName;
import persistence.sql.ddl.ValidateEntity;

public class DeleteQuery<T> {

    private final Class<?> entityClass;
    private final T entity;

    public DeleteQuery(Class<?> entityClass, T entity) {
        new ValidateEntity(entityClass);
        this.entityClass = entityClass;
        this.entity = entity;
    }

    public String generateQuery() {
        return new SimpleQueryBuilder()
            .delete()
            .from(new TableName(entityClass).getTableName())
            .where("id = " + new EntityId<>(entity).getId())
            .build();
    }

}
