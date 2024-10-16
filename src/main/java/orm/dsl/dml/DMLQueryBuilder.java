package orm.dsl.dml;

import orm.QueryProvider;
import orm.SQLDialect;
import orm.TableEntity;
import orm.dsl.ImplQueryBuilder;
import orm.dsl.step.dml.InsertIntoStep;
import orm.settings.JpaSettings;

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

    @Override
    public SQLDialect dialect() {
        return this.settings.getDialect();
    }
}
