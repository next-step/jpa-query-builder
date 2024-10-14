package orm.dsl.ddl;

import orm.dsl.QueryBuilder;

public interface DDLTableIfNotExistStep extends QueryBuilder {

    QueryBuilder ifNotExist();
}
