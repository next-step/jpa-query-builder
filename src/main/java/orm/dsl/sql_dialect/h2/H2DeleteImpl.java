package orm.dsl.sql_dialect.h2;

import orm.TableEntity;
import orm.dsl.QueryRunner;
import orm.dsl.dml.DeleteImpl;

public class H2DeleteImpl<E> extends DeleteImpl<E> {
    public H2DeleteImpl(TableEntity<E> tableEntity, QueryRunner queryRunner) {
        super(tableEntity, queryRunner);
    }
}
