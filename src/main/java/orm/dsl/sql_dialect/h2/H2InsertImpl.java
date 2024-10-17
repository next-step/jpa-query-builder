package orm.dsl.sql_dialect.h2;

import orm.TableEntity;
import orm.dsl.QueryExecutor;
import orm.dsl.dml.InsertImpl;

public class H2InsertImpl<E> extends InsertImpl<E> {

    public H2InsertImpl(TableEntity<E> tableEntity, QueryExecutor queryExecutor) {
        super(tableEntity, queryExecutor);
    }
}
