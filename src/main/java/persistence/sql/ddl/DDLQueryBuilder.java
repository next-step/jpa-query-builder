package persistence.sql.ddl;

import persistence.sql.TableMeta;


public abstract class DDLQueryBuilder implements QueryBuilder {

    TableMeta tableMeta;

    public DDLQueryBuilder(Class<?> entityClass) {
        this.tableMeta = new TableMeta(entityClass);
    }
}
