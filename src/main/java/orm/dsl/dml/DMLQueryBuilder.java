package orm.dsl.dml;

import orm.QueryProvider;
import orm.SQLDialect;
import orm.TableEntity;
import orm.dsl.ImplQueryBuilder;
import orm.dsl.step.ddl.CreateTableStep;
import orm.dsl.step.dml.InsertIntoStep;
import orm.settings.JpaSettings;
import orm.settings.SnakeForPropertyNamingStrategy;

public class DMLQueryBuilder implements QueryProvider {

    private final JpaSettings settings;

    public DMLQueryBuilder() {
        this.settings = JpaSettings.ofDefault();
    }

    public DMLQueryBuilder(JpaSettings settings) {
        this.settings = settings;
    }

    public <ENTITY> InsertIntoStep insertInto(Class<ENTITY> entityClass) {
//        return new ImplQueryBuilder(dialect())
//                .buildCreateTable(new TableEntity<>(entityClass, settings));
        return null;
    }

    @Override
    public SQLDialect dialect() {
        return this.settings.getDialect();
    }
}
