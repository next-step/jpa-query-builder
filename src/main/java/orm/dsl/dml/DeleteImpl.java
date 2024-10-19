package orm.dsl.dml;

import orm.TablePrimaryField;
import orm.dsl.QueryRenderer;
import orm.TableEntity;
import orm.dsl.QueryRunner;
import orm.dsl.condition.Condition;
import orm.dsl.condition.EqualCondition;
import orm.dsl.step.dml.ConditionStep;
import orm.dsl.step.dml.DeleteFromStep;
import orm.exception.IdValueRequiredException;
import orm.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class DeleteImpl <E> implements DeleteFromStep {

    private final QueryRunner queryRunner;
    private final TableEntity<E> tableEntity;
    private final List<Condition> deleteConditions;

    public DeleteImpl(TableEntity<E> tableEntity, QueryRunner queryRunner) {
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
        queryBuilder.append("DELETE FROM ");
        queryBuilder.append(tableEntity.getTableName());

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
            throw new IdValueRequiredException("Id value is required for delete operation");
        }
    }

    @Override
    public void execute() {
        queryRunner.execute(extractSql());
    }
}
