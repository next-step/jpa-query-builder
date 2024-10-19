package orm.dsl.dml;

import orm.TableEntity;
import orm.TablePrimaryField;
import orm.dsl.QueryRenderer;
import orm.dsl.QueryRunner;
import orm.dsl.condition.Condition;
import orm.dsl.condition.EqualCondition;
import orm.dsl.step.dml.ConditionStep;
import orm.dsl.step.dml.DeleteFromStep;
import orm.dsl.step.dml.UpdateStep;
import orm.exception.IdValueRequiredException;
import orm.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class UpdateImpl<E> implements UpdateStep<E> {

    private final QueryRunner queryRunner;
    private final TableEntity<E> tableEntity;
    private final List<Condition> deleteConditions;

    public UpdateImpl(TableEntity<E> tableEntity, QueryRunner queryRunner) {
        this.tableEntity = tableEntity;
        this.queryRunner = queryRunner;
        this.deleteConditions = new ArrayList<>();
    }

    @Override
    public ConditionStep where(Condition condition) {
        deleteConditions.add(condition);
        return this;
    }

    @Override
    public ConditionStep where(Condition... conditions) {
        deleteConditions.addAll(List.of(conditions));
        return this;
    }

    @Override
    public String extractSql() {
        QueryRenderer queryRenderer = new QueryRenderer();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("UPDATE ");
        queryBuilder.append(tableEntity.getTableName());
        queryBuilder.append(" SET ");
        queryBuilder.append(queryRenderer.joinColumnAndValuePairWithComma(tableEntity.getNonIdFields()));

        if(CollectionUtils.isNotEmpty(deleteConditions)) {
            queryBuilder.append(queryRenderer.renderWhere(deleteConditions));
        }

        return queryBuilder.toString();
    }

    @Override
    public ConditionStep byId() {
        final TablePrimaryField id = tableEntity.getId();
        throwIfNoId(id);
        this.deleteConditions.clear();
        this.deleteConditions.add(new EqualCondition(id.getFieldName(), id.getFieldValue()));
        return this;
    }

    private void throwIfNoId(TablePrimaryField id) {
        if(id.getFieldValue() == null) {
            throw new IdValueRequiredException("Id value is required to update entity");
        }
    }

    @Override
    public void execute() {
        queryRunner.execute(extractSql());
    }
}
