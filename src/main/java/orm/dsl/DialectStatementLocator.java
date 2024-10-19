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

public class DialectStatementLocator {

    private static final DialectStatementMap statementMap = new DialectStatementMap();
    private final SQLDialect dialect;
    private final QueryRunner queryRunner;

    public DialectStatementLocator(SQLDialect dialect) {
        this.dialect = dialect;
        this.queryRunner = new QueryRunner();
    }

    public DialectStatementLocator(SQLDialect dialect, QueryRunner queryRunner) {
        this.dialect = dialect;
        this.queryRunner = queryRunner;
    }

    public <E> CreateTableStep createTable(TableEntity<E> tableEntity) {
        return newInstanceOfImpl(tableEntity, statementMap.getCreateTableImpl(dialect));
    }

    public <E> DropTableStep dropTable(TableEntity<E> tableEntity) {
        return newInstanceOfImpl(tableEntity, statementMap.getDropTableImpl(dialect));
    }

    public <E> InsertIntoStep insert(TableEntity<E> tableEntity) {
        return newInstanceOfImpl(tableEntity, statementMap.getInsertIntoImpl(dialect));
    }

    public <E> SelectFromStep<E> selectFrom(TableEntity<E> tableEntity) {
        return newInstanceOfImpl(tableEntity, statementMap.getSelectImpl(dialect));
    }

    public <E> DeleteFromStep deleteFrom(TableEntity<E> tableEntity) {
        return newInstanceOfImpl(tableEntity, statementMap.getDeleteIntoImpl(dialect));
    }

    private <E, T extends QueryExtractor> T newInstanceOfImpl(TableEntity<E> tableEntity, Class<T> implClass) {
        try {
            return implClass.getDeclaredConstructor(TableEntity.class, QueryRunner.class).newInstance(tableEntity, queryRunner);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
