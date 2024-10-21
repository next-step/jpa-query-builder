package persistence.sql.dml;

import persistence.sql.ddl.TableName;
import persistence.sql.ddl.ValidateEntity;
import persistence.sql.dml.querybuilder.QueryBuilder;

public class FindAllQuery {

    private final Class<?> entityClass;

    public FindAllQuery(Class<?> entityClass) {
        new ValidateEntity(entityClass);
        this.entityClass = entityClass;
    }

    public String generateQuery() {
        return new QueryBuilder()
            .select("*")
            .from(new TableName(entityClass).getTableName())
            .build();
    }
}
