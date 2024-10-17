package orm.dsl.dml;

import orm.QueryProvider;
import orm.SQLDialect;
import orm.TableEntity;
import orm.dsl.ImplQueryBuilder;
import orm.dsl.step.dml.DeleteFromStep;
import orm.dsl.step.dml.InsertIntoStep;
import orm.settings.JpaSettings;
import persistence.sql.ddl.Person;

public class DMLQueryBuilder implements QueryProvider {

    private final JpaSettings settings;

    public DMLQueryBuilder() {
        this.settings = JpaSettings.ofDefault();
    }

    public DMLQueryBuilder(JpaSettings settings) {
        this.settings = settings;
    }

    public <E> InsertIntoStep insertInto(Class<E> entityClass) {
        return new ImplQueryBuilder(dialect())
                .buildInsert(new TableEntity<>(entityClass, settings));
    }

    public <E> DeleteFromStep deleteFrom(Class<E> entityClass) {
        return new ImplQueryBuilder(dialect())
                .buildDelete(new TableEntity<>(entityClass, settings));
    }

    @Override
    public SQLDialect dialect() {
        return this.settings.getDialect();
    }
}
