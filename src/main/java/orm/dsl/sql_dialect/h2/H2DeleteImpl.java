package orm.dsl.sql_dialect.h2;

import orm.TableEntity;
import orm.dsl.QueryExecutor;
import orm.dsl.dml.DeleteImpl;
import orm.dsl.dml.SelectImpl;

public class H2DeleteImpl<E> extends DeleteImpl<E> {
    public H2DeleteImpl(TableEntity<E> tableEntity, QueryExecutor queryExecutor) {
        super(tableEntity, queryExecutor);
    }
}
