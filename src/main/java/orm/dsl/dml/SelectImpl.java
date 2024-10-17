package orm.dsl.dml;

import jdbc.RowMapper;
import orm.QueryRenderer;
import orm.TableEntity;
import orm.dsl.QueryExecutor;
import orm.dsl.condition.Condition;
import orm.dsl.step.dml.ConditionStep;
import orm.dsl.step.dml.SelectFromStep;
import orm.exception.NotYetImplementedException;
import orm.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class SelectImpl<E> implements SelectFromStep<E>{

    private final QueryExecutor queryExecutor;
    private final TableEntity<E> tableEntity;
    private final List<Condition> selectConditions;

    public SelectImpl(TableEntity<E> tableEntity, QueryExecutor queryExecutor) {
        this.tableEntity = tableEntity;
        this.queryExecutor = queryExecutor;
        this.selectConditions = new ArrayList<>();
    }

    @Override
    public ConditionStep where(Condition condition) {
        selectConditions.add(condition);
        return this;
    }

    @Override
    public ConditionStep where(Condition... conditions) {
        selectConditions.addAll(List.of(conditions));
        return this;
    }

    @Override
    public String build() {
        QueryRenderer queryRenderer = new QueryRenderer();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT ");
        queryBuilder.append(queryRenderer.singleColumnListDotted(tableEntity.getAllFields()));
        queryBuilder.append(" FROM ");
        queryBuilder.append(tableEntity.getTableName());

        if (CollectionUtils.isNotEmpty(selectConditions)) {
            queryBuilder.append(queryRenderer.renderWhere(selectConditions));
        }

        return queryBuilder.toString();
    }

    @Override
    public <T> E fetch(RowMapper<T> rowMapper) {
        return null;
    }

    @Override
    public <T> List<E> fetchOne(RowMapper<T> rowMapper) {
        return List.of();
    }

    @Override
    public E fetchOne() {
        throw new NotYetImplementedException("아직 구현 안되었습니다. 2-2에 진행");
//        return queryExecutor.execute(build(), tableEntity.getTableClass());
    }

    @Override
    public List<E> fetch() {
        throw new NotYetImplementedException("아직 구현 안되었습니다. 2-2에 진행");
//        return List.of();
    }
}
