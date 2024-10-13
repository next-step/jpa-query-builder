package orm.dsl.ddl;

import orm.dsl.QueryBuilder;

public interface DDLFirstStep extends QueryBuilder {

    // DDL 구문중
    QueryBuilder ifNotExist();
}
