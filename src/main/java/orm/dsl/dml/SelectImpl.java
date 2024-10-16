package orm.dsl.dml;

import orm.TableEntity;
import orm.dsl.step.dml.SelectFromStep;

public abstract class SelectImpl<E> implements SelectFromStep {

    private final TableEntity<E> tableEntity;

    public SelectImpl(TableEntity<E> tableEntity) {
        this.tableEntity = tableEntity;
    }
}
