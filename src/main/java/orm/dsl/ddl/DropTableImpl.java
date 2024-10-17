package orm.dsl.ddl;

import jdbc.JdbcTemplate;
import orm.TableEntity;
import orm.dsl.QueryBuilder;
import orm.dsl.QueryExecutor;
import orm.dsl.step.ddl.DropTableStep;

public abstract class DropTableImpl<E> implements DropTableStep {

    private final QueryExecutor queryExecutor;
    protected final TableEntity<E> tableEntity;
    protected boolean ifNotExist = false;

    public DropTableImpl(TableEntity<E> tableEntity, QueryExecutor queryExecutor) {
        this.queryExecutor = queryExecutor;
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
