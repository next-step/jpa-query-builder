package orm.dsl.ddl;

import orm.TableEntity;
import orm.dsl.QueryExtractor;
import orm.dsl.QueryRunner;
import orm.dsl.step.ddl.DropTableStep;

public abstract class DropTableImpl<E> implements DropTableStep {

    private final QueryRunner queryRunner;
    protected final TableEntity<E> tableEntity;
    protected boolean ifNotExist = false;

    public DropTableImpl(TableEntity<E> tableEntity, QueryRunner queryRunner) {
        this.queryRunner = queryRunner;
        this.tableEntity = tableEntity;
    }

    @Override
    public QueryExtractor ifNotExist() {
        this.ifNotExist = true;
        return this;
    }

    @Override
    public void execute() {
        queryRunner.execute(extractSql());
    }


    public String renderIfNotExist() {
        return "IF NOT EXISTS";
    }
}
