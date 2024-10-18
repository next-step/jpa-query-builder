package orm.dsl;

import orm.SQLDialect;
import orm.TableEntity;
import orm.dsl.sql_dialect.DialectStatementMap;
import orm.dsl.step.ddl.CreateTableStep;
import orm.dsl.step.ddl.DropTableStep;
import orm.dsl.step.dml.DeleteFromStep;
import orm.dsl.step.dml.InsertIntoStep;
import orm.dsl.step.dml.SelectFromStep;

import java.lang.reflect.InvocationTargetException;

public class ImplQueryBuilder {

    private static final DialectStatementMap statementMap = new DialectStatementMap();
    private final SQLDialect dialect;
    private final QueryRunner queryRunner;

    public ImplQueryBuilder(SQLDialect dialect) {
        this.dialect = dialect;
        this.queryRunner = new QueryRunner();
    }

    public ImplQueryBuilder(SQLDialect dialect, QueryRunner queryRunner) {
        this.dialect = dialect;
        this.queryRunner = queryRunner;
    }

    public <E> CreateTableStep buildCreateTable(TableEntity<E> tableEntity) {
        return newInstanceOfImpl(tableEntity, statementMap.getCreateTableImpl(dialect));
    }

    public <E> DropTableStep buildDropTable(TableEntity<E> tableEntity) {
        return newInstanceOfImpl(tableEntity, statementMap.getDropTableImpl(dialect));
    }

    public <E> InsertIntoStep buildInsert(TableEntity<E> tableEntity) {
        return newInstanceOfImpl(tableEntity, statementMap.getInsertIntoImpl(dialect));
    }

    public <E> SelectFromStep<E> buildSelect(TableEntity<E> tableEntity) {
        return newInstanceOfImpl(tableEntity, statementMap.getSelectImpl(dialect));
    }

    public <E> DeleteFromStep buildDelete(TableEntity<E> tableEntity) {
        return newInstanceOfImpl(tableEntity, statementMap.getDeleteIntoImpl(dialect));
    }

    private <E, T extends QueryBuilder> T newInstanceOfImpl(TableEntity<E> tableEntity, Class<T> implClass) {
        try {
            return implClass.getDeclaredConstructor(TableEntity.class, QueryRunner.class).newInstance(tableEntity, queryRunner);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
