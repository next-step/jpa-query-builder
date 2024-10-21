package persistence.sql.dml;

import persistence.sql.ddl.TableName;
import persistence.sql.ddl.ValidateEntity;
import persistence.sql.dml.querybuilder.QueryBuilder;

public class DeleteByIdQuery<ID> {

    private final Class<?> entityClass;
    private final ID id;

    public DeleteByIdQuery(Class<?> entityClass, ID id) {
        new ValidateEntity(entityClass);
        this.entityClass = entityClass;
        this.id = id;
    }

    public String generateQuery() {
        return new QueryBuilder()
            .delete()
            .from(new TableName(entityClass).getTableName())
            .where("id = " + id)
            .build();
    }

}
