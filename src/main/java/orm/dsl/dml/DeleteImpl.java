package orm.dsl.dml;

import orm.TableEntity;
import orm.TablePrimaryField;
import orm.dsl.QueryRenderer;
import orm.dsl.QueryRunner;
import orm.dsl.condition.Condition;
import orm.dsl.condition.Conditions;
import orm.dsl.condition.EqualCondition;
import orm.dsl.step.dml.ConditionStep;
import orm.dsl.step.dml.DeleteFromStep;
import orm.exception.IdValueRequiredException;

import java.util.List;

public abstract class DeleteImpl<E> implements DeleteFromStep {

    private final QueryRunner queryRunner;
    private final TableEntity<E> tableEntity;
    private final Conditions conditions;

    public DeleteImpl(TableEntity<E> tableEntity, QueryRunner queryRunner) {
        this.tableEntity = tableEntity;
        this.queryRunner = queryRunner;
        this.conditions = new Conditions();
    }

    @Override
    public ConditionStep where(Condition condition) {
        conditions.add(condition);
        return this;
    }

    @Override
    public ConditionStep where(Condition... conditionsVarArgs) {
        conditions.addAll(List.of(conditionsVarArgs));
        return this;
    }

    @Override
    public String extractSql() {
        StringBuilder queryBuilder = new StringBuilder();
        QueryRenderer queryRenderer = new QueryRenderer();
        queryBuilder.append("DELETE FROM ");
        queryBuilder.append(tableEntity.getTableName());

        if (conditions.hasCondition()) {
            queryBuilder.append(queryRenderer.renderWhere(conditions));
        }

        return queryBuilder.toString();
    }

    @Override
    public ConditionStep byId() {
        final TablePrimaryField id = tableEntity.getId();
        throwIfNoId(id);
        this.conditions.clear();
        this.conditions.add(new EqualCondition(id.getFieldName(), id.getFieldValue()));
        return this;
    }

    private void throwIfNoId(TablePrimaryField id) {
        if (id.getFieldValue() == null) {
            throw new IdValueRequiredException("Id value is required for delete operation");
        }
    }

    @Override
    public void execute() {
        queryRunner.execute(extractSql());
    }
}
