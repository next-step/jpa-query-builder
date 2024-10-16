package orm.dsl.dml;

import orm.QueryRenderer;
import orm.TableEntity;
import orm.dsl.QueryBuilder;
import orm.dsl.condition.Condition;
import orm.dsl.step.dml.SelectFromStep;

public abstract class SelectImpl<E> implements SelectFromStep {

    private final TableEntity<E> tableEntity;

    public SelectImpl(TableEntity<E> tableEntity) {
        this.tableEntity = tableEntity;
    }

    @Override
    public QueryBuilder where(Condition condition) {
        return null;
    }

    @Override
    public QueryBuilder where(Condition... conditions) {
        return null;
    }

    @Override
    public String build() {
        QueryRenderer queryRenderer = new QueryRenderer();
        return "SELECT (%s) FROM %s".formatted(
                queryRenderer.singleColumnListDotted(tableEntity.getAllFields()),
                tableEntity.getTableName()
        );
    }
}
