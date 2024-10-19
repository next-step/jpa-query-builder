package orm.dsl.sql_dialect.h2;

import orm.TableEntity;
import orm.dsl.QueryRunner;
import orm.dsl.dml.DeleteImpl;
import orm.dsl.dml.UpdateImpl;

public class H2UpdateImpl<E> extends UpdateImpl<E> {
    public H2UpdateImpl(TableEntity<E> tableEntity, QueryRunner queryRunner) {
        super(tableEntity, queryRunner);
    }
}
