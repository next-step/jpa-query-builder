package orm.dsl.step.ddl;

import orm.dsl.QueryBuilder;

public interface DDLTableIfNotExistStep extends QueryBuilder {
    QueryBuilder ifNotExist();
}
