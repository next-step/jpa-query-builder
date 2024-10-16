package orm.dsl.ddl;

import orm.TableEntity;
import orm.dsl.QueryBuilder;
import orm.dsl.step.ddl.DropTableStep;

public abstract class DropTableImpl<E> implements DropTableStep {

    protected final TableEntity<E> tableEntity;
    protected boolean ifNotExist = false;

    public DropTableImpl(TableEntity<E> tableEntity) {
        this.tableEntity = tableEntity;
    }

    @Override
    public QueryBuilder ifNotExist() {
        this.ifNotExist = true;
        return this;
    }

    public String renderIfNotExist() {
        return "IF NOT EXISTS";
    }
}
