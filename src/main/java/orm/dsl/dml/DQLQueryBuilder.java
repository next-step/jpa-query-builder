package orm.dsl.dml;

import orm.QueryProvider;
import orm.SQLDialect;
import orm.TableEntity;
import orm.dsl.ImplQueryBuilder;
import orm.dsl.step.dml.SelectFromStep;
import orm.settings.JpaSettings;

public class DQLQueryBuilder implements QueryProvider {

    private final JpaSettings settings;

    public DQLQueryBuilder() {
        this.settings = JpaSettings.ofDefault();
    }

    public DQLQueryBuilder(JpaSettings settings) {
        this.settings = settings;
    }

    public <E> SelectFromStep selectFrom(Class<E> entityClass) {
        return new ImplQueryBuilder(dialect())
                .buildSelect(new TableEntity<>(entityClass, settings));
    }

    @Override
    public SQLDialect dialect() {
        return this.settings.getDialect();
    }
}
