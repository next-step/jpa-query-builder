package persistence.sql.dml;

import persistence.sql.ddl.TableName;
import persistence.sql.ddl.ValidateEntity;

public class FindByIdQuery<ID> {

    private final Class<?> entityClass;
    private final ID id;

    public FindByIdQuery(Class<?> entityClass, ID id) {
        new ValidateEntity(entityClass);
        this.entityClass = entityClass;
        this.id = id;
    }

    public String generateQuery() {
        return new SimpleQueryBuilder()
            .select("*")
            .from(new TableName(entityClass).getTableName())
            .where("id = " + id)
            .build();
    }
}
