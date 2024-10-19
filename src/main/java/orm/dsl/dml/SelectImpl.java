package orm.dsl.dml;

import jdbc.RowMapper;
import orm.dsl.QueryRenderer;
import orm.TableEntity;
import orm.dsl.QueryRunner;
import orm.dsl.condition.Condition;
import orm.dsl.condition.EqualCondition;
import orm.dsl.step.dml.ConditionForFetchStep;
import orm.dsl.step.dml.SelectFromStep;
import orm.exception.NotYetImplementedException;
import orm.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class SelectImpl<E> implements SelectFromStep<E>{

    private final QueryRunner queryRunner;
    private final TableEntity<E> tableEntity;
    private final List<Condition> selectConditions;

    public SelectImpl(TableEntity<E> tableEntity, QueryRunner queryRunner) {
        this.tableEntity = tableEntity;
        this.queryRunner = queryRunner;
        this.selectConditions = new ArrayList<>();
    }

    @Override
    public ConditionForFetchStep<E> where(Condition condition) {
        selectConditions.add(condition);
        return this;
    }

    @Override
    public ConditionForFetchStep<E> where(Condition... conditions) {
        selectConditions.addAll(List.of(conditions));
        return this;
    }

    @Override
    public String extractSql() {
        QueryRenderer queryRenderer = new QueryRenderer();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT ");
        queryBuilder.append(queryRenderer.joinColumnNamesWithComma(tableEntity.getAllFields()));
        queryBuilder.append(" FROM ");
        queryBuilder.append(tableEntity.getTableName());

        if (CollectionUtils.isNotEmpty(selectConditions)) {
            queryBuilder.append(queryRenderer.renderWhere(selectConditions));
        }

        return queryBuilder.toString();
    }

    @Override
    public <T> List<T> fetch(RowMapper<T> rowMapper) {
        return queryRunner.fetch(extractSql(), rowMapper);
    }

    @Override
    public <T> T fetchOne(RowMapper<T> rowMapper) {
        return queryRunner.fetchOne(extractSql(), rowMapper);
    }

    // 모든 검색조건을 날려 findAll로 만듬
    @Override
    public ConditionForFetchStep<E> findAll() {
        this.selectConditions.clear();
        return this;
    }

    @Override
    public ConditionForFetchStep<E> findById(Object id) {
        this.selectConditions.clear();
        this.selectConditions.add(new EqualCondition(tableEntity.getId().getFieldName(), id));
        return this;
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
