package orm.dsl.ddl;

import orm.TableEntity;
import orm.dsl.QueryBuilder;

public abstract class DropTableImpl<ENTITY> implements DropTableStep {

    protected final TableEntity<ENTITY> tableEntity;
    protected boolean ifNotExist = false;

    public DropTableImpl(TableEntity<ENTITY> tableEntity) {
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
