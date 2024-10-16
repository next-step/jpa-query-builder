package orm.dsl;

import orm.SQLDialect;
import orm.TableEntity;
import orm.dsl.step.ddl.CreateTableStep;
import orm.dsl.step.ddl.DropTableStep;
import orm.dsl.dialect.DialectStatementMap;
import orm.dsl.step.dml.InsertIntoStep;

import java.lang.reflect.InvocationTargetException;

public class ImplQueryBuilder {

    private static final DialectStatementMap statementMap = new DialectStatementMap();
    private final SQLDialect dialect;

    public ImplQueryBuilder(SQLDialect dialect) {
        this.dialect = dialect;
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

    private <E, T extends QueryBuilder> T newInstanceOfImpl(TableEntity<E> tableEntity, Class<T> implClass) {
        try {
            return implClass.getDeclaredConstructor(TableEntity.class).newInstance(tableEntity);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


}
